package cn.rongcapital.mkt.event.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class EventBehavierListIn extends BaseInput {

	 @NotNull
	 @JsonProperty("event_id")
	 private Long eventId;
	 
	 @JsonProperty("attributes")
	 private String attributes;
	 
	 @NotEmpty
	 @JsonProperty("user_id")
	 private String userId;
	 
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
