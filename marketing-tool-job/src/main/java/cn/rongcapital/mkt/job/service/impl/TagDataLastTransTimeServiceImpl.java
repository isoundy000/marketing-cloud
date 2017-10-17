package cn.rongcapital.mkt.job.service.impl;

import java.util.Calendar;
import java.util.Date;
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
public class TagDataLastTransTimeServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 购物记录中单个微信用户（公众号标识＋openid）最后一次购买（取订单号的消费时间记最后一次购买时间）的时间");
        logger.info("用户价值－购买价值－最后一次购买－（本月内、三个月内、半年内）");
        //handleData(getByLastTransTime());
        Criteria criteriaAll = Criteria.where("mid").gt(-1);
        List<DataParty> dataPartyList = mongoTemplate.find(new Query(criteriaAll),cn.rongcapital.mkt.po.mongodb.DataParty.class);
        for (DataParty dataParty : dataPartyList) {
			//获取mid
			Integer mid = dataParty.getMid();
			//查询payMent表
			//Float averageIncomeAmount = dataPaymentDao.selectAverageIncomeAmountByKeyid(mid);
			
			Date lastTransTime = dataShoppingDao.selectLastTransTimeByKeyid(mid);
			if(null != lastTransTime){
				// 距离现在多少个月
		        int monthDiff = 0;
		        Calendar now = Calendar.getInstance();
		        Calendar targetTime = Calendar.getInstance();
		        targetTime.setTime(lastTransTime);
		        monthDiff = now.get(Calendar.YEAR) * 12 + now.get(Calendar.MONTH) - targetTime.get(Calendar.YEAR) * 12
		                        - targetTime.get(Calendar.MONTH);
		        
				Update update = new Update().set("lastShoppingTime", monthDiff);
				updateMongodbTag(mongoTemplate,mid, update);
			}
		  
		}
    }

    // 已经将包含时间的数据对象取出, 交给mongoDB处理
    private List<ShoppingWechat> getByLastTransTime() {
        return dataShoppingDao.selectListByLastTransTimeandWeChatInfo();
    }

    // 看看BaseTagData的代码
    @Override
    public void tagData(ShoppingWechat shoppingWechat) {
        Criteria criteria = Criteria.where("mid").is(shoppingWechat.getDataPartyId());
        // 距离现在多少个月
        int monthDiff = 0;
        Calendar now = Calendar.getInstance();
        Calendar targetTime = Calendar.getInstance();
        targetTime.setTime(shoppingWechat.getLastShoppingTime());
        monthDiff = now.get(Calendar.YEAR) * 12 + now.get(Calendar.MONTH) - targetTime.get(Calendar.YEAR) * 12
                        - targetTime.get(Calendar.MONTH);
        Update update = new Update().set("lastShoppingTime", monthDiff);
        // 这里的lastshoppingtime改为目标月份与当前月份差几个月.
        mongoTemplate.findAndModify(new Query(criteria), update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }

}
