package cn.rongcapital.mkt.job.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.jetty.util.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.AudienceListPartyMap;

@Service
public class AudienceCountTaskImpl implements TaskService {
	@Autowired
	private AudienceListDao audienceListDao;
	@Autowired
	private AudienceListPartyMapDao audienceListPartyMapDao;
	private static int batchSize = 10;

	@Override
	public void task(Integer taskId) {
		Log.info("更新人群管理中人数Task----start----");
		AudienceList audienceListT = new AudienceList();
		audienceListT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int totalRecord = audienceListDao.selectListCount(audienceListT);
		int totalPage = (totalRecord + batchSize - 1) / batchSize;
		for (int pageIndex = 1; pageIndex <= totalPage; pageIndex++) {
			audienceListT = new AudienceList(pageIndex, batchSize);
			audienceListT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			List<AudienceList> audienceListList = audienceListDao.selectList(audienceListT);
			if (CollectionUtils.isEmpty(audienceListList)) {
				break;
			}
			for (AudienceList audienceList : audienceListList) {
				AudienceListPartyMap audienceListPartyMapT = new AudienceListPartyMap();
				audienceListPartyMapT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				audienceListPartyMapT.setAudienceListId(audienceList.getId());
				int audienceCount = audienceListPartyMapDao.selectListCount(audienceListPartyMapT);
				audienceList.setAudienceRows(audienceCount);
				audienceListDao.updateById(audienceList);
			}
		}
		Log.info("更新人群管理中人数Task----end----");
	}

}
