package cn.rongcapital.mkt.job.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.TagSourceEnum;
import cn.rongcapital.mkt.dao.*;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.*;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.po.mongodb.CustomTagLeaf;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.FindCustomTagInfoService;
import cn.rongcapital.mkt.service.TagCustomTagToDataPartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Yunfeng on 2016-7-13.
 */
@Service
public class CustomTagMappingSyncTaskServiceImpl implements TaskService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomTagMapDao customTagMapDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ContactListDao contactListDao;
    @Autowired
    private WechatQrcodeDao wechatQrcodeDao;
    @Autowired
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;
    @Autowired
    private DataPopulationDao dataPopulationDao;
    @Autowired
    private TagCustomTagToDataPartyService tagCustomTagToDataPartyService;
    @Autowired
    private FindCustomTagInfoService findCustomTagInfoService;

    private final Integer BATCH_SIZE = 500;
    private final String SEPARATE_QRCODE_TAGS = ",";
    private final String WECHAT_BITMAP = "00000011000000000";
    private final String BATCHID = "batchId";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void task(Integer taskId) {
        int totalPage = queryTotalPage();
        for(int i = 0; i<totalPage; i++){
            List<CustomTagMap> customTagMapList = this.queryCustomTagMapByBatchSize(i*BATCH_SIZE);
            if(CollectionUtils.isEmpty(customTagMapList)) return;
            for(CustomTagMap customTagMap : customTagMapList){
                switch (TagSourceEnum.getTagSourceEnum(customTagMap.getTagSource())){
                    case FILE_SOURCE_ACCESS:
                        Query query = new Query(Criteria.where(BATCHID).is(customTagMap.getMapId()));
                        List<cn.rongcapital.mkt.po.mongodb.DataParty> dataPartyList = mongoTemplate.find(query,cn.rongcapital.mkt.po.mongodb.DataParty.class);
                        if(CollectionUtils.isEmpty(dataPartyList)) continue;
                        for(DataParty dataParty : dataPartyList){
                            tagCustomTagToDataPartyService.tagCustomTagToDataParty(dataParty,findCustomTagInfoService.findCustomTagInfoByTagId(customTagMap.getTagId()));
                        }
                        break;
                    case CONTACT_DOCUMENT_SOURCE_ACCESS:
                        String mapId = customTagMap.getMapId();
                        ContactList paramContactList = new ContactList();
                        paramContactList.setStatus(Integer.valueOf(ApiConstant.TABLE_DATA_STATUS_VALID));
                        paramContactList.setContactTemplId(Integer.valueOf(mapId));
                        List<ContactList> contactLists = contactListDao.selectList(paramContactList);
                        if(!CollectionUtils.isEmpty(contactLists)){
                            for(ContactList targetContactList : contactLists){
                                tagCustomTagToDataPartyService.tagCustomTagToDataPartyById(customTagMap.getTagId(),targetContactList.getKeyid());
                            }
                        }
                        break;
                }
            }
        }

        WechatQrcode wechatQrcode = new WechatQrcode();
        wechatQrcode.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        wechatQrcode.setPageSize(Integer.MAX_VALUE);
        List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcode);
        if(CollectionUtils.isEmpty(wechatQrcodeList)) return;
        for(WechatQrcode targetWechatQrcode : wechatQrcodeList){
            String relationTags = targetWechatQrcode.getRelatedTags();
            if(StringUtils.isEmpty(relationTags)) continue;
            if(relationTags.contains(SEPARATE_QRCODE_TAGS)){
                String[] relationTag = relationTags.split(SEPARATE_QRCODE_TAGS);
                for(String tag : relationTag){
                    if (tagWechatQrcodeData(targetWechatQrcode, tag)) break;
                }
            }else{
                tagWechatQrcodeData(targetWechatQrcode,relationTags);
            }
        }

    }

    private boolean tagWechatQrcodeData(WechatQrcode targetWechatQrcode, String tag) {
        WechatQrcodeFocus paramWechatQrcodeFocus = new WechatQrcodeFocus();
        paramWechatQrcodeFocus.setPageSize(Integer.MAX_VALUE);
        paramWechatQrcodeFocus.setQrcodeId(String.valueOf(targetWechatQrcode.getId()));
        List<WechatQrcodeFocus> wechatQrcodeFocusList = wechatQrcodeFocusDao.selectList(paramWechatQrcodeFocus);
        if(CollectionUtils.isEmpty(wechatQrcodeFocusList)) return true;
        for(WechatQrcodeFocus wechatQrcodeFocus : wechatQrcodeFocusList){
            DataPopulation paramDataPopulation = new DataPopulation();
            paramDataPopulation.setBitmap(WECHAT_BITMAP);
            paramDataPopulation.setWxmpId(wechatQrcodeFocus.getWxAcct());
            paramDataPopulation.setWxCode(wechatQrcodeFocus.getWxName());
            List<DataPopulation> dataPopulationList = dataPopulationDao.selectList(paramDataPopulation);
            if(CollectionUtils.isEmpty(dataPopulationList)) continue;
            tagCustomTagToDataPartyService.tagCustomTagToDataPartyById(tag,dataPopulationList.get(0).getKeyid());
        }
        return false;
    }

    private List<CustomTagMap> queryCustomTagMapByBatchSize(Integer startIndex) {
        CustomTagMap paramCustomTagMap = new CustomTagMap();
        paramCustomTagMap.setStartIndex(startIndex);
        paramCustomTagMap.setPageSize(BATCH_SIZE);
        paramCustomTagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<CustomTagMap> customTagMapList = customTagMapDao.selectList(paramCustomTagMap);
        return customTagMapList;
    }

    private int queryTotalPage() {
        CustomTagMap paramCustomTagMap = new CustomTagMap();
        paramCustomTagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        Integer totalCount = customTagMapDao.selectListCount(paramCustomTagMap);
        return (totalCount + BATCH_SIZE - 1)/BATCH_SIZE;
    }

}
