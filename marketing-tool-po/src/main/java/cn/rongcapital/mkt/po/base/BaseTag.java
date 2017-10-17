package cn.rongcapital.mkt.po.base;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.po.mongodb.CustomTagTypeLayer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Yunfeng on 2016-9-21.
 */
@Document(collection = "custom_tag_tree")
public class BaseTag implements Serializable {

    private static final long serialVersionUID = -8650105374962428497L;

    @Id
    private String id;

    @Field(value = "tag_id")
    private String tagId;

    @Field(value = "tag_name")
    private String tagName;

    @Field(value = "tag_type")
    private Integer tagType;

    @Field(value = "level")
    private Integer level;

    @Field(value = "children")
    private List<String> children;

    @Field(value = "parent")
    private String parent;

    @Field(value = "create_time")
    private Date createTime;

    @Field(value = "update_time")
    private Date updateTime;

    @Field(value = "path")
    private String path;

    @Field(value = "status")
    private Integer status;

    @Field(value = "source")
    private String source;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public boolean validateTag(){
        if(this.getTagName() == null || (this.getPath() == null && !"自定义".equals(this.getTagName()))){
            return false;
        }
        return true;
    }

    public List<BaseTag> getChildrenTags(){
        return null;
    }

    public boolean removeChildrenTags(String tagName){
        return false;
    }

    public boolean addChildren(String tagName){
        return false;
    }

    //返回的ParentTag只包含了ParentTag的唯一标识，要获取完整的信息，需要从数据库里搜索，数据库里搜索不到则认为是非法Tag
    public BaseTag getParentTag(){
        if(this.getParent() == null) return null;
        BaseTag parentTag = new CustomTagTypeLayer();
        parentTag.setTagName(this.getParent());
        parentTag.setPath(this.getPath().substring(this.getParent().length()+ApiConstant.CUSTOM_TAG_SEPARATOR.length()));
        if("".equals(parentTag.getPath())) parentTag.setPath(null);
        return parentTag;
    }
}
