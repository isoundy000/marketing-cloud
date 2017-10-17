package cn.rongcapital.mkt.vo.in;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;


@JsonIgnoreProperties(ignoreUnknown=true)
public class WechatQrcodeIn extends BaseInput{

	private long id;
	
	private String wx_acct;
	

    private String wx_name;
	
    private Integer ch_code;
    
    private String ch_name;
    
    private String qrcode_name;
    
    private String expiration_time;
    
//    @NotNull
    private Byte is_audience;

    private String fixed_audience;

    private List<String> association_tags;
    
    private String comments;
    
    private Byte status;
    
    private String qrcode_pic;
    
    private String qrcode_url;
 
    private String ticket;

    @JsonProperty("wx_name")
	public String getWx_name() {
		return wx_name;
	}

	public void setWx_name(String wx_name) {
		this.wx_name = wx_name;
	}

	@JsonProperty("ch_code")
	public Integer getCh_code() {
		return ch_code;
	}

	public void setCh_code(Integer ch_code) {
		this.ch_code = ch_code;
	}

	@JsonProperty("is_audience")
	public Byte getIs_audience() {
		return is_audience;
	}

	public void setIs_audience(Byte is_audience) {
		this.is_audience = is_audience;
	}

	public String getFixed_audience() {
		return fixed_audience;
	}

	public void setFixed_audience(String fixed_audience) {
		this.fixed_audience = fixed_audience;
	}

//	@JsonProperty("association_tags")
//	public List<AssociationTag> getAssociation_tags() {
//		return association_tags;
//	}
//
//	public void setAssociation_tags(List<AssociationTag> association_tags) {
//		this.association_tags = association_tags;
//	}

	@JsonProperty("comments")
	public String getComments() {
		return comments;
	}

	public List<String> getAssociation_tags() {
		return association_tags;
	}

	public void setAssociation_tags(List<String> association_tags) {
		this.association_tags = association_tags;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@JsonProperty("status")
	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@JsonProperty("qrcode_pic")
	public String getQrcode_pic() {
		return qrcode_pic;
	}

	public void setQrcode_pic(String qrcode_pic) {
		this.qrcode_pic = qrcode_pic;
	}

	@JsonProperty("qrcode_url")
	public String getQrcode_url() {
		return qrcode_url;
	}

	public void setQrcode_url(String qrcode_url) {
		this.qrcode_url = qrcode_url;
	}

	@JsonProperty("ticket")
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getQrcode_name() {
		return qrcode_name;
	}

	public void setQrcode_name(String qrcode_name) {
		this.qrcode_name = qrcode_name;
	}

	@JsonProperty("id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty("wx_acct")
	public String getWx_acct() {
		return wx_acct;
	}

	public void setWx_acct(String wx_acct) {
		this.wx_acct = wx_acct;
	}

	@JsonProperty("ch_name")
	public String getCh_name() {
		return ch_name;
	}

	public void setCh_name(String ch_name) {
		this.ch_name = ch_name;
	}

	@JsonProperty("expiration_time")
	public String getExpiration_time() {
		return expiration_time;
	}

	public void setExpiration_time(String expiration_time) {
		this.expiration_time = expiration_time;
	}

}
