package cn.rongcapital.mkt.common.enums;

/**
 * Created by Yunfeng on 2016-7-12.
 */
public enum H5ImgtextType {

    YI_QI_XIU(1,"易企秀"),
    TU_ZHAN(1,"兔展"),
    MAKA(1,"MAKA");

    private Integer type;
    private String ownerName;

    H5ImgtextType(Integer type, String ownerName) {
        this.type = type;
        this.ownerName = ownerName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
