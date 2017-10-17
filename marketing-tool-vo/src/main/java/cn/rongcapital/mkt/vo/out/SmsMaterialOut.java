package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by byf on 11/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsMaterialOut {

    private Long id;
    private String materialId;
    private String materialName;
    private Integer smsType;
    private Integer smsSignId;
    private String smsSignContent;
    private Integer smsTemplateId;
    private String smsTemplateContent;
    private String smsTemplateName;
    private String createTime;
    private Integer editStatus;
    private Integer deleteStatus;
    private Integer materielStockTotal;
    private List<SmsMaterialMaterielOut> smsMaterialMaterielOutList;
    private List<SmsMaterialVariableOut> smsMaterialVariableOutList;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("material_id")
    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    @JsonProperty("material_name")
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @JsonProperty("sms_type")
    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    @JsonProperty("sms_sign_id")
    public Integer getSmsSignId() {
        return smsSignId;
    }

    public void setSmsSignId(Integer smsSignId) {
        this.smsSignId = smsSignId;
    }

    @JsonProperty("sms_sign_content")
    public String getSmsSignContent() {
        return smsSignContent;
    }

    public void setSmsSignContent(String smsSignContent) {
        this.smsSignContent = smsSignContent;
    }

    @JsonProperty("sms_template_id")
    public Integer getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(Integer smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    @JsonProperty("sms_template_content")
    public String getSmsTemplateContent() {
        return smsTemplateContent;
    }

    public void setSmsTemplateContent(String smsTemplateContent) {
        this.smsTemplateContent = smsTemplateContent;
    }

    @JsonProperty("sms_template_name")
    public String getSmsTemplateName() {
        return smsTemplateName;
    }

    public void setSmsTemplateName(String smsTemplateName) {
        this.smsTemplateName = smsTemplateName;
    }

    @JsonProperty("create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @JsonProperty("edit_status")
    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    @JsonProperty("delete_status")
    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @JsonProperty("materiel_stock_total")
    public Integer getMaterielStockTotal() {
        return materielStockTotal;
    }

    public void setMaterielStockTotal(Integer materielStockTotal) {
        this.materielStockTotal = materielStockTotal;
    }

    @JsonProperty("materiel_list")
    public List<SmsMaterialMaterielOut> getSmsMaterialMaterielOutList() {
        return smsMaterialMaterielOutList;
    }

    public void setSmsMaterialMaterielOutList(List<SmsMaterialMaterielOut> smsMaterialMaterielOutList) {
        this.smsMaterialMaterielOutList = smsMaterialMaterielOutList;
    }

    @JsonProperty("variable_list")
    public List<SmsMaterialVariableOut> getSmsMaterialVariableOutList() {
        return smsMaterialVariableOutList;
    }

    public void setSmsMaterialVariableOutList(List<SmsMaterialVariableOut> smsMaterialVariableOutList) {
        this.smsMaterialVariableOutList = smsMaterialVariableOutList;
    }
}
