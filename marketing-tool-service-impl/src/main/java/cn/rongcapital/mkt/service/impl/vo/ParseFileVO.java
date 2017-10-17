package cn.rongcapital.mkt.service.impl.vo;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ethan on 16/7/6.
 */
public class ParseFileVO {

    private ArrayList<Map<String,Object>> legalDataList;

    private ArrayList<String> illegaRawDataList;

    private String header;

    private String illegaColumns;

    private Integer totalRows;

    private Integer emailRows;
    private Integer mobileRows;
    private Integer duplicateRows;

    public ArrayList<Map<String, Object>> getLegalDataList() {
        return legalDataList;
    }

    public void setLegalDataList(ArrayList<Map<String, Object>> legalDataList) {
        this.legalDataList = legalDataList;
    }

    public String getIllegaColumns() {
        return illegaColumns;
    }

    public void setIllegaColumns(String illegaColumns) {
        this.illegaColumns = illegaColumns;
    }

    public ArrayList<String> getIllegaRawDataList() {
        return illegaRawDataList;
    }

    public void setIllegaRawDataList(ArrayList<String> illegaRawDataList) {
        this.illegaRawDataList = illegaRawDataList;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getEmailRows() {
        return emailRows;
    }

    public void setEmailRows(Integer emailRows) {
        this.emailRows = emailRows;
    }

    public Integer getMobileRows() {
        return mobileRows;
    }

    public void setMobileRows(Integer mobileRows) {
        this.mobileRows = mobileRows;
    }

    public Integer getDuplicateRows() {
        return duplicateRows;
    }

    public void setDuplicateRows(Integer duplicateRows) {
        this.duplicateRows = duplicateRows;
    }
}
