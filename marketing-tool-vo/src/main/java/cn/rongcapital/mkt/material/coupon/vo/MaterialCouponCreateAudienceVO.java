/*************************************************
 * @功能简述: 新建固定人群Vo类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 1.0
 * @date: 2016/12/16
 *************************************************/
package cn.rongcapital.mkt.material.coupon.vo;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MaterialCouponCreateAudienceVO extends BaseInput{

    @NotNull
    @JsonProperty("id")
    Long id;
    
    @NotBlank
	@JsonProperty("name")
    String name;
    
	@JsonProperty("blur_search")
    String blurSearch;
    
	@JsonProperty("receive_status")
    String releaseStatus;
    
	@JsonProperty("verify_status")
    String verifyStatus;
    
	@JsonProperty("expire_status")
    String expireStatus;
	
	@NotEmpty
    @JsonProperty("user_token")
    private String userToken = null;
    public MaterialCouponCreateAudienceVO(Long id, String name, String blurSearch, String releaseStatus,
            String verifyStatus, String expireStatus) {
        super();
        this.id = id;
        this.name = name;
        this.blurSearch = blurSearch;
        this.releaseStatus = releaseStatus;
        this.verifyStatus = verifyStatus;
        this.expireStatus = expireStatus;
    }

    public MaterialCouponCreateAudienceVO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlurSearch() {
        return blurSearch;
    }

    public void setBlurSearch(String blurSearch) {
        this.blurSearch = blurSearch;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getExpireStatus() {
        return expireStatus;
    }

    public void setExpireStatus(String expireStatus) {
        this.expireStatus = expireStatus;
    }

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
    
}
