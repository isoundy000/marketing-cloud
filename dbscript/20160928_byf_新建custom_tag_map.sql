

DROP TABLE IF EXISTS `custom_tag_map`;

CREATE TABLE `custom_tag_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` varchar(45) NOT NULL COMMENT '标签编号',
  `tag_source` int(11) NOT NULL COMMENT '标签来源：0-细分 1-活动 2-联系人档案  3-文件接入 4-微信二维码',
  `map_id` varchar(45) DEFAULT NULL COMMENT 'tag标识的细分/活动/联系人的ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义标签映射表';
