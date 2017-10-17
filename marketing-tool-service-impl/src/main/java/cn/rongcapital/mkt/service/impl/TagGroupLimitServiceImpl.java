package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagGroupLimitDao;
import cn.rongcapital.mkt.po.TagGroupLimit;
import cn.rongcapital.mkt.service.TagGroupLimitService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

/*************************************************
 * @功能及特点的描述简述: 细分的组和组内标签个数限制接口
 *
 * @see （与该类关联的类): TagGroupLimitService
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.5 
 * @date(创建、开发日期): 2016-11-07 最后修改日期: 2016-11-07
 * @复审人: 丛树林
 *************************************************/
@Service
public class TagGroupLimitServiceImpl implements TagGroupLimitService {
	@Autowired
	TagGroupLimitDao tagGroupLimitDao;

	/**
	 * @功能简述: 获取细分的标签组和组内标签个数限制接口
	 * 
	 *
	 * @param: source
	 *             来源
	 * @return: BaseOutput
	 */
	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput getTagGroupLimit(String source) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		TagGroupLimit param = new TagGroupLimit();
		param.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		if (StringUtils.isNotBlank(source)) {
			param.setSource(source);
		} else {
			param.setSource("incake");
		}

        List<TagGroupLimit> selectList = tagGroupLimitDao.selectList(param);
        if (CollectionUtils.isNotEmpty(selectList)) {
            TagGroupLimit tagGroupLimit = selectList.get(0);
            Map<String, Object> contactListMap = new HashMap<String, Object>();
            contactListMap.put("group_limit", tagGroupLimit.getGroupLimit());
            contactListMap.put("tag_limit", tagGroupLimit.getTagLimit());
            result.setTotal(ApiConstant.INT_ONE);
            result.getData().add(contactListMap);
        }

		return result;
	}

}
