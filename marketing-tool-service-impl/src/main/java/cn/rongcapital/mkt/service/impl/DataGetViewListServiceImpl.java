package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.DataGetViewListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataGetViewListServiceImpl implements DataGetViewListService {

    @Autowired
    private ImportTemplateDao importTemplateDao;

    @Override
    public Object getViewList(String method, String userToken, String ver, Integer mdType) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        ImportTemplate paramImportTemplate = new ImportTemplate();
        paramImportTemplate.setTemplType(mdType);

        List<ImportTemplate> importTemplateList =
                        importTemplateDao.selectViewListByTemplType(paramImportTemplate);
        
        if (importTemplateList != null && !importTemplateList.isEmpty()) {
            for (ImportTemplate importTemplate : importTemplateList) {
                Map<String, Object> map = new HashMap<>();
                map.put("field_name", importTemplate.getFieldName());
                map.put("templ_name", importTemplate.getTemplName());
                map.put("selected", importTemplate.getSelected());
                result.getData().add(map);
            }
        }
        result.setTotal(result.getData().size());

        return Response.ok().entity(result).build();
    }

}
