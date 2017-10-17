
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

import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.job.service.BaseTagData;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.ShoppingWechat;
import cn.rongcapital.mkt.po.mongodb.DataParty;

@Service
public class TagDataIsShoppingUserServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPaymentDao dataPaymentDao;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 公众号下，openid有购买记录的用户标记为是购买用户，其余的标记为不是购买用户");
        logger.info("（新增）品牌联系强度－客户流失概率－是否购买用户－（是／否）");
        Criteria criteriaAll = Criteria.where("mid").gt(-1).and("isShoppingUser").ne(true);
       // Update updateAll = new Update().set("isShoppingUser", false);
        //mongoTemplate.findAndModify(new Query(criteriaAll), updateAll, cn.rongcapital.mkt.po.mongodb.DataParty.class);
      //  handleData(getShoppingUserList());
        
        List<DataParty> notIsshoppingUserList = mongoTemplate.find(new Query(criteriaAll),cn.rongcapital.mkt.po.mongodb.DataParty.class);
        
        for (DataParty dataParty : notIsshoppingUserList) {
        	boolean isShoppingFlag = false;
			//获取mid
			Integer mid = dataParty.getMid();
			//查询payMent表
			Integer status = dataPaymentDao.selectWxCodeIsNullStatus(mid);
			if(status > 0){
				isShoppingFlag = true;
			}
			Update update = new Update().set("isShoppingUser", isShoppingFlag);
			updateMongodbTag(mongoTemplate,mid, update);
		}
    }

    public List<ShoppingWechat> getShoppingUserList() {
        return dataPaymentDao.selectAllDataByWechatInfo();
    }

    @Override
    public void tagData(ShoppingWechat shoppingWechat) {
        Criteria criteria = Criteria.where("mid").is(shoppingWechat.getDataPartyId());
        Update update = new Update().set("isShoppingUser", true);
        mongoTemplate.findAndModify(new Query(criteria), update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }
    

}
