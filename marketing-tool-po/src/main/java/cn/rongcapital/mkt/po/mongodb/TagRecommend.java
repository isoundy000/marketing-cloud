package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/*************************************************
 * @功能及特点的描述简述: 推荐标签列表
 * 
 * @see （与该类关联的类): TagRecommend
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.3 @date(创建、开发日期): 2016-09-21 最后修改日期: 2016-09-21
 * @复审人: 丛树林
 *************************************************/
@Document(collection = "tag_recommend")
public class TagRecommend implements Serializable {

	private static final long serialVersionUID = 5009763496605546365L;

	@Id
	private String id;
	@Field(value = "tag_id")
	private String tagId;
	@Field(value = "tag_name")
	private String tagName;
	@Field(value = "tag_list")
	private List<String> tagList;
	private int status;
	private Boolean flag;
	@Field(value = "tag_desc")
	private String tagDesc;
	@Field(value = "create_time")
	private Date createTime;
	@Field(value = "update_time")
	private Date updateTime;
	@Field(value = "tag_name_eng")
	private String tagNameEng;

    @Field(value = "source")
    private String source;
    
    private Integer seq;
    
    @Field(value = "search_mod")
    private Integer searchMod;
    
    @Field(value = "update_flag")
    private Integer updateFlag;
    
    @Field(value = "tag_version")
    private Integer tagVersion;
    
    public TagRecommend() {}
    
    public TagRecommend(String id, String tagId, String tagName, List<String> tagList, int status,
                    Boolean flag, String tagDesc, Date createTime, Date updateTime,
                    String tagNameEng, String source, Integer seq, Integer searchMod) {
        super();
        this.id = id;
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagList = tagList;
        this.status = status;
        this.flag = flag;
        this.tagDesc = tagDesc;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.tagNameEng = tagNameEng;
        this.source = source;
        this.seq = seq;
        this.searchMod = searchMod;
    }



    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
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

    public String getTagNameEng() {
        return tagNameEng;
    }

    public void setTagNameEng(String tagNameEng) {
        this.tagNameEng = tagNameEng;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    
    
    public Integer getSearchMod() {
        return searchMod;
    }

    public void setSearchMod(Integer searchMod) {
        this.searchMod = searchMod;
    }
    
    public Integer getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Integer updateFlag) {
		this.updateFlag = updateFlag;
	}
	

	public Integer getTagVersion() {
		return tagVersion;
	}

	public void setTagVersion(Integer tagVersion) {
		this.tagVersion = tagVersion;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((searchMod == null) ? 0 : searchMod.hashCode());
        result = prime * result + ((seq == null) ? 0 : seq.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + status;
        result = prime * result + ((tagDesc == null) ? 0 : tagDesc.hashCode());
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        result = prime * result + ((tagList == null) ? 0 : tagList.hashCode());
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        result = prime * result + ((tagNameEng == null) ? 0 : tagNameEng.hashCode());
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
        TagRecommend other = (TagRecommend) obj;
        if (createTime == null) {
            if (other.createTime != null)
                return false;
        } else if (!createTime.equals(other.createTime))
            return false;
        if (flag == null) {
            if (other.flag != null)
                return false;
        } else if (!flag.equals(other.flag))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (searchMod == null) {
            if (other.searchMod != null)
                return false;
        } else if (!searchMod.equals(other.searchMod))
            return false;
        if (seq == null) {
            if (other.seq != null)
                return false;
        } else if (!seq.equals(other.seq))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        if (status != other.status)
            return false;
        if (tagDesc == null) {
            if (other.tagDesc != null)
                return false;
        } else if (!tagDesc.equals(other.tagDesc))
            return false;
        if (tagId == null) {
            if (other.tagId != null)
                return false;
        } else if (!tagId.equals(other.tagId))
            return false;
        if (tagList == null) {
            if (other.tagList != null)
                return false;
        } else if (!tagList.equals(other.tagList))
            return false;
        if (tagName == null) {
            if (other.tagName != null)
                return false;
        } else if (!tagName.equals(other.tagName))
            return false;
        if (tagNameEng == null) {
            if (other.tagNameEng != null)
                return false;
        } else if (!tagNameEng.equals(other.tagNameEng))
            return false;
        if (updateTime == null) {
            if (other.updateTime != null)
                return false;
        } else if (!updateTime.equals(other.updateTime))
            return false;
        return true;
    }

}