package cn.rongcapital.mkt.vo.out;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 系统标签的out
 * 
 * @return
 * @author shuiyangyang
 * @Date 2016-11-09
 */
public class TagSystemTreeTagOut {
    private String tagId;
    private String tagName;
    private Boolean flag;
    private String tagNameEng;
    private Integer searchMod;
    private List<String> tagList;
    private String tagCover ;
    private String tagDesc;
    
    public TagSystemTreeTagOut(){}
    
    

    public TagSystemTreeTagOut(String tagId, String tagName, Boolean flag, String tagCover) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
		this.flag = flag;
		this.tagCover = tagCover;
	}


	public TagSystemTreeTagOut(String tagId, String tagName, Boolean flag, List<String> tagList, String tagCover) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
		this.flag = flag;
		this.tagList = tagList;
		this.tagCover = tagCover;
	}


	public TagSystemTreeTagOut(String tagId, String tagName, Boolean flag, String tagNameEng, Integer searchMod,
                    List<String> tagList) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.flag = flag;
        this.tagNameEng = tagNameEng;
        this.searchMod = searchMod;
        this.tagList = tagList;
    }
    

    public TagSystemTreeTagOut(String tagId, String tagName, Boolean flag, String tagNameEng, Integer searchMod,
            String tagCover, String tagDesc) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.flag = flag;
        this.tagNameEng = tagNameEng;
        this.searchMod = searchMod;
        this.tagCover = tagCover;
        this.tagDesc = tagDesc;
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

    @JsonProperty("flag")
    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @JsonProperty("tag_name_eng")
    public String getTagNameEng() {
        return tagNameEng;
    }

    public void setTagNameEng(String tagNameEng) {
        this.tagNameEng = tagNameEng;
    }
    
    @JsonProperty("search_mod")
    public Integer getSearchMod() {
        return searchMod;
    }

    public void setSearchMod(Integer searchMod) {
        this.searchMod = searchMod;
    }

    @JsonProperty("tag_list")
    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
    
    @JsonProperty("tag_cover")
    public String getTagCover() {
        return tagCover;
    }

    public void setTagCover(String tagCover) {
        this.tagCover = tagCover;
    }
    
    @JsonProperty("tag_desc")
    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((searchMod == null) ? 0 : searchMod.hashCode());
        result = prime * result + ((tagCover == null) ? 0 : tagCover.hashCode());
        result = prime * result + ((tagDesc == null) ? 0 : tagDesc.hashCode());
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        result = prime * result + ((tagList == null) ? 0 : tagList.hashCode());
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        result = prime * result + ((tagNameEng == null) ? 0 : tagNameEng.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TagSystemTreeTagOut other = (TagSystemTreeTagOut) obj;
        if (flag == null) {
            if (other.flag != null) return false;
        } else if (!flag.equals(other.flag)) return false;
        if (searchMod == null) {
            if (other.searchMod != null) return false;
        } else if (!searchMod.equals(other.searchMod)) return false;
        if (tagCover == null) {
            if (other.tagCover != null) return false;
        } else if (!tagCover.equals(other.tagCover)) return false;
        if (tagDesc == null) {
            if (other.tagDesc != null) return false;
        } else if (!tagDesc.equals(other.tagDesc)) return false;
        if (tagId == null) {
            if (other.tagId != null) return false;
        } else if (!tagId.equals(other.tagId)) return false;
        if (tagList == null) {
            if (other.tagList != null) return false;
        } else if (!tagList.equals(other.tagList)) return false;
        if (tagName == null) {
            if (other.tagName != null) return false;
        } else if (!tagName.equals(other.tagName)) return false;
        if (tagNameEng == null) {
            if (other.tagNameEng != null) return false;
        } else if (!tagNameEng.equals(other.tagNameEng)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "TagSystemTreeTagOut [tagId=" + tagId + ", tagName=" + tagName + ", flag=" + flag + ", tagNameEng="
                + tagNameEng + ", searchMod=" + searchMod + ", tagList=" + tagList + ", tagCover=" + tagCover
                + ", tagDesc=" + tagDesc + "]";
    }
    
    
}
