package cn.rongcapital.mkt.common.enums;

public enum DirectlyCityEnum {

	Beijing("Beijing", "北京市"), 
	Shanghai("Shanghai", "上海市"), 
	Tianjin("Tianjin", "天津市"), 
	Chongqing("Chongqing", "重庆市"), 
	;
	
	private DirectlyCityEnum(String code,String description){
		this.code = code;
		this.description = description;
	}
	
    /**
     * 直辖市代码
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
    

}
