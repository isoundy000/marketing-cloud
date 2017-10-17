drop table sms_material;

CREATE TABLE `sms_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(200) DEFAULT NULL COMMENT '素材编号',
	`name` varchar(250) DEFAULT NULL COMMENT '素材名称',
  `channel_type` tinyint(4) DEFAULT '0' COMMENT '通道类型：0:营销短信素材,1:服务通知素材,2：短信验证码素材',
  `sms_type` tinyint(4) DEFAULT '0' COMMENT '短信类型：0:固定短信,1:变量短信', 
	`sms_templet_id` int(11) NOT NULL COMMENT '模板主键id',
	`sms_templet_content` longtext COMMENT '模板内容',
	`sms_sign_id` int(11) COMMENT '签名主键id',
	`sms_sign_name` varchar(250) DEFAULT NULL COMMENT '签名名称',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '素材状态：0：有效,1：删除',
  `creator` varchar(200) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL,
  `update_user` varchar(200) DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='短信素材';

ALTER TABLE sms_templet ADD COLUMN name varchar(250) COMMENT '模板名称';