package cn.rongcapital.mkt.job.service.vo;

import cn.rongcapital.mkt.po.DataParty;

import java.util.List;

/**
 * Created by ethan on 16/6/30.
 */
public class DataPartySyncVO<T> {

    private List<DataParty> dataPartyList;

    private List<T> extendDataList;

    public List<T> getExtendDataList() {
        return extendDataList;
    }

    public void setExtendDataList(List<T> extendDataList) {
        this.extendDataList = extendDataList;
    }

    public List<DataParty> getDataPartyList() {
        return dataPartyList;
    }

    public void setDataPartyList(List<DataParty> dataPartyList) {
        this.dataPartyList = dataPartyList;
    }

}
