package cn.rongcapital.mkt.po.mongodb;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.po.base.BaseTag;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yunfeng on 2016-9-21.
 */
public class CustomTagTypeLayer extends BaseTag{

    public CustomTagTypeLayer(){
        this.setTagType(ApiConstant.CUSTOM_TAG_LAYER_TYPE);
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
        return super.validateTag();
    }
}
