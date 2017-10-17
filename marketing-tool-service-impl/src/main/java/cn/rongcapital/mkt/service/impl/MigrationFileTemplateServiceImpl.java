package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.service.MigrationFileTemplateService;
import cn.rongcapital.mkt.vo.BaseInput;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-30.
 */
@Service
public class MigrationFileTemplateServiceImpl implements MigrationFileTemplateService{

    @Autowired
    private ImportTemplateDao importTemplateDao;

    @Override
    public Object getMigrationFileTemplateList(BaseInput baseInput) {
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);
        List<Map<String,Object>> templateList = importTemplateDao.selectTemplateList();
        if(templateList != null && templateList.size() > 0 ){
            for(Map<String,Object> map : templateList){
                map.put("template_name",map.remove("templ_name"));
                map.put("template_id",map.remove("templ_type"));
                baseOutput.getData().add(map);
            }
        }
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
        baseOutput.setTotal(baseOutput.getData().size());
        return Response.ok().entity(baseOutput).build();
    }
}
