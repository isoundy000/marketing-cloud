-- 根据尹恒那边微信二维码的要求修改为wechat_qrcode_focus表添加一列用来存储微信公众号
ALTER TABLE wechat_qrcode_focus add column wxmp_id varchar(45) DEFAULT null COMMENT '微信公众号唯一ID';