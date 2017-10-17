package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.service.InsertCustomTagService;
import cn.rongcapital.mkt.service.IsExistsCustomTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yunfeng on 2016-9-21.
 * 向Mongo中插入CustomTag,如果数据不合法返回null，插入成功，返回当前的CustomTag
 * 这里不支持对标签的级联增加，如果要插入的新标签的父标签还不存在，则插入失败，返回null。
 */
@Service
public class InsertCustomTagServiceImpl implements InsertCustomTagService {

    @Autowired
    private MongoBaseTagDaoImpl mongoBaseTagDao;

    @Autowired
    private IsExistsCustomTagService isExistsCustomTagService;

    @Override
    public BaseTag insertCustomTag(BaseTag baseTag) {
        if(!baseTag.validateTag()) return null;
        if(mongoBaseTagDao.findOneBaseTag(baseTag)!=null) return mongoBaseTagDao.findOneBaseTag(baseTag);
        BaseTag parentTag = baseTag.getParentTag();
        parentTag = mongoBaseTagDao.findOneBaseTag(parentTag);
        if(parentTag == null) return null;
        if(parentTag.getChildren()!= null && !parentTag.getChildren().contains(baseTag.getTagName())){
            parentTag.getChildren().add(baseTag.getTagName());
            mongoBaseTagDao.updateBaseTag(parentTag);
        }else{
            List<String> childrenList = new ArrayList<>();
            childrenList.add(baseTag.getTagName());
            parentTag.setChildren(childrenList);
            mongoBaseTagDao.updateBaseTag(parentTag);
        }
        if(mongoBaseTagDao.insertBaseTagDao(baseTag)){
            return mongoBaseTagDao.findOneBaseTag(baseTag);
        }
        return null;
    }

    @Override
    public BaseTag cascadingInsertCustomTag(BaseTag baseTag) {
        if(!baseTag.validateTag()) return null;
        if(mongoBaseTagDao.findOneBaseTag(baseTag) != null) return baseTag;
        BaseTag parentTag = baseTag.getParentTag();
        if(isExistsCustomTagService.isExistsCustomTag(parentTag)){
            insertCustomTag(baseTag);
        }else{
            String[] parentTagAncestors = parentTag.getPath().split(ApiConstant.CUSTOM_TAG_SEPARATOR);
            if(parentTagAncestors.length >= 2){
                parentTag.setParent(parentTagAncestors[ApiConstant.INT_ONE]);
            }
            cascadingInsertCustomTag(parentTag);
            insertCustomTag(baseTag);
        }
        return baseTag;
    }

    @Override
    public BaseTag insertCustomTagLeafFromSystemIn(String tagName, String tagSource) {
        if(StringUtils.isEmpty(tagName) || StringUtils.isEmpty(tagSource)) return null;
        BaseTag baseTag = new CustomTagLeaf();
        baseTag.setTagName(tagName);
        baseTag.setSource(tagSource);
        baseTag.setStatus(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
        baseTag.setCreateTime(Calendar.getInstance().getTime());
        baseTag.setParent(ApiConstant.CUSTOM_TAG_SYSTEM_PARENT);
        baseTag.setPath(ApiConstant.CUSTOM_TAG_SYSTEM_ROOT);
        return insertCustomTag(baseTag);
    }

}
