package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class ChannelTypeMapping extends BaseQuery{
    private Integer id;

    private String mediaChannel;

    private String mediaChannelName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMediaChannel() {
        return mediaChannel;
    }

    public void setMediaChannel(String mediaChannel) {
        this.mediaChannel = mediaChannel == null ? null : mediaChannel.trim();
    }

    public String getMediaChannelName() {
        return mediaChannelName;
    }

    public void setMediaChannelName(String mediaChannelName) {
        this.mediaChannelName = mediaChannelName == null ? null : mediaChannelName.trim();
    }
}