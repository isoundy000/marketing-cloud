package cn.rongcapital.mkt.common.enums;

/**
 * 主数据查询策略枚举
 * @author lijinkai 2016-09-29
 *
 */
public enum ImportTemplClassEnum {

    PARTY("PARTY", "dataGetFilterAudiencesPartyServiceImpl"), 
    POPULATION("POPULATION", "dataGetFilterAudiencesPopulationServiceImpl"), 
    CUSTOMER_TAGS("CUSTOM_TAG", "dataGetFilterAudiencesCustomTagServiceImpl"), 
    ARCH_POINT("ARCH_POINT", "dataGetFilterAudiencesArchPointServiceImpl"), 
    MEMBER("MEMBER", "dataGetFilterAudiencesMemberServiceImpl"), 
    LOGIN("LOGIN", "dataGetFilterAudiencesLoginServiceImpl"), 
    PAYMENT("PAYMENT", "dataGetFilterAudiencesPaymentServiceImpl"), 
    SHOPPING("SHOPPING", "dataGetFilterAudiencesShoppingServiceImpl"),
	;
    private String code = "";
    private String value = "";
    
    private ImportTemplClassEnum(String code,String value){
    	
    	this.code = code;
        this.value = value;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
