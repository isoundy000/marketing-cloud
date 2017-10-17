/**
 * 
 */
package cn.rongcapital.mkt.event.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.event.service.EventProcessor;

/**
 * @author shangchunming
 *
 */
public class TestSystemEventProcessor implements EventProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestSystemEventProcessor.class);

	private TestEventCallback callback;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.rongcapital.mkt.event.service.EventProcessor#process(java.lang.String)
	 */
	@Override
	public void process(String event) {
		LOGGER.info("processing SYSTEM event: {}", event);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			//
		}
		LOGGER.info("SYSTEM event processed: {}", event);
		this.callback.onEvent("SYSTEM", event);
	}

	/**
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(TestEventCallback callback) {
		this.callback = callback;
	}

}
