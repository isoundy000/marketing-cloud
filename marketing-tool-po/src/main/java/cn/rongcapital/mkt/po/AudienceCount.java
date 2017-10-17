package cn.rongcapital.mkt.po;

import java.io.Serializable;

public class AudienceCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long audienceCount;

    private Long audiencePeopleCount;

    public Long getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(Long audienceCount) {
        this.audienceCount = audienceCount;
    }

    public Long getAudiencePeopleCount() {
        return audiencePeopleCount;
    }

    public void setAudiencePeopleCount(Long audiencePeopleCount) {
        this.audiencePeopleCount = audiencePeopleCount;
    }
}
