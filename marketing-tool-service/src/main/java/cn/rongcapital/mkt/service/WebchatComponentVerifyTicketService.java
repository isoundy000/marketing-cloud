package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.in.ComponentVerifyTicketIn;

public interface WebchatComponentVerifyTicketService {

	public void insert(String componentVerifyTicketXml);
	
	public void insert(ComponentVerifyTicketIn componentVerifyTicketIn,String msg_signature,String timestamp,String nonce);
	
	
}
