package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * 根据页面输入值模糊查询标签，返回标签或者标签值 （全路径的标签或者标签值《带有标签类型：标签值，标签》分页
 * 
 * 接口：mkt.tag.system.fuzzy.list.get
 * 
 * @param tagName
 * @param index
 * @param size
 * @return
 * @author shuiyangyang
 * @date 2016-11-11
 */
public class TagSystemFuzzyListGetOut {
    private String tagId;   //标签ID

    private String tagName; //标签名称  

    private String tagValue;//标签值

    private String tagPath; //标签路径

    private Boolean isTag = false;   //是否是标签，0-标签，1-标签值
    
    private Integer searchMod;  //搜索标识
    
    private String tagValueSeq; // 标签值序号
    
    private String tagCover;
    
    private Boolean flag;
    
    public TagSystemFuzzyListGetOut(){}

    public TagSystemFuzzyListGetOut(String tagId, String tagName, String tagValue, String tagPath,
                    String isTag, Integer searchMod, String tagValueSeq) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.tagPath = tagPath;
        this.isTag = "1".equals(isTag);
        this.searchMod = searchMod;
        this.tagValueSeq = tagValueSeq;
    }

    @JsonProperty("tag_id")
    public String getTagId() {
        return tagId==null?"":tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @JsonProperty("tag_name")
    public String getTagName() {
        return tagName==null?"":tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonProperty("tag_value")
    public String getTagValue() {
        return tagValue==null?"":tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @JsonProperty("tag_path")
    public String getTagPath() {
        return tagPath==null?"":tagPath;
    }

    public void setTagPath(String tagPath) {
        this.tagPath = tagPath;
    }

    @JsonProperty("is_tag")
    public Boolean getIsTag() {
        return isTag;
    }

    public void setIsTag(String isTag) {
        this.isTag = "1".equals(isTag);
    }

    @JsonProperty("search_mod")
    public Integer getSearchMod() {
        return searchMod;
    }

    public void setSearchMod(Integer searchMod) {
        this.searchMod = searchMod;
    }

    @JsonProperty("tag_value_seq")
    public String getTagValueSeq() {
        return tagValueSeq;
    }

    public void setTagValueSeq(String tagValueSeq) {
        this.tagValueSeq = tagValueSeq;
    }
    
    @JsonProperty("tag_cover")
    public String getTagCover() {
        return tagCover;
    }

    public void setTagCover(String tagCover) {
        this.tagCover = tagCover;
    }
    
    @JsonProperty("flag")
    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "TagSystemFuzzyListGetOut [tagId=" + tagId + ", tagName=" + tagName + ", tagValue=" + tagValue
                + ", tagPath=" + tagPath + ", isTag=" + isTag + ", searchMod=" + searchMod + ", tagValueSeq="
                + tagValueSeq + ", tagCover=" + tagCover + ", flag=" + flag + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((isTag == null) ? 0 : isTag.hashCode());
        result = prime * result + ((searchMod == null) ? 0 : searchMod.hashCode());
        result = prime * result + ((tagCover == null) ? 0 : tagCover.hashCode());
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
        result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
        result = prime * result + ((tagPath == null) ? 0 : tagPath.hashCode());
        result = prime * result + ((tagValue == null) ? 0 : tagValue.hashCode());
        result = prime * result + ((tagValueSeq == null) ? 0 : tagValueSeq.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TagSystemFuzzyListGetOut other = (TagSystemFuzzyListGetOut) obj;
        if (flag == null) {
            if (other.flag != null) return false;
        } else if (!flag.equals(other.flag)) return false;
        if (isTag == null) {
            if (other.isTag != null) return false;
        } else if (!isTag.equals(other.isTag)) return false;
        if (searchMod == null) {
            if (other.searchMod != null) return false;
        } else if (!searchMod.equals(other.searchMod)) return false;
        if (tagCover == null) {
            if (other.tagCover != null) return false;
        } else if (!tagCover.equals(other.tagCover)) return false;
        if (tagId == null) {
            if (other.tagId != null) return false;
        } else if (!tagId.equals(other.tagId)) return false;
        if (tagName == null) {
            if (other.tagName != null) return false;
        } else if (!tagName.equals(other.tagName)) return false;
        if (tagPath == null) {
            if (other.tagPath != null) return false;
        } else if (!tagPath.equals(other.tagPath)) return false;
        if (tagValue == null) {
            if (other.tagValue != null) return false;
        } else if (!tagValue.equals(other.tagValue)) return false;
        if (tagValueSeq == null) {
            if (other.tagValueSeq != null) return false;
        } else if (!tagValueSeq.equals(other.tagValueSeq)) return false;
        return true;
    }

}
