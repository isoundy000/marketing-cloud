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
public class TagDataTotalIncomeAmountServiceImpl extends BaseTagData implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPaymentDao dataPaymentDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void task(Integer taskId) {
        logger.info("tag task : 支付记录中支付状态成功，单个微信用户（公众号标识＋openid）收入金额总额");
        logger.info("用户价值－购买价值－总计交易金额－（50元以下、 51-100元 、101-150元、 151-200 、201-300 、301以上）末级标签需替换之前的5000、5001-20000-20001-50000、50001以上  ");
        //handleData(getByTotalIncomeAmount());
        
        Criteria criteriaAll = Criteria.where("mid").gt(-1);
        List<DataParty> dataPartyList = mongoTemplate.find(new Query(criteriaAll),cn.rongcapital.mkt.po.mongodb.DataParty.class);
        for (DataParty dataParty : dataPartyList) {
			//获取mid
			Integer mid = dataParty.getMid();
			//查询payMent表
			Float totalIncomeAmount = dataPaymentDao.selectTotalIncomeAmountByKeyid(mid);
			Update update = new Update().set("totalIncome", totalIncomeAmount);
			updateMongodbTag(mongoTemplate,mid, update);
		}
    }

    // 已经将shopping总数获取到, 交给mongoDB处理
    public List<ShoppingWechat> getByTotalIncomeAmount() {
        return dataPaymentDao.selectTotalIncomeAmountByWechatInfo();
    }

    @Override
    public void tagData(ShoppingWechat shoppingWechat) {
        Criteria criteria = Criteria.where("mid").is(shoppingWechat.getDataPartyId());
        Update update = new Update().set("totalIncome", shoppingWechat.getTotalIncome().floatValue());
        mongoTemplate.findAndModify(new Query(criteria), update, cn.rongcapital.mkt.po.mongodb.DataParty.class);
    }
}
