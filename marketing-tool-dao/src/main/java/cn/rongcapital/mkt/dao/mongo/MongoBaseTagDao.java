package cn.rongcapital.mkt.dao.mongo;

import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.DataParty;

import java.util.List;

/**
 * Created by Yunfeng on 2016-9-21.
 */
public interface MongoBaseTagDao {

    boolean insertBaseTagDao(BaseTag baseTag);

    BaseTag findOneBaseTag(BaseTag baseTag);

    List<BaseTag> findBaseTagListByTagType(Integer tagType);

    BaseTag updateBaseTag(BaseTag baseTag);

    BaseTag findCustomTagLeafByTagId(String tagId);

    List<DataParty> findMDataByTagId(String tagId, Integer startIndex, Integer pageSize);

    Long findTotalMDataCount(String tagId);

    void deleteCustomTag(BaseTag baseTag);

    void deleteCustomTagLeafByTagId(String tagId);

    BaseTag findOneCustomTagBySource(BaseTag baseTag);
    
    List<BaseTag> findCustomTagLeafListByFuzzyTagName(String tagName);
}
