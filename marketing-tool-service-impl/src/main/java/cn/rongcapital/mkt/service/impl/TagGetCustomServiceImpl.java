package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.service.TagGetCustomService;

@Service
public class TagGetCustomServiceImpl implements TagGetCustomService {

    @Autowired
    private CustomTagDao customTagDao;

    @Override
    public List<String> getCustomizeTagByContactId(String ver, Integer contactId) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("contactId", contactId);
        List<String> resultList = customTagDao.selectTagsByContactId(paramMap);
        return resultList;
    }

}
