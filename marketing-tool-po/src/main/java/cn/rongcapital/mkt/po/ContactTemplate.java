package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.io.Serializable;
import java.util.Date;

public class ContactTemplate extends BaseQuery implements Serializable {
    private Integer id;

    private Long contactId;

    private String contactName;

    private String contactTitle;

    private String contactDescript;

    private String fieldName;

    private String fieldCode;

    private Integer fieldType;

    private String selected;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private String qrcodeUrl;

    private String qrcodeShorturl;

    private String qrcodePic;

    private Integer pageViews;

    private String keyList;

    private Integer required;

    private Integer ischecked;

    private Byte isRememberImportKey;

    private Integer fieldIndex;

    private Byte isShownInFeedback;

    private Byte delStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle == null ? null : contactTitle.trim();
    }

    public String getContactDescript() {
        return contactDescript;
    }

    public void setContactDescript(String contactDescript) {
        this.contactDescript = contactDescript == null ? null : contactDescript.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
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

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl == null ? null : qrcodeUrl.trim();
    }

    public String getQrcodeShorturl() {
        return qrcodeShorturl;
    }

    public void setQrcodeShorturl(String qrcodeShorturl) {
        this.qrcodeShorturl = qrcodeShorturl == null ? null : qrcodeShorturl.trim();
    }

    public String getQrcodePic() {
        return qrcodePic;
    }

    public void setQrcodePic(String qrcodePic) {
        this.qrcodePic = qrcodePic == null ? null : qrcodePic.trim();
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    public String getKeyList() {
        return keyList;
    }

    public void setKeyList(String keyList) {
        this.keyList = keyList == null ? null : keyList.trim();
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getIschecked() {
        return ischecked;
    }

    public void setIschecked(Integer ischecked) {
        this.ischecked = ischecked;
    }

    public Byte getIsRememberImportKey() {
        return isRememberImportKey;
    }

    public void setIsRememberImportKey(Byte isRememberImportKey) {
        this.isRememberImportKey = isRememberImportKey;
    }

    public Integer getFieldIndex() {
        return fieldIndex;
    }

    public void setFieldIndex(Integer fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public Byte getIsShownInFeedback() {
        return isShownInFeedback;
    }

    public void setIsShownInFeedback(Byte isShownInFeedback) {
        this.isShownInFeedback = isShownInFeedback;
    }

    public Byte getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Byte delStatus) {
        this.delStatus = delStatus;
    }
}
