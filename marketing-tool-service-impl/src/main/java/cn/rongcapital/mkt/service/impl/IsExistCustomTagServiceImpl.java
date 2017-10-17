package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDao;
import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDaoImpl;
import cn.rongcapital.mkt.po.base.BaseTag;
import cn.rongcapital.mkt.service.IsExistsCustomTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yunfeng on 2016-9-21.
 */
@Service
public class IsExistCustomTagServiceImpl implements IsExistsCustomTagService {

    @Autowired
    private MongoBaseTagDaoImpl mongoBaseTagDao;

    @Override
    public boolean isExistsCustomTag(BaseTag baseTag) {
        if(!baseTag.validateTag()) return false;
        BaseTag targetTag = mongoBaseTagDao.findOneBaseTag(baseTag);
        if(targetTag != null) return true;
        return false;
    }

}
