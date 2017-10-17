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

import cn.rongcapital.mkt.common.enums.TaskTagEnum;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;

/*************************************************
 * @功能及特点的描述简述: 购物记录中单个微信用户（公众号标识＋openid）最后一次购买（取订单号的消费时间记最后一次购买时间）的时间 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：Marketing Cloud系统
 * @author: 丛树林
 * @version: v1.1 @date(创建、开发日期)：2016-08-01 最后修改日期：2016-08-01 @复审人：
 *************************************************/
@Service
public class ConsumptionLastTimeTagImpl implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataPaymentDao dataPaymentDao;

	@Autowired
	private DataPartyDao dataPartyDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * @功能简述 定时任务程序入口
	 * @param：taskId 定时任务ID
	 * 
	 */
	@Override
	public void task(Integer taskId) {
		logger.debug("ConsumptionLastTimeTagTask-----start");
		// 单个微信用户（公众号标识＋openid）最后一次购买（取订单号的消费时间记最后一次购买时间）的时间
		DataPayment dataPayment = new DataPayment();
		String payStatus = TaskTagEnum.getDescriptionByCode(0);
		dataPayment.setPayStatus(payStatus);
		List<DataPayment> dataPaymentList = dataPaymentDao.selectList(dataPayment);

		// 2通过Mobile将dataParty的ID查询出来
//		for (DataPayment dPayment : dataPaymentList) {
//			DataParty dataParty = new DataParty();
//			dataParty.setMobile(dPayment.getMobile());
//			Integer id = dataPartyDao.selectIdByMappingId(new Long(dPayment.getMobile()));
//			if (id != null) {
//				Query query = Query.query(Criteria.where("mid").is(id));
//				Update update = new Update().set("payLastTime", dPayment.getUpdateTime());
//				cn.rongcapital.mkt.po.mongodb.DataParty dp = mongoTemplate.findAndModify(query, update,
//						cn.rongcapital.mkt.po.mongodb.DataParty.class);
//				if (dp != null) {
//					logger.debug("ConsumptionLastTimeTagTask-----DataPay-----id:{}-----Pay Last Time:{}", dp.getId(),
//							dp.getUpdateTime());
//				} else {
//					logger.error("ConsumptionLastTimeTagTask-----DataPayment-----id:{}-----未找到主数据", id);
//				}
//			}
//		}
		logger.debug("ConsumptionLastTimeTagTask-----end!");
	}

}
