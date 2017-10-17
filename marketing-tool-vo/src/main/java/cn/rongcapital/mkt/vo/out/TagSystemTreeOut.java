package cn.rongcapital.mkt.vo.out;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 系统标签分类的输出
 * 
 * @return
 * @author shuiyangyang
 * @Date 2016-11-09
 */
public class TagSystemTreeOut {
    private String tagId;
    private String tagName;
    private Integer level;
    private List<Object> children;
    private Integer includeCount;	//包含标签个数
    
    public TagSystemTreeOut() {}

    public TagSystemTreeOut(String tagId, String tagName, Integer level, List<Object> children) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.level = level;
        this.children = children;
    }

    @JsonProperty("tag_id")
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @JsonProperty("tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @JsonProperty("children")
    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }
    
    @JsonProperty("includeCount")
    public Integer getIncludeCount() {
		return includeCount;
	}

	public void setIncludeCount(Integer includeCount) {
		this.includeCount = includeCount;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TagSystemTreeOut other = (TagSystemTreeOut) obj;
        if (children == null) {
            if (other.children != null)
                return false;
        } else if (!children.equals(other.children))
            return false;
        if (level == null) {
            if (other.level != null)
                return false;
        } else if (!level.equals(other.level))
            return false;
        if (tagId == null) {
            if (other.tagId != null)
                return false;
        } else if (!tagId.equals(other.tagId))
            return false;
        if (tagName == null) {
            if (other.tagName != null)
                return false;
        } else if (!tagName.equals(other.tagName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TagSystemTreeOut [tagId=" + tagId + ", tagName=" + tagName + ", level=" + level
                        + ", children=" + children + "]";
    }


}
