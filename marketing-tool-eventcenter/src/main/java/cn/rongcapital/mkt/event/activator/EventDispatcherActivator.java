/**
 * 
 */
package cn.rongcapital.mkt.event.activator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import cn.rongcapital.mkt.event.service.EventDispatcher;

/**
 * 事件分发Activator
 * 
 * @author shangchunming
 *
 */
public final class EventDispatcherActivator implements EventDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventDispatcherActivator.class);

	private static final String HEADER_KEY_EVENT_TYPE = "eventCenter.eventType";

	private MessageChannel channel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.rongcapital.mkt.event.service.EventDispatcher#dispatch(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void dispatch(String eventType, String event) {
		try {
			LOGGER.debug("dispatching the event, type: {}, event: {}", eventType, event);
			this.channel.send(MessageBuilder.withPayload(event).setHeader(HEADER_KEY_EVENT_TYPE, eventType).build());
			LOGGER.info("the event dispatched, type: {}, event: {}", eventType, event);
		} catch (Exception e) {
			LOGGER.error("dispatch the event failed, eventType: " + eventType + ", event: " + event + ", error: "
					+ e.getMessage(), e);
		}
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}

}
