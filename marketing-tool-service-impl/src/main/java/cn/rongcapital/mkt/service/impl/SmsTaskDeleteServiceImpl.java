/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.SmsTaskDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsTaskDeleteIn;


@Service
public class SmsTaskDeleteServiceImpl implements SmsTaskDeleteService {
    
    @Autowired
    SmsTaskHeadDao smsTaskHeadDao;

    @Autowired
    SmsTaskBodyDao smsTaskBodyDao;

    /**
     * 接口：mkt.sms.task.delete 根据任务id逻辑删除记录
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016.10.19
     */
    @Override
    public BaseOutput smsTaskDelete(SmsTaskDeleteIn body, SecurityContext securityContext) {


        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        Long id = body.getId();

        SmsTaskHead smsTaskHead = new SmsTaskHead();
        smsTaskHead.setId(id);

        List<SmsTaskHead> smsTaskHeadLists = smsTaskHeadDao.selectList(smsTaskHead);



        // 判断数据是否存在
        if (smsTaskHeadLists == null || smsTaskHeadLists.size() <= 0) {
            result.setCode(ReturnCode.DATA_NOT_EXIST.getCode());
            result.setMsg(ReturnCode.DATA_NOT_EXIST.getMsg());
            return result;
        }

        // 判断数据是否已经删除
        if (smsTaskHeadLists.get(0).getStatus() == ApiConstant.TABLE_DATA_STATUS_INVALID) {
            result.setCode(ReturnCode.DATA_IS_DEL.getCode());
            result.setMsg(ReturnCode.DATA_IS_DEL.getMsg());
            return result;
        }

        // 判断任务是否执行中
        if (smsTaskHeadLists.get(0).getSmsTaskStatus() == SmsTaskStatusEnum.TASK_EXECUTING.getStatusCode()) {
            result.setCode(ReturnCode.TASK_DOING.getCode());
            result.setMsg(ReturnCode.TASK_DOING.getMsg());
            return result;
        }

        smsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
        // 逻辑删除，并返回逻辑删除的数量
        smsTaskHeadDao.updateById(smsTaskHead);


        // 继续逻辑删除smsTaskBody中的数据
        SmsTaskBody smsTaskBody = new SmsTaskBody();
        smsTaskBody.setSmsTaskHeadId(id);
        smsTaskBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsTaskBody> smsTaskBodyLists = smsTaskBodyDao.selectList(smsTaskBody);
        if (smsTaskBodyLists != null && smsTaskBodyLists.size() > 0) {
            for (SmsTaskBody smsTaskBodyList : smsTaskBodyLists) {
                smsTaskBodyList.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                smsTaskBodyDao.updateById(smsTaskBodyList);
            }
        }

        return result;
    }

    // 自定义返回状态
    private enum ReturnCode {
        SUCCESS(0, "SUCCESS"), 
        TASK_DOING(1, "任务执行中 不能删除"), 
        DATA_IS_DEL(2, "重复删除"),
        DATA_NOT_EXIST(3, "数据不存在");

        private int code;

        private String msg;

        private ReturnCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }
    }
}
