package cn.rongcapital.mkt.common.enums;
/**
 * 页面积分管理地图，省份对应关系
 * @author lijinkai
 *
 */
public enum SegmentProvinceMap {

    S_110000("北京市", "北京"), 
    S_120000("天津市", "天津"), 
    S_130000("河北省", "河北"), 
    S_140000("山西省", "山西"), 
    S_150000("内蒙古自治区", "内蒙古"), 
    S_210000("辽宁省", "辽宁"), 
    S_220000("吉林省", "吉林"), 
    S_230000("黑龙江省", "黑龙江"), 
    S_310000("上海市", "上海"), 
    S_320000("江苏省", "江苏"), 
    S_330000("浙江省", "浙江"), 
    S_340000("安徽省", "安徽"), 
    S_350000("福建省", "福建"), 
    S_360000("江西省", "江西"), 
    S_370000("山东省", "山东"), 
    S_410000("河南省", "河南"), 
    S_420000("湖北省", "湖北"), 
    S_430000("湖南省", "湖南"), 
    S_440000("广东省", "广东"), 
    S_450000("广西壮族自治区", "广西"), 
    S_460000("海南省", "海南"), 
    S_500000("重庆市", "重庆"), 
    S_510000("四川省", "四川"), 
    S_520000("贵州省", "贵州"), 
    S_530000("云南省", "云南"), 
    S_540000("西藏自治区", "西藏"), 
    S_610000("陕西省", "陕西"), 
    S_620000("甘肃省", "甘肃"), 
    S_630000("青海省", "青海"), 
    S_640000("宁夏回族自治区", "宁夏"), 
    S_650000("新疆维吾尔自治区", "新疆"), 
    S_710000("台湾省", "台湾"), 
    S_810000("香港特别行政区", "香港"), 
    S_820000("澳门特别行政区", "澳门"),
    ;
    private SegmentProvinceMap(String provinceName, String shortName) {
        this.provinceName = provinceName;
        this.shortName = shortName;
    }

    private String provinceName;

    private String shortName;

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
