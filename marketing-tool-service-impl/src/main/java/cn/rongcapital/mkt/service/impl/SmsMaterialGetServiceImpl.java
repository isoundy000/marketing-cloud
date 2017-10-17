package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsMaterialVariableTypeEnum;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.service.SmsMaterialGetService;
import cn.rongcapital.mkt.service.SmsMaterialService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsMaterialCountOut;
import cn.rongcapital.mkt.vo.out.SmsMaterialMaterielOut;
import cn.rongcapital.mkt.vo.out.SmsMaterialOut;
import cn.rongcapital.mkt.vo.out.SmsMaterialVariableOut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

import static cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum.FIXED;

/**
 * Created by byf on 11/9/16.
 */
@Service
public class SmsMaterialGetServiceImpl implements SmsMaterialGetService{

    @Autowired
    private SmsMaterialDao smsMaterialDao;

    @Autowired
    private SmsTempletDao smsTempletDao;

    @Autowired
    private SmsMaterialMaterielMapDao smsMaterialMaterielMapDao;

    @Autowired
    private SmsMaterialVariableMapDao smsMaterialVariableMapDao;

    @Autowired
    private SmsMaterialService smsMaterialService;

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Override
    public BaseOutput getSmsMaterialById(Long id) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setId(id.intValue());
        paramSmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterial> smsMaterialList = smsMaterialDao.selectList(paramSmsMaterial);

        if(CollectionUtils.isEmpty(smsMaterialList)){
            baseOutput.setCode(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode());
            baseOutput.setMsg(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg());
            return baseOutput;
        }

        SmsMaterial rs = smsMaterialList.get(0);

        SmsMaterialMaterielMap paramSmsMaterialMaterielMap = new SmsMaterialMaterielMap();
        paramSmsMaterialMaterielMap.setSmsMaterialId(id);
        paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterialMaterielMap> smsMaterialMaterielMapList = smsMaterialMaterielMapDao.selectList(paramSmsMaterialMaterielMap);

        SmsMaterialVariableMap paramSmsMaterialVariableMap = new SmsMaterialVariableMap();
        paramSmsMaterialVariableMap.setSmsMaterialId(id);
        paramSmsMaterialVariableMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterialVariableMap> smsMaterialVariableMapList = smsMaterialVariableMapDao.selectList(paramSmsMaterialVariableMap);

        SmsMaterialOut smsMaterialOut = getSmsMaterialOut(rs, null, smsMaterialMaterielMapList, smsMaterialVariableMapList);

        baseOutput.getData().add(smsMaterialOut);
        baseOutput.setTotal(baseOutput.getData().size());
        return baseOutput;
    }

    @Override
    public BaseOutput getSmsMaterialListByKeyword(String searchWord, Integer channelType, Integer smsType, Integer index, Integer size) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);

        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setName(searchWord);
        paramSmsMaterial.setChannelType(channelType == null? null : channelType.byteValue());
        paramSmsMaterial.setSmsType(smsType == -1? null : smsType.byteValue());
        paramSmsMaterial.setStartIndex((index - 1) * size);
        paramSmsMaterial.setPageSize(size);
        List<SmsMaterial> smsMaterialList = smsMaterialDao.selectListByKeyword(paramSmsMaterial);
        int totalCount = smsMaterialDao.selectListByKeywordCount(paramSmsMaterial);

        if(CollectionUtils.isEmpty(smsMaterialList)) return baseOutput;

        for(SmsMaterial smsMaterial : smsMaterialList){
            SmsTemplet paramSmsTemplet = new SmsTemplet();
            paramSmsTemplet.setId(smsMaterial.getSmsTempletId());
            paramSmsTemplet.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<SmsTemplet> smsTempletList = smsTempletDao.selectList(paramSmsTemplet);
            String templateName = CollectionUtils.isEmpty(smsTempletList)?"":smsTempletList.get(0).getName();

            SmsMaterialMaterielMap paramSmsMaterialMaterielMap = new SmsMaterialMaterielMap();
            paramSmsMaterialMaterielMap.setSmsMaterialId(smsMaterial.getId().longValue());
            paramSmsMaterialMaterielMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<SmsMaterialMaterielMap> smsMaterialMaterielMapList = smsMaterialMaterielMapDao.selectList(paramSmsMaterialMaterielMap);

            SmsMaterialOut smsMaterialOut = getSmsMaterialOut(smsMaterial,templateName, smsMaterialMaterielMapList, null);
            if(!CollectionUtils.isEmpty(smsMaterialOut.getSmsMaterialMaterielOutList())){
                smsMaterialOut.setMaterielStockTotal(smsMaterialOut.getSmsMaterialMaterielOutList().get(0).getMaterielStockTotal());
                smsMaterialOut.setSmsMaterialMaterielOutList(null);
            }
            if(smsMaterialOut.getSmsType() == FIXED.getStatusCode()){   //Todo:说明:这句代码代表如果是固定短信,则优惠码总数设置为-1(下期重构一下这块)
                smsMaterialOut.setMaterielStockTotal(-1);
            }
            baseOutput.getData().add(smsMaterialOut);
        }

        baseOutput.setTotal(baseOutput.getData().size());
        baseOutput.setTotalCount(totalCount);
        return baseOutput;
    }

    @Override
    public BaseOutput getSmsMaterialCount(Integer channelType) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);

        SmsMaterialCountOut smsMaterialCountOut = new SmsMaterialCountOut();
        SmsMaterial paramSmsMaterial = new SmsMaterial();
        paramSmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        paramSmsMaterial.setChannelType(channelType == -1?null:channelType.byteValue());
        paramSmsMaterial.setSmsType(FIXED.getStatusCode().byteValue());
        Integer fixedCount = smsMaterialDao.selectListCount(paramSmsMaterial);
        smsMaterialCountOut.setSmsType(FIXED.getStatusCode());
        smsMaterialCountOut.setSmsCount(fixedCount);
        baseOutput.getData().add(smsMaterialCountOut);

        paramSmsMaterial.setSmsType(SmsTempletTypeEnum.VARIABLE.getStatusCode().byteValue());
        Integer variableCount = smsMaterialDao.selectListCount(paramSmsMaterial);
        smsMaterialCountOut = new SmsMaterialCountOut();
        smsMaterialCountOut.setSmsType(SmsTempletTypeEnum.VARIABLE.getStatusCode());
        smsMaterialCountOut.setSmsCount(variableCount);
        baseOutput.getData().add(smsMaterialCountOut);

        return baseOutput;
    }

    private SmsMaterialOut getSmsMaterialOut(SmsMaterial rs, String smsTemplateName, List<SmsMaterialMaterielMap> smsMaterialMaterielMapList, List<SmsMaterialVariableMap> smsMaterialVariableMapList) {
        SmsMaterialOut smsMaterialOut = new SmsMaterialOut();
        smsMaterialOut.setId(rs.getId() == null?null:Long.valueOf(rs.getId()));
        smsMaterialOut.setMaterialId(rs.getCode());
        smsMaterialOut.setMaterialName(rs.getName());
        smsMaterialOut.setSmsType(rs.getSmsType()==null?null:Integer.valueOf(rs.getSmsType()));
        smsMaterialOut.setSmsSignId(rs.getSmsSignId());
        smsMaterialOut.setSmsSignContent(rs.getSmsSignName());
        smsMaterialOut.setSmsTemplateId(rs.getSmsTempletId());
        smsMaterialOut.setSmsTemplateContent(rs.getSmsTempletContent());
        smsMaterialOut.setSmsTemplateName(smsTemplateName);
        smsMaterialOut.setCreateTime(DateUtil.getStringFromDate(rs.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        smsMaterialOut.setEditStatus(smsMaterialService.smsMaterialValidate(rs.getId())?0:1);
        smsMaterialOut.setDeleteStatus(smsMaterialOut.getEditStatus());
        if(!CollectionUtils.isEmpty(smsMaterialMaterielMapList)){
            List<SmsMaterialMaterielOut> smsMaterialMaterielOutList = new LinkedList<>();
            for(SmsMaterialMaterielMap smsMaterialMaterielMap : smsMaterialMaterielMapList){

                MaterialCoupon paramMaterialCoupon = new MaterialCoupon();
                paramMaterialCoupon.setId(smsMaterialMaterielMap.getSmsMaterielId());
                List<MaterialCoupon> materialCouponList = materialCouponDao.selectList(paramMaterialCoupon);
                if(CollectionUtils.isEmpty(materialCouponList)) continue;

                MaterialCoupon tempMaterialCoupon = materialCouponList.get(0);
                SmsMaterialMaterielOut smsMaterialMaterielOut = new SmsMaterialMaterielOut();
                smsMaterialMaterielOut.setMaterielId(smsMaterialMaterielMap.getId().intValue());
                smsMaterialMaterielOut.setMaterielType(smsMaterialMaterielMap.getSmsMaterielType());
                smsMaterialMaterielOut.setMaterielName(tempMaterialCoupon.getTitle());
                smsMaterialMaterielOut.setMaterielAmount(tempMaterialCoupon.getAmount().doubleValue());
                smsMaterialMaterielOut.setMaterielStockTotal(tempMaterialCoupon.getStockTotal());
                smsMaterialMaterielOutList.add(smsMaterialMaterielOut);
            }
            smsMaterialOut.setSmsMaterialMaterielOutList(smsMaterialMaterielOutList);
        }
        if(!CollectionUtils.isEmpty(smsMaterialVariableMapList)){
            List<SmsMaterialVariableOut> smsMaterialVariableOutList = new LinkedList<>();
            for(SmsMaterialVariableMap smsMaterialVariableMap : smsMaterialVariableMapList){
                SmsMaterialVariableOut smsMaterialVariableOut = new SmsMaterialVariableOut();
                smsMaterialVariableOut.setVariableName(smsMaterialVariableMap.getSmsVariableName());
                smsMaterialVariableOut.setVariableType(SmsMaterialVariableTypeEnum.getTypeValueByTypeCode(smsMaterialVariableMap.getSmsVariableType()));
                smsMaterialVariableOut.setVariableValue(smsMaterialVariableMap.getSmsVariableValue());
                smsMaterialVariableOutList.add(smsMaterialVariableOut);
            }
            smsMaterialOut.setSmsMaterialVariableOutList(smsMaterialVariableOutList);
        }
        return smsMaterialOut;
    }
}
