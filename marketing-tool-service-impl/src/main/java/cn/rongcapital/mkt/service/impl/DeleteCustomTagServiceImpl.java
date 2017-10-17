package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.service.DeleteCustomTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Yunfeng on 2016-9-27.
 */
@Service
public class DeleteCustomTagServiceImpl implements DeleteCustomTagService {

    @Autowired
    private MongoBaseTagDaoImpl mongoBaseTagDao;

    @Override
    public void deleteCustomTagByTagNameAndTagSource(String tagName, String tagSource) {
        BaseTag baseTag = new CustomTagLeaf();
        baseTag.setTagName(tagName);
        baseTag.setSource(tagSource);
        baseTag.setPath(ApiConstant.CUSTOM_TAG_SYSTEM_ROOT);
        mongoBaseTagDao.deleteCustomTag(baseTag);
    }

    @Override
    public void deleteCustomTagLeafByTagId(String tagId) {
        if(StringUtils.isEmpty(tagId)) return;
        mongoBaseTagDao.deleteCustomTagLeafByTagId(tagId);
    }

}
