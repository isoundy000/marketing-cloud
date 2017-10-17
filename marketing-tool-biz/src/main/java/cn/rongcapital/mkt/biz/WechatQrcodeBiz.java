package cn.rongcapital.mkt.biz;

import java.io.FileNotFoundException;
import java.io.IOException;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.WechatQrcodeIn;

public interface WechatQrcodeBiz {

	BaseOutput getQrcode(int sceneId,String actionName,String wxAcct)  throws FileNotFoundException, IOException;
	
	BaseOutput getQrcodes(int startSceneId,int endSceneId,String actionName)  throws FileNotFoundException, IOException;
	
	BaseOutput createQrcode(WechatQrcodeIn wechatQrcodeIn);
	
	BaseOutput getWechatQrcodeTicket();
	
	BaseOutput enableQrcode(WechatQrcodeIn wechatQrcodeIn);

}
