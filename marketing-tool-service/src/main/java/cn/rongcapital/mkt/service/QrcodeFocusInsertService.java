package cn.rongcapital.mkt.service;

import java.util.Date;

/**
 * Created by Yunfeng on 2016-9-9.
 */
public interface QrcodeFocusInsertService {
    boolean insertQrcodeFoucsInfo(String qrCodeTicket,String openId, Date scanQrTime,Integer status,String wxmpId);
}
