package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.WechatQrcodeDao;
import cn.rongcapital.mkt.dao.WechatQrcodeFocusDao;
import cn.rongcapital.mkt.dao.WechatQrcodeTicketDao;
import cn.rongcapital.mkt.po.WechatQrcode;
import cn.rongcapital.mkt.po.WechatQrcodeFocus;
import cn.rongcapital.mkt.po.WechatQrcodeTicket;
import cn.rongcapital.mkt.service.QrcodeFocusInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Yunfeng on 2016-9-9.
 */
@Service
public class QrcodeFocusInsertServiceImpl implements QrcodeFocusInsertService{

    private final Integer FIRST_INDEX = 0;

    @Autowired
    private WechatQrcodeDao wechatQrcodeDao;

    @Autowired
    private WechatQrcodeFocusDao wechatQrcodeFocusDao;
    
    @Autowired
    private WechatQrcodeTicketDao wechatQrcodeTicketDao;
    
    @Override
    public boolean insertQrcodeFoucsInfo(String qrCodeTicket,String openId, Date scanQrTime, Integer focusStatus,String wxmpId) {
        boolean insertSuccess = false;
        Integer effectRow = 0;
        if (validateParameter(scanQrTime, focusStatus)) return insertSuccess;
        if(!StringUtils.isEmpty(qrCodeTicket)){
        	WechatQrcodeTicket wechatQrcodeTicketTemp = new WechatQrcodeTicket();
        	wechatQrcodeTicketTemp.setTicket(qrCodeTicket);
        	List<WechatQrcodeTicket> wechatQrcodeTicketes = wechatQrcodeTicketDao.selectList(wechatQrcodeTicketTemp);
        	if(wechatQrcodeTicketes!=null&&wechatQrcodeTicketes.size()>0){
        		WechatQrcodeTicket wechatQrcodeTicket = wechatQrcodeTicketes.get(0);
                WechatQrcode wechatQrcode = new WechatQrcode();
                wechatQrcode.setTicket(String.valueOf(wechatQrcodeTicket.getId()));
                List<WechatQrcode> wechatQrcodeList = wechatQrcodeDao.selectList(wechatQrcode);
                effectRow = InsertNewFocusTime(openId, scanQrTime, focusStatus, wxmpId, effectRow, wechatQrcodeList);
        	}
        }else {
            WechatQrcodeFocus wechatQrcodeFocus = new WechatQrcodeFocus();
            wechatQrcodeFocus.setWxAcct(wxmpId);
            wechatQrcodeFocus.setOpenid(openId);
            wechatQrcodeFocus.setFocusStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
            List<WechatQrcodeFocus> wechatQrcodeFocusList = wechatQrcodeFocusDao.selectList(wechatQrcodeFocus);
            effectRow = updateUnfocusTime(scanQrTime, focusStatus, effectRow, wechatQrcodeFocusList);
        }

        if(effectRow > 0){
            insertSuccess = true;
        }

        return insertSuccess;
    }

    private Integer updateUnfocusTime(Date scanQrTime, Integer focusStatus, Integer effectRow, List<WechatQrcodeFocus> wechatQrcodeFocusList) {
        if(!CollectionUtils.isEmpty(wechatQrcodeFocusList)){
            WechatQrcodeFocus wechatQrcodeFocusData = wechatQrcodeFocusList.get(FIRST_INDEX);
            wechatQrcodeFocusData.setUnfocusDatetime(scanQrTime);
            wechatQrcodeFocusData.setFocusStatus(focusStatus.byteValue());
            effectRow = wechatQrcodeFocusDao.updateById(wechatQrcodeFocusData);
        }
        return effectRow;
    }

    private Integer InsertNewFocusTime(String openId, Date scanQrTime, Integer focusStatus, String wxmpId, Integer effectRow, List<WechatQrcode> wechatQrcodeList) {
        WechatQrcode wechatQrcode;
        if(!CollectionUtils.isEmpty(wechatQrcodeList)){
            wechatQrcode = wechatQrcodeList.get(FIRST_INDEX);
            WechatQrcodeFocus wechatQrcodeFocus = new WechatQrcodeFocus();
            wechatQrcodeFocus.setQrcodeId(String.valueOf(wechatQrcode.getId()));
            wechatQrcodeFocus.setWxAcct(wxmpId);
            wechatQrcodeFocus.setWxName(wechatQrcode.getWxName());
            wechatQrcodeFocus.setOpenid(openId);
            wechatQrcodeFocus.setChCode(wechatQrcode.getChCode());
            wechatQrcodeFocus.setFocusDatetime(scanQrTime);
            wechatQrcodeFocus.setFocusStatus(focusStatus.byteValue());
            effectRow = wechatQrcodeFocusDao.insert(wechatQrcodeFocus);
        }
        return effectRow;
    }

    private boolean validateParameter(Date scanQrTime, Integer focusStatus) {
        if(scanQrTime == null || focusStatus == null){
            return true;
        }
        return false;
    }
}
