package cn.rongcapital.mkt.job.vo.in;

/**
 * Created by Yunfeng on 2016-6-25.
 */
public class H5PersonalGroup {
    private String ucode;
    private String headImage;
    private String nickname;
    private String memberCount;
    private H5PersonalGroupMembers members;

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }

    public H5PersonalGroupMembers getMembers() {
        return members;
    }

    public void setMembers(H5PersonalGroupMembers members) {
        this.members = members;
    }
}
