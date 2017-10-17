/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 0.0.1
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.ContactTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
public class ContactTemplateDaoTest extends AbstractUnitTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ContactTemplateDao contactTemplateDao;

	@Before
	public void setUp() throws Exception {
		logger.info("测试: ContactTemplateDao 开始---------------------");
	}

	@Test
	public void testSelectFieldCodeListByFieldNameList() {
		logger.info("测试方法: selectFieldCodeListByFieldNameList ");
	}

	@Test
	public void testSelectRealContactTemplateListCount() {
		logger.info("测试方法: selectRealContactTemplateListCount ");
		ContactTemplate contactTemplate = new ContactTemplate();
		contactTemplate.setStatus((byte) 0);
		contactTemplate.setContactName("3");
		int count = contactTemplateDao.selectRealContactTemplateListCount(contactTemplate);
		logger.info("测试方法: selectRealContactTemplateListCount count:" + count);
		// 断言
		Assert.assertEquals(4, count);
	}

	@Test
	public void testDeleteByCId() {
		logger.info("测试方法: deleteByCId ");
	}

	@Test
	public void testUpdateById1() {
		logger.info("测试方法: updateById1 ");
	}

	@Test
	public void testSelectListAll() {
		logger.info("测试方法: selectListAll ");
	}

	@Test
	public void testSelectIdByContactId() {
		logger.info("测试方法: selectIdByContactId ");
	}

	@Test
	public void testUpdateKeyListById() {
		logger.info("测试方法: updateKeyListById ");
	}

	@Test
	public void testUpdatePageViewsById() {
		logger.info("测试方法: updatePageViewsById ");
	}

	@Test
	public void testSelectListGroupByCId() {
		logger.info("测试方法: selectListGroupByCId ");
	}

	@Test
	public void testInsert() {
		logger.info("测试方法: insert ");
	}

	@Test
	public void testSelectListCount() {
		logger.info("测试方法: selectListCount ");
	}

	@Test
	public void testUpdateById() {
		logger.info("测试方法: updateById ");
	}

	@Test
	public void testSelectList() {
		logger.info("测试方法: selectList ");
	}

	@Test
	public void testSelectListByIdList() {
		logger.info("测试方法: selectListByIdList ");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试: ContactTemplateDao 结束---------------------");
	}
}