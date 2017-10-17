package cn.rongcapital.mkt.vo.out;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 接口 mkt.tag.system.flag.list.get 返回值
 * 
 * @author shuiyangyang
 * @Date 2016-11-09
 */
public class TagSystemFlagListGetOut {
    private String tagId;
    private String tagName;
    private List<String> tagList;
    private Boolean flag;
    private String tagDesc;
    private String tagNameEng;
    private Integer seq;
    private Integer searchMod;
    
    public TagSystemFlagListGetOut(){}
    
    public TagSystemFlagListGetOut(String tagId, String tagName, List<String> tagList, Boolean flag,
                    String tagDesc, String tagNameEng, Integer seq, Integer searchMod) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagList = tagList;
        this.flag = flag;
        this.tagDesc = tagDesc;
        this.tagNameEng = tagNameEng;
        this.seq = seq;
        this.searchMod = searchMod;
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
    @JsonProperty("tag_List")
    public List<String> getTagList() {
        return tagList;
    }
    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
    @JsonProperty("tag_flag")
    public Boolean getFlag() {
        return flag;
    }
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    @JsonProperty("tag_desc")
    public String getTagDesc() {
        return tagDesc;
    }
    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }
    @JsonProperty("tag_name_eng")
    public String getTagNameEng() {
        return tagNameEng;
    }
    public void setTagNameEng(String tagNameEng) {
        this.tagNameEng = tagNameEng;
    }
    @JsonProperty("tag_seq")
    public Integer getSeq() {
        return seq;
    }
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
    @JsonProperty("search_mod")
    public Integer getSearchMod() {
        return searchMod;
    }
    public void setSearchMod(Integer searchMod) {
        this.searchMod = searchMod;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((searchMod == null) ? 0 : searchMod.hashCode());
        result = prime * result + ((seq == null) ? 0 : seq.hashCode());
        result = prime * result + ((tagDesc == null) ? 0 : tagDesc.hashCode());
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        result = prime * result + ((tagList == null) ? 0 : tagList.hashCode());
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        result = prime * result + ((tagNameEng == null) ? 0 : tagNameEng.hashCode());
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
        TagSystemFlagListGetOut other = (TagSystemFlagListGetOut) obj;
        if (flag == null) {
            if (other.flag != null)
                return false;
        } else if (!flag.equals(other.flag))
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
        return true;
    }

    @Override
    public String toString() {
        return "TagSystemFlagListGetOut [tagId=" + tagId + ", tagName=" + tagName + ", tagList="
                        + tagList + ", flag=" + flag + ", tagDesc=" + tagDesc + ", tagNameEng="
                        + tagNameEng + ", seq=" + seq + ", searchMod=" + searchMod + "]";
    }
    
    
}



