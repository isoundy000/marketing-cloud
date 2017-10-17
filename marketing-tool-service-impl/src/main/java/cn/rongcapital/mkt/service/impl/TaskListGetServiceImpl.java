/*************************************************
 * @功能简述: 获取后台任务列表    mkt.task.list.get 
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.service.TaskListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaskListGetServiceImpl implements TaskListGetService {
	@Autowired
	private TaskRunLogDao taskRunLogDao;

	@Override
	public BaseOutput taskListGet() {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		List<Object> taskRunLogList = taskRunLogDao.taskRunLogList();
		
		baseOutput.setData(taskRunLogList);
		baseOutput.setTotal(baseOutput.getData().size());
		
		return baseOutput;
	}
}
