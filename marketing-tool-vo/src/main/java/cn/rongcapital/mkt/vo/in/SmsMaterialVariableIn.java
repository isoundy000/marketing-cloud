package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by byf on 12/2/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsMaterialVariableIn {

    @NotEmpty
    private String variableName;
    @NotEmpty
    private String variableValue;
    @NotNull
    private Integer variableType;

    @JsonProperty("variable_name")
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @JsonProperty("variable_value")
    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    @JsonProperty("variable_type")
    public Integer getVariableType() {
        return variableType;
    }

    public void setVariableType(Integer variableType) {
        this.variableType = variableType;
    }
}
