package cn.rongcapital.mkt.vo.sms.in;

import java.io.Serializable;

public class SmsRequestVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String partner_Name;
    private String partner_No;
    private String partner_Phone;
    private String partner_Msg;
    private String partner_Time;
    
    public String getPartner_Name() {
        return partner_Name;
    }
    public void setPartner_Name(String partner_Name) {
        this.partner_Name = partner_Name;
    }
    public String getPartner_No() {
        return partner_No;
    }
    public void setPartner_No(String partner_No) {
        this.partner_No = partner_No;
    }
    public String getPartner_Phone() {
        return partner_Phone;
    }
    public void setPartner_Phone(String partner_Phone) {
        this.partner_Phone = partner_Phone;
    }
    public String getPartner_Msg() {
        return partner_Msg;
    }
    public void setPartner_Msg(String partner_Msg) {
        this.partner_Msg = partner_Msg;
    }
    public String getPartner_Time() {
        return partner_Time;
    }
    public void setPartner_Time(String partner_Time) {
        this.partner_Time = partner_Time;
    }
    
    
    
   
}
