CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `user_id` varchar(50) NOT NULL COMMENT '用户ID',
  `comp_id` varchar(50) NOT NULL COMMENT '用户公司ID',
  `comp_name` varchar(200) NOT NULL COMMENT '用户公司名称',
  `status` tinyint(4) NOT NULL COMMENT '0 :失效 1:有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
