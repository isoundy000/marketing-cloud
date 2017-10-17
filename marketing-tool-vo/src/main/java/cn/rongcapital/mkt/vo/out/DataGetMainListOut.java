package cn.rongcapital.mkt.vo.out;

import java.util.List;
import java.util.Map;

import cn.rongcapital.mkt.vo.BaseOutput;

public class DataGetMainListOut extends BaseOutput {

    public DataGetMainListOut() {}

    public DataGetMainListOut(int code, String msg, int total, List<Object> data) {
        super(code, msg, total, data);
    }

    private int mdType;
    
    private List<Map<String, Object>> countList ;
    
    private List<Integer> dataTypeList;
    
    private List<Integer> contactWayList;
    
    private Byte timeCondition;

    public List<Map<String, Object>> getCountList() {
        return countList;
    }

    public void setCountList(List<Map<String, Object>> countList) {
        this.countList = countList;
    }

    public int getMdType() {
        return mdType;
    }

    public void setMdType(int mdType) {
        this.mdType = mdType;
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

