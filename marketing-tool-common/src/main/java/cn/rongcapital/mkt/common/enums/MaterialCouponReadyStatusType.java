package cn.rongcapital.mkt.common.enums;

public enum MaterialCouponReadyStatusType {

    UNREADY("unready", "未准备完成"), READY("ready", "已准备完成");

	private MaterialCouponReadyStatusType(String code, String description) {
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

	public static MaterialCouponReadyStatusType getByCode(String code) {
		MaterialCouponReadyStatusType result = null;
		MaterialCouponReadyStatusType[] dataTypeEnums = MaterialCouponReadyStatusType.values();
		for (MaterialCouponReadyStatusType item : dataTypeEnums) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return result;
	}

	public static boolean isContainCode(String code) {
		boolean result = false;
		MaterialCouponReadyStatusType[] dataTypeEnums = MaterialCouponReadyStatusType.values();
		for (MaterialCouponReadyStatusType dataTypeEnum : dataTypeEnums) {
			if (dataTypeEnum.getCode().equals(code)) {
				return true;
			}
		}
		return result;
	}
}
