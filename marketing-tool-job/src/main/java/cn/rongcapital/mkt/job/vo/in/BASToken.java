package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-7-19.
 */
public class BASToken {
    private DataObject dataObject;
    private Boolean success = false;

    public BASToken() {
    }

    public BASToken(DataObject dataObject, Boolean success) {
        this.dataObject = dataObject;
        this.success = success;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
