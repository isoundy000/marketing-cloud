package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "system_custom_tag_tree")
public class SystemCustomTagTree implements Serializable {

	private static final long serialVersionUID = 5009763496605546365L;

	@Id
	private String id;
	@Field(value = "tag_tree_id")
	private String tagTreeId;
	@Field(value = "tag_tree_name")
	private String tagTreeName;
	private int level;
	@Field(value = "is_deleted")
	private int isDeleted;
	private String parent;
	@Field(value = "create_time")
	private Date createTime;
	@Field(value = "update_time")
	private Date updateTime;
	private List<String> children;
	@Field(value = "is_show")
	private Boolean isShow;
	@Field(value = "children_tag")
	private List<String> childrenTag;
	
	public SystemCustomTagTree () {}

    public SystemCustomTagTree(String id, String tagTreeId, String tagTreeName, int level, int isDeleted, String parent,
            Date createTime, Date updateTime, List<String> children, Boolean isShow, List<String> childrenTag) {
        super();
        this.id = id;
        this.tagTreeId = tagTreeId;
        this.tagTreeName = tagTreeName;
        this.level = level;
        this.isDeleted = isDeleted;
        this.parent = parent;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.children = children;
        this.isShow = isShow;
        this.childrenTag = childrenTag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagTreeId() {
        return tagTreeId;
    }

    public void setTagTreeId(String tagTreeId) {
        this.tagTreeId = tagTreeId;
    }

    public String getTagTreeName() {
        return tagTreeName;
    }

    public void setTagTreeName(String tagTreeName) {
        this.tagTreeName = tagTreeName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getisDeleted() {
        return isDeleted;
    }

    public void setisDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public List<String> getChildrenTag() {
        return childrenTag;
    }

    public void setChildrenTag(List<String> childrenTag) {
        this.childrenTag = childrenTag;
    }

    @Override
    public String toString() {
        return "SystemCustomTagTree [id=" + id + ", tagTreeId=" + tagTreeId + ", tagTreeName=" + tagTreeName
                + ", level=" + level + ", isDeleted=" + isDeleted + ", parent=" + parent + ", createTime=" + createTime
                + ", updateTime=" + updateTime + ", children=" + children + ", isShow=" + isShow + ", childrenTag="
                + childrenTag + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((childrenTag == null) ? 0 : childrenTag.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isShow == null) ? 0 : isShow.hashCode());
        result = prime * result + level;
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + isDeleted;
        result = prime * result + ((tagTreeId == null) ? 0 : tagTreeId.hashCode());
        result = prime * result + ((tagTreeName == null) ? 0 : tagTreeName.hashCode());
        result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SystemCustomTagTree other = (SystemCustomTagTree) obj;
        if (children == null) {
            if (other.children != null) return false;
        } else if (!children.equals(other.children)) return false;
        if (childrenTag == null) {
            if (other.childrenTag != null) return false;
        } else if (!childrenTag.equals(other.childrenTag)) return false;
        if (createTime == null) {
            if (other.createTime != null) return false;
        } else if (!createTime.equals(other.createTime)) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (isShow == null) {
            if (other.isShow != null) return false;
        } else if (!isShow.equals(other.isShow)) return false;
        if (level != other.level) return false;
        if (parent == null) {
            if (other.parent != null) return false;
        } else if (!parent.equals(other.parent)) return false;
        if (isDeleted != other.isDeleted) return false;
        if (tagTreeId == null) {
            if (other.tagTreeId != null) return false;
        } else if (!tagTreeId.equals(other.tagTreeId)) return false;
        if (tagTreeName == null) {
            if (other.tagTreeName != null) return false;
        } else if (!tagTreeName.equals(other.tagTreeName)) return false;
        if (updateTime == null) {
            if (other.updateTime != null) return false;
        } else if (!updateTime.equals(other.updateTime)) return false;
        return true;
    }
	
	
}