/**
 * 
 */
package cn.rongcapital.mkt.event.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.event.service.EventDispatcher;

/**
 * @author shangchunming
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "classpath:event-center-test.xml" })
@ContextConfiguration(locations = { "classpath:event-center-test-rabbit.xml" })
public class EventTest {

	@Mock
	private TestEventCallback callback;

	@Autowired
	private TestSystemEventProcessor systemEventProcessor;

	@Autowired
	private TestMarketingEventProcessor marketingEventProcessor;

	@Autowired
	private EventDispatcher eventDispatcher;

	@Test
	public void test() {
		// dispatch event
		for (int i = 1; i <= 5; i++) {
			this.eventDispatcher.dispatch("SYSTEM", "system-event-" + i);
			this.eventDispatcher.dispatch("MARKETING", "marketing-event-" + i);
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e) {
			// //
			// }
		}

		// wait
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			//
		}

		// check
		Mockito.verify(this.callback).onEvent("SYSTEM", "system-event-1");
		Mockito.verify(this.callback).onEvent("SYSTEM", "system-event-2");
		Mockito.verify(this.callback).onEvent("SYSTEM", "system-event-3");
		Mockito.verify(this.callback).onEvent("SYSTEM", "system-event-4");
		Mockito.verify(this.callback).onEvent("SYSTEM", "system-event-5");
		Mockito.verify(this.callback).onEvent("MARKETING", "marketing-event-1");
		Mockito.verify(this.callback).onEvent("MARKETING", "marketing-event-2");
		Mockito.verify(this.callback).onEvent("MARKETING", "marketing-event-3");
		Mockito.verify(this.callback).onEvent("MARKETING", "marketing-event-4");
		Mockito.verify(this.callback).onEvent("MARKETING", "marketing-event-5");

		System.out.println("test done");
	}

	@Before
	public void initMocks() {
		// initialize the mock annotations
		MockitoAnnotations.initMocks(this);
		// set the callback
		this.systemEventProcessor.setCallback(this.callback);
		this.marketingEventProcessor.setCallback(this.callback);
	}

	/**
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(TestEventCallback callback) {
		this.callback = callback;
	}

	/**
	 * @param systemEventProcessor
	 *            the systemEventProcessor to set
	 */
	public void setSystemEventProcessor(TestSystemEventProcessor systemEventProcessor) {
		this.systemEventProcessor = systemEventProcessor;
	}

	/**
	 * @param marketingEventProcessor
	 *            the marketingEventProcessor to set
	 */
	public void setMarketingEventProcessor(TestMarketingEventProcessor marketingEventProcessor) {
		this.marketingEventProcessor = marketingEventProcessor;
	}

	/**
	 * @param eventDispatcher
	 *            the eventDispatcher to set
	 */
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}

}
