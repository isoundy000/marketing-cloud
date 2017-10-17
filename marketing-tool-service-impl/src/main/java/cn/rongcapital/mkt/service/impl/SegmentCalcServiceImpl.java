package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisConnectionManager;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.service.SegmentCalcService;
import cn.rongcapital.mkt.vo.SegmentGroupRedisVO;
import cn.rongcapital.mkt.vo.SegmentGroupTagRedisVO;
import cn.rongcapital.mkt.vo.SegmentGroupTagValueRedisVO;
import cn.rongcapital.mkt.vo.SegmentRedisVO;
import cn.rongcapital.mkt.vo.in.SegmentCreUpdateIn;
import cn.rongcapital.mkt.vo.in.SystemTagIn;
import cn.rongcapital.mkt.vo.in.SystemValueIn;
import cn.rongcapital.mkt.vo.in.TagGroupsIn;
import redis.clients.jedis.Jedis;

@Service
public class SegmentCalcServiceImpl implements SegmentCalcService {
    
    private static Logger logger = LoggerFactory.getLogger(SegmentCalcServiceImpl.class);
    public final static int OPER_INTERSECTION=1 ;  //交集
    public final static int OPER_UNION=2 ;  //并集
    public final static int OPER_DIFFERENCE=3 ;  //差集
    
    public final static String KEY_PREFIX_TAGCOVERID="tagcoverid:"; //ids前缀
    public final static String KEY_PREFIX_TAGCOVERID_ALL="tagcoverid:all"; //key:tagcoverid:all,用于计算排除的差集
    public final static String KEY_PREFIX_UNION="tagcoverid:union";  //并集前缀
    public final static String KEY_PREFIX_INTER="tagcoverid:inter";//交集前缀
    public final static String KEY_PREFIX_GROUPINTER="tagcoverid:groupinter";//组交集前缀
    public final static String KEY_PREFIX_DIFF="tagcoverid:diff:"; //差集前缀
    public final static String KEY_PREFIX_SEGMENTCOVER="segmentcover:"; //细分segment前缀
    public final static String KEY_PREFIX_SEGMENTCOVERID="segmentcoverid:"; //细分segmentcover ids前缀
    
    public final static int REDISDB_INDEX=2; //数据库索引
    public final static int REDIS_EXPIRE_SECOND=36000;  //10小时过期
    public final static Integer GROUP_CHANGE=1; //组内容改变，需要重新计算
    
    private ThreadLocal<SegmentRedisVO> segmentRedis=new ThreadLocal<SegmentRedisVO>(); //存储漏斗计算结果
    private ThreadLocal<String> uuid=new ThreadLocal<String>();    
    private ThreadLocal<ArrayList<String>> tempKeys=new ThreadLocal<ArrayList<String>>();
    
    /**
     * 计算整个细分覆盖
     */
    @Override
    public void calcSegmentCover(SegmentCreUpdateIn segment) {
       Long startTimeSegmentCalc=System.currentTimeMillis(); 
       if(segment==null) return;
       uuid.set(GenerateUUid.generateShortUuid());
       logger.info("uuid:"+ uuid.get());  
       segmentRedis.set(new SegmentRedisVO());
       segmentRedis.get().setSegmentCoverCount(0L);
       segmentRedis.get().setSegmentCoverIds("");
       Long defaultSegmentHeadId=new Long(0);
       if(segment.getSegmentHeadId()!=null) {
           defaultSegmentHeadId=segment.getSegmentHeadId();
       }
       segmentRedis.get().setSegmentHeadId(defaultSegmentHeadId);
       segmentRedis.get().setSegmentName(segment.getSegmentName());
       String updataTime=DateUtil.getStringFromDate(new Date(),"yyyy-MM-dd HH:mm:ss");
       segmentRedis.get().setUpdateTime(updataTime);
           
             
       List<TagGroupsIn> tagGroups=segment.getFilterGroups();
       if(tagGroups==null||tagGroups.size()<1) {
           logger.info("Segment's groups are null");
           return;
       }
       
       Jedis jedis = JedisConnectionManager.getConnection(); 
       jedis.select(REDISDB_INDEX);
       try{
           for(TagGroupsIn tagGroup:tagGroups) {
               this.initSegmentRedis(tagGroup,jedis);
               this.calcSegmentCoverByGroup(tagGroup,jedis);
           }   
           this.calcSegmentTotalCover(jedis);
       }catch (Exception e) {
           logger.info("redis Exception:"+e.toString());                  
       } finally {                  
           JedisConnectionManager.closeConnection(jedis);            
          
       }
       Long endTimeSegmentCalc=System.currentTimeMillis();    
       logger.info("Segment calculation costs="+(endTimeSegmentCalc-startTimeSegmentCalc)+"ms");
       this.loggerSegment();
       //this.clearTempRedisKeys();
       return;

    }

    /**
     * 计算分组内的标签覆盖
     * @param taggroup 组
     * @return
     */
    
    private void calcSegmentCoverByGroup(TagGroupsIn tagGroup,Jedis jedis) throws Exception {
       Long startTime=System.currentTimeMillis();
       String curr="";
       
       if(StringUtils.isBlank(uuid.get())) {
           uuid.set(GenerateUUid.generateShortUuid());
           logger.info("group uuid:"+ uuid.get());
       }       
       List<SegmentGroupRedisVO> segmentGroups=segmentRedis.get().getSegmentGroups();
       
           for(SegmentGroupRedisVO segmentGroup:segmentGroups) {
               if(segmentGroup.getGroupId().equals(tagGroup.getGroupId())) { 
                   curr=segmentGroup.getGroupId()+"-"+segmentGroup.getGroupName();
                   List<SegmentGroupTagRedisVO> segmentGroupTags=segmentGroup.getTagList();
                   if(segmentGroupTags==null||segmentGroupTags.size()<1 ) {
                       logger.info("calcSegmentCover:标签组的标签对象为null");
                       segmentGroup.setGroupCoverCount(0L);
                       segmentGroup.setGroupCoverIds("");
                       return ;
                   }                   
                   this.calcItemCover(segmentGroup,segmentGroupTags,OPER_INTERSECTION,false,jedis);  
               }
           }       
               
       Long endTime=System.currentTimeMillis();
       logger.info("Group "+curr+" costs="+(endTime-startTime)+"ms");
       return;
    }
    
    
    /**
     * 按组初始化细分存储结构
     * @param tagGroup
     * @return
     */
    private SegmentRedisVO initSegmentRedis(TagGroupsIn tagGroup,Jedis jedis) {
        
        if(tagGroup==null) {
            logger.info("calcSegmentCover:initSegmentRedis:标签组对象为null");
            return segmentRedis.get();
        }
        Long initStartTime=System.currentTimeMillis();
        List<SegmentGroupRedisVO> segmentGroups=segmentRedis.get().getSegmentGroups();
        if(segmentGroups==null) {
            segmentGroups=new ArrayList<SegmentGroupRedisVO>();            
        }
        
        SegmentGroupRedisVO segmentGroupRedis=new SegmentGroupRedisVO();
        segmentGroupRedis.setGroupCoverCount(0L);
        segmentGroupRedis.setGroupCoverIds("");
        segmentGroupRedis.setGroupId(tagGroup.getGroupId());
        segmentGroupRedis.setGroupIndex(tagGroup.getGroupIndex());
        segmentGroupRedis.setGroupName(tagGroup.getGroupName());
        segmentGroupRedis.setGroupChange(tagGroup.getGroupChange());       
        List<SystemTagIn> sysTags=tagGroup.getTagList();
        if(sysTags!=null&&sysTags.size()>0) {
            List<SegmentGroupTagRedisVO> segmentGroupTags=new ArrayList<SegmentGroupTagRedisVO>();
            for(SystemTagIn tag:sysTags) {
                SegmentGroupTagRedisVO segmentGroupTagRedis=new SegmentGroupTagRedisVO();
                segmentGroupTagRedis.setCalcTagCoverCount(0L);
                segmentGroupTagRedis.setCalcTagCoverIds("");
                segmentGroupTagRedis.setTagId(tag.getTagId());
                if(tag.getTagExclude()==null||tag.getTagExclude()==0){ //没排除0
                    segmentGroupTagRedis.setTagExclude(false);
                } else {
                    segmentGroupTagRedis.setTagExclude(true);
                }                
                segmentGroupTagRedis.setTagIndex(tag.getTagIndex());
                segmentGroupTagRedis.setTagName(tag.getTagName());
                segmentGroupTagRedis.setTagCoverIds(tag.getTagId()); //标签覆盖人群ID为标签的Tagid指针
                Long tagCount=jedis.scard(KEY_PREFIX_TAGCOVERID+tag.getTagId());                
                /*
                Long tagCount=0L;
                String strTagCount=jedis.hget("tagcover:"+tag.getTagId(), "covercount");
                if(StringUtils.isNumeric(strTagCount)) {
                    tagCount=Long.parseLong(strTagCount);
                }
                */
                segmentGroupTagRedis.setTagCoverCount(tagCount); 
                segmentGroupTagRedis.setGroupId(segmentGroupRedis.getGroupId());
                segmentGroupTagRedis.setFunnelCount(tagCount);
                segmentGroupTags.add(segmentGroupTagRedis);
                List<SystemValueIn> tagValues=tag.getTagValueList();
                if(tagValues==null||tagValues.size()==0) {
                    //标签无任何值
                    segmentGroupTagRedis.setCalcTagCoverCount(segmentGroupTagRedis.getTagCoverCount());
                    segmentGroupTagRedis.setCalcTagCoverIds(KEY_PREFIX_TAGCOVERID+segmentGroupTagRedis.getTagCoverIds());                    
                }
                if(tagValues!=null&&tagValues.size()>0) {   
                    List<SegmentGroupTagValueRedisVO> SegmentGroupTagValues=new ArrayList<SegmentGroupTagValueRedisVO>();
                    for(SystemValueIn tagvalue:tagValues){
                        SegmentGroupTagValueRedisVO segmentGroupTagValueRedis=new SegmentGroupTagValueRedisVO();
                        segmentGroupTagValueRedis.setTagValueId(tagvalue.getTagValueId());
                        segmentGroupTagValueRedis.setTagValue(tagvalue.getTagValue());
                        segmentGroupTagValueRedis.setTagValueCoverCount(0L);
                        segmentGroupTagValueRedis.setTagId(segmentGroupTagRedis.getTagId());
                        segmentGroupTagValueRedis.setGroupId(segmentGroupRedis.getGroupId());  
                        String idskey=KEY_PREFIX_TAGCOVERID+tagvalue.getTagValueId();  
                        Long count=jedis.scard(idskey);
                        /*
                        Long count=0L;
                        String strcount=jedis.hget("tagcover:"+tagvalue.getTagValueId(), "covercount");
                        if(StringUtils.isNumeric(strcount)) {
                            count=Long.parseLong(strcount);
                        }
                        */
                        logger.info("Get TagValue's["+tagvalue.getTagValueId()+"] coverIds="+idskey+" count="+count);
                        segmentGroupTagValueRedis.setTagValueCoverCount(count);
                        segmentGroupTagValueRedis.setTagValueCoverIds(idskey);
                        SegmentGroupTagValues.add(segmentGroupTagValueRedis);
                    }                
                    segmentGroupTagRedis.setTagValueList(SegmentGroupTagValues);
                } 
            }                
            segmentGroupRedis.setTagList(segmentGroupTags); 
        }
        segmentGroups.add(segmentGroupRedis);        
        segmentRedis.get().setSegmentGroups(segmentGroups);
        Long initEndTime=System.currentTimeMillis();
        logger.info("Init segment para costs="+(initEndTime-initStartTime)+"ms");
        return segmentRedis.get();
    }
    
   
    
    private  void calcItemCover(SegmentGroupRedisVO segmentGroup,List items,int operation,boolean exclude,Jedis jedis) throws JedisException {
        
        if(items==null||items.size()<1) {            
            return ;
        }  
        Long tagStartTime=System.currentTimeMillis();
        String curr="";
        if(tempKeys.get()==null) {
            tempKeys.set(new ArrayList<String>());
        }
        if(jedis==null) {
            jedis = JedisConnectionManager.getConnection(); 
            jedis.select(REDISDB_INDEX);
        }        
        
        if(items.size()==1) { //只有一个标签或标签值时，不必计算交集并集
            String idskey="";
            if(items.get(0) instanceof SegmentGroupTagRedisVO) {               
                SegmentGroupTagRedisVO tag=(SegmentGroupTagRedisVO)items.get(0); 
                curr="Tag:"+tag.getTagId();
                calcItemCover(segmentGroup,tag.getTagValueList(),OPER_UNION,tag.isTagExclude(),jedis); //递归求标签值                    
                segmentGroup.setGroupCoverCount(tag.getCalcTagCoverCount());
                segmentGroup.setGroupCoverIds(tag.getCalcTagCoverIds());
                tag.setFunnelCount(tag.getCalcTagCoverCount());
                logger.info("===Only 1  Tag calc tagid:"+tag.getTagId()+" count="+tag.getCalcTagCoverCount()+ "groupCoverIds=TagCoverIds="+tag.getCalcTagCoverIds());
            } else if (items.get(0) instanceof SegmentGroupTagValueRedisVO) {
                SegmentGroupTagValueRedisVO tagValue=(SegmentGroupTagValueRedisVO)items.get(0);
                curr="TagValue:"+tagValue.getTagValueId();
                idskey=tagValue.getTagValueCoverIds();  
                Long count=tagValue.getTagValueCoverCount();                
                String tagId=tagValue.getTagId();
                SegmentGroupTagRedisVO segmentGroupTag=getTag(segmentGroup.getGroupId(),tagId);
                segmentGroupTag.setCalcTagCoverIds(idskey);
                segmentGroupTag.setCalcTagCoverCount(count);
                logger.info("===Only 1 Tagvalue calc Tag tagid="+tagId+" calcTagCount="+count+ "calcTagCoverIds="+idskey);
            }           
        }    
        else {
                if(operation==OPER_INTERSECTION) {
                    
                    //求交集(标签)
                    if(items.get(0) instanceof SegmentGroupTagRedisVO) {
                        curr="SegmentGroupTags 交集";
                        List<SegmentGroupTagRedisVO> segmentGroupTags=(List<SegmentGroupTagRedisVO>)items;                        
                        for(SegmentGroupTagRedisVO segmentGroupTag:segmentGroupTags) {
                            calcItemCover(segmentGroup,segmentGroupTag.getTagValueList(),OPER_UNION,segmentGroupTag.isTagExclude(),jedis); //递归求标签值
                        }
                        /*
                        String dstkey=this.generateKeyFromTags(KEY_PREFIX_GROUPINTER,uuid.get(),segmentGroupTags);
                        String[] keys=getCalcTagIds(segmentGroupTags); //使用计算后产生的Ids
                      
                        Long count=jedis.sinterstore(dstkey, keys); 
                        jedis.expire(dstkey, REDIS_EXPIRE_SECOND);
                        tempKeys.get().add(dstkey);
                        //Long count=jedis.scard(dstkey);
                        */
                        funnelInterCalculation(segmentGroup,segmentGroupTags,jedis);                        
                    } else if(items.get(0) instanceof SegmentGroupTagValueRedisVO) {
                        //标签值之间不计算交集
                        logger.info("===Inter calc===TagValues should not calculate intersection"); 
                    }
                    
                }else if(operation==OPER_UNION) {
                    //求并集（标签值）
                    if(items.get(0) instanceof SegmentGroupTagRedisVO) {
                        logger.info("===Union calc===Tags should not calculate union");
                        //标签之间不计算并集                        
                    } else if (items.get(0) instanceof SegmentGroupTagValueRedisVO) {
                        curr="Tags 并集";
                        List<SegmentGroupTagValueRedisVO> segmentGroupTagValues=(List<SegmentGroupTagValueRedisVO>)items;
                        String dstkey=this.generateKeyFromTagValues(KEY_PREFIX_UNION,uuid.get(),segmentGroupTagValues); 
                        String[] keys=getTagValueIds(segmentGroupTagValues);
                        Long count=jedis.sunionstore(dstkey, keys);
                        //Long count=jedis.scard(dstkey);
                        jedis.expire(dstkey, REDIS_EXPIRE_SECOND);
                        tempKeys.get().add(dstkey);
                        String tagId=segmentGroupTagValues.get(0).getTagId();
                        SegmentGroupTagRedisVO segmentGroupTag=getTag(segmentGroup.getGroupId(),tagId);
                        segmentGroupTag.setCalcTagCoverIds(dstkey);
                        segmentGroupTag.setCalcTagCoverCount(count);
                        logger.info("===Union calc Tag tagid="+tagId+" count="+count);
                    }                    
                   
                }   
        }
        if(exclude) {
            //进行求差集
            //仅标签
            if (items.get(0) instanceof SegmentGroupTagValueRedisVO) {
                List<SegmentGroupTagValueRedisVO> segmentGroupTagValues=(List<SegmentGroupTagValueRedisVO>)items;
                String tagId=segmentGroupTagValues.get(0).getTagId();
                curr="Tags 补集:"+tagId;
                SegmentGroupTagRedisVO segmentGroupTag=getTag(segmentGroup.getGroupId(),tagId);
                String diffkey=KEY_PREFIX_DIFF+segmentGroupTag.getTagCoverIds();
                
                String[] keys=new String[2];
                //keys[0]=KEY_PREFIX_TAGCOVERID+segmentGroupTag.getTagCoverIds();
                keys[0]=KEY_PREFIX_TAGCOVERID_ALL;
                keys[1]=segmentGroupTag.getCalcTagCoverIds();                
                Long count=jedis.sdiffstore(diffkey, keys);
                jedis.expire(diffkey, REDIS_EXPIRE_SECOND);
                tempKeys.get().add(diffkey);
                segmentGroupTag.setCalcTagCoverCount(count);
                segmentGroupTag.setCalcTagCoverIds(diffkey);
                logger.info("===Diff calc Tag tagid="+tagId+" count="+count+" key[0]="+keys[0]+"- key[1]="+keys[1]);
            }
            
        }
        Long tagEndTime=System.currentTimeMillis();
        logger.info(curr+" calcItemCover costs="+(tagEndTime-tagStartTime)+"ms");
        return ;
    }
    
    private String generateKeyFromTagValues(String prefix,String uuid,List<SegmentGroupTagValueRedisVO> segmentGroupTagValues) {
        String ret=prefix;  
        if(segmentGroupTagValues!=null&&segmentGroupTagValues.size()>0) {            
            for(SegmentGroupTagValueRedisVO segmentGroupTagValue:segmentGroupTagValues) {
                if(segmentGroupTagValue!=null) {
                    ret+=":"+segmentGroupTagValue.getTagValueId();                
                }            
            }
            ret+=":"+uuid;
        }      
        return ret;
    }
    
    private String generateKeyFromTags(String prefix,String uuid,List<SegmentGroupTagRedisVO> segmentGroupTags) {
        String ret=prefix;  
        if(segmentGroupTags!=null&&segmentGroupTags.size()>0) {            
            for(SegmentGroupTagRedisVO segmentGroupTag:segmentGroupTags) {
                if(segmentGroupTag!=null) {
                    ret+=":"+segmentGroupTag.getTagId();                
                }            
            }
            ret+=":"+uuid;
        }      
        return ret;
    }
    
    /**
     * 从标签中获得标签值ID的数组
     * @param segmentGroupTagValues
     * @return
     */
    private  String[] getTagValueIds(List<SegmentGroupTagValueRedisVO> segmentGroupTagValues) {
        
        if(segmentGroupTagValues==null||segmentGroupTagValues.size()<1) return null;
        String[] tagValueIds=new String[segmentGroupTagValues.size()];
        int i=0;
        for(SegmentGroupTagValueRedisVO segmentGroupTagValue:segmentGroupTagValues) {
            if(segmentGroupTagValue!=null) {
                tagValueIds[i]=KEY_PREFIX_TAGCOVERID+segmentGroupTagValue.getTagValueId();             
            } 
            i++;
        }
        return tagValueIds;
    }
    
    private  String[] getCalcTagIds(List<SegmentGroupTagRedisVO> segmentGroupTags) {
        
        if(segmentGroupTags==null||segmentGroupTags.size()<1) return null;
        String[] tagIds=new String[segmentGroupTags.size()];
        int i=0;
        for(SegmentGroupTagRedisVO segmentGroupTag:segmentGroupTags) {
            if(segmentGroupTag!=null) {
                tagIds[i]=segmentGroupTag.getCalcTagCoverIds();  
                logger.info("Get Tag's calcTagCoverIds="+tagIds[i]);
            }     
            i++;   
        }
        return tagIds;
    }
    
    /**
     * 根据tagId获得标签vo SegmentGroupTagRedisVO
     * @param tagId
     * @return
     */
    private SegmentGroupTagRedisVO getTag(String groupId,String tagId)
    {
        if(segmentRedis.get()==null||StringUtils.isBlank(tagId)) return null;        
        List<SegmentGroupRedisVO> segmentGroups=segmentRedis.get().getSegmentGroups();
        if(segmentGroups==null||segmentGroups.size()<1) return null;
        for(SegmentGroupRedisVO segmentGroup:segmentGroups) {
            if(segmentGroup.getGroupId().equals(groupId)) {
                List<SegmentGroupTagRedisVO> segmentGroupTags=segmentGroup.getTagList();
                if(segmentGroupTags==null||segmentGroupTags.size()<1) return null;
                for(SegmentGroupTagRedisVO segmentGroupTag:segmentGroupTags) {
                    if(tagId.trim().equals(segmentGroupTag.getTagId().trim())) {
                        return segmentGroupTag;
                    }
                }                
            }          
        }
        return null;
    }
    
    /**
     * 计算整个细分的覆盖
     */
    private void calcSegmentTotalCover(Jedis jedis) throws Exception {
        if(segmentRedis.get()==null) return ;
        List<SegmentGroupRedisVO> segmentGroups=segmentRedis.get().getSegmentGroups();
        if(segmentGroups==null||segmentGroups.size()<1) return ;
        String dstkey=KEY_PREFIX_SEGMENTCOVERID+segmentRedis.get().getSegmentHeadId()+uuid.get();   
        String logstr="";
        if(segmentGroups.size()==1) {
            //只有一个group
            segmentRedis.get().setSegmentCoverCount(segmentGroups.get(0).getGroupCoverCount());
            String groupCoverIds=segmentGroups.get(0).getGroupCoverIds();
            String[] keys=new String[2];
            keys[0]=groupCoverIds;
            keys[1]="Nothing"+uuid.get();
            jedis.sunionstore(dstkey, keys); //用户空结合，进行copy 当前ID集合           
            segmentRedis.get().setSegmentCoverIds(dstkey);    
            jedis.expire(dstkey, REDIS_EXPIRE_SECOND);
            logstr="segment coverid="+dstkey+"  covercount="+segmentGroups.get(0).getGroupCoverCount()+" [only 1 group]";
        } else {
        
            String[] groupKey=new String[segmentGroups.size()];
            int i=0;
            for(SegmentGroupRedisVO segmentGroup:segmentGroups) {            
                groupKey[i]=segmentGroup.getGroupCoverIds();
                logger.info("segment used ["+i+"]groupCoverid="+groupKey[i]);
                i++;
                
            }
            Long segmentCoverCount=jedis.sunionstore(dstkey, groupKey);
            //Long segmentCoverCount=jedis.scard(dstkey);        
            segmentRedis.get().setSegmentCoverCount(segmentCoverCount);        
            segmentRedis.get().setSegmentCoverIds(dstkey);
            jedis.expire(dstkey, REDIS_EXPIRE_SECOND);
            logstr="segment coverid="+dstkey+" covercount="+segmentCoverCount+" [multi groups]";
        }
        logger.info(logstr);
    }
    
    public void loggerSegment() {
        if(segmentRedis==null) {
            logger.info("segmentRedis is null ...");            
        }
        List<SegmentGroupRedisVO> segmentGroups=segmentRedis.get().getSegmentGroups();
        if(segmentGroups==null||segmentGroups.size()<1) {
            logger.info("segmentRedis:segmentGroups is null ...or size()=0"); 
            return;
        }
      
        Long segmentCoverCount=segmentRedis.get().getSegmentCoverCount();
        String segmentCoverIds=segmentRedis.get().getSegmentCoverIds();
        logger.info("segmentRedis: segmentCoverCount="+segmentCoverCount+" segmentCoverIds="+segmentCoverIds);
        for(SegmentGroupRedisVO segmentGroup:segmentGroups) {
            String groupMarker="["+segmentGroup.getGroupId()+"-"+segmentGroup.getGroupName()+"]";
            Long groupCoverCount=segmentGroup.getGroupCoverCount();
            String groupCoverIds=segmentGroup.getGroupCoverIds();
            logger.info("segmentRedis:segmentGroups"+groupMarker+" groupCoverCount="+groupCoverCount+" groupCoverIds="+groupCoverIds);
            List<SegmentGroupTagRedisVO> segmentGroupTags=segmentGroup.getTagList();
            if(segmentGroupTags==null) {
                logger.info("segmentRedis:segmentGroups"+groupMarker+": Tags is null");
                continue;
            }
            if(segmentGroupTags.size()<1) {
                logger.info("segmentRedis:segmentGroups"+groupMarker+": Tags size()=0");
                continue;
            }
            for(SegmentGroupTagRedisVO segmentGroupTag:segmentGroupTags) {
                String tagMarker="["+segmentGroupTag.getTagId()+"-"+segmentGroupTag.getTagName()+"]";
                Long tagCoverCount=segmentGroupTag.getTagCoverCount();
                String tagCoverIds=segmentGroupTag.getTagCoverIds();
                Long calcTagCoverCount=segmentGroupTag.getCalcTagCoverCount();
                Long funnelCount=segmentGroupTag.getFunnelCount();
                String calcTagCoverIds=segmentGroupTag.getCalcTagCoverIds();
                boolean exclude=segmentGroupTag.isTagExclude();
                logger.info("segmentRedis:segmentGroups"+groupMarker+":Tags"+tagMarker+" calcTagCoverCount="+calcTagCoverCount+" calcTagCoverIds="+calcTagCoverIds+" exclude="+exclude+" funnelCount="+funnelCount+" tagCoverCount="+tagCoverCount+" tagCoverIds="+tagCoverIds);
                
                List<SegmentGroupTagValueRedisVO>  segmentGroupTagValues=segmentGroupTag.getTagValueList();
                if(segmentGroupTagValues==null) {
                    logger.info("segmentRedis:segmentGroups"+groupMarker+":Tags"+tagMarker+": TagVaues is null");
                    continue;
                }
                if(segmentGroupTagValues.size()<1) {
                    logger.info("segmentRedis:segmentGroups"+groupMarker+":Tags"+tagMarker+": TagVaues size()=0");
                    continue;
                }
                for(SegmentGroupTagValueRedisVO segmentGroupTagValue:segmentGroupTagValues) {
                    String tagValueMarker="["+segmentGroupTagValue.getTagValueId()+"-"+segmentGroupTagValue.getTagValue()+"]";
                    Long tagValueCoverCount=segmentGroupTagValue.getTagValueCoverCount();
                    String tagValueCoverIds=segmentGroupTagValue.getTagValueCoverIds();
                    logger.info("segmentRedis:segmentGroups"+groupMarker+":Tags"+tagMarker+":TagValus"+tagValueMarker+" tagValueCoverCount="+tagValueCoverCount+" tagValueCoverIds="+tagValueCoverIds);
                }
            }
        }
            
            
        
    }
    
    /**
     * 保存计算的segment结果
     */
    public boolean saveSegmentCover() throws JedisException {
        if(segmentRedis.get()==null) {
            logger.info("segmentRedis is null, nothing saved");
            return false;
        }
        Long saveStartTime=System.currentTimeMillis();
        Long segmentHeadId=segmentRedis.get().getSegmentHeadId();
        if(segmentHeadId==null) {
            logger.info("segmentRedis:segmentHeadId is null, nothing saved");
            return false;
        }
        String segmentHeadName=segmentRedis.get().getSegmentName();
        if(segmentHeadName==null) {
            segmentHeadName="";
        }
        Long segmentCoverCount=segmentRedis.get().getSegmentCoverCount();
        String sourceIds=segmentRedis.get().getSegmentCoverIds();
        String updatetime=segmentRedis.get().getUpdateTime();
        String keyids=KEY_PREFIX_SEGMENTCOVERID+segmentHeadId;    
        String key=KEY_PREFIX_SEGMENTCOVER+segmentHeadId;
        Jedis savejedis = JedisConnectionManager.getConnection(); 
        HashMap<String,String> segmentCover=new HashMap<String,String>();
        segmentCover.put("segmentheadid", segmentHeadId.toString());
        segmentCover.put("segmentheadname", segmentHeadName);
        segmentCover.put("segmentcovercount", segmentCoverCount.toString());
        segmentCover.put("segmentcoverid",keyids);
        segmentCover.put("updatetime", updatetime);
                    
        String rs;
        try {
            savejedis.select(REDISDB_INDEX);    
            if(savejedis.exists(sourceIds)) {
                savejedis.rename(sourceIds, keyids);
                savejedis.persist(keyids);
            }
            rs = savejedis.hmset(key, segmentCover);
            logger.info("Save Segment Key:"+key+ " 原始细分ID="+sourceIds+" 细分count="+segmentCoverCount+"-->最新细分ID="+keyids); 
        } catch (Exception e) {
            logger.info("Save Segment Exception:"+e.toString()); 
            throw new JedisException("设置key和HASH值异常!",e);
        } finally {
            JedisConnectionManager.closeConnection(savejedis);
        }
        Long saveEndTime=System.currentTimeMillis();
        logger.info(" Saving Segment costs="+(saveEndTime-saveStartTime)+"ms");
        return rs != null && rs.equals("OK") ? true : false;
        
    }
    
    /**
     * 漏斗的各层tag的自身结论值计算和存储
     * @return
     */
    private Long funnelInterCalculation(SegmentGroupRedisVO segmentGroup,List<SegmentGroupTagRedisVO> segmentGroupTags,Jedis jedis) {
        if(segmentGroupTags==null||segmentGroupTags.size()==0) return 0L;
        int size=segmentGroupTags.size();
        Long funnelCount=0L;
        String dkey="";
        if(size==1) {
            funnelCount=segmentGroupTags.get(0).getCalcTagCoverCount();
            segmentGroup.setGroupCoverIds(segmentGroupTags.get(0).getCalcTagCoverIds());
            segmentGroup.setGroupCoverCount(funnelCount);
        } else {        
            for(int i=0;i<size-1;i++) {
                String[] skeys=new String[2];            
                dkey=KEY_PREFIX_GROUPINTER+":group:"+segmentGroup.getGroupId()+"_lv"+(i+1)+"_"+uuid.get();
                if(i==0){
                    skeys[0]=segmentGroupTags.get(i).getCalcTagCoverIds();
                    segmentGroupTags.get(i).setFunnelCount(segmentGroupTags.get(i).getCalcTagCoverCount());
                } else {
                    skeys[0]=KEY_PREFIX_GROUPINTER+":group:"+segmentGroup.getGroupId()+"_lv"+i+"_"+uuid.get(); 
                }            
                skeys[1]=segmentGroupTags.get(i+1).getCalcTagCoverIds();
                funnelCount=jedis.sinterstore(dkey, skeys); 
                segmentGroupTags.get(i+1).setFunnelCount(funnelCount);
                jedis.expire(dkey, REDIS_EXPIRE_SECOND);
                tempKeys.get().add(dkey);    
                logger.info("===Inter calc group level"+(i+1)+" Tag="+segmentGroupTags.get(i+1).getGroupId()+"-"+segmentGroupTags.get(i+1).getTagName()+" funnelCount="+funnelCount+" tagCoverIds="+dkey);
            }
            segmentGroup.setGroupCoverIds(dkey);
            segmentGroup.setGroupCoverCount(funnelCount);
        }       
        logger.info("===Inter calc group="+segmentGroup.getGroupId()+"-"+segmentGroup.getGroupName()+" groupCoverCount="+funnelCount+" groupcoverid="+dkey);
        return funnelCount;
      
    }
    
    /**
     * 清除临时产生的redis数据
     */
    private void clearTempRedisKeys() {
        Long clearStartTime=System.currentTimeMillis();
        Jedis jedis = JedisConnectionManager.getConnection(); 
        try{             
            jedis.select(REDISDB_INDEX);
            if(tempKeys.get()!=null&&tempKeys.get().size()>0) {
                String[] keys=new String[tempKeys.get().size()];
                keys=tempKeys.get().toArray(keys);
                jedis.del(keys);
                tempKeys.get().clear();
            }
        Long clearEndTime=System.currentTimeMillis();
        logger.info("clear temp key costs="+(clearEndTime-clearStartTime)+"ms");
        } catch (Exception e) {
            logger.info("Del tempKey Exception:"+e.toString());
            
        } finally {
            JedisConnectionManager.closeConnection(jedis);
        }
        
    }

    public SegmentRedisVO getSegmentRedis() {
        return segmentRedis.get();
    }

    public void setSegmentRedis(SegmentRedisVO segmentRedis) {
        this.segmentRedis.set(segmentRedis); 
    }
   
   
}
