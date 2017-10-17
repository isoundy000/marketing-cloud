package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.BaseTagData;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.ShoppingWechat;
import cn.rongcapital.mkt.po.mongodb.DataParty;

@Service
public class TagDataSingleMonthShoppingCountServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 购物记录中单个微信用户（公众号标识＋openid）单月购买次数（订单号）");
        logger.info("用户价值－购买价值－最后一次购买－（本月内、三个月内、半年内）");
        //handleData(getSingleMonthShoppingCount());
        Criteria criteriaAll = Criteria.where("mid").gt(-1);
        List<DataParty> dataPartyList = mongoTemplate.find(new Query(criteriaAll),cn.rongcapital.mkt.po.mongodb.DataParty.class);
        for (DataParty dataParty : dataPartyList) {
			//获取mid
			Integer mid = dataParty.getMid();
			//获取渠道偏好
			Integer singleMonthShoppingCount = dataShoppingDao.selectSingleMonthShoppingCountByKeyid(mid);
			Update update = new Update().set("singleMonthShoppingCount", singleMonthShoppingCount);
			updateMongodbTag(mongoTemplate,mid, update);
		}
        
    }

    public List<ShoppingWechat> getSingleMonthShoppingCount() {
        return dataShoppingDao.selectSingleMonthShoppingCountByWeChatInfo();
    }

    @Override
    public void tagData(ShoppingWechat shoppingWechat) {
        Criteria criteria = Criteria.where("mid").is(shoppingWechat.getDataPartyId());
        Update update = new Update().set("singleMonthShoppingCount", shoppingWechat.getSingleMonthShoppingCount());
        mongoTemplate.findAndModify(new Query(criteria), update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }

}
