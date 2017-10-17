package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagValueCountDao;
import cn.rongcapital.mkt.po.TagValueCount;
import cn.rongcapital.mkt.service.TagSystemValueListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
@Service
public class TagSystemValueListGetServiceImpl implements TagSystemValueListGetService{
    
    private static final String IS_TAG_VALUE = "0";
    
    @Autowired
    private TagValueCountDao tagValueCountDao;

    /**
     * 获取标签值
     * 
     * 接口：mkt.tag.system.value.list.get
     * @param tagId
     * @return
     * @author shuiyangyang
     * @Date 2016-11-14
     */
    @Override
    public BaseOutput getTagSystemValueList(String tagId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        
        TagValueCount tagValueCountSearch = new TagValueCount();
        tagValueCountSearch.setTagId(tagId);
        tagValueCountSearch.setIsTag(TagSystemValueListGetServiceImpl.IS_TAG_VALUE);
        tagValueCountSearch.setStartIndex(null);
        tagValueCountSearch.setPageSize(null);
        
        // 根据tag_id 查询数据
        List<TagValueCount> tagValueCountLists = tagValueCountDao.selectList(tagValueCountSearch);
        
        if(tagValueCountLists != null && tagValueCountLists.size() > 0) {
            // 设置数量
            result.setTotal(tagValueCountLists.size());
            
            for(TagValueCount tagValueCountList : tagValueCountLists) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tag_value", tagValueCountList.getTagValue());
                map.put("tag_value_seq", tagValueCountList.getTagValueSeq());
                map.put("value_count", tagValueCountList.getValueCount());
                result.getData().add(map);
            }
        }
        
        return result;
    }

}
