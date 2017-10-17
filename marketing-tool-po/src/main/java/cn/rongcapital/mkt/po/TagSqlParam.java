package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class TagSqlParam extends BaseQuery {
	
	private static final long serialVersionUID = 1L;

	private Long id;

    private String tagId;

    private String lowerLimitValue;

    private String upperLimitValue;

    private String scopeValue;

    private String tagName;

    private String reserve2;

    private String reserve3;

    private String reserve4;
    
    private Integer count;
    

    public TagSqlParam() {
    	
	}
    
    
	public TagSqlParam(String tagName) {
		this.tagName = tagName;
	}


	public TagSqlParam(String tagId, String lowerLimitValue, String upperLimitValue, String scopeValue) {
		this.tagId = tagId;
		this.lowerLimitValue = lowerLimitValue;
		this.upperLimitValue = upperLimitValue;
		this.scopeValue = scopeValue;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId == null ? null : tagId.trim();
    }

    public String getLowerLimitValue() {
        return lowerLimitValue;
    }

    public void setLowerLimitValue(String lowerLimitValue) {
        this.lowerLimitValue = lowerLimitValue == null ? null : lowerLimitValue.trim();
    }

    public String getUpperLimitValue() {
        return upperLimitValue;
    }

    public void setUpperLimitValue(String upperLimitValue) {
        this.upperLimitValue = upperLimitValue == null ? null : upperLimitValue.trim();
    }

    public String getScopeValue() {
        return scopeValue;
    }

    public void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue == null ? null : scopeValue.trim();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2 == null ? null : reserve2.trim();
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3 == null ? null : reserve3.trim();
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4 == null ? null : reserve4.trim();
    }

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
    
}