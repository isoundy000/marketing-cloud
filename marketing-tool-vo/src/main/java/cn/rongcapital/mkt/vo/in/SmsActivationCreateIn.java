package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by byf on 10/18/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsActivationCreateIn extends BaseInput{

    private Long taskId;

    @NotEmpty
    private String taskName;

    @NotNull
    private Long taskSignatureId;

    @NotNull
    private Long taskMaterialId;

    @NotEmpty
    private String taskMaterialContent;

    private List<SmsTargetAudienceIn> smsTargetAudienceInArrayList;

    @NotNull
    private Integer taskSendType;
    private Integer taskAppType;

    @JsonProperty("task_id")
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @JsonProperty("task_name")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @JsonProperty("task_signature_id")
    public Long getTaskSignatureId() {
        return taskSignatureId;
    }

    public void setTaskSignatureId(Long taskSignatureId) {
        this.taskSignatureId = taskSignatureId;
    }

    @JsonProperty("task_material_id")
    public Long getTaskMaterialId() {
		return taskMaterialId;
	}

	public void setTaskMaterialId(Long taskMaterialId) {
		this.taskMaterialId = taskMaterialId;
	}

	@JsonProperty("task_material_content")
	public String getTaskMaterialContent() {
		return taskMaterialContent;
	}

	public void setTaskMaterialContent(String taskMaterialContent) {
		this.taskMaterialContent = taskMaterialContent;
	}

	@JsonProperty("target_audience_list")
    public List<SmsTargetAudienceIn> getSmsTargetAudienceInArrayList() {
        return smsTargetAudienceInArrayList;
    }

    public void setSmsTargetAudienceInArrayList(List<SmsTargetAudienceIn> smsTargetAudienceInArrayList) {
        this.smsTargetAudienceInArrayList = smsTargetAudienceInArrayList;
    }

    @JsonProperty("task_send_type")
    public Integer getTaskSendType() {
        return taskSendType;
    }

    public void setTaskSendType(Integer taskSendType) {
        this.taskSendType = taskSendType;
    }

    @JsonProperty("task_app_type")
    public Integer getTaskAppType() {
        return taskAppType;
    }

    public void setTaskAppType(Integer taskAppType) {
        this.taskAppType = taskAppType;
    }
}
