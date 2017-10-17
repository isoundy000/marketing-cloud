package cn.rongcapital.mkt.po.mongodb;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.po.base.BaseTag;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yunfeng on 2016-9-21.
 */
public class CustomTagLeaf extends BaseTag{

    public CustomTagLeaf(){
        this.setTagType(ApiConstant.CUSTOM_TAG_LEAF_TYPE);
        this.setTagId(RandomStringUtils.random(10,true,true) + System.currentTimeMillis());
    }

    @Override
    public List<BaseTag> getChildrenTags() {
        return null;
    }

    @Override
    public boolean removeChildrenTags(String tagName) {
        return false;
    }

    @Override
    public boolean addChildren(String tagName) {
        return false;
    }

    @Override
    public BaseTag getParentTag() {
        return super.getParentTag();
    }

    @Override
    public boolean validateTag(){
        if(super.validateTag()){
            if(this.getParent() != null && this.getSource() != null){
                return true;
            }
        }
        return false;
    }

}
