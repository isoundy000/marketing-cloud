package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class ColumnsOut {

	private String colName;
	
	private String colCode;

	@JsonProperty("col_name")
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@JsonProperty("col_code")
	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((colCode == null) ? 0 : colCode.hashCode());
        result = prime * result + ((colName == null) ? 0 : colName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColumnsOut other = (ColumnsOut) obj;
        if (colCode == null) {
            if (other.colCode != null)
                return false;
        } else if (!colCode.equals(other.colCode))
            return false;
        if (colName == null) {
            if (other.colName != null)
                return false;
        } else if (!colName.equals(other.colName))
            return false;
        return true;
    }
	
	
	
}
