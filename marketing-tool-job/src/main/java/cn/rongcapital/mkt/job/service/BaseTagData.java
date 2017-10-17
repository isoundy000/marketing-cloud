package cn.rongcapital.mkt.job.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.ShoppingWechat;

public class BaseTagData {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPartyDao dataPartyDao;
    
    public void handleData(List<ShoppingWechat> shoppingWechats) {
        if (CollectionUtils.isEmpty(shoppingWechats)) {
            return;
        }

        for (ShoppingWechat shoppingWechat : shoppingWechats) {
            DataParty paramDataParty = new DataParty();
            if (!StringUtils.isEmpty(shoppingWechat.getPubId())) {
                paramDataParty.setWxmpId(shoppingWechat.getPubId());
            }

            if (!StringUtils.isEmpty(shoppingWechat.getOpenId())) {
                paramDataParty.setWxCode(shoppingWechat.getOpenId());
            }
            // 这里应该是一条数据
            List<DataParty> dataParties = dataPartyDao.selectList(paramDataParty);
            if (CollectionUtils.isEmpty(dataParties)) {
                // 这其实就是个问题, 根据外键不能查数据 ?
                logger.error("无法通过openId,pubId获取主数据");
                continue;
            } else {
                if (dataParties.size() > 0) {
                    logger.error("通过openId,pubId定位出超过一条的主数据了 : openid={},pubid={}", shoppingWechat.getOpenId(),
                                    shoppingWechat.getPubId());
                    DataParty dataParty = dataParties.get(0);
                    shoppingWechat.setDataPartyId(dataParty.getId());
                    tagData(shoppingWechat);
                }
            }
        }
    }

    protected void tagData(ShoppingWechat data) {
        // do nothing
    }
    
    /**
     * @Title: updateMongodbTag   
     * @Description: 更新Mongodb标签
     * @param: @param mid	主键
     * @param: @param update      
     * @return: void      
     * @throws
     */
    public void updateMongodbTag(MongoTemplate mongoTemplate,Integer mid,Update update){
    	Criteria criteria = Criteria.where("mid").is(mid);
    	mongoTemplate.findAndModify(new Query(criteria), update,cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }
    
    

}
