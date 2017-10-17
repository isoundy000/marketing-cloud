package cn.rongcapital.mkt.common.enums;

public enum TaskTagEnum {

	PAY_SUCCESS(0, "成功"), PAY_FAIL(1, "失败"),

	;

	private TaskTagEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * 数据类型代码
	 */
	private int code;

	/**
	 * 数据类型描述
	 */
	private String description;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static String getDescriptionByCode(int code) {
		String result = "";
		TaskTagEnum[] taskTagEnums = TaskTagEnum.values();
		for (TaskTagEnum taskTagEnum : taskTagEnums) {
			if (taskTagEnum.getCode() == code) {
				return taskTagEnum.getDescription();
			}
		}
		return result;
	}

}
