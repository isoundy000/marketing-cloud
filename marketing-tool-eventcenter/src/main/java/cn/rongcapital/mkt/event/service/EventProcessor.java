/**
 * 
 */
package cn.rongcapital.mkt.event.service;

/**
 * 事件处理器
 * 
 * @author shangchunming
 *
 */
public interface EventProcessor {

	/**
	 * 处理事件
	 * 
	 * @param event
	 *            事件
	 */
	void process(String event);

}
