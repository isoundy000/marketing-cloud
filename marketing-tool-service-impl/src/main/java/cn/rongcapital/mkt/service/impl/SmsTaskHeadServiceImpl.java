package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTaskAppEnum;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsTaskHeadService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.ColumnsOut;
import cn.rongcapital.mkt.vo.sms.out.SmsTaskSendStatusVo;

@Service
public class SmsTaskHeadServiceImpl implements SmsTaskHeadService {

	private final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;
	@Autowired
	private MQTopicService mqTopicService;

	private final String TASK_TOTAL_COVER_NUM = "总覆盖";

	private final String TASK_SENDING_SUCCESS_NUM = "已成功";

	private final String TASK_WAITING_NUM = "等待中";

	private final String TASK_SENDING_FAIL_NUM = "已失败";

	// 等待处理
	private final int SMS_DETAIL_WAITING = 0;
	// 发送成功
	private final int SMS_DETAIL_SEND_SUCCESS = 1;
	// 发送失败
	private final int SMS_DETAIL_SEND_FAILURE = 2;

	@Override
	public BaseOutput smsTaskHeadList(String userId, Integer index, Integer size, String smsTaskAppType,
			String smsTaskStatus, String smsTaskName) throws Exception {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
		if (StringUtils.isNotEmpty(smsTaskAppType)) {
			smsTaskHeadTemp.setSmsTaskAppType(Integer.parseInt(smsTaskAppType));
		}
		if (StringUtils.isNotEmpty(smsTaskStatus)) {
			smsTaskHeadTemp.setSmsTaskStatus(Integer.parseInt(smsTaskStatus));
		}
		if (StringUtils.isNotEmpty(smsTaskName)) {
			smsTaskHeadTemp.setSmsTaskName(smsTaskName);
		}
		smsTaskHeadTemp.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
		smsTaskHeadTemp.setOrderField("create_time");
		smsTaskHeadTemp.setOrderFieldType("DESC");
		smsTaskHeadTemp.setStartIndex((index - 1) * size);
		smsTaskHeadTemp.setPageSize(size);

		int totalCount = smsTaskHeadDao.selectListCount(smsTaskHeadTemp);
		output.setTotalCount(totalCount);
		if (totalCount > 0) {
			List<SmsTaskHead> dataList = smsTaskHeadDao.selectList(smsTaskHeadTemp);
			dataList = this.getSmsTaskHeads(dataList);
			this.setBaseOut(output, dataList);
		}
		List<Object> colNames = this.getColumnsOutList();
		output.setColNames(colNames);
		return output;
	}

	private List<SmsTaskHead> getSmsTaskHeads(List<SmsTaskHead> dataList) {
		if (CollectionUtils.isNotEmpty(dataList)) {
			/*
			 * SmsTaskStatusEnum smsTaskStatusEnum =
			 * SmsTaskStatusEnum.TASK_EXECUTING; Integer status =
			 * smsTaskStatusEnum.getStatusCode();
			 */ for (Iterator<SmsTaskHead> iter = dataList.iterator(); iter.hasNext();) {
				SmsTaskHead smsTaskHead = iter.next();
				if (smsTaskHead != null) {
					Integer smsTaskStatus = smsTaskHead.getSmsTaskStatus();
					smsTaskHead.setSmsTaskStatusStr(SmsTaskStatusEnum.getDescriptionByStatus(smsTaskStatus));
					Integer smsTaskAppType = smsTaskHead.getSmsTaskAppType();
					smsTaskHead.setSmsTaskAppTypeStr(
							SmsTaskAppEnum.getDescriptionByStatus(NumUtil.int2OneByte(smsTaskAppType)));
					smsTaskHead
							.setCreateTimeStr(DateUtil.getStringFromDate(smsTaskHead.getCreateTime(), FORMAT_STRING));
					/**
					 * 统计执行中的总数
					 */
					/*
					 * if(smsTaskStatus.byteValue()==status){
					 * this.setSmsTaskHeadNumsById(smsTaskHead); }
					 */
					this.setSmsTaskHeadPers(smsTaskHead);
				}
			}
		}
		return dataList;
	}

	private void setSmsTaskHeadPers(SmsTaskHead smsTaskHead) {
		Integer totalCoverNum = smsTaskHead.getTotalCoverNum();
		Integer sendingFailNumPer = 0;
		Integer sendingSuccessNumPer = 0;
		Integer waitingNumPer = 0;
		if (totalCoverNum > 0) {
			Integer sendingFailNum = smsTaskHead.getSendingFailNum();
			Integer sendingSuccessNum = smsTaskHead.getSendingSuccessNum();
			Integer waitingNum = smsTaskHead.getWaitingNum();
			sendingFailNumPer = sendingFailNum * 100 / totalCoverNum;
			sendingSuccessNumPer = sendingSuccessNum * 100 / totalCoverNum;
			waitingNumPer = waitingNum * 100 / totalCoverNum;
		}
		smsTaskHead.setSendingFailNumPer(sendingFailNumPer);
		smsTaskHead.setSendingSuccessNumPer(sendingSuccessNumPer);
		smsTaskHead.setWaitingNumPer(waitingNumPer);
	}

	private void setSmsTaskHeadNumsById(SmsTaskHead smsTaskHead) {
		List<SmsTaskSendStatusVo> smsTaskStatusCountMapList = smsTaskHeadDao.countStatusById(smsTaskHead.getId());
		if (smsTaskStatusCountMapList != null & smsTaskStatusCountMapList.size() > 0) {
			long waitingNum = 0;
			long sendingSuccessNum = 0;
			long sendingFailNum = 0;
			for (Iterator<SmsTaskSendStatusVo> iter = smsTaskStatusCountMapList.iterator(); iter.hasNext();) {
				SmsTaskSendStatusVo smsTaskSendStatusVo = iter.next();
				if (smsTaskSendStatusVo != null) {
					Integer smsTaskSendStatus = (Integer) smsTaskSendStatusVo.getSmsTaskSendStatus();
					Long count = (Long) smsTaskSendStatusVo.getCount();
					if (smsTaskSendStatus != null) {
						switch (smsTaskSendStatus) {
						case SMS_DETAIL_WAITING: {
							// 等待处理
							waitingNum = count;
						}
						case SMS_DETAIL_SEND_SUCCESS: {
							// 发送成功
							sendingSuccessNum = count;
						}
						case SMS_DETAIL_SEND_FAILURE: {
							// 发送失败
							sendingFailNum = count;
						}
						}
					}
				}
			}
			long totalCoverNum = waitingNum + sendingSuccessNum + sendingFailNum;
			smsTaskHead.setWaitingNum(Integer.parseInt(String.valueOf(waitingNum)));
			smsTaskHead.setSendingSuccessNum(Integer.parseInt(String.valueOf(sendingSuccessNum)));
			smsTaskHead.setSendingFailNum(Integer.parseInt(String.valueOf(sendingFailNum)));
			smsTaskHead.setTotalCoverNum(Integer.parseInt(String.valueOf(totalCoverNum)));
		}
	}

	private List<Object> getColumnsOutList() {
		List<Object> columnsOutList = new ArrayList<Object>();
		// 总覆盖
		ColumnsOut columnsOut0 = new ColumnsOut();
		columnsOut0.setColCode("totalCoverNum");
		columnsOut0.setColName(this.TASK_TOTAL_COVER_NUM);
		columnsOutList.add(columnsOut0);
		// 已成功
		ColumnsOut columnsOut1 = new ColumnsOut();
		columnsOut1.setColCode("sendingSuccessNum");
		columnsOut1.setColName(this.TASK_SENDING_SUCCESS_NUM);
		columnsOutList.add(columnsOut1);
		// 等待中
		ColumnsOut columnsOut2 = new ColumnsOut();
		columnsOut2.setColCode("waitingNum");
		columnsOut2.setColName(this.TASK_WAITING_NUM);
		columnsOutList.add(columnsOut2);
		// 已失败
		ColumnsOut columnsOut3 = new ColumnsOut();
		columnsOut3.setColCode("sendingFailNum");
		columnsOut3.setColName(this.TASK_SENDING_FAIL_NUM);
		columnsOutList.add(columnsOut3);
		return columnsOutList;
	}

	private BaseOutput newSuccessBaseOutput() {
		return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
	}

	private <O> void setBaseOut(BaseOutput out, List<O> dataList) {
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}
		out.setTotal(dataList.size());
		out.getData().addAll(dataList);
	}

	@Override
	public BaseOutput smsTaskHeadPublish(String userId, Integer id) throws Exception {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
		smsTaskHeadTemp.setId(Long.parseLong(String.valueOf(id)));
		List<SmsTaskHead> smsTaskHeadList = smsTaskHeadDao.selectList(smsTaskHeadTemp);
		if (CollectionUtils.isNotEmpty(smsTaskHeadList)) {
			SmsTaskHead smsTaskHeadBack = smsTaskHeadList.get(0);
			if (smsTaskHeadBack != null) {
				/**
				 * 只有未启动、暂停、已预约才可以发布任务
				 */
				if (smsTaskHeadBack.getSmsTaskStatus().equals(SmsTaskStatusEnum.TASK_UNSTART.getStatusCode())
						|| smsTaskHeadBack.getSmsTaskStatus().equals(SmsTaskStatusEnum.TASK_PAUSE.getStatusCode())
						|| smsTaskHeadBack.getSmsTaskStatus()
								.equals(SmsTaskStatusEnum.TASK_RESERVATION.getStatusCode())) {
					smsTaskHeadBack.setSmsTaskStatus(2);
					smsTaskHeadDao.updateById(smsTaskHeadBack);
					Integer audienceGenerateStatus = smsTaskHeadBack.getAudienceGenerateStatus();
					if (audienceGenerateStatus != null && audienceGenerateStatus != 1
							&& smsTaskHeadBack.getTotalCoverNum() > 0) {
						mqTopicService.sendSmsByTaskId(String.valueOf(id));
					}
				}
			}
		}
		return output;
	}

	@Override
	public BaseOutput getSmsTaskHeadById(Integer id) throws Exception {
		BaseOutput output = this.newSuccessBaseOutput();
		SmsTaskHead smsTaskHeadTemp = new SmsTaskHead();
		smsTaskHeadTemp.setId(Long.parseLong(String.valueOf(id)));
		List<SmsTaskHead> smsTaskHeadList = smsTaskHeadDao.selectList(smsTaskHeadTemp);
		if (CollectionUtils.isNotEmpty(smsTaskHeadList)) {
			this.setBaseOut(output, smsTaskHeadList);
		}
		return output;
	}

	@Override
	public int getTaskStatusCount(SmsTaskBody smsTaskBody) {
		return smsTaskHeadDao.selectTaskStatusCount(smsTaskBody);
	}

}
