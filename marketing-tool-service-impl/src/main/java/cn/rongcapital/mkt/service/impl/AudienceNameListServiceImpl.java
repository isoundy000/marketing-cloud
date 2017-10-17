/*************************************************
 * @功能简述: AudienceListService实现类
 * @see: MkyApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/6/6
 *************************************************/


package cn.rongcapital.mkt.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;

import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.AudienceNameListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceNameListServiceImpl implements AudienceNameListService {
    @Autowired
    AudienceListDao audienceListDao;

    @Autowired
    SegmentationHeadDao segmentationHeadDao;


    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput audienceNameList(String userToken) {

        AudienceList audiencelist=new AudienceList();
        SegmentationHead segmentationHead=new SegmentationHead();
        
        List<AudienceList> audList = audienceListDao.selectList(audiencelist);

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        if (!audList.isEmpty()) {


            for (AudienceList audience : audList) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("audience_list_id", audience.getId());
                map.put("audience_list_name", audience.getAudienceName());
                map.put("audience_list_type", "0");

                result.getData().add(map);
            }
        }
        
        List<SegmentationHead> segList = segmentationHeadDao.selectList(segmentationHead);
        
        if (!segList.isEmpty()) {


            for (SegmentationHead segment: segList) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("audience_list_id", segment.getId());
                map.put("audience_list_name", segment.getName());
                map.put("audience_list_type", "1");

                result.getData().add(map);
            }
        }


        result.setTotal(result.getData().size());
        return result;
    }



}
