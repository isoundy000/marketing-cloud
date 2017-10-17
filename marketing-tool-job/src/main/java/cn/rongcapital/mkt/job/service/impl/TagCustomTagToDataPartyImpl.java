package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.service.TagCustomTagToDataPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Yunfeng on 2016-9-23.
 */
@Service
public class TagCustomTagToDataPartyImpl implements TaskService {

    //Todo:1.遍历DataCustomTag中同步到DataParty并且已经同步到Mongo中的数据。
    //Todo:2.根据DataCustomTag中的数据查找到标签的ID
    //Todo:3.根据DataParty中的ID查找到Mongo中对应的人的信息,然后将TagId添加到TagList当中

    protected int BATCH_SIZE = 500;
    protected Integer SYNCED_FINISHED = 2;

    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FindCustomTagInfoService findCustomTagInfoService;

    @Autowired
    private TagCustomTagToDataPartyService tagCustomTagToDataPartyService;

    @Override
    public void task(Integer taskId) {
        int totalCount = queryTotalCount();
        if(totalCount < 1) return;
        int totalPageNum = (totalCount + BATCH_SIZE - 1)/BATCH_SIZE;
        for(int i = 0; i<totalPageNum; i++){
            List<DataCustomerTags> dataCustomTagsList = querySyncedDataCustomTags(Integer.valueOf(i*BATCH_SIZE));
            if(CollectionUtils.isEmpty(dataCustomTagsList)) continue;
            for(DataCustomerTags dataCustomTag : dataCustomTagsList){
                Integer keyId = dataCustomTag.getKeyid();
                Query query = new Query(Criteria.where("mid").is(keyId));
                DataParty dataParty = mongoTemplate.findOne(query,DataParty.class);
                if(dataParty != null){
                    BaseTag baseTag = findCustomTagInfoService.findCustomTagByTag(buildBaseTag(dataCustomTag));
                    tagCustomTagToDataPartyService.tagCustomTagToDataParty(dataParty,baseTag);
                }
            }
        }
    }

    private List<DataCustomerTags> querySyncedDataCustomTags(Integer startIndex) {
        DataCustomerTags paramDataCustomTags = new DataCustomerTags();
        paramDataCustomTags.setStatus(SYNCED_FINISHED.byteValue());
        paramDataCustomTags.setStartIndex(startIndex);
        paramDataCustomTags.setPageSize(BATCH_SIZE);
        return dataCustomerTagsDao.selectList(paramDataCustomTags);
    }

    private int queryTotalCount() {
        DataCustomerTags paramDataCustomerTags = new DataCustomerTags();
        paramDataCustomerTags.setStatus(SYNCED_FINISHED.byteValue());
        return dataCustomerTagsDao.selectListCount(paramDataCustomerTags);
    }

    private BaseTag buildBaseTag(DataCustomerTags paramDataCustomTags) {
        BaseTag baseTag = new CustomTagLeaf();
        baseTag.setTagName(paramDataCustomTags.getTagName());
        baseTag.setSource(paramDataCustomTags.getSource());
        String tagPath = null;
        String tagParent = null;
        if(paramDataCustomTags.getTagTypeLayerThree() != null){
            tagPath = ApiConstant.CUSTOM_TAG_SEPARATOR + paramDataCustomTags.getTagTypeLayerThree()
                    + ApiConstant.CUSTOM_TAG_SEPARATOR + paramDataCustomTags.getTagTypeLayerTwo()
                    + ApiConstant.CUSTOM_TAG_SEPARATOR + paramDataCustomTags.getTagTypeLayerOne()
                    + ApiConstant.CUSTOM_TAG_ROOT;
            tagParent = paramDataCustomTags.getTagTypeLayerThree();
        }else if(paramDataCustomTags.getTagTypeLayerTwo() != null){
            tagPath = ApiConstant.CUSTOM_TAG_SEPARATOR + paramDataCustomTags.getTagTypeLayerTwo()
                    + ApiConstant.CUSTOM_TAG_SEPARATOR + paramDataCustomTags.getTagTypeLayerOne()
                    + ApiConstant.CUSTOM_TAG_ROOT;
            tagParent = paramDataCustomTags.getTagTypeLayerTwo();
        }else{
            tagPath = ApiConstant.CUSTOM_TAG_SEPARATOR + paramDataCustomTags.getTagTypeLayerOne()
                    + ApiConstant.CUSTOM_TAG_ROOT;
            tagParent = paramDataCustomTags.getTagTypeLayerOne();
        }
        baseTag.setParent(tagParent);
        baseTag.setPath(tagPath);
        baseTag.setCreateTime(paramDataCustomTags.getCreateTime());
        return baseTag;
    }
}
