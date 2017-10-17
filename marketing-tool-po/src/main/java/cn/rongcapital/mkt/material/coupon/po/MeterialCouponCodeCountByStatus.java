package cn.rongcapital.mkt.material.coupon.po;


/**
 * Created by ShanJingqi on 2016-12-07.
 */
public class MeterialCouponCodeCountByStatus{

	private Long id;
	private String status;
	private Long count;
	public MeterialCouponCodeCountByStatus(Long id, String status, Long count) {
		super();
		this.id = id;
		this.status = status;
		this.count = count;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	
}
