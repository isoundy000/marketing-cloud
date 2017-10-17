/*************************************************
 * @功能简述: 编辑某条主数据详细信息的业务类实现
 * @see MktApi：
 * @author: xuning
 * @version: 1.0 @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.GenderUtils;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.service.DataMainBasicInfoUpdateService;
import cn.rongcapital.mkt.service.MainBasicInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.DataMainBaseInfoUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class DataMainBasicInfoUpdateServiceImpl implements DataMainBasicInfoUpdateService {
    @Autowired
    DataPartyDao dataPartyDao;
    
    @Autowired
    private DataPopulationDao dataPopulationDao;

    @Autowired
    private MainBasicInfoGetService mainBasicInfoGetService;

    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    public BaseOutput updateBaseInfoByContactId(DataMainBaseInfoUpdateIn body) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        
        Integer id = body.getContactId();
        Integer dataType = body.getDataType();
        
        Integer keyId = mainBasicInfoGetService.getKeyIdFromContactInfo(id, dataType, null);
        
		if (keyId != null) {
			result.setCode(ApiErrorCode.BIZ_ERROR_CONTACTINFO_KEYID.getCode());
			result.setMsg("不能获取关联的keyid!");
			return result;
		}

        DataParty po = new DataParty();

        po.setName(body.getName());
        if (body.getGender() == null) {
            po.setGender((byte) 3);
        } else {
            po.setGender(Byte.parseByte(GenderUtils.charToInt(body.getGender()) + ""));
        }
        Date date = DateUtil.getDateFromString(body.getBirthday(), "yyyy-MM-dd");
        po.setBirthday(date);
        po.setProvice(body.getProvice());
        po.setCity(body.getCity());
        po.setJob(body.getJob());
        String monthlyIncome = body.getMonthlyIncome();
        BigDecimal monthIncome = null;
        if (StringUtils.isBlank(monthlyIncome)) {	
        	monthIncome = new BigDecimal("0");
      	} else{
      		monthIncome = new BigDecimal(monthlyIncome);
      	}
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入) 
        monthIncome = monthIncome.setScale(2, BigDecimal.ROUND_HALF_UP);
        po.setMonthlyIncome(monthIncome);
        String monthlyConsume = body.getMonthlyConsume();
        BigDecimal monthConsume = null;
        if (StringUtils.isBlank(monthlyConsume)) {	
        	monthConsume = new BigDecimal("0");
      	} else{
      		monthConsume = new BigDecimal(monthlyConsume);
      	}
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入) 
        monthConsume = monthConsume.setScale(2, BigDecimal.ROUND_HALF_UP);
        po.setMonthlyConsume(monthConsume);
        po.setId(keyId);

        int res = dataPartyDao.updateById(po);
        if (res == 0) {
            result.setCode(ApiErrorCode.DB_ERROR.getCode());
            result.setMsg(ApiErrorCode.DB_ERROR.getMsg());
            return result;
        }
        
//        DataPopulation dataPopulation = new DataPopulation();
//        
//        dataPopulation.setName(body.getName());
//        if (body.getGender() == null) {
//        	dataPopulation.setGender((byte) 3);
//        } else {
//        	dataPopulation.setGender(Byte.parseByte(GenderUtils.charToInt(body.getGender()) + ""));
//        }
//		dataPopulation.setBirthday(date);
//		dataPopulation.setBloodType(body.getBloodType());
//		dataPopulation.setIq(body.getIq());
//		dataPopulation.setCitizenship(body.getCitizenship());
//		dataPopulation.setProvice(body.getProvice());
//		dataPopulation.setCity(body.getCity());
//		dataPopulation.setJob(body.getJob());
//		dataPopulation.setNationality(body.getNationality());
//		dataPopulation.setMonthlyIncome(monthIncome);
//		dataPopulation.setMonthlyConsume(monthConsume);
//		dataPopulation.setMaritalStatus(body.getMaritalStatus());
//		dataPopulation.setEducation(body.getEducation());
//		dataPopulation.setEmployment(body.getEmployment());
//		dataPopulation.setMobile(mobile);
//        
//        int count = dataPopulationDao.updateDataPopulation(dataPopulation);
//        if (count == 0) {
//            result.setCode(ApiErrorCode.DB_ERROR.getCode());
//            result.setMsg(ApiErrorCode.DB_ERROR.getMsg());
//            return result;
//        }
//        
        return mainBasicInfoGetService.getMainBasicInfo(body.getContactId(), body.getDataType(), body.getUserToken());
    }
}
