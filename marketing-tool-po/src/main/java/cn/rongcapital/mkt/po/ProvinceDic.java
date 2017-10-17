package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ProvinceDic extends BaseQuery{
    /**
	 * 
	 */
	private static final long serialVersionUID = 156679433589303886L;

	private Integer provinceId;

    private Integer provinceCode;

    private String provinceNamec;

    private String provinceNamee;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceNamec() {
        return provinceNamec;
    }

    public void setProvinceNamec(String provinceNamec) {
        this.provinceNamec = provinceNamec == null ? null : provinceNamec.trim();
    }

    public String getProvinceNamee() {
        return provinceNamee;
    }

    public void setProvinceNamee(String provinceNamee) {
        this.provinceNamee = provinceNamee == null ? null : provinceNamee.trim();
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
}