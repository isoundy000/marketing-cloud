package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagGroupLimitDao;
import cn.rongcapital.mkt.po.TagGroupLimit;
import cn.rongcapital.mkt.service.TagGroupLimitService;
import cn.rongcapital.mkt.vo.BaseOutput;

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
@RunWith(MockitoJUnitRunner.class)
public class TagGroupLimitServiceTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private TagGroupLimitService tagGroupLimitService;

	@Mock
	TagGroupLimitDao tagGroupLimitDao;

	@Before
	public void setUp() throws Exception {
		logger.info("测试：TagGroupLimitService 准备---------------------");
		List<TagGroupLimit> selectList = new ArrayList<TagGroupLimit>();
		// mock数据
		TagGroupLimit tagGroupLimit = new TagGroupLimit();
		tagGroupLimit.setGroupLimit(5);
		tagGroupLimit.setTagLimit(2);
		tagGroupLimit.setStatus((byte) 0);
		tagGroupLimit.setSource("test");
		selectList.add(tagGroupLimit);

		Mockito.when(tagGroupLimitDao.selectList(any())).thenReturn(selectList);

		tagGroupLimitService = new TagGroupLimitServiceImpl();

		// 把mock的dao set进入service
		ReflectionTestUtils.setField(tagGroupLimitService, "tagGroupLimitDao", tagGroupLimitDao);

	}

	@Test
	public void testGetTagGroupLimit() {
		logger.info("测试方法: GetTagGroupLimit");
		String source = "test";
		BaseOutput result = tagGroupLimitService.getTagGroupLimit(source);

		// 断言
		Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());

		TagGroupLimit object = (TagGroupLimit) result.getData().get(0);
		Assert.assertEquals("test", object.getSource());
		Assert.assertEquals("5", object.getGroupLimit() + "");
		Assert.assertEquals("2", object.getTagLimit() + "");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试：TagGroupLimitService 结束---------------------");
	}

}
