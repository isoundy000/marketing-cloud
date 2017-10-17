package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by byf on 11/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagListSecondarySearchIn extends BaseInput{

    @NotEmpty
    private String tagId;

    @NotEmpty
    private String tagName;

    private String keyWord;
    private List<SystemValueIn> selectTagValueList;

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

    @JsonProperty("key_word")
    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @JsonProperty("select_tag_value_list")
    public List<SystemValueIn> getSelectTagValueList() {
        return selectTagValueList;
    }

    public void setSelectTagValueList(List<SystemValueIn> selectTagValueList) {
        this.selectTagValueList = selectTagValueList;
    }
}
