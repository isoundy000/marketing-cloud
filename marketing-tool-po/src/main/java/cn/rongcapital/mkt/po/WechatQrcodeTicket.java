package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class WechatQrcodeTicket extends BaseQuery {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String url;

    private Integer sceneId;

    private Date createTime;

    private String ticket;
    
    private Integer state;
    
    private String wxAcct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
    
    public String getWxAcct() {
        return wxAcct;
    }

    public void setWxAcct(String wxAcct) {
        this.wxAcct = wxAcct == null ? null : wxAcct.trim();
    }
}