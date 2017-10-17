/*************************************************
 * @功能简述: 获取某条主数据详细信息
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0 @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.GenderUtils;
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.service.MainBasicInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.BasicContactInfoOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class MainBasicInfoGetServiceImpl implements MainBasicInfoGetService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DataPopulationDao dataPopulationDao;

	@Autowired
	private DataCustomerTagsDao dataCustomerTagsDao;

	@Autowired
	private DataArchPointDao dataArchPointDao;

	@Autowired
	private DataMemberDao dataMemberDao;

	@Autowired
	private DataLoginDao dataLoginDao;

	@Autowired
	private DataPaymentDao dataPaymentDao;

	@Autowired
	private DataShoppingDao dataShoppingDao;

	@Autowired
	private DataPartyDao dataPartyDao;

	@Override
	public Integer getKeyIdFromContactInfo(Integer contactId, Integer dataType, String userToken) {

		Integer keyid = null;

		if (dataType == DataTypeEnum.PARTY.getCode()) {
			keyid = contactId; // dataPartyDao.selectMobileById(contactId);
		} else if (dataType == DataTypeEnum.POPULATION.getCode()) {
			keyid = dataPopulationDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
			keyid = dataCustomerTagsDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
			keyid = dataArchPointDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.MEMBER.getCode()) {
			keyid = dataMemberDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.LOGIN.getCode()) {
			keyid = dataLoginDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
			keyid = dataPaymentDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
			keyid = dataShoppingDao.selectObjectById(contactId).getKeyid();
		} else if (dataType == DataTypeEnum.WECHAT.getCode()) {
			keyid = dataPopulationDao.selectObjectById(contactId).getKeyid();
		}

		return keyid;
	}

	/**
	 * 微信视图显示信息
	 */
	// private BasicContactInfoOut getWeiXinDataVo(Integer dataType, Integer
	// keyId) {
	// BasicContactInfoOut dataVo = null;
	// if (dataType == DataTypeEnum.WECHAT.getCode()) {
	// dataVo = new BasicContactInfoOut();
	// DataPopulation dataPopulationTemp = new DataPopulation();
	// dataPopulationTemp.setKeyid(keyId);
	// // dataPopulationTemp.setBitmap(ApiConstant.WEIXIN_BITMAP);
	// List<DataPopulation> dataPopulations =
	// dataPopulationDao.selectList(dataPopulationTemp);
	// if (CollectionUtils.isNotEmpty(dataPopulations)) {
	// for (Iterator<DataPopulation> iter = dataPopulations.iterator();
	// iter.hasNext();) {
	// DataPopulation dataPopulation = iter.next();
	// String bitMap = dataPopulation.getBitmap();
	// if (bitMap.equals(ApiConstant.WEIXIN_BITMAP)) {
	// dataVo.setWx_country(dataPopulation.getCitizenship());
	// dataVo.setWx_provice(dataPopulation.getProvice());
	// dataVo.setWx_city(dataPopulation.getCity());
	// String gender = GenderUtils.byteToChar(dataPopulation.getGender());
	// dataVo.setWx_gender(String.valueOf(gender));
	// dataVo.setWx_nickname(dataPopulation.getNickname());
	// dataVo.setPhoto(dataPopulation.getHeadImgUrl());
	// } else {
	// dataVo.setContactId(keyId);
	// dataVo.setName(dataPopulation.getName());
	// String gender = GenderUtils.byteToChar(dataPopulation.getGender());
	// dataVo.setGender(gender);
	// String birthday =
	// DateUtil.getStringFromDate(dataPopulation.getBirthday(), "yyyy-MM-dd");
	// dataVo.setBirthday(birthday);
	//
	// String identifyNo = dataPopulation.getIdentifyNo();
	// if (StringUtils.isNotBlank(identifyNo)) {
	// dataVo.setIdentifyNo(Long.valueOf(dataPopulation.getIdentifyNo()));
	// }
	//
	// String drivingLicense = dataPopulation.getDrivingLicense();
	// if (StringUtils.isNotBlank(drivingLicense)) {
	// dataVo.setDrivingLicense(Long.valueOf(drivingLicense));
	// }
	//
	// dataVo.setMobile(dataPopulation.getMobile());
	// dataVo.setTel(dataPopulation.getTel());
	// dataVo.setEmail(dataPopulation.getEmail());
	// dataVo.setQq(dataPopulation.getQq());
	// dataVo.setWechat(null);
	// dataVo.setAcctNo(dataPopulation.getAcctNo());
	// dataVo.setCitizenship(dataPopulation.getCitizenship());
	// dataVo.setProvice(dataPopulation.getProvice());
	// dataVo.setCity(dataPopulation.getCity());
	// dataVo.setJob(dataPopulation.getJob());
	// dataVo.setPhoto(dataPopulation.getHeadImgUrl());
	//
	// DecimalFormat df = new DecimalFormat("0.00");
	// BigDecimal monthlyIncome = dataPopulation.getMonthlyIncome();
	// if (monthlyIncome != null) {
	// String monthIncome = df.format(monthlyIncome);
	// dataVo.setMonthlyIncome(monthIncome);
	// }
	//
	// BigDecimal monthlyConsume = dataPopulation.getMonthlyConsume();
	//
	// if (monthlyConsume != null) {
	// String monthConsume = df.format(monthlyConsume);
	// dataVo.setMonthlyConsume(monthConsume);
	// }
	// }
	// }
	//
	// }
	// }
	// return dataVo;
	//
	// }

	/**
	 * 微信视图显示信息
	 */
	private BasicContactInfoOut getWeiXinDataVo(Integer dataType, Integer keyId) {

		BasicContactInfoOut dataVo = new BasicContactInfoOut();

		DataPopulation dataPop = new DataPopulation();
		dataPop.setKeyid(keyId);

		List<DataPopulation> selectList = dataPopulationDao.selectList(dataPop);
		logger.info("----getWeiXinDataVo----List size" + selectList.size());

		if(CollectionUtils.isNotEmpty(selectList)){
			dataVo = getBasicContactInfoOut(dataVo,selectList);
		}
		return dataVo;
	}

	/**
	 * 根据Data_population微信视图显示信息
	 */
	private BasicContactInfoOut getWeiXinDataVoById(Integer id) {

		BasicContactInfoOut dataVo = new BasicContactInfoOut();

		DataPopulation dataPop = new DataPopulation();
		dataPop.setId(id);

		List<DataPopulation> selectList = dataPopulationDao.selectList(dataPop);
		logger.info("----getWeiXinDataVo----List size" + selectList.size());

		if(CollectionUtils.isNotEmpty(selectList)){
			dataVo = getBasicContactInfoOut(dataVo,selectList);
		}
		return dataVo;
	}
	
	
	private BasicContactInfoOut getBasicContactInfoOut(BasicContactInfoOut dataVo,List<DataPopulation> selectList){
		for (DataPopulation dataPopulation : selectList) {
			dataVo.setWx_country(dataPopulation.getCitizenship());
			dataVo.setWx_provice(dataPopulation.getProvice());
			dataVo.setWx_city(dataPopulation.getCity());
			String gender = GenderUtils.byteToChar(dataPopulation.getGender());
			dataVo.setWx_gender(String.valueOf(gender));
			dataVo.setWx_nickname(dataPopulation.getNickname());
			dataVo.setPhoto(dataPopulation.getHeadImgUrl());
			dataVo.setGender(String.valueOf(gender));
			dataVo.setContactId(dataPopulation.getKeyid());
			dataVo.setName(dataPopulation.getName());
			String birthday = DateUtil.getStringFromDate(dataPopulation.getBirthday(), "yyyy-MM-dd");
			dataVo.setBirthday(birthday);

			String identifyNo = dataPopulation.getIdentifyNo();
			if (StringUtils.isNotBlank(identifyNo)) {
				dataVo.setIdentifyNo(Long.valueOf(dataPopulation.getIdentifyNo()));
			}

			String drivingLicense = dataPopulation.getDrivingLicense();
			if (StringUtils.isNotBlank(drivingLicense)) {
				dataVo.setDrivingLicense(Long.valueOf(drivingLicense));
			}

			dataVo.setMobile(dataPopulation.getMobile());
			dataVo.setTel(dataPopulation.getTel());
			dataVo.setEmail(dataPopulation.getEmail());
			dataVo.setQq(dataPopulation.getQq());
			dataVo.setIq(dataPopulation.getIq());
			dataVo.setWechat(dataPopulation.getWxCode());
			dataVo.setNationality(dataPopulation.getNationality());
			dataVo.setEducation(dataPopulation.getEducation());
			dataVo.setEmployment(dataPopulation.getEmployment());
			dataVo.setBloodType(dataPopulation.getBloodType());
			dataVo.setAcctType(dataPopulation.getAcctType());
			dataVo.setMaritalStatus(dataPopulation.getMaritalStatus());
			dataVo.setAcctNo(dataPopulation.getAcctNo());
			dataVo.setCitizenship(dataPopulation.getCitizenship());
			dataVo.setProvice(dataPopulation.getProvice());
			dataVo.setCity(dataPopulation.getCity());
			dataVo.setJob(dataPopulation.getJob());
			dataVo.setPhoto(dataPopulation.getHeadImgUrl());

			DecimalFormat df = new DecimalFormat("0.00");
			BigDecimal monthlyIncome = dataPopulation.getMonthlyIncome();
			if (monthlyIncome != null) {
				String monthIncome = df.format(monthlyIncome);
				dataVo.setMonthlyIncome(monthIncome);
			}

			BigDecimal monthlyConsume = dataPopulation.getMonthlyConsume();

			if (monthlyConsume != null) {
				String monthConsume = df.format(monthlyConsume);
				dataVo.setMonthlyConsume(monthConsume);
			}
		}
		return dataVo;
	}
	
	
	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput getMainBasicInfo(Integer contactId, Integer dataType, String userToken) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		if (contactId == null || dataType == null) {
			result.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
			result.setMsg("传入参数不合法!");
			return result;
		}
		
		/**
		 * 微信视图显示信息
		 */
		if (dataType == DataTypeEnum.WECHAT.getCode()) {
			//组数据显示调用
			BasicContactInfoOut dataVo = this.getWeiXinDataVo(dataType, contactId);		
			List<Object> data = new ArrayList<Object>();
			data.add(dataVo);
			result.setData(data);
			result.setTotal(data.size());
		}else if(dataType == DataTypeEnum.WECHAT1.getCode()){
			//粉丝管理显示调用
			BasicContactInfoOut dataVo =  getWeiXinDataVoById(contactId);
			List<Object> data = new ArrayList<Object>();
			data.add(dataVo);
			result.setData(data);
			result.setTotal(data.size());
		}		
		return result;
	}

}
