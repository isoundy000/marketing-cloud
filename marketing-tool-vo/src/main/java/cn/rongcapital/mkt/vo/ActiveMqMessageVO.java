package cn.rongcapital.mkt.vo;


public class ActiveMqMessageVO {
    //任务名字
    private String taskName;
    
    //服务名字
    private String serviceName;
    // json
    private String message;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
