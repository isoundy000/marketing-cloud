/*************************************************
 * @功能简述: 
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 20176/1/9
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventModelCountOut {
	@JsonProperty("event_total_count")
	private Long totalCount;
	
	@JsonProperty("event_1st_channel_count")
	private Long firstChannelCount;
	
	@JsonProperty("event_2nd_channel_count")
	private Long secondChannelCount;
	
	@JsonProperty("event_3rd_channel_count")
	private Long thirdChannelCount;
	public EventModelCountOut(Long firstChannelCount, Long secondChannelCount, Long thirdChannelCount) {
		super();
		this.firstChannelCount = firstChannelCount;
		this.secondChannelCount = secondChannelCount;
		this.thirdChannelCount = thirdChannelCount;
	}
	
	public Long getTotalCount() {
		return totalCount = firstChannelCount + secondChannelCount + thirdChannelCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getFirstChannelCount() {
		return firstChannelCount;
	}

	public void setFirstChannelCount(Long firstChannelCount) {
		this.firstChannelCount = firstChannelCount;
	}

	public Long getSecondChannelCount() {
		return secondChannelCount;
	}

	public void setSecondChannelCount(Long secondChannelCount) {
		this.secondChannelCount = secondChannelCount;
	}

	public Long getThirdChannelCount() {
		return thirdChannelCount;
	}

	public void setThirdChannelCount(Long thirdChannelCount) {
		this.thirdChannelCount = thirdChannelCount;
	}
	
}
