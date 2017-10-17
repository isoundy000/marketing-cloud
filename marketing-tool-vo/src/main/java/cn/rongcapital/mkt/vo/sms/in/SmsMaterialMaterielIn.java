package cn.rongcapital.mkt.vo.sms.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by byf on 12/2/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsMaterialMaterielIn {

    @NotNull
    private Long materielId;
    @NotNull
    private Integer materielType;

    @JsonProperty("materiel_id")
    public Long getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Long materielId) {
        this.materielId = materielId;
    }

    @JsonProperty("materiel_type")
    public Integer getMaterielType() {
        return materielType;
    }

    public void setMaterielType(Integer materielType) {
        this.materielType = materielType;
    }

}
