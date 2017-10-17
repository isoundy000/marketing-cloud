package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.DataUpateViewListService;

public class DataUpateViewListServiceImpl implements DataUpateViewListService {

    @Autowired
    private ImportTemplateDao importTemplateDao;

    @Override
    public boolean getViewList(String method, String userToken, String ver,
                    List<ImportTemplate> importTemplateList) {

        boolean result = false;

        if (importTemplateList != null && !importTemplateList.isEmpty()) {
            for (ImportTemplate importTemplate : importTemplateList) {
                importTemplateDao.updateByTemplNameandFieldName(importTemplate);
            }

            result = true;
        }

        return result;
    }

}
