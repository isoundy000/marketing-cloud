package cn.rongcapital.mkt.service;

public interface TagSystemCommonUtilService {
    
    /**
     * 功能描述：根据标签tag_id获取标签覆盖率
     * 任何异常都返回：0%
     * 
     * @param tagId
     * @return
     */
    public String getTagCover(String tagId);
    public int getTagCoverAmount(String tagId);
    public boolean isTagCoverData(String tagId);
}
