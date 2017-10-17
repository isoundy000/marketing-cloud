/*************************************************
 * @功能及特点的描述简述: 事件行为mongoDB repository类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-07 
 * @date(最后修改日期)：2017-01-07 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.mongodb.event;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

public interface  EventBehaviorRepository extends MongoRepository<EventBehavior,Long> {

	/**
	 * 方法以findBy开头，后面跟DataParty中的属性名(首字母大写)
	 * @param mid
	 * @return
	 */
	public List<EventBehavior> findById(String id);
	
	Page<EventBehavior> findAll(Pageable pageable);

}
