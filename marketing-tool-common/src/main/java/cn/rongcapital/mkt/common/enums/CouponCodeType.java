package cn.rongcapital.mkt.common.enums;

public enum CouponCodeType {

    COMMON("common", "通用码"), GENERATE("generate", "平台生成码"), OWN("own", "自有码");

	private CouponCodeType(String code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * 数据类型代码
	 */
	private String code;

	/**
	 * 数据类型描述
	 */
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static CouponCodeType getByCode(String code) {
		CouponCodeType result = null;
		CouponCodeType[] dataTypeEnums = CouponCodeType.values();
		for (CouponCodeType item : dataTypeEnums) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return result;
	}

	public static boolean isContainCode(String code) {
		boolean result = false;
		CouponCodeType[] dataTypeEnums = CouponCodeType.values();
		for (CouponCodeType dataTypeEnum : dataTypeEnums) {
			if (dataTypeEnum.getCode().equals(code)) {
				return true;
			}
		}
		return result;
	}
}
