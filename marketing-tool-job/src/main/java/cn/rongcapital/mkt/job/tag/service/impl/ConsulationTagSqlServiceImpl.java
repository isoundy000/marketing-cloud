package cn.rongcapital.mkt.job.tag.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.TagDefinitionDao;
import cn.rongcapital.mkt.dao.TagSqlParamDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.TagDefinition;
import cn.rongcapital.mkt.po.TagSqlParam;

/*************************************************
 * @功能简述: 拼装系统标签可修改标签依赖SQL
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2016/12/16
 * @复审人:
 *************************************************/
@Service
public class ConsulationTagSqlServiceImpl implements TaskService{
	
	@Autowired
	private TagSqlParamDao tagSqlParamDao;
	
	@Autowired
	private TagDefinitionDao tagDefinitionDao;

	@Override
	public void task(Integer taskId) {
		execute();
	}
	
	private void execute(){
		
		TagDefinition tagDefinition = new TagDefinition();
		tagDefinition.setPageSize(null);
		tagDefinition.setIsUpdate("YES");
		List<TagDefinition> definitionList = tagDefinitionDao.selectList(tagDefinition);
		for (TagDefinition tagdef : definitionList) {
			//获取模板
			String model = tagdef.getModel();
			TagSqlParam tagSqlParam = tagSqlParamDao.selectList(new TagSqlParam(tagdef.getTagName())).get(0);
			String handlerSqlParam = handlerSqlParam(model, tagSqlParam);
			String overStr = tagdef.getSql_definition().replaceAll("PARAM", handlerSqlParam);
			tagdef.setReserve2(overStr);
			tagdef.setIsUpdate("NO");
			tagDefinitionDao.updateById(tagdef);
		}
		
	}
	
	private String handlerSqlParam(String model,TagSqlParam tagSqlParam){
		StringBuilder sb = new StringBuilder();
		String lowerLimitValue = tagSqlParam.getLowerLimitValue();
		String upperLimitValue = tagSqlParam.getUpperLimitValue();
		String scopeValue = tagSqlParam.getScopeValue();
		if(!StringUtils.isEmpty(lowerLimitValue)){
			sb.append(model.replaceAll("P1", lowerLimitValue).replaceAll("P2", lowerLimitValue)+" ");
		}
		String[] split = scopeValue.split(",");
		for (String str : split) {
			sb.append(model.replaceAll("P1", str).replaceAll("P2", splitParam(str))+" ");
		}
		if(!StringUtils.isEmpty(upperLimitValue)){
			sb.append(model.replaceAll("P1", upperLimitValue).replaceAll("P2", upperLimitValue)+" ");
		}
		return sb.toString();
	}
	
	private String splitParam(String initString){
		String[] split = initString.replaceAll("BETWEEN", "").replaceAll(" ", "").split("AND");
		return split[0]+"-"+split[1];
	}
	
}
