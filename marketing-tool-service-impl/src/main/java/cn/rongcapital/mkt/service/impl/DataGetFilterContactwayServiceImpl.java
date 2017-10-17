package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.ContactWayDao;
import cn.rongcapital.mkt.po.ContactWay;
import cn.rongcapital.mkt.service.DataGetFilterContactwayService;

@Service
public class DataGetFilterContactwayServiceImpl implements DataGetFilterContactwayService {

    @Autowired
    private ContactWayDao contactWayDao;

    @Override
    public List<ContactWay> getFilterContactway(String method, String userToken, String ver) {

        List<ContactWay> contactWayList = contactWayDao.selectAllContactWay();

        return contactWayList;
    }

}
