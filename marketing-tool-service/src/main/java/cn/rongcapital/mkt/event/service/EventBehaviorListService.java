package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.event.vo.in.EventBehavierListIn;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;

public interface EventBehaviorListService {

	EventBehaviorOut getEventBehavierList(EventBehavierListIn eventBehavierListIn);

}
