package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 12/5/16.
 */
public class SmsMaterialMaterielOut {

    private Integer materielId;
    private Integer materielType;
    private String materielName;
    private Double materielAmount;
    private Integer materielStockTotal;

    @JsonProperty("materiel_id")
    public Integer getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Integer materielId) {
        this.materielId = materielId;
    }

    @JsonProperty("materiel_type")
    public Integer getMaterielType() {
        return materielType;
    }

    public void setMaterielType(Integer materielType) {
        this.materielType = materielType;
    }

    @JsonProperty("materiel_name")
    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    @JsonProperty("materiel_amount")
    public Double getMaterielAmount() {
        return materielAmount;
    }

    public void setMaterielAmount(Double materielAmount) {
        this.materielAmount = materielAmount;
    }

    @JsonProperty("materiel_stock_total")
    public Integer getMaterielStockTotal() {
        return materielStockTotal;
    }

    public void setMaterielStockTotal(Integer materielStockTotal) {
        this.materielStockTotal = materielStockTotal;
    }
}
