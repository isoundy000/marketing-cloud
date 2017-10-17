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
public class TagDataTotalShoppingCountServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 购物记录中单个微信用户（公众号标识＋openid）购买的总次数（订单号） ");
        logger.info("用户价值－购买价值－总购买频次－（1次、2次、3次、4次以上），末级标签需替换之前的高、中、低");
//        handleData(getByTotalShoppingCount());
        Criteria criteriaAll = Criteria.where("mid").gt(-1);
        List<DataParty> dataPartyList = mongoTemplate.find(new Query(criteriaAll),cn.rongcapital.mkt.po.mongodb.DataParty.class);
        for (DataParty dataParty : dataPartyList) {
			//获取mid
			Integer mid = dataParty.getMid();
			//获取购买总次数
			Integer totalShoppingCount = dataShoppingDao.selectTotalShoppingCountByKeyid(mid);
			Update update = new Update().set("totalShoppingCount", totalShoppingCount);
			updateMongodbTag(mongoTemplate,mid, update);
		}
    }

    // 已经将shopping总数获取到, 交给mongoDB处理
    public List<ShoppingWechat> getByTotalShoppingCount() {
        return dataShoppingDao.selectTotalShoppingCountByWeChatInfo();
    }

    @Override
    public void tagData(ShoppingWechat shoppingWechat) {
        Criteria criteria = Criteria.where("mid").is(shoppingWechat.getDataPartyId());
        Update update = new Update().set("totalShoppingCount", shoppingWechat.getTotalShoppingCount());
        mongoTemplate.findAndModify(new Query(criteria),update,cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }

}
