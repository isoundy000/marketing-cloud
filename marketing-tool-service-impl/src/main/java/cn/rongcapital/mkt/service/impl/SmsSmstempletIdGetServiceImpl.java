package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.common.enums.SmsTempleteAuditStatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dao.SmsTempletMaterialMapDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.po.SmsTempletMaterialMap;
import cn.rongcapital.mkt.service.SmsSmstempletIdGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SmsSmstempletIdGetOut;
import cn.rongcapital.mkt.vo.out.SmsSmstempletMaterialData;

@Service
public class SmsSmstempletIdGetServiceImpl implements SmsSmstempletIdGetService {

    @Autowired
    private SmsTempletDao smsTempletDao;
    
    @Autowired
    private SmsMaterialDao smsMaterialDao;

    @Autowired
    private SmsTempletMaterialMapDao smsTempletMaterialMapDao;
    
    /**
     * 短信模板id查询模板
     * 
     * 接口：mkt.sms.smstemplet.id.get
     * 
     * @param id
     * @return
     * @Date 2016-11-11
     * @author shuiyangyang
     */
    @Override
    public BaseOutput getSmsSmstempletById(Integer id) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        SmsTemplet smsTempletSelect = new SmsTemplet();
        smsTempletSelect.setId(id);
        List<SmsTemplet> smsTempletList = smsTempletDao.selectList(smsTempletSelect);

        if (smsTempletList != null && smsTempletList.size() > 0) {
            // 如果返回多个则只显示第一个
            SmsTemplet smsTempletresult = smsTempletList.get(0);

            SmsSmstempletIdGetOut smsSmstempletIdGetOut = new SmsSmstempletIdGetOut(
                            smsTempletresult.getId(), smsTempletresult.getChannelType(),
                            smsTempletresult.getType(), smsTempletresult.getAuditStatus(),
                            smsTempletresult.getName(), smsTempletresult.getAuditReason(), null,
                            smsTempletresult.getContent(), true,
                            deleteCheck(smsTempletresult.getId()));
            smsSmstempletIdGetOut.setAuditTime(DateUtil.getStringFromDate(
                            smsTempletresult.getAuditTime(), "yyyy-MM-dd HH:mm:ss"));
            
            if(smsTempletresult.getType().intValue() == SmsTempletTypeEnum.VARIABLE.getStatusCode()){
            	
                List<SmsSmstempletMaterialData> materialList = getMaterialData(id);
                
                smsSmstempletIdGetOut.setMaterialList(materialList);
            }
            
            result.getData().add(smsSmstempletIdGetOut);
            result.setTotal(ApiConstant.INT_ONE);
        }
        return result;
    }
    /**
     * 检查模板是否被素材用到
     * 用到不能删除 false
     * 没有用到可以删除true
     * @param smsTempletId
     * @return
     * @Date 2016-11-11
     * @author shuiyangyang
     */
    private Boolean deleteCheck(Integer smsTempletId) {
        // 查询素材中是否用到
        SmsMaterial smsMaterialSerach = new SmsMaterial();
        smsMaterialSerach.setSmsTempletId(smsTempletId);
        smsMaterialSerach.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<SmsMaterial> smsMaterialLists = smsMaterialDao.selectList(smsMaterialSerach);
        if (smsMaterialLists != null && smsMaterialLists.size() > 0) {
            return false;
        }
        return true;
    }

    
    private List<SmsSmstempletMaterialData> getMaterialData(Integer id){
    	
        List<SmsSmstempletMaterialData> materialList = new ArrayList<SmsSmstempletMaterialData>();
        
        SmsTempletMaterialMap smsTempletMaterialMap = new SmsTempletMaterialMap();
        
        smsTempletMaterialMap.setSmsTempletId(Long.valueOf(id));
        smsTempletMaterialMap.setStatus((byte) 0);
        smsTempletMaterialMap.setStartIndex(null);
        smsTempletMaterialMap.setPageSize(null);
        
        List<SmsTempletMaterialMap> smsTempletMaterialMapList = smsTempletMaterialMapDao.selectList(smsTempletMaterialMap);
        
        SmsSmstempletMaterialData smsSmstempletMaterialData;
        
        for(SmsTempletMaterialMap smsTempletMaterial : smsTempletMaterialMapList){
        	smsSmstempletMaterialData = new SmsSmstempletMaterialData();
        	
			BeanUtils.copyProperties(smsTempletMaterial, smsSmstempletMaterialData);
        	
			materialList.add(smsSmstempletMaterialData);
        }
        
        return materialList;
    }
}
