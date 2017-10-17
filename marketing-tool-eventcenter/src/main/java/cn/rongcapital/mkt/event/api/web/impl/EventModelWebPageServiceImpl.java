package cn.rongcapital.mkt.event.api.web.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.rongcapital.mkt.event.api.EventModelWebPageService;
import cn.rongcapital.mkt.event.service.EventModelCountService;
import cn.rongcapital.mkt.event.service.EventSourceListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Controller
public class EventModelWebPageServiceImpl implements EventModelWebPageService{
	 /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EventModelWebPageServiceImpl.class);

    @Autowired
    private EventModelCountService eEventModelCountService;
    
    @Autowired
    private EventSourceListService eventSourceListService;

	@Override
	public BaseOutput getEventModelCount(String userToken, String version) {
		return eEventModelCountService.getEventModelCountList();
	}

	@Override
	public BaseOutput getEventSourceListByChannel(String userToken, String version, String channel) {
		return eventSourceListService.getEventSourceListByChannel(channel);
	}

}
