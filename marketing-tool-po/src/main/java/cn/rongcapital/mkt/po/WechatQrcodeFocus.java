package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class WechatQrcodeFocus extends BaseQuery {
    private Integer id;

    private String qrcodeId;

    private String wxName;

    private Integer chCode;

    private String openid;

    private Date focusDatetime;

    private Date unfocusDatetime;

    private Byte focusStatus;

    private String wxAcct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(String qrcodeId) {
        this.qrcodeId = qrcodeId == null ? null : qrcodeId.trim();
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    public Integer getChCode() {
        return chCode;
    }

    public void setChCode(Integer chCode) {
        this.chCode = chCode;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Date getFocusDatetime() {
        return focusDatetime;
    }

    public void setFocusDatetime(Date focusDatetime) {
        this.focusDatetime = focusDatetime;
    }

    public Date getUnfocusDatetime() {
        return unfocusDatetime;
    }

    public void setUnfocusDatetime(Date unfocusDatetime) {
        this.unfocusDatetime = unfocusDatetime;
    }

    public Byte getFocusStatus() {
        return focusStatus;
    }

    public void setFocusStatus(Byte focusStatus) {
        this.focusStatus = focusStatus;
    }

	public String getWxAcct() {
		return wxAcct;
	}

	public void setWxAcct(String wxAcct) {
		this.wxAcct = wxAcct;
	}

}
