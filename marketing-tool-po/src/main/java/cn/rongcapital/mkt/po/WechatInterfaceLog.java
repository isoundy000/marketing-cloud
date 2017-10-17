package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class WechatInterfaceLog extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String interfaceName;

    private String methodName;

    private String params;

    private Date createTime;
    
    public WechatInterfaceLog(String interfaceName,String methodName,String params,Date createTime){
    	this.interfaceName = interfaceName;
    	this.methodName = methodName;
    	this.params = params;
    	this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName == null ? null : interfaceName.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }    
    
}
