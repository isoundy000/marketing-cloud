package cn.rongcapital.mkt.po;

public class RuleEngineResult {
	
	
	private Integer result;
	
	private String rule_code;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getRule_code() {
		return rule_code;
	}

	public void setRule_code(String rule_code) {
		this.rule_code = rule_code;
	}

	@Override
	public String toString() {
		return "Result [result=" + result + ", rule_code=" + rule_code + "]";
	}

}
