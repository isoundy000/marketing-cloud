/*************************************************
 * @功能简述: dao test基类用于启动框架
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 
 * @date: 
 * @复审人: 
*************************************************/
package cn.rongcapital.mkt.dao.testbase;


import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootDaoTestMain.class)
public abstract class AbstractUnitTest { }
