/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月5日 
 * @date(最后修改日期)：2017年1月5日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.dao.mongo.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.dao.mongo.MongoBaseTagDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.mongodb.MongoEventRepository;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

@RunWith(SpringJUnit4ClassRunner.class)
public class MongoEventReceiveDaoTest extends AbstractUnitTest {
  
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    
    @Autowired
    private MongoEventRepository mongoEventRepository;
   
    @Test
    public void test()
    {  
        
        long time =System.currentTimeMillis();
        String json="{\"subject\": {\"openid\":2222456,\"mobile\":13842629999,\"mail\":\"ypslu@163.com\"},\"time\":"+time+",\"object\":"
                + "{\"coupon_name\":\"10元优惠券\",\"attributes\":{\"color\":\"red\",\"value\":10}}}";
        EventBehavior jsonob =JSON.parseObject(json,EventBehavior.class);
        EventBehavior insert= mongoEventRepository.insert(jsonob); //插入 
        EventBehavior query= mongoEventRepository.findById(insert.getId()); //查询刚插入的数据
        Assert.assertEquals(insert.getTime(), query.getTime());

    }
}
