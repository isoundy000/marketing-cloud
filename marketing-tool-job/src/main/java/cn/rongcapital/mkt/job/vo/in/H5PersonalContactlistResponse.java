package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-6-20.
 */
public class H5PersonalContactlistResponse {

    private String message;
    private int total;
    private String uin;
    private PersonalContacts contacts;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public PersonalContacts getContacts() {
        return contacts;
    }

    public void setContacts(PersonalContacts contacts) {
        this.contacts = contacts;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }
}
