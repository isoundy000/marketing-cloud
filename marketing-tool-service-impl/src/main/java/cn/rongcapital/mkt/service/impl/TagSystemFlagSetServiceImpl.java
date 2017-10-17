package cn.rongcapital.mkt.service.impl;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.TagRecommend;
import cn.rongcapital.mkt.service.TagSystemFlagSetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagSystemFlagSetIn;

@Service
public class TagSystemFlagSetServiceImpl implements TagSystemFlagSetService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 接口:mkt.tag.system.flag.set 
     * 推荐标签（设置）
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016-11-08
     */
    @Override
    public BaseOutput updateFlag(TagSystemFlagSetIn body, SecurityContext securityContext) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        String tagId = body.getTagId();
        boolean flag = body.getFlag();
        WriteResult writeResult =
                        mongoTemplate.updateMulti(new Query(Criteria.where("tag_id").is(tagId)),
                                        new Update().set("flag", flag), TagRecommend.class);

        if (writeResult != null) {
            // 设置更新的数量
            result.setTotal(writeResult.getN());
        }
        return result;
    }


}
