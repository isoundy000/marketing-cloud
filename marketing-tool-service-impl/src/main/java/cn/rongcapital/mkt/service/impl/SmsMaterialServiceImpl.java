package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponStatusUpdateVO;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialMaterielIn;
import cn.rongcapital.mkt.vo.in.SmsMaterialVariableIn;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialDeleteIn;
import cn.rongcapital.mkt.vo.sms.in.SmsMaterialIn;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Service
public class SmsMaterialServiceImpl implements SmsMaterialService {

    private final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    private final Integer MODIFY_TYPE_INSERT = 0;
    private final Integer MODIFY_TYPE_UPDATE = 1;
    private final int COUPON_STATUS_EXPIRED = 1;

    @Autowired
    private SmsMaterialDao smsMaterialDao;

    @Autowired
    private SmsTaskHeadDao smsTaskHeadDao;

    @Autowired
    private SmsTempletDao smsTempletDao;

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Autowired
    private SmsMaterialMaterielMapDao smsMaterialMaterielMapDao;

    @Autowired
    private SmsMaterialVariableMapDao smsMaterialVariableMapDao;

    @Autowired
    private MaterialCouponStatusUpdateService materialCouponStatusUpdateService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput insertOrUpdateSmsMaterial(@NotEmpty SmsMaterialIn smsMaterialIn) {
        BaseOutput output = getNewSuccessBaseOutput();
        int couponStatus = 0;
        SmsMaterial smsMaterial = getSmsMaterial(smsMaterialIn);
        if (smsMaterial.getId() != null) {
            if (smsMaterialValidate(smsMaterial.getId())) {
                couponStatus = modifySmsMaterialComponentAndVariable(smsMaterialIn, MODIFY_TYPE_UPDATE);
                if(couponStatus == COUPON_STATUS_EXPIRED){
                    output.setMsg("优惠券已过期,请重新选择优惠券");
                    output.setCode(ApiErrorCode.SYSTEM_ERROR.getCode());
                    return output;
                }
                smsMaterialDao.updateById(smsMaterial);
            } else {
                output.setCode(ApiErrorCode.SMS_ERROR_MATERIAL_CAN_NOT_UPDATE.getCode());
                output.setMsg(ApiErrorCode.SMS_ERROR_MATERIAL_CAN_NOT_UPDATE.getMsg());
            }
        } else {
            smsMaterialDao.insert(smsMaterial);
            smsMaterialIn.setId(smsMaterial.getId());
            couponStatus = modifySmsMaterialComponentAndVariable(smsMaterialIn, MODIFY_TYPE_INSERT);
        }
        output.getData().add(smsMaterial);
        output.setDate(DateUtil.getStringFromDate(new Date(), FORMAT_STRING));
        return output;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private int modifySmsMaterialComponentAndVariable(@NotEmpty SmsMaterialIn smsMaterialIn, @NotNull Integer modifyType) {
        //0如果是修改,则删除以前的物料和变量数据
        if (modifyType.equals(MODIFY_TYPE_UPDATE)) {
            //Todo:需要先把优惠券选出来,然后把原始的优惠券给释放掉
            SmsMaterialMaterielMap paramSmsMaterialMaterielMap = new SmsMaterialMaterielMap();
            paramSmsMaterialMaterielMap.setSmsMaterialId(Long.valueOf(smsMaterialIn.getId()));
            paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<SmsMaterialMaterielMap> oldSmsMaterialMaterielMapList = smsMaterialMaterielMapDao.selectList(paramSmsMaterialMaterielMap);
            if(CollectionUtils.isNotEmpty(oldSmsMaterialMaterielMapList)){
                for(SmsMaterialMaterielMap smsMaterialMaterielMap : oldSmsMaterialMaterielMapList){
                    MaterialCoupon couple4Search = new MaterialCoupon();
                    couple4Search.setId(smsMaterialMaterielMap.getSmsMaterielId());
                    couple4Search.setStatus((byte) 0);
                    List<MaterialCoupon> poList = materialCouponDao.selectList(couple4Search);
                    if (poList.size() == 0) {
                        return COUPON_STATUS_EXPIRED;
                    }
                    MaterialCoupon po = poList.get(0);
                    if (po.getEndTime() != null && po.getEndTime().compareTo(new Date()) < 0) {
                        return COUPON_STATUS_EXPIRED;
                    }

                    MaterialCouponStatusUpdateVO paramMaterialCouponStatusUpdateVO = new MaterialCouponStatusUpdateVO();
                    paramMaterialCouponStatusUpdateVO.setId(smsMaterialMaterielMap.getSmsMaterielId());
                    paramMaterialCouponStatusUpdateVO.setStatus(MaterialCouponStatusEnum.UNUSED.getCode());
                    materialCouponStatusUpdateService.updateMaterialCouponStatus(paramMaterialCouponStatusUpdateVO);
                }
            }

            //删除掉了优惠券的关联信息
            paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            smsMaterialMaterielMapDao.deleteBySmsMaterialId(paramSmsMaterialMaterielMap);

            //删除掉了变量的关联信息
            SmsMaterialVariableMap paramSmsMaterialVariableMap = new SmsMaterialVariableMap();
            paramSmsMaterialVariableMap.setSmsMaterialId(Long.valueOf(smsMaterialIn.getId()));
            paramSmsMaterialVariableMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            smsMaterialVariableMapDao.deleteBySmsMaterialId(paramSmsMaterialVariableMap);
        }
        //1修改SmsMaterialComponent表
        List<SmsMaterialMaterielIn> smsMaterialMaterielInList = smsMaterialIn.getSmsMaterialMaterielInList();
        if (!org.springframework.util.CollectionUtils.isEmpty(smsMaterialMaterielInList)) {
            for (SmsMaterialMaterielIn smsMaterialMaterielIn : smsMaterialMaterielInList) {
                SmsMaterialMaterielMap insertSmsMaterialMaterielMap = new SmsMaterialMaterielMap();
                insertSmsMaterialMaterielMap.setSmsMaterialId(Long.valueOf(smsMaterialIn.getId()));
                insertSmsMaterialMaterielMap.setSmsMaterielId(smsMaterialMaterielIn.getMaterielId());
                insertSmsMaterialMaterielMap.setSmsMaterielType(smsMaterialMaterielIn.getMaterielType());
                insertSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
                smsMaterialMaterielMapDao.insert(insertSmsMaterialMaterielMap);

                //Todo:更新优惠券的状态信息
                MaterialCouponStatusUpdateVO paramMaterialCouponStatusUpdateVO = new MaterialCouponStatusUpdateVO();
                paramMaterialCouponStatusUpdateVO.setId(smsMaterialMaterielIn.getMaterielId());
                paramMaterialCouponStatusUpdateVO.setStatus(MaterialCouponStatusEnum.USED.getCode());
                materialCouponStatusUpdateService.updateMaterialCouponStatus(paramMaterialCouponStatusUpdateVO);
            }
        }

        //2修改SmsMaterialVariable表
        List<SmsMaterialVariableIn> smsMaterialVariableInList = smsMaterialIn.getSmsMaterialVariableInList();
        if (!org.springframework.util.CollectionUtils.isEmpty(smsMaterialVariableInList)) {
            for (SmsMaterialVariableIn smsMaterialVariableIn : smsMaterialVariableInList) {
                SmsMaterialVariableMap insertSmsMaterialVariableMap = new SmsMaterialVariableMap();
                insertSmsMaterialVariableMap.setSmsMaterialId(Long.valueOf(smsMaterialIn.getId()));
                insertSmsMaterialVariableMap.setSmsVariableName(smsMaterialVariableIn.getVariableName());
                insertSmsMaterialVariableMap.setSmsVariableValue(smsMaterialVariableIn.getVariableValue());
                insertSmsMaterialVariableMap.setSmsVariableType(smsMaterialVariableIn.getVariableType());
                smsMaterialVariableMapDao.insert(insertSmsMaterialVariableMap);
            }
        }

        return 0;
    }

    @Override
    public BaseOutput deleteSmsMaterial(@NotEmpty SmsMaterialDeleteIn smsMaterialDeleteIn) {
        BaseOutput output = getNewSuccessBaseOutput();
        if (smsMaterialDeleteIn.getId() != null) {
            if (smsMaterialValidate(smsMaterialDeleteIn.getId())) {
                SmsMaterial smsMaterial = new SmsMaterial();
                smsMaterial.setId(smsMaterialDeleteIn.getId());
                smsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                smsMaterialDao.updateById(smsMaterial);

                //1.干掉物料表
                SmsMaterialMaterielMap paramSmsMaterialMaterielMap = new SmsMaterialMaterielMap();
                paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
                paramSmsMaterialMaterielMap.setSmsMaterialId(Long.valueOf(smsMaterialDeleteIn.getId()));

                //Todo:这里要进行优惠券状态的回改.
                List<SmsMaterialMaterielMap> oldSmsMaterialMaterielMapList = smsMaterialMaterielMapDao.selectList(paramSmsMaterialMaterielMap);
                if(CollectionUtils.isNotEmpty(oldSmsMaterialMaterielMapList)){
                    for(SmsMaterialMaterielMap smsMaterialMaterielMap : oldSmsMaterialMaterielMapList){
                        MaterialCouponStatusUpdateVO paramMaterialCouponStatusUpdateVO = new MaterialCouponStatusUpdateVO();
                        paramMaterialCouponStatusUpdateVO.setId(smsMaterialMaterielMap.getSmsMaterielId());
                        paramMaterialCouponStatusUpdateVO.setStatus(MaterialCouponStatusEnum.UNUSED.getCode());
                        materialCouponStatusUpdateService.updateMaterialCouponStatus(paramMaterialCouponStatusUpdateVO);
                    }
                }

                paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                smsMaterialMaterielMapDao.deleteBySmsMaterialId(paramSmsMaterialMaterielMap);
                //2.干掉变量表
                SmsMaterialVariableMap paramSmsMaterialVariableMap = new SmsMaterialVariableMap();
                paramSmsMaterialVariableMap.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                paramSmsMaterialVariableMap.setSmsMaterialId(Long.valueOf(smsMaterialDeleteIn.getId()));
                smsMaterialVariableMapDao.deleteBySmsMaterialId(paramSmsMaterialVariableMap);
            } else {
                output.setCode(ApiErrorCode.SMS_ERROR_MATERIAL_CAN_NOT_DELETE.getCode());
                output.setMsg(ApiErrorCode.SMS_ERROR_MATERIAL_CAN_NOT_DELETE.getMsg());
            }
        } else {
            output.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
            output.setMsg(ApiErrorCode.PARAMETER_ERROR.getMsg());
        }
        return output;
    }

    public boolean smsMaterialValidate(Integer id) {
        SmsTaskHead paramSmsTaskHead = new SmsTaskHead();
        paramSmsTaskHead.setSmsTaskMaterialId(Long.valueOf(id));
        paramSmsTaskHead.setSmsTaskStatus(SmsTaskStatusEnum.TASK_FINISH.getStatusCode());
        paramSmsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectListByMaterial(paramSmsTaskHead);
        return CollectionUtils.isEmpty(smsTaskHeads);
    }

    /**
     * 根据输入得到素材对象
     *
     * @param smsMaterialIn 素材类型
     * @return SmsMaterial
     */
    private SmsMaterial getSmsMaterial(@NotEmpty SmsMaterialIn smsMaterialIn) {
        SmsMaterial smsMaterial = new SmsMaterial();
        if (smsMaterial.getId() == null) {
            smsMaterial.setCreator(getValidateString(smsMaterialIn.getCreator()));
            smsMaterial.setCreateTime(new Date());
            smsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        }
        smsMaterial.setId(getValidateInteger(smsMaterialIn.getId()));
        smsMaterial.setUpdateUser(getValidateString(smsMaterialIn.getUpdateUser()));
        smsMaterial.setSmsSignId(getValidateInteger(smsMaterialIn.getSmsSignId()));
        smsMaterial.setName(getValidateString(smsMaterialIn.getName()));
        smsMaterial.setSmsTempletContent(getValidateString(smsMaterialIn.getSmsTempletContent()));
        smsMaterial.setSmsSignName(getValidateString(smsMaterialIn.getSmsSignName()));
        smsMaterial.setSmsType(getValidateInteger(smsMaterialIn.getSmsType()).byteValue());
        smsMaterial.setSmsTempletId(getValidateInteger(smsMaterialIn.getSmsTempletId()));
        if (smsMaterialIn.getSmsTempletId() != null && smsMaterialIn.getSmsTempletId() != 0) {
            SmsTemplet paramSmsTemplet = new SmsTemplet();
            paramSmsTemplet.setId(smsMaterialIn.getSmsTempletId());
            paramSmsTemplet.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<SmsTemplet> smsTemplets = smsTempletDao.selectList(paramSmsTemplet);
            if (CollectionUtils.isNotEmpty(smsTemplets)) {
                smsMaterial.setChannelType(smsTemplets.get(0).getChannelType());
            }
        }
        return smsMaterial;
    }

    private String getValidateString(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return value;
    }

    private Integer getValidateInteger(Integer value) {
        if (value == null) {
            return null;
        }
        return value;
    }

    private BaseOutput getNewSuccessBaseOutput() {
        return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
    }

}
