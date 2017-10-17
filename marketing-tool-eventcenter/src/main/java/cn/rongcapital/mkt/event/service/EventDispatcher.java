/**
 * 
 */
package cn.rongcapital.mkt.event.service;

/**
 * 事件分发器
 * 
 * @author shangchunming
 *
 */
public interface EventDispatcher {

	/**
	 * 分发事件
	 * 
	 * @param eventType
	 *            事件类型
	 * @param event
	 *            事件
	 */
	void dispatch(String eventType, String event);

}
