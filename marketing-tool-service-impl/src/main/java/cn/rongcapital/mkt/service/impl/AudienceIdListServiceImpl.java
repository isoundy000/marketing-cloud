/*************************************************
 * @功能简述: AudienceIdListService实现类
 * @see: MkyApi
 * @author: Xu Kun
 * @version: 1.0
 * @date: 2016/6/30
 *************************************************/


package cn.rongcapital.mkt.service.impl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.AudienceIdListService;

import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceIdListServiceImpl implements AudienceIdListService {
    
    @Autowired
    AudienceListDao audienceListDao;

    @Autowired
    AudienceListPartyMapDao audienceListPartyMapDao;
    
    @Autowired
    SegmentationHeadDao segmentationHeadDao;

    public static final Integer POOL_INDEX = 2;
    
    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput audienceIdList(String userToken,String audience_ids,String audience_type) {
        
        List<Integer> audienceList=new ArrayList<Integer>();   
        
        List<String> audPartyIdList =new ArrayList<String>();
        
        String[] aud_idList=audience_ids.split(",");
        
        for(String aud_id : aud_idList){                
            audienceList.add(Integer.parseInt(aud_id));                 
        }
        
        
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        
        //细分人群
        if(audience_type.equals("0")){
            
                                    
//            if(aud_idList[0].equals("0")){
//                
//                List<Segment> segmentList=mongoTemplate.findAll(Segment.class,"segment");
//                
//                for(Segment segment : segmentList){
//                    
//                    String keyid=segment.getMappingKeyid();
//                    result.getData().add(keyid);
//                }
//                            
//                result.setTotal(segmentList.size());
//                
//                
//            }else{
//                
//                //需要去重               
//                
//                //Query query = new Query();                
//                //query1.addCriteria(Criteria.where("segmentation_head_id").is(157));
//                //List segList=mongoTemplate.getCollection("segment").distinct("mapping_keyid",query1.getQueryObject());
//                
//                Query query = new Query();
//                query.addCriteria(Criteria.where("segmentation_head_id").in(audienceList));                                
//                List segList=mongoTemplate.getCollection("segment").distinct("mapping_keyid",query.getQueryObject());
//                
//                for(int i=0;i<segList.size();i++){
//                    
//                    result.getData().add(segList.get(i));
//                }                
//                result.setTotal(segList.size());
//                
//            }
        	
        	List<String> headIds = new ArrayList<String>();
        	
        	String[] arrHeadIds;
        	
            if(aud_idList[0].equals("0")){
                
            	SegmentationHead segmentationHead = new SegmentationHead();
            	
            	segmentationHead.setStartIndex(null);
            	segmentationHead.setPageSize(null);
            	
            	List<SegmentationHead> segmentationHeadList = segmentationHeadDao.selectList(segmentationHead);
                
            	for(SegmentationHead segmentation : segmentationHeadList){
            		headIds.add("segmentcoverid:"+segmentation.getId()+"");
            	}
            	
            }else{
                
            	for(Integer headids : audienceList){
            		headIds.add("segmentcoverid:"+ headids);
            	}
            	
            }
           
            arrHeadIds = (String[]) headIds.toArray(new String[headIds.size()]);
            
        	Set<String> mids = new HashSet<String>();
        	
        	try {
        		 mids = JedisClient.sunion(POOL_INDEX,arrHeadIds);
			} catch (JedisException e) {
				e.printStackTrace();
			}
        	
        	result.getData().add(mids);
        	result.setTotal(mids.size());
            
        }
        
        
      //固定人群,
        if(audience_type.equals("1")){
        
            
            if(aud_idList[0].equals("0")){
                
                audPartyIdList = audienceListPartyMapDao.selectPartyIdList(null);
                
            }else{
                                        
                audPartyIdList = audienceListPartyMapDao.selectPartyIdList(audienceList);
                
            }
            
          for(int i=0;i<audPartyIdList.size();i++){
              
              result.getData().add(audPartyIdList.get(i));
          }
        
          
          result.setTotal(audPartyIdList.size());
          
        }
        
       
        
       
        return result;
    }



}
