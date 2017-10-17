package cn.rongcapital.mkt.service.impl.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by ethan on 16/7/5.
 */
public class MainDataVO {

    private List<Map<String, Object>> resultList;

    private List<Map<String, Object>> countList;
    
    private List<Integer> dataTypeList;
    
    private List<Integer> contactWayList;
    
    private Byte timeCondition;

    private Integer totalCount;

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Map<String, Object>> getCountList() {
        return countList;
    }

    public void setCountList(List<Map<String, Object>> countList) {
        this.countList = countList;
    }

    public List<Integer> getDataTypeList() {
        return dataTypeList;
    }

    public void setDataTypeList(List<Integer> dataTypeList) {
        this.dataTypeList = dataTypeList;
    }

    public List<Integer> getContactWayList() {
        return contactWayList;
    }

    public void setContactWayList(List<Integer> contactWayList) {
        this.contactWayList = contactWayList;
    }

    public Byte getTimeCondition() {
        return timeCondition;
    }

    public void setTimeCondition(Byte timeCondition) {
        this.timeCondition = timeCondition;
    }
    
}
