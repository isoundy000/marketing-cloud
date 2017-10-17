drop table wechat_register;
drop table wechat_group;
drop table wechat_member;
drop table img_text_asset;
drop table wechat_interface_log;

CREATE TABLE `wechat_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wx_code` varchar(200) DEFAULT NULL COMMENT '成员微信账号：\n公众号：openid\n个人号 ：ucode',
  `wx_name` varchar(200) DEFAULT NULL COMMENT '成员微信名称',
  `wx_group_id` varchar(20) NOT NULL COMMENT '所属微信群组的ID,wechat_group.id',
  `nickname` varchar(1000) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `country` varchar(500) DEFAULT NULL,
  `province` varchar(500) DEFAULT NULL,
  `city` varchar(500) DEFAULT NULL,
  `county` varchar(500) DEFAULT NULL COMMENT '位置',
  `birthday` varchar(500) DEFAULT NULL,
  `signature` varchar(1000) DEFAULT NULL COMMENT '签名',
  `is_friend` varchar(45) DEFAULT NULL COMMENT '是否好友',
  `pub_id` varchar(200) DEFAULT NULL COMMENT '公众号唯一标识',
  `uin` varchar(200) DEFAULT NULL COMMENT '个人号唯一标识',
  `subscribe_yn` varchar(200) DEFAULT NULL,
  `subscribe_time` varchar(500) DEFAULT NULL,
  `active_time` varchar(500) DEFAULT NULL,
  `activity_48h_yn` varchar(200) DEFAULT NULL,
  `head_image_url` varchar(1000) DEFAULT NULL,
  `remark` varchar(5000) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `selected` tinyint(4) NOT NULL DEFAULT '0' COMMENT '往data_party表里的同步状态',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `fans_json` longtext COMMENT '微信原始对象的属性json',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信成员(好友、粉丝)信息表';

CREATE TABLE `img_text_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(1024) DEFAULT NULL COMMENT '图文名称',
  `type` tinyint(4) DEFAULT '0' COMMENT '0:微信资产,1:h5资产',
  `owner_name` varchar(200) DEFAULT NULL COMMENT '创建者名称:微信号/h5平台名',
  `pc_preview_url` varchar(1000) DEFAULT NULL COMMENT '电脑预览url',
  `mobile_preview_url` varchar(1000) DEFAULT NULL COMMENT '手机预览url',
  `imgfile_url` varchar(1000) DEFAULT NULL COMMENT '缩略图url',
  `material_id` varchar(200) DEFAULT NULL COMMENT '图文素材在大连H5数据库中的主键',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pub_id` varchar(45) DEFAULT NULL COMMENT '图文所属公众号id',
  `pub_name` varchar(200) DEFAULT NULL COMMENT '图文所属公众号名称',
  `wechat_status` tinyint(4) DEFAULT NULL COMMENT '微信图文状态  1:仍在微信服务器上  0:从微信服务器上被删除了',
  `show_cover_pic` tinyint(4) DEFAULT NULL COMMENT '是否显示封面 0:不显示  1:显示',
  `thumb_ready` tinyint(4) DEFAULT NULL COMMENT '微信图文是否下载完毕: 0代表已经下载完毕 1代表尚未下载完毕',
  `wx_type` tinyint(4) DEFAULT '1' COMMENT '微信资产 1:图文资产',
  `digest` varchar(1024) DEFAULT NULL COMMENT '摘要',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图文资产表';

CREATE TABLE `wechat_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '群组编号',
  `group_id` varchar(100) DEFAULT NULL COMMENT 'H5返回的group_id',
  `group_name` varchar(500) DEFAULT NULL COMMENT '群组名称，好友全部对到好友组',
  `group_nickname` varchar(500) DEFAULT NULL COMMENT '群组的别称',
  `header_image` varchar(500) DEFAULT NULL COMMENT '群组头像缩略图',
  `wx_acct` varchar(500) NOT NULL COMMENT '对应微信号：wechat_register.wx_acct\n',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `count` int(11) DEFAULT NULL COMMENT '群组人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信群组信息';

CREATE TABLE `wechat_register` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_acct` varchar(500) NOT NULL COMMENT '微信账号\n个人号： uin\n服务号/订阅号：weixin_id',
  `name` varchar(500) DEFAULT NULL COMMENT '微信(个人、订阅、服务号)名称',
  `type` int(11) NOT NULL DEFAULT '-1' COMMENT '微信接入类型\n0-服务号\n1-个人号\n2-订阅号\n3-未认证服务号\n4-未认证订阅号',
  `nickname` varchar(500) DEFAULT NULL COMMENT '个人号的昵称',
  `header_image` varchar(1000) DEFAULT NULL COMMENT '个人号的头像缩略图',
  `sex` varchar(10) DEFAULT NULL COMMENT '个人号的性别',
  `province` varchar(50) DEFAULT NULL COMMENT '个人号的省',
  `city` varchar(50) DEFAULT NULL COMMENT '个人号的市',
  `signature` varchar(100) DEFAULT NULL COMMENT '个人号的签名文字',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `app_id` varchar(22) NOT NULL,
  `wechat_qrcode` varchar(1000) DEFAULT NULL,
  `is_auth` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-未认证\n1-已认证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信公众号信息';

CREATE TABLE `wechat_interface_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interface_name` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '接口名称',
  `method_name` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '方法名称',
  `params` longtext CHARACTER SET utf8 COMMENT '参数',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;