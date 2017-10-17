package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailStateDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.po.SmsTaskDetailState;
import cn.rongcapital.mkt.po.SmsTaskHead;

/**
 * @author lihaiguang
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsSendTaskServiceTest {
	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	    @Mock
	    private SmsTaskHeadDao smsTaskHeadDao;
	    
	    @Mock
		private SmsTaskDetailDao smsTaskDetailDao;

	    @Mock
		private SmsTaskDetailStateDao smsTaskDetailStateDao;
	    
	    List<SmsTaskDetail> smsDetailList;
	    List<SmsTaskHead> smsHeadList;
	    TaskService smsSendTaskService;
	    @Before
	    public void setUp() throws Exception {
	    	SmsTaskHead head ;
	    	SmsTaskDetail detail = new SmsTaskDetail();
	    	
	    	smsHeadList = new ArrayList<>();
	    	head= new SmsTaskHead();
	    	head.setId(1l);
	    	head.setStatus((byte)0);
	    	head.setSmsTaskStatus(2);//执行中
	    	smsHeadList.add(head);
	    	
	    	smsDetailList = new ArrayList<>();
	    	for(int i=0; i<7; i++){
	    		detail = new SmsTaskDetail();
	    		detail.setId(Long.valueOf(i));
	    		detail.setReceiveMobile("1355213489"+ i);
	    		detail.setSendMessage("send message count " + i);
	    		smsDetailList.add(detail);
	    	}
	    	
	    	Mockito.when(smsTaskHeadDao.selectList(any())).thenReturn(smsHeadList);
	    	Mockito.when(smsTaskHeadDao.updateById(any())).thenReturn(1);
	    	Mockito.when(smsTaskDetailDao.selectList(any())).thenReturn(smsDetailList);
	    	
	    	SmsTaskHeadDao smsTaskHeadDao2 = Mockito.mock(SmsTaskHeadDao.class);
	    	Mockito.doAnswer(new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation) throws Throwable {
					Object[] args = invocation.getArguments();
					SmsTaskHead smsHead = null;
					smsHead = (SmsTaskHead)args[0];
					smsHead.setSmsTaskStatus(4);
					smsHead.setWaitingNum(0);
					return smsHead;
				}
	    		
	    	}).when(smsTaskHeadDao2).updateById(any());
	    	
	    	SmsTaskDetailStateDao smsTaskDetailStateDao = Mockito.mock(SmsTaskDetailStateDao.class);
	    	Mockito.doAnswer(new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation) throws Throwable {
					Object[] args = invocation.getArguments();
					SmsTaskDetailState state = null;
					state = (SmsTaskDetailState)args[0];
					logger.info("update sms state id is {}, state is {}",  state.getSmsTaskDetailId(),
							state.getSmsTaskSendStatus());
					return state;
				}
			}).when(smsTaskDetailStateDao).updateDetailState(any());
	    	
	    	smsSendTaskService = new SmsSendTaskServiceImpl();
	    	ReflectionTestUtils.setField(smsSendTaskService, "smsTaskHeadDao", smsTaskHeadDao);
	    	//ReflectionTestUtils.setField(smsSendTaskService, "smsTaskHeadDao", smsTaskHeadDao2);
	    	ReflectionTestUtils.setField(smsSendTaskService, "smsTaskDetailDao", smsTaskDetailDao);
	    	ReflectionTestUtils.setField(smsSendTaskService, "smsTaskDetailStateDao", smsTaskDetailStateDao);
	    }
	    
	    @Test
	    public void testmessageSendRecordGet() {
	    	smsSendTaskService.task("1");
	    	logger.info("test end=========================");
	    }
	    
	    @After
	    public void tearDown() throws Exception {}


}
