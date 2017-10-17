package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class CityDic extends BaseQuery{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer cityId;

    private Integer provinceId;

    private Integer cityCode;

    private String provinceNamec;

    private String cityNamec;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String cityNamee;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvinceNamec() {
        return provinceNamec;
    }

    public void setProvinceNamec(String provinceNamec) {
        this.provinceNamec = provinceNamec == null ? null : provinceNamec.trim();
    }

    public String getCityNamec() {
        return cityNamec;
    }

    public void setCityNamec(String cityNamec) {
        this.cityNamec = cityNamec == null ? null : cityNamec.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCityNamee() {
        return cityNamee;
    }

    public void setCityNamee(String cityNamee) {
        this.cityNamee = cityNamee == null ? null : cityNamee.trim();
    }
}