package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataShopping;
import cn.rongcapital.mkt.po.OrderCount;


/**
 * Created by Yunfeng on 2016-7-29.
 */
@Service
public class TestSystemTagImpl implements TaskService{

    //Todo:1将总的购物次数(按pubId和openId分类)统计出来,并且统计出mobile(即MappingID)
    //Todo:2通过Mobile将dataParty的ID查询出来
    //Todo:3通过dataPartyId将统计出的数据插入到MongoDB中

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void task(Integer taskId) {
        //1将总的购物次数(按pubId和openId分类)统计出来,并且统计出mobile(即MappingID)
        DataShopping dataShopping = new DataShopping();
        dataShopping.setStatus(2);
        List<OrderCount> orderCountList = dataShoppingDao.selectListByWxData(dataShopping);

        //2通过Mobile将dataParty的ID查询出来
        for(OrderCount orderCount : orderCountList){
            DataParty dataParty = new DataParty();
            dataParty.setMobile(orderCount.getMobile());
            List<DataParty> dataPartieList = dataPartyDao.selectList(dataParty);
            if(!CollectionUtils.isEmpty(dataPartieList)){
                for(DataParty mobileDataParty : dataPartieList){
                    orderCount.setDataPartyId(mobileDataParty.getId());
                }
            }
        }

        //3通过dataPartyId将统计出的数据插入到MongoDB中
        //3.1根据datapartyId选出Mongo里面的Dataparty数据
        for(OrderCount orderCount : orderCountList){
            Query query = Query.query(Criteria.where("mid").is(orderCount.getDataPartyId()));
            Update update = new Update().set("totalCount", orderCount.getOrderCount());
            mongoTemplate.findAndModify(query,update,cn.rongcapital.mkt.po.mongodb.DataParty.class);
        }
    }
}
