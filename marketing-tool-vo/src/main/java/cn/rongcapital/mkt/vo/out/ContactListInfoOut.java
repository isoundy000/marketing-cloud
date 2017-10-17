package cn.rongcapital.mkt.vo.out;

import java.util.List;

public class ContactListInfoOut {
	private String contact_id;
	private String contact_name;
	private String contact_title;
	private String qrcode_shorturl;
	private String qrcode_pic;
	private String contact_status;
	private String contact_descript;
	private List<Field_List> field_list;

	public String getContact_id() {
		return contact_id;
	}

	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_title() {
		return contact_title;
	}

	public void setContact_title(String contact_title) {
		this.contact_title = contact_title;
	}

	public String getQrcode_shorturl() {
		return qrcode_shorturl;
	}

	public void setQrcode_shorturl(String qrcode_shorturl) {
		this.qrcode_shorturl = qrcode_shorturl;
	}

	public String getQrcode_pic() {
		return qrcode_pic;
	}

	public void setQrcode_pic(String qrcode_pic) {
		this.qrcode_pic = qrcode_pic;
	}

	public String getContact_status() {
		return contact_status;
	}

	public void setContact_status(String contact_status) {
		this.contact_status = contact_status;
	}

	public List<Field_List> getField_list() {
		return field_list;
	}

	public void setField_list(List<Field_List> field_list) {
		this.field_list = field_list;
	}

	public String getContact_descript() {
		return contact_descript;
	}

	public void setContact_descript(String contact_descript) {
		this.contact_descript = contact_descript;
	}
	
	
}
