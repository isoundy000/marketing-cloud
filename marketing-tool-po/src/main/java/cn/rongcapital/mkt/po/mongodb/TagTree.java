package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*************************************************
 * @功能及特点的描述简述: 标签组列表
 * 
 * @see （与该类关联的类): TagTree
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.3 @date(创建、开发日期): 2016-09-21 最后修改日期: 2016-09-21
 * @复审人: 丛树林
 *************************************************/
@Document(collection = "tag_tree")
public class TagTree implements Serializable {

	private static final long serialVersionUID = 5009763496605546365L;

	@Id
	private String id;
	@Field(value = "tag_id")
	private String tagId;
	@Field(value = "tag_name")
	private String tagName;
	private int level;
	private String path;
	private int status;
	private String parent;
	@Field(value = "create_time")
	private Date createTime;
	@Field(value = "update_time")
	private Date updateTime;
	
    @Field(value = "source")
    private String source;

	private List<String> children;
	
	public TagTree() {}
	
	public TagTree(String id, String tagId, String tagName, int level, String path, int status,
                    String parent, Date createTime, Date updateTime, String source,
                    List<String> children) {
        super();
        this.id = id;
        this.tagId = tagId;
        this.tagName = tagName;
        this.level = level;
        this.path = path;
        this.status = status;
        this.parent = parent;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.source = source;
        this.children = children;
    }



    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParent() {
		return parent;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}

	public List<String> getChildren() {
		return children;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + level;
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + status;
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
        TagTree other = (TagTree) obj;
        if (children == null) {
            if (other.children != null)
                return false;
        } else if (!children.equals(other.children))
            return false;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (level != other.level)
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        if (status != other.status)
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
        if (updateTime == null) {
            if (other.updateTime != null)
                return false;
        } else if (!updateTime.equals(other.updateTime))
            return false;
        return true;
    }
	
	

}