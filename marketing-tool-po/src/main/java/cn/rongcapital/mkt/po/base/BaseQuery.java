/*************************************************
 * @功能简述: PO基类(常规公共字段)
 * @项目名称: marketing cloud
 * @see:
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人:
 *************************************************/

package cn.rongcapital.mkt.po.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.rongcapital.mkt.common.constant.ApiConstant;

public class BaseQuery implements Serializable{

	private static final long serialVersionUID = 1L;

	private transient Integer startIndex = ApiConstant.PAGE_START_INDEX_DEFAULT;// 开始索引

    private transient Integer pageSize = ApiConstant.PAGE_PAGE_SIZE_DEFAULT;// pageSize

    private transient String orderField;// 排序字段

    private transient String orderFieldType;// 排序字段类型:asc,desc
    
    
    
    private transient String mid;    //主数据ID
    private transient String md_type;//主数据类型
    private transient String mapping_keyid;//主数据keyid
    

    // 自定义参数
    private transient Map<String, Object> customMap = new HashMap<>();

    public BaseQuery() {}

    public BaseQuery(Integer startIndex, Integer pageSize) {
        this.pageSize = pageSize;
        this.startIndex = startIndex;
        if (this.startIndex == null || this.startIndex < 1) {
            this.startIndex = 1;
        }

        if (this.pageSize == null || this.pageSize < 0) {
            this.pageSize = 10;
        }

        if (this.pageSize > 100) {
            this.pageSize = 100;
        }

        this.startIndex = (this.startIndex - 1) * this.pageSize;
    }


    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderFieldType() {
        if ("DESC".equalsIgnoreCase(orderFieldType) || "ASC".equalsIgnoreCase(orderFieldType)) {
            return orderFieldType.toUpperCase();
        }
        return null;
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }

    public Map<String, Object> getCustomMap() {
        return customMap;
    }

    public void setCustomMap(Map<String, Object> customMap) {
        this.customMap = customMap;
    }
    
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
    
    public String getMd_type() {
        return md_type;
    }

    public void setMd_type(String md_type) {
        this.md_type = md_type;
    }
    
    public String getMapping_keyid() {
        return mapping_keyid;
    }

    public void setMapping_keyid(String mapping_keyid) {
        this.mapping_keyid = mapping_keyid;
    }

}
