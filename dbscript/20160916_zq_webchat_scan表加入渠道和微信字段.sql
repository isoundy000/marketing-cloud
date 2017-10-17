ALTER TABLE wechat_qrcode_scan ADD COLUMN `ch_code` INT(11) NULL COMMENT '渠道编号 wechat_channel.id';
ALTER TABLE wechat_qrcode_scan ADD COLUMN `wx_name` VARCHAR(100) DEFAULT NULL COMMENT '微信公众号名称wechat_qrcode.wx_name';
ALTER TABLE wechat_qrcode_scan ADD COLUMN `wx_acct` VARCHAR(500) DEFAULT NULL COMMENT '微信公众号代码wechat_qrcode.wx_acct';

