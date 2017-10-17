package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class TaskSchedule extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	public TaskSchedule() {
		
	}
	public TaskSchedule(Integer startIndex, Integer pageSize) {
		super(startIndex, pageSize);
	}

    private Integer id;

    private String taskName;

    private String taskDescript;

    private String taskType;

    private Date startTime;

    private Date endTime;

    private Float intervalMinutes;

    private String schedule;

    private String serviceName;

    private Byte taskStatus;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Integer campaignHeadId;

    private String campaignItemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskDescript() {
        return taskDescript;
    }

    public void setTaskDescript(String taskDescript) {
        this.taskDescript = taskDescript == null ? null : taskDescript.trim();
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Float getIntervalMinutes() {
        return intervalMinutes;
    }

    public void setIntervalMinutes(Float intervalMinutes) {
        this.intervalMinutes = intervalMinutes;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule == null ? null : schedule.trim();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public Byte getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Byte taskStatus) {
        this.taskStatus = taskStatus;
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

    public Integer getCampaignHeadId() {
        return campaignHeadId;
    }

    public void setCampaignHeadId(Integer campaignHeadId) {
        this.campaignHeadId = campaignHeadId;
    }

    public String getCampaignItemId() {
        return campaignItemId;
    }

    public void setCampaignItemId(String campaignItemId) {
        this.campaignItemId = campaignItemId == null ? null : campaignItemId.trim();
    }
}
