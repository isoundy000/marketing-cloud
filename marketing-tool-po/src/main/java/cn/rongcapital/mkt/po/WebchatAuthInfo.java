package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class WebchatAuthInfo extends BaseQuery {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String authorizerAppid;

    private String authorizerAccessToken;

    private String authorizerRefreshToken;

    private String expiresIn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid == null ? null : authorizerAppid.trim();
    }

    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public void setAuthorizerAccessToken(String authorizerAccessToken) {
        this.authorizerAccessToken = authorizerAccessToken == null ? null : authorizerAccessToken.trim();
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken == null ? null : authorizerRefreshToken.trim();
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn == null ? null : expiresIn.trim();
    }
}
