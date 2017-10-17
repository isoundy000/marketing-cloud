package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.OriginalDataCustomerTagsDao;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.OriginalDataCustomerTags;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.service.InsertCustomTagService;
import cn.rongcapital.mkt.service.IsExistsCustomTagService;
import cn.rongcapital.mkt.service.OriginalDataCustomTagScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bianyulong on 16/6/24.
 */
@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class OriginalDataCustomTagScheduleServiceImpl implements OriginalDataCustomTagScheduleService, TaskService {

    @Autowired
    private OriginalDataCustomerTagsDao originalDataCustomerTagsDao;

    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;

    @Autowired
    private MongoBaseTagDaoImpl mongoBaseTagDao;

    @Autowired
    private IsExistsCustomTagService isExistsCustomTagService;

    @Autowired
    private InsertCustomTagService insertCustomTagService;
    
	@Autowired
	Environment env;
	
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void cleanData() {
    	
        int BATCH_NUM = Integer.valueOf(env.getProperty("orginal.to.data.batch.num"));
        
        OriginalDataCustomerTags paramOriginalDataCustomTags = new OriginalDataCustomerTags();
        paramOriginalDataCustomTags.setStatus(StatusEnum.ACTIVE.getStatusCode().byteValue());
        int totalCount = originalDataCustomerTagsDao.selectListCount(paramOriginalDataCustomTags);
        int totalPages = (totalCount + BATCH_NUM - 1) / BATCH_NUM;
        paramOriginalDataCustomTags.setPageSize(BATCH_NUM);
        for (int i = 0; i < totalPages; i++) {
            paramOriginalDataCustomTags.setStartIndex(Integer.valueOf(i * BATCH_NUM));
            List<OriginalDataCustomerTags> originalDataCustomerTags =
                    originalDataCustomerTagsDao.selectList(paramOriginalDataCustomTags);
            if (originalDataCustomerTags.isEmpty()) {
                continue;
            }
            handleOriginalDataCustomTags(originalDataCustomerTags);
        }
    }

    // 处理OriginalDataArchPoint的数据
    private void handleOriginalDataCustomTags(List<OriginalDataCustomerTags> tmpOriginalDataCustomTags) {
        if (tmpOriginalDataCustomTags.isEmpty()) {
            return;
        }

        int batchCount = tmpOriginalDataCustomTags.size();

        List<DataCustomerTags> dataCustomerTags = new ArrayList<>(batchCount);
        // 将OriginalDataCustomTags的数据同步到DataCustomTags
        for (int i = 0; i < batchCount; i++) {
            DataCustomerTags paramDataCustomTags = new DataCustomerTags();
            OriginalDataCustomerTags tmpOriginalDataCustomerTag = tmpOriginalDataCustomTags.get(i);
            BeanUtils.copyProperties(tmpOriginalDataCustomerTag, paramDataCustomTags);

            //将用户上传文件上传的CustomTag保存到Mongo中
            insertCustomTagByCustomTagFile(paramDataCustomTags);

            // 因为在一个事务里 , 直接修改OriginalDataCustomTags的状态
            tmpOriginalDataCustomerTag.setStatus(StatusEnum.PROCESSED.getStatusCode().byteValue());
            originalDataCustomerTagsDao.updateById(tmpOriginalDataCustomerTag);
            dataCustomerTags.add(paramDataCustomTags);
        }

        Map<String, List<DataCustomerTags>> paramMap = new HashMap<>();
        paramMap.put("list", dataCustomerTags);

        dataCustomerTagsDao.cleanAndUpdateByOriginal(paramMap);
    }

    private void insertCustomTagByCustomTagFile(DataCustomerTags paramDataCustomTags) {
        BaseTag baseTag = buildBaseTag(paramDataCustomTags);
        if(!isExistsCustomTagService.isExistsCustomTag(baseTag)){
            insertCustomTagService.cascadingInsertCustomTag(baseTag);
        }
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

    @Override
    public void task(Integer taskId) {
        cleanData();
    }
}
