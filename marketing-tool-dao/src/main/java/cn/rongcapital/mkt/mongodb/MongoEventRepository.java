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

package cn.rongcapital.mkt.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

public interface MongoEventRepository extends MongoRepository<EventBehavior, Long>{
    
     //方法以findBy开头，后面跟EventBehavior中的属性名(首字母大写) 
   public EventBehavior findById(String id);
}
