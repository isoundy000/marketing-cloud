package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.CustomTagMapEnum;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagTypeLayer;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.DataUpateMainSegmenttagService;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.service.InsertCustomTagService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

/**
 * @author nianjun
 */
@Service
public class DataUpateMainSegmenttagServiceImpl implements DataUpateMainSegmenttagService {

    @Autowired
    private CustomTagDao customTagDao;

    @Autowired
    private CustomTagMapDao customTagMapDao;
    
    @Autowired
    private MongoBaseTagDaoImpl mongoBaseTagDao;
    
    @Autowired
    private InsertCustomTagService insertCustomTagService;
    
    @Autowired
    private FindCustomTagInfoService findCustomTagInfoService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateMainSegmenttag(String method, String userToken, String ver, String tagName,
                    Integer contactId) {

        boolean result = false;

        if (StringUtils.isEmpty(tagName)) {
            return result;
        }

        Set<String> tagNames = new HashSet<>();

        if (tagName.contains(",")) {
            String[] tagNameArray = tagName.split(",");
            if (tagNameArray.length < 1) {
                return result;
            }

            // 做个简单的去重操作
            for (String tag : tagNameArray) {
                tagNames.add(tag);
            }
        } else {
            tagNames.add(tagName);
        }

        // 这个result意义不大
        result = true;
        for (String tag : tagNames) {
            result = result & updateTag(tag, contactId);
        }

        return result;
    }

    private boolean updateTag(String tagName, Integer contactId) {
        // step 1 检查tag是否已经存在数据库中
    	Date createTime = new Date();
        CustomTagTypeLayer customTagTypeLayer=new CustomTagTypeLayer();
        customTagTypeLayer.setTagName(tagName);
        customTagTypeLayer.setSource(TagSourceEnum.SEGMENTATION_SOURCE_ACCESS.getTagSourceName());
        BaseTag baseTag=mongoBaseTagDao.findOneCustomTagBySource(customTagTypeLayer);
        
        // 不存在则入库
        if (baseTag==null) {
        	insertCustomTagService.insertCustomTagLeafFromSystemIn(tagName, TagSourceEnum.SEGMENTATION_SOURCE_ACCESS.getTagSourceName());
            
        } //存在则不处理，不用在更新覆盖人群，因为mongo中已无覆盖人群字段
        
        
        // step 2 将用户id与tag关联起来
        // step 2.1 根据tag_id查询用户是否已经关联该tag
        BaseTag existBaseTag=mongoBaseTagDao.findOneCustomTagBySource(customTagTypeLayer);       
        List<DataParty> lists=findCustomTagInfoService.findMDataByTagId(existBaseTag.getTagId(), null, null);
        
        CustomTagMap paramCustomTagMap = new CustomTagMap();
        paramCustomTagMap.setTagId(existBaseTag.getTagId());
        paramCustomTagMap.setMapId(Integer.toString(contactId));       

        // step 2.2 如果没有将该tag关联用户 , 则插入数据库数据
        if (lists==null || lists.size()<1) {
            paramCustomTagMap.setTagSource(Integer.valueOf(CustomTagMapEnum.AUDIENCE.getCode()));
            paramCustomTagMap.setTagId(existBaseTag.getTagId());
            paramCustomTagMap.setStatus(Byte.valueOf(StatusEnum.ACTIVE.getStatusCode().toString()));
            paramCustomTagMap.setCreateTime(createTime);
            customTagMapDao.insert(paramCustomTagMap);

            return true;
        }
        return false;
    }

    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput getMainSegmenttagNames(Integer map_id) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        CustomTagMap customTagMap = new CustomTagMap();
        customTagMap.setMapId(Integer.toString(map_id));
        customTagMap.setStatus(new Byte("0"));
        List<CustomTagMap> customTagMapList = customTagMapDao.selectList(customTagMap);

        for (CustomTagMap customTagMap2 : customTagMapList) {
            BaseTag baseTag=findCustomTagInfoService.findCustomTagInfoByTagId(customTagMap2.getTagId());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tag_name", baseTag.getTagName());

            result.getData().add(map);
        }

        result.setTotal(customTagMapList.size());
        return result;
    }


}
