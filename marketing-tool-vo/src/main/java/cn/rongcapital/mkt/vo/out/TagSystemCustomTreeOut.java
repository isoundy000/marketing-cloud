package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 系统标签自定义分类输出
 * 
 * @return
 * @author shuiyangyang
 * @Date 2016-12-06
 */
public class TagSystemCustomTreeOut {
    private String tagTreeId=null;
    private String tagTreeName=null;
    private Integer level;
    private List<Object> children = new ArrayList<Object>();
    private List<Object> childrenTag = new ArrayList<Object>();
    private Integer tagCount=0;

    public TagSystemCustomTreeOut() {}

    public TagSystemCustomTreeOut(String tagTreeId, String tagTreeName, Integer level, List<Object> children,
            List<Object> childrenTag, Integer tagCount) {
        super();
        this.tagTreeId = tagTreeId;
        this.tagTreeName = tagTreeName;
        this.level = level;
        this.children = children == null ? this.children : children;
        this.childrenTag = childrenTag == null ? this.childrenTag : childrenTag;
        this.tagCount = tagCount;
    }

    @JsonProperty("tag_tree_id")
    public String getTagTreeId() {
        return tagTreeId;
    }

    public void setTagTreeId(String tagTreeId) {
        this.tagTreeId = tagTreeId == null?this.tagTreeId:tagTreeId;
    }

    @JsonProperty("tag_tree_name")
    public String getTagTreeName() {
        return tagTreeName;
    }

    public void setTagTreeName(String tagTreeName) {
        this.tagTreeName = tagTreeName==null?this.tagTreeName:tagTreeName;
    }

    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level==null?this.level:level;
    }

    @JsonProperty("children")
    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children == null ? this.children : children;
    }

    @JsonProperty("children_tag")
    public List<Object> getChildrenTag() {
        return childrenTag;
    }

    public void setChildrenTag(List<Object> childrenTag) {
        this.childrenTag = childrenTag == null ? this.childrenTag : childrenTag;
    }

    @JsonProperty("tag_count")
    public Integer getTagCount() {
        return tagCount;
    }

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount==null?this.tagCount:tagCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((childrenTag == null) ? 0 : childrenTag.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((tagCount == null) ? 0 : tagCount.hashCode());
        result = prime * result + ((tagTreeId == null) ? 0 : tagTreeId.hashCode());
        result = prime * result + ((tagTreeName == null) ? 0 : tagTreeName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TagSystemCustomTreeOut other = (TagSystemCustomTreeOut) obj;
        if (children == null) {
            if (other.children != null) return false;
        } else if (!children.equals(other.children)) return false;
        if (childrenTag == null) {
            if (other.childrenTag != null) return false;
        } else if (!childrenTag.equals(other.childrenTag)) return false;
        if (level == null) {
            if (other.level != null) return false;
        } else if (!level.equals(other.level)) return false;
        if (tagCount == null) {
            if (other.tagCount != null) return false;
        } else if (!tagCount.equals(other.tagCount)) return false;
        if (tagTreeId == null) {
            if (other.tagTreeId != null) return false;
        } else if (!tagTreeId.equals(other.tagTreeId)) return false;
        if (tagTreeName == null) {
            if (other.tagTreeName != null) return false;
        } else if (!tagTreeName.equals(other.tagTreeName)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "TagSystemCustomTreeOut [tagTreeId=" + tagTreeId + ", tagTreeName=" + tagTreeName + ", level=" + level
                + ", children=" + children + ", childrenTag=" + childrenTag + ", tagCount=" + tagCount + "]";
    }


}
