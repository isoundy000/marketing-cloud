CREATE TABLE `wechat_interface_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interface_name` varchar(500) NOT NULL COMMENT '接口名称',
  `method_name` varchar(500) DEFAULT NULL COMMENT '方法名称',
  `params` longtext COMMENT '参数',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29685 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

ALTER TABLE wechat_qrcode_ticket ADD COLUMN wx_acct varchar(500) CHARACTER SET utf8 COMMENT '微信账号';

ALTER TABLE wechat_member ADD COLUMN fans_json longtext COMMENT '微信原始对象的属性json';