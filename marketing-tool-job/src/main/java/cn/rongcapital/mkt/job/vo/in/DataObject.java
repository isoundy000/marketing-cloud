package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-7-19.
 */
public class DataObject {
    private String sessionToken;
    private String id;

    public DataObject() {
    }

    public DataObject(String sessionToken, String id) {
        this.sessionToken = sessionToken;
        this.id = id;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
