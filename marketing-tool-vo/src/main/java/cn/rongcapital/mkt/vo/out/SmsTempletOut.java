package cn.rongcapital.mkt.vo.out;

import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;

public class SmsTempletOut extends BaseOutput{

	
	/**
	 * 固定模板总数
	 */
	private Integer typeFixedCount =0;
	
	/**
	 * 变量模板总数
	 */
	private Integer typeVariableCount =0;
	
	/**
	 * 未审核模板总数
	 */
	private Integer auditStatusNoCheckCount =0;
	
	/**
	 * 审核通过模板总数
	 */
	private Integer auditStatusPassCount =0;
	
	/**
	 * 审核未通过模板总数
	 */
	private Integer auditStatusNoPassCount =0;
	
	public Integer getTypeFixedCount() {
		return typeFixedCount;
	}

	public void setTypeFixedCount(Integer typeFixedCount) {
		this.typeFixedCount = typeFixedCount;
	}

	public Integer getTypeVariableCount() {
		return typeVariableCount;
	}

	public void setTypeVariableCount(Integer typeVariableCount) {
		this.typeVariableCount = typeVariableCount;
	}

	public Integer getAuditStatusNoCheckCount() {
		return auditStatusNoCheckCount;
	}

	public void setAuditStatusNoCheckCount(Integer auditStatusNoCheckCount) {
		this.auditStatusNoCheckCount = auditStatusNoCheckCount;
	}

	public Integer getAuditStatusPassCount() {
		return auditStatusPassCount;
	}

	public void setAuditStatusPassCount(Integer auditStatusPassCount) {
		this.auditStatusPassCount = auditStatusPassCount;
	}

	public Integer getAuditStatusNoPassCount() {
		return auditStatusNoPassCount;
	}

	public void setAuditStatusNoPassCount(Integer auditStatusNoPassCount) {
		this.auditStatusNoPassCount = auditStatusNoPassCount;
	}

	
	
}
