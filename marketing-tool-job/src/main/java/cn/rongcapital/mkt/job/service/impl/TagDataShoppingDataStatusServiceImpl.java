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
public class TagDataShoppingDataStatusServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Autowired
    private MongoTemplate mongoTemplate;
        
    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 购物记录的订单状态中，交易完成／交易关闭／待支付");
        logger.info("（新增）品牌联系强度－客户流失概率－支付状态－（交易完成／交易关闭／待支付）");
        //handleData(getShoppingDataStatus());
        Criteria criteriaAll = Criteria.where("mid").gt(-1);
        List<DataParty> dataPartyList = mongoTemplate.find(new Query(criteriaAll),cn.rongcapital.mkt.po.mongodb.DataParty.class);
        for (DataParty dataParty : dataPartyList) {
			//获取mid
			Integer mid = dataParty.getMid();
			//获取渠道偏好
			List<ShoppingWechat> shoppingWechatList = dataShoppingDao.selectOrderStatusByKeyid(mid);
			 Update update = new Update();
			 StringBuffer sb = new StringBuffer();
			 for (ShoppingWechat shoppingWechat : shoppingWechatList) {
				 String status = shoppingWechat.getOrderStatus();
				 switch (status){
		            case "交易关闭":
		            	sb.append(status+",");
		            	break;
		            case "交易完成":
		            	sb.append(status+",");
		            	break;
		            case "待支付":
		            	sb.append(status+",");
		                break;
		        }
			}
			 update.set("orderStatus", sb.toString());
		   updateMongodbTag(mongoTemplate,mid, update);
		}
    }

    public List<ShoppingWechat> getShoppingDataStatus() {
        return dataShoppingDao.selectAllDataByWechatInfo();
    }

    @Override
    public void tagData(ShoppingWechat shoppingWechat) {
        Criteria criteria = Criteria.where("mid").is(shoppingWechat.getDataPartyId());
        Update update = new Update().set("orderStatus", shoppingWechat.getOrderStatus());
        mongoTemplate.findAndModify(new Query(criteria), update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }

}
