-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 10.200.11.13    Database: mkt_dev
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.12-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` char(10) DEFAULT NULL,
  `description` char(10) DEFAULT NULL,
  `url` char(10) DEFAULT NULL,
  `content` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audience_columns`
--

DROP TABLE IF EXISTS `audience_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_columns` (
  `id` int(5) unsigned NOT NULL DEFAULT '1',
  `field_name` varchar(45) NOT NULL COMMENT '列名',
  `field_code` varchar(45) NOT NULL COMMENT '英文列名，对应data_app等表的column',
  `field_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '顺序',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人群列表列名';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_columns`
--

LOCK TABLES `audience_columns` WRITE;
/*!40000 ALTER TABLE `audience_columns` DISABLE KEYS */;
INSERT INTO `audience_columns` VALUES (1,'名称','audience_list_name',1,0,'2016-06-12 15:50:42','2016-06-12 08:53:55'),(2,'人数','audience_count',2,0,'2016-06-12 15:50:42','2016-06-12 08:53:55'),(3,'来源','source_name',3,0,'2016-06-12 15:50:42','2016-06-12 08:53:55'),(4,'创建时间','create_time',4,0,'2016-06-12 15:50:42','2016-06-12 07:51:20');
/*!40000 ALTER TABLE `audience_columns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audience_list`
--

DROP TABLE IF EXISTS `audience_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '人群编号',
  `audience_name` varchar(45) DEFAULT NULL COMMENT '人群名称',
  `audience_rows` int(45) DEFAULT '0' COMMENT '人群包含联系人数',
  `source` varchar(512) DEFAULT NULL COMMENT '来源',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '产生时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人群管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_list`
--

LOCK TABLES `audience_list` WRITE;
/*!40000 ALTER TABLE `audience_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `audience_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audience_list_party_map`
--

DROP TABLE IF EXISTS `audience_list_party_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_list_party_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audience_list_id` int(11) NOT NULL COMMENT '人群ID',
  `party_id` int(11) NOT NULL COMMENT '主数据(联系人)ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0-未删除  1-删除 ',
  `oper` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人群管理详细信息，某个人群包含的联系人\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_list_party_map`
--

LOCK TABLES `audience_list_party_map` WRITE;
/*!40000 ALTER TABLE `audience_list_party_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `audience_list_party_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_save_audience`
--

DROP TABLE IF EXISTS `campaign_action_save_audience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_save_audience` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL COMMENT '动作名称',
  `audience_id` int(11) DEFAULT NULL COMMENT 'audience_list表里的id',
  `audience_name` varchar(45) DEFAULT NULL COMMENT 'audience_list表里的audience_name',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:未删除，1：已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作属性表:保存人群';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_save_audience`
--

LOCK TABLES `campaign_action_save_audience` WRITE;
/*!40000 ALTER TABLE `campaign_action_save_audience` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_save_audience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_send_h5`
--

DROP TABLE IF EXISTS `campaign_action_send_h5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_h5` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL COMMENT '动作名称',
  `pub_asset_id` int(11) DEFAULT NULL COMMENT '对应wechat_asset表的asset_id',
  `prv_asset_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset_group表的id,不限群组，则传null',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT '对应img_text_asset表的id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '对应大连那边的公众号pub_id',
  `uin` varchar(512) DEFAULT NULL COMMENT '对应大连那边的个人号uin',
  `ucode` varchar(512) DEFAULT NULL COMMENT '对应大连的群组ucode',
  `material_id` int(11) DEFAULT NULL COMMENT '对应大连那边的material_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作属性表:发送h5图文';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_send_h5`
--

LOCK TABLES `campaign_action_send_h5` WRITE;
/*!40000 ALTER TABLE `campaign_action_send_h5` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_send_h5` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_send_privt`
--

DROP TABLE IF EXISTS `campaign_action_send_privt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_privt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL COMMENT '节点名称',
  `uin` varchar(200) DEFAULT NULL COMMENT '对应大连的个人号uin',
  `asset_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset表的asset_id',
  `ucode` varchar(500) DEFAULT NULL COMMENT '对应大连的群组ucode',
  `group_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset_group表的id,不限群组，则传null',
  `text_info` varchar(2048) DEFAULT NULL COMMENT '要发送的文本信息',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作属性表:发送个人号信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_send_privt`
--

LOCK TABLES `campaign_action_send_privt` WRITE;
/*!40000 ALTER TABLE `campaign_action_send_privt` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_send_privt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_send_pub`
--

DROP TABLE IF EXISTS `campaign_action_send_pub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_pub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `material_id` int(11) DEFAULT NULL COMMENT '对应大连的material_id',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset表的id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '对应大连的公众号pub_id',
  `asset_id` int(11) DEFAULT NULL COMMENT '对应wechat_asset表的asset_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作属性表:发送公众号消息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_send_pub`
--

LOCK TABLES `campaign_action_send_pub` WRITE;
/*!40000 ALTER TABLE `campaign_action_send_pub` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_send_pub` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_set_tag`
--

DROP TABLE IF EXISTS `campaign_action_set_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_set_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `tag_ids` varchar(512) DEFAULT NULL COMMENT '对应custom_tag表的id,多个用逗号分隔',
  `tag_names` varchar(512) DEFAULT NULL COMMENT '自定义标签名，多个用逗号分隔',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作属性表:设置标签 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_set_tag`
--

LOCK TABLES `campaign_action_set_tag` WRITE;
/*!40000 ALTER TABLE `campaign_action_set_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_set_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_wait`
--

DROP TABLE IF EXISTS `campaign_action_wait`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_wait` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '0:相对时间1:指定时间',
  `relative_value` int(11) DEFAULT NULL,
  `relative_type` tinyint(4) DEFAULT NULL COMMENT '0:小时，1：天，2：周，3：月',
  `specific_time` datetime DEFAULT NULL COMMENT '指定时间',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动作属性表:等待';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_wait`
--

LOCK TABLES `campaign_action_wait` WRITE;
/*!40000 ALTER TABLE `campaign_action_wait` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_wait` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_audience_target`
--

DROP TABLE IF EXISTS `campaign_audience_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_audience_target` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `segmentation_id` int(11) DEFAULT NULL,
  `segmentation_name` varchar(200) DEFAULT NULL,
  `allowed_new` tinyint(4) DEFAULT '0' COMMENT '0:允许,1:不允许',
  `refresh_interval` int(11) DEFAULT NULL,
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2:周,3:月',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='目标人群';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_audience_target`
--

LOCK TABLES `campaign_audience_target` WRITE;
/*!40000 ALTER TABLE `campaign_audience_target` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_audience_target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_body`
--

DROP TABLE IF EXISTS `campaign_body`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_id` int(11) DEFAULT NULL,
  `node_type` tinyint(4) DEFAULT '0' COMMENT '0:触发,1:受众,2决策,3行动,4:分支线',
  `item_type` tinyint(4) DEFAULT NULL COMMENT '0,1,2,3...按照原型里出现的顺序',
  `item_id` varchar(200) DEFAULT NULL COMMENT '关联具体节点表的业务id',
  `statistics_url` varchar(1024) DEFAULT NULL COMMENT '报表url',
  `pos_x` varchar(20) DEFAULT NULL COMMENT '组件x坐标',
  `pos_y` varchar(20) DEFAULT NULL COMMENT '组件y坐标',
  `pos_z` varchar(20) DEFAULT NULL,
  `audience_count` int(11) DEFAULT '0' COMMENT '当前节点受众人数',
  `task_id` int(11) DEFAULT NULL COMMENT '关联task_schedule表的id',
  `description` varchar(512) DEFAULT NULL COMMENT '节点描述',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `head_item_unq` (`head_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营销活动body表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_body`
--

LOCK TABLES `campaign_body` WRITE;
/*!40000 ALTER TABLE `campaign_body` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_body` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_columns`
--

DROP TABLE IF EXISTS `campaign_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_columns` (
  `id` int(5) unsigned NOT NULL DEFAULT '1',
  `field_name` varchar(45) NOT NULL COMMENT '列名',
  `field_code` varchar(45) NOT NULL COMMENT '英文列名',
  `field_order` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动列表中文列名';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_columns`
--

LOCK TABLES `campaign_columns` WRITE;
/*!40000 ALTER TABLE `campaign_columns` DISABLE KEYS */;
INSERT INTO `campaign_columns` VALUES (1,'名称','name',1,0,'2016-06-12 16:15:42','2016-06-12 08:53:25'),(2,'状态','status',2,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(3,'创建时间','create_time',3,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(4,'计划开始时间','start_time',4,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(5,'计划结束时间','end_time',5,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(6,'受众人群','segmentation_name',6,0,'2016-06-12 16:15:42','2016-06-12 08:15:42');
/*!40000 ALTER TABLE `campaign_columns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_prop_compare`
--

DROP TABLE IF EXISTS `campaign_decision_prop_compare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_prop_compare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `prop_type` tinyint(4) DEFAULT NULL COMMENT '属性类型,0:文本,1:数字,2:日期',
  `rule` tinyint(4) DEFAULT NULL COMMENT '0：等于,1:包含2:starts_with，3：ends_with4:empty,5:大于,6:小于,7:大于等于,8:小于等于',
  `exclude` tinyint(4) DEFAULT NULL COMMENT '0:不选中"不",1:选中“不”',
  `value` varchar(1024) DEFAULT NULL COMMENT '输入的文本',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人属性比较表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_prop_compare`
--

LOCK TABLES `campaign_decision_prop_compare` WRITE;
/*!40000 ALTER TABLE `campaign_decision_prop_compare` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_prop_compare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_prvt_friends`
--

DROP TABLE IF EXISTS `campaign_decision_prvt_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_prvt_friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `uin` varchar(200) DEFAULT NULL COMMENT '对应大连那边的uin',
  `asset_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset表的asset_id',
  `ucode` varchar(500) DEFAULT NULL COMMENT '对应大连的群组ucode',
  `group_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset_group表的id,不限群组,传null',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='是否是个人号好友';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_prvt_friends`
--

LOCK TABLES `campaign_decision_prvt_friends` WRITE;
/*!40000 ALTER TABLE `campaign_decision_prvt_friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_prvt_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_pub_fans`
--

DROP TABLE IF EXISTS `campaign_decision_pub_fans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_pub_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `pub_id` varchar(500) DEFAULT NULL COMMENT '对应大连的公众号pub_id',
  `asset_id` int(11) DEFAULT NULL COMMENT '公众号名称,对应wechat_asset表的asset_id',
  `subscribe_time` tinyint(4) DEFAULT NULL COMMENT '订阅时间,0:一天，1：一周，2：一月，3：三月，4：六月，5：一年，6：一年以上',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月,4:分钟',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='是否订阅了公众号';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_pub_fans`
--

LOCK TABLES `campaign_decision_pub_fans` WRITE;
/*!40000 ALTER TABLE `campaign_decision_pub_fans` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_pub_fans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_tag`
--

DROP TABLE IF EXISTS `campaign_decision_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `campaign_head_id` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `rule` tinyint(4) DEFAULT '0' COMMENT '0:全部匹配，1：匹配其一，2：匹配二个及以上',
  `tag_ids` varchar(1024) DEFAULT NULL COMMENT '标签id,多个用逗号分隔',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签判断';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_tag`
--

LOCK TABLES `campaign_decision_tag` WRITE;
/*!40000 ALTER TABLE `campaign_decision_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_tag_map`
--

DROP TABLE IF EXISTS `campaign_decision_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_tag_map` (
  `id` int(11) NOT NULL DEFAULT '0',
  `campaign_decision_tag_id` int(11) DEFAULT NULL COMMENT '关联campaign_decision_tag表的id',
  `tag_id` int(11) DEFAULT NULL,
  `tag_name` varchar(200) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='campaign_decision_tag和tag的关联表\n该表停止使用,tag信息放到campaign_decision_tag表的tag_ids列里';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_tag_map`
--

LOCK TABLES `campaign_decision_tag_map` WRITE;
/*!40000 ALTER TABLE `campaign_decision_tag_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_tag_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_wechat_forward`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_forward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_forward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL COMMENT '对应wechat_asset表的asset_id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '对的大连的公众号pub_id',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset表的id',
  `material_id` int(11) DEFAULT NULL COMMENT '对应大连的material_id',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '送达状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月',
  `forward_times` tinyint(4) DEFAULT NULL COMMENT '转发次数,0:不限,1:超一人次，2：超十，3：超一百，4：超五百',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信图文是否转发';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_wechat_forward`
--

LOCK TABLES `campaign_decision_wechat_forward` WRITE;
/*!40000 ALTER TABLE `campaign_decision_wechat_forward` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_wechat_forward` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_wechat_read`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_read` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL COMMENT '对应wechat_asset表的asset_id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '对的大连的公众号pub_id',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset表的id',
  `material_id` int(11) DEFAULT NULL COMMENT '对应大连的material_id',
  `read_time` tinyint(4) DEFAULT NULL COMMENT '查看时长,0:不限,1:超一分钟,2:超三分钟,3:超五分钟,4:超十分钟',
  `read_percent` tinyint(4) DEFAULT NULL COMMENT '查看完成度，0：不限，1：超一半，2:全部阅读',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信图文是否查看';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_wechat_read`
--

LOCK TABLES `campaign_decision_wechat_read` WRITE;
/*!40000 ALTER TABLE `campaign_decision_wechat_read` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_wechat_read` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_wechat_sent`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_sent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_sent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `pub_id` varchar(1024) DEFAULT NULL COMMENT '公众号id',
  `pub_name` varchar(1024) DEFAULT NULL COMMENT '公众号名称',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '送达状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月',
  `wechat_h5_id` int(11) DEFAULT NULL,
  `wechat_h5_name` varchar(1024) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信图文是否发送\n大连接口不支持，暂时停用该表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_wechat_sent`
--

LOCK TABLES `campaign_decision_wechat_sent` WRITE;
/*!40000 ALTER TABLE `campaign_decision_wechat_sent` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_wechat_sent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_head`
--

DROP TABLE IF EXISTS `campaign_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `publish_status` tinyint(4) DEFAULT '0' COMMENT '0:未发布,1:已发布,2:活动中,3:已结束',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营销活动head表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_head`
--

LOCK TABLES `campaign_head` WRITE;
/*!40000 ALTER TABLE `campaign_head` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_head` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_node_item`
--

DROP TABLE IF EXISTS `campaign_node_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_node_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ptype` tinyint(4) NOT NULL COMMENT 'ptype=-1表示该元素为父节点类型',
  `type` tinyint(4) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  `display_index` int(11) DEFAULT NULL COMMENT '页面上显示的顺序',
  `url` varchar(1024) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_ptype_unq` (`ptype`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_node_item`
--

LOCK TABLES `campaign_node_item` WRITE;
/*!40000 ALTER TABLE `campaign_node_item` DISABLE KEYS */;
INSERT INTO `campaign_node_item` VALUES (1,-1,0,'触发','trigger',NULL,0,NULL,NULL,0,NULL,'2016-06-08 06:11:03'),(2,-1,1,'受众','audiences',NULL,1,NULL,NULL,0,NULL,'2016-06-08 06:11:03'),(3,-1,2,'决策','decisions',NULL,2,NULL,NULL,0,NULL,'2016-06-08 06:11:03'),(4,-1,3,'行动','activity',NULL,3,NULL,NULL,0,NULL,'2016-06-08 06:11:03'),(5,0,0,'预约触发','timer-trigger','&#xe63f;',0,NULL,NULL,0,NULL,'2016-07-21 09:20:34'),(6,0,2,'手动触发','manual-trigger','&#xe63e;',2,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(7,1,0,'目标人群','target-group','&#xe639;',0,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(8,2,0,'联系人属性比较','attr-comparison','&#xe66e;',0,NULL,NULL,1,NULL,'2016-07-01 02:07:47'),(9,2,1,'微信图文是否发送','wechat-send','&#xe66d;',1,NULL,NULL,1,NULL,'2016-06-27 07:23:36'),(10,2,2,'微信图文是否查看','wechat-check','&#xe66c;',2,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(11,2,3,'微信图文是否转发','wechat-forwarded','&#xe673;',3,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(12,2,4,'是否订阅公众号','subscriber-public','&#xe66b;',4,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(14,2,6,'标签判断','label-judgment','&#xe671;',6,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(15,3,0,'等待','wait-set','&#xe670;',0,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(16,3,1,'保存当前人群','save-current-group','&#xe669;',1,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(17,3,2,'设置标签','set-tag','&#xe668;',2,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(18,3,3,'添加到其它活动',NULL,NULL,3,NULL,NULL,1,NULL,'2016-06-08 03:31:10'),(19,3,4,'转移到其它活动',NULL,NULL,4,NULL,NULL,1,NULL,'2016-06-08 03:31:10'),(20,3,5,'发送微信图文','send-img','&#xe63a;',5,NULL,NULL,0,NULL,'2016-06-08 06:16:10');
/*!40000 ALTER TABLE `campaign_node_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_switch`
--

DROP TABLE IF EXISTS `campaign_switch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_switch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(255) DEFAULT NULL COMMENT '关联campaign_body表中的item_id',
  `type` tinyint(4) DEFAULT NULL COMMENT '分支类型:0:是分支,1:非分支,2:多条件分支',
  `draw_type` tinyint(4) DEFAULT '0' COMMENT '连线类型,0:curveTriangle',
  `color` varchar(100) DEFAULT NULL,
  `next_item_id` varchar(255) DEFAULT NULL COMMENT '关联campaign_body表中的item_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='营销活动中的连线表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_switch`
--

LOCK TABLES `campaign_switch` WRITE;
/*!40000 ALTER TABLE `campaign_switch` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_switch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_trigger_timer`
--

DROP TABLE IF EXISTS `campaign_trigger_timer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_trigger_timer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，前端生成',
  `name` varchar(100) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_trigger_timer`
--

LOCK TABLES `campaign_trigger_timer` WRITE;
/*!40000 ALTER TABLE `campaign_trigger_timer` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_trigger_timer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_list`
--

DROP TABLE IF EXISTS `contact_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '月收入',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '月均消费',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '婚姻状况',
  `education` varchar(45) DEFAULT NULL COMMENT '教育程度',
  `employment` varchar(45) DEFAULT NULL COMMENT '就业情况',
  `nationality` varchar(45) DEFAULT NULL COMMENT '民族',
  `blood_type` varchar(45) DEFAULT NULL COMMENT '血型',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '国籍',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未导入主数据 1:已经为数据填写了bitmap 2:数据已经进入主数据表 3:删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 01:00:00' COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  `nickname` varchar(128) DEFAULT NULL,
  `head_img_url` varchar(1000) DEFAULT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `language` varchar(100) DEFAULT NULL,
  `unionid` varchar(45) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `commit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `fill_device` varchar(45) DEFAULT NULL COMMENT '填写设备',
  `OS` varchar(45) DEFAULT NULL COMMENT '操作系统',
  `brower` varchar(45) DEFAULT NULL COMMENT '浏览器',
  `IP` varchar(45) DEFAULT NULL COMMENT 'IP',
  `wx_nickname` varchar(45) DEFAULT NULL COMMENT '微信-昵称',
  `wx_gender` varchar(45) DEFAULT NULL COMMENT '微信-性别',
  `wx_openid` varchar(45) DEFAULT NULL COMMENT '微信-openid',
  `wx_header_url` varchar(45) DEFAULT NULL COMMENT '微信-头像',
  `wx_country` varchar(45) DEFAULT NULL COMMENT '微信-国家',
  `wx_city` varchar(45) DEFAULT NULL COMMENT '微信-城市',
  `contact_templ_id` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `download_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_list`
--

LOCK TABLES `contact_list` WRITE;
/*!40000 ALTER TABLE `contact_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_template`
--

DROP TABLE IF EXISTS `contact_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_id` bigint(11) DEFAULT NULL,
  `contact_name` varchar(45) DEFAULT NULL COMMENT '表单名称',
  `contact_title` varchar(50) DEFAULT NULL COMMENT '标题',
  `contact_descript` varchar(100) DEFAULT NULL COMMENT '描述',
  `field_name` varchar(45) DEFAULT NULL COMMENT '列名',
  `field_code` varchar(45) DEFAULT NULL COMMENT '英文列名，对应contact_list表的column',
  `field_type` int(11) DEFAULT NULL,
  `selected` tinyint(4) DEFAULT '1' COMMENT '选中 0-未选中 1-选中',
  `status` tinyint(4) DEFAULT '0' COMMENT '0-未启用\n1-启用\n2-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `qrcode_url` varchar(128) DEFAULT NULL COMMENT '二维码\n包含内容为：新建表单的url',
  `qrcode_pic` varchar(128) DEFAULT NULL COMMENT '二维码图片文件名称',
  `page_views` int(11) DEFAULT '0' COMMENT '浏览次数',
  `key_list` varchar(500) DEFAULT NULL COMMENT '主键名称，例如\n姓名,电话\n',
  `required` int(11) DEFAULT NULL COMMENT '0:非必须 1:必须',
  `ischecked` int(11) DEFAULT NULL COMMENT '0:不校验 1:校验',
  `is_remember_import_key` tinyint(4) DEFAULT '0' COMMENT '0:没有记住默认的导入key 1:已经是默认的导入key',
  `field_index` int(11) DEFAULT NULL COMMENT '联系人模板的显示顺序',
  `is_shown_in_feedback` tinyint(4) DEFAULT '0' COMMENT '0:显示 1:不显示',
  `del_status` tinyint(4) DEFAULT '0' COMMENT '0:正常显示 1:逻辑删除',
  `qrcode_shorturl` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '二维码短链接\\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人表单模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_template`
--

LOCK TABLES `contact_template` WRITE;
/*!40000 ALTER TABLE `contact_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_way`
--

DROP TABLE IF EXISTS `contact_way`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_way` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_way`
--

LOCK TABLES `contact_way` WRITE;
/*!40000 ALTER TABLE `contact_way` DISABLE KEYS */;
INSERT INTO `contact_way` VALUES (1,'手机',0,NULL,'0000-00-00 00:00:00'),(2,'电子邮件',0,NULL,'0000-00-00 00:00:00'),(3,'微信号',0,NULL,'0000-00-00 00:00:00');
/*!40000 ALTER TABLE `contact_way` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_way_map`
--

DROP TABLE IF EXISTS `contact_way_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_way_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_way_id` int(11) NOT NULL COMMENT 'contact_way对应的Id',
  `contact_way_name` varchar(45) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '0为未勾选,1为勾选',
  `time_condition` datetime DEFAULT NULL,
  `time_condition_abbreviation` varchar(1) DEFAULT NULL COMMENT '缩写H:小时 , D:天 , W:周',
  `time_condition_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_way_map`
--

LOCK TABLES `contact_way_map` WRITE;
/*!40000 ALTER TABLE `contact_way_map` DISABLE KEYS */;
INSERT INTO `contact_way_map` VALUES (1,0,'全部',1,NULL,'A',NULL),(2,1,'手机',0,NULL,'A',NULL),(3,2,'点子邮件',0,NULL,'A',NULL);
/*!40000 ALTER TABLE `contact_way_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_tag`
--

DROP TABLE IF EXISTS `custom_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签编号',
  `name` varchar(40) DEFAULT NULL COMMENT '标签名称',
  `cover_audience_count` int(11) DEFAULT NULL COMMENT '覆盖人群数量',
  `oper` varchar(45) DEFAULT NULL COMMENT '操作者',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义标签（细分/活动/联系人）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_tag`
--

LOCK TABLES `custom_tag` WRITE;
/*!40000 ALTER TABLE `custom_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `custom_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_tag_map`
--

DROP TABLE IF EXISTS `custom_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(20) NOT NULL COMMENT '标签编号',
  `type` tinyint(40) DEFAULT NULL COMMENT '类型：0-细分 1-活动 2-联系人档案  3-文件接入 4-客户上传标签',
  `map_id` int(11) DEFAULT NULL COMMENT 'tag标识的细分/活动/联系人的ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义标签映射表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_tag_map`
--

LOCK TABLES `custom_tag_map` WRITE;
/*!40000 ALTER TABLE `custom_tag_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `custom_tag_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_tag_original_data_map`
--

DROP TABLE IF EXISTS `custom_tag_original_data_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag_original_data_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_id` int(11) NOT NULL,
  `original_data_type` int(11) NOT NULL,
  `original_data_id` int(11) NOT NULL COMMENT '上传数据的主键ID',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_tag_original_data_map`
--

LOCK TABLES `custom_tag_original_data_map` WRITE;
/*!40000 ALTER TABLE `custom_tag_original_data_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `custom_tag_original_data_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_arch_point`
--

DROP TABLE IF EXISTS `data_arch_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_arch_point` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `event_id` int(11) DEFAULT NULL COMMENT '事件ID',
  `event_name` varchar(45) DEFAULT NULL COMMENT '事件名称',
  `click_time` datetime DEFAULT NULL COMMENT '点击时间',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `cookie` varchar(128) DEFAULT NULL,
  `batch_id` varchar(45) NOT NULL DEFAULT '' COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `event_id_click_time_unq` (`event_id`,`click_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='埋点统计';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_arch_point`
--

LOCK TABLES `data_arch_point` WRITE;
/*!40000 ALTER TABLE `data_arch_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_arch_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_customer_tags`
--

DROP TABLE IF EXISTS `data_customer_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_customer_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_type` varchar(45) DEFAULT NULL,
  `tag_name` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `identify_no` varchar(19) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '1970-06-30 16:00:00' COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_customer_tags_tag_type_tag_name` (`tag_type`,`tag_name`) COMMENT '客户名称和客户类型唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_customer_tags`
--

LOCK TABLES `data_customer_tags` WRITE;
/*!40000 ALTER TABLE `data_customer_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_customer_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_login`
--

DROP TABLE IF EXISTS `data_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_login` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_type` varchar(100) DEFAULT NULL COMMENT '登录方式',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `logout_time` datetime DEFAULT NULL COMMENT '退出时间',
  `login_ip` varchar(45) DEFAULT NULL COMMENT '登录IP',
  `login_device` varchar(45) DEFAULT NULL COMMENT '登录设备',
  `resolution_ratio` varchar(100) DEFAULT NULL COMMENT '分辨率',
  `login_url` varchar(100) DEFAULT NULL COMMENT '登录页面',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_data_login_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录行为';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_login`
--

LOCK TABLES `data_login` WRITE;
/*!40000 ALTER TABLE `data_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_member`
--

DROP TABLE IF EXISTS `data_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_member` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` varchar(45) DEFAULT NULL COMMENT '会员卡号',
  `member_points` varchar(10) DEFAULT NULL COMMENT '会员积分',
  `member_level` varchar(45) DEFAULT NULL COMMENT '会员等级',
  `regist_time` date DEFAULT NULL COMMENT '开卡时间',
  `card_amt` decimal(22,2) DEFAULT NULL COMMENT '卡内余额',
  `expire` date DEFAULT NULL COMMENT '过期日',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_memberid` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员卡记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_member`
--

LOCK TABLES `data_member` WRITE;
/*!40000 ALTER TABLE `data_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_option_map`
--

DROP TABLE IF EXISTS `data_option_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_option_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL COMMENT '数据表对应的Id',
  `table_name` varchar(45) NOT NULL COMMENT '数据表的名字',
  `option_status` tinyint(1) DEFAULT '0' COMMENT '数据表的状态 : 0为未勾选,1为勾选',
  PRIMARY KEY (`id`),
  UNIQUE KEY `table_name_UNIQUE` (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_option_map`
--

LOCK TABLES `data_option_map` WRITE;
/*!40000 ALTER TABLE `data_option_map` DISABLE KEYS */;
INSERT INTO `data_option_map` VALUES (1,0,'data_party',0),(2,1,'data_population',1),(3,2,'data_customer_tags',1),(4,3,'data_arch_point',1),(5,4,'data_member',1),(6,5,'data_login',1),(7,6,'data_payment',1),(8,7,'data_shopping',1);
/*!40000 ALTER TABLE `data_option_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_party`
--

DROP TABLE IF EXISTS `data_party`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_party` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id,主数据ID,主数据生成时产生的ID',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `birthday` date DEFAULT NULL COMMENT '年龄',
  `citizenship` varchar(45) DEFAULT NULL,
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '月收入',
  `member_level` varchar(10) DEFAULT NULL COMMENT '会员等级',
  `member_points` varchar(11) DEFAULT NULL COMMENT '会员积分',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '月均消费',
  `last_login` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `md_type` int(11) DEFAULT NULL COMMENT '主数据类型\\n0-主数据\\n8-微信',
  `mapping_keyid` varchar(100) DEFAULT NULL COMMENT '主数据ID对应的keyid，如：手机号、身份证号、微信openid等',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(45) DEFAULT NULL,
  `identify_no` varchar(45) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `wxmp_id` varchar(45) DEFAULT NULL,
  `wx_code` varchar(45) DEFAULT NULL,
  `wx_uin` varchar(45) DEFAULT NULL COMMENT '微信个人号',
  `wxperson_id` varchar(45) DEFAULT NULL COMMENT '微信个人号下粉丝唯一标识',
  `IDFA` varchar(45) DEFAULT NULL,
  `IMEI` varchar(45) DEFAULT NULL,
  `unionid` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `flag1` varchar(45) DEFAULT NULL COMMENT '预留字段1',
  `flag2` varchar(45) DEFAULT NULL COMMENT '预留字段2',
  `bitmap` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_key` (`mobile`,`tel`,`email`,`qq`,`identify_no`,`driving_license`,`wxmp_id`,`wx_code`,`wx_uin`,`wxperson_id`,`IDFA`,`IMEI`,`unionid`,`acct_no`,`flag1`,`flag2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_party`
--

LOCK TABLES `data_party` WRITE;
/*!40000 ALTER TABLE `data_party` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_party` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_party_tag_rule_map`
--

DROP TABLE IF EXISTS `data_party_tag_rule_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_party_tag_rule_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_type` tinyint(4) DEFAULT '0' COMMENT '规则类型:0:一一对应,1:数字范围对应',
  `tag_id` int(11) NOT NULL COMMENT 'tag表的id',
  `md_type` int(11) DEFAULT NULL COMMENT '对应MongoDB的data_party表中的md_type\n1-人口属性\n2-客户标签\n3-埋点统计\n4-会员卡记录\n5-登录行为\n6-支付记录\n7-购物记录\n8-微信',
  `field_name` varchar(512) NOT NULL COMMENT 'mongo的dataParty的po类的字段名',
  `field_value` varchar(512) DEFAULT NULL COMMENT 'mongo的dataParty的po类的字段值',
  `min` float DEFAULT NULL COMMENT 'type=1时，会有值，null表示无限小',
  `max` float DEFAULT NULL COMMENT 'type=1时，会有值,null表示无限大',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=699 DEFAULT CHARSET=utf8 COMMENT='给data_party表打标签的规则:data_party表的列和tag相关表的对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_party_tag_rule_map`
--

LOCK TABLES `data_party_tag_rule_map` WRITE;
/*!40000 ALTER TABLE `data_party_tag_rule_map` DISABLE KEYS */;
INSERT INTO `data_party_tag_rule_map` VALUES (1,0,5869,1,'gender','1',NULL,NULL,0,NULL,'2016-08-09 03:17:01'),(2,0,5870,1,'gender','2',NULL,NULL,0,NULL,'2016-08-09 03:17:01'),(13,0,5889,1,'maritalStatus','已婚',NULL,NULL,0,NULL,'2016-08-09 06:22:34'),(14,0,5890,1,'maritalStatus','未婚',NULL,NULL,0,NULL,'2016-08-09 06:22:34'),(15,0,5891,1,'maritalStatus','未知',NULL,NULL,0,NULL,'2016-08-09 06:22:34'),(16,0,5895,1,'bloodType','O',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(17,0,5892,1,'bloodType','A',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(18,0,5893,1,'bloodType','B',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(19,0,5894,1,'bloodType','AB',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(20,0,6068,1,'citizenship','中国',NULL,NULL,0,NULL,'2016-08-09 06:24:12'),(21,0,6068,1,'citizenship','china',NULL,NULL,0,NULL,'2016-08-09 06:24:12'),(31,0,5869,8,'sex','1',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(32,0,5870,8,'sex','2',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(34,1,6631,0,'lastShoppingTime','dataValue != null && dataValue<=1',NULL,NULL,0,NULL,'2016-08-08 03:45:32'),(35,1,6632,0,'lastShoppingTime','dataValue != null && dataValue<=3',NULL,NULL,0,NULL,'2016-08-08 03:45:32'),(36,1,6633,0,'lastShoppingTime','dataValue != null && dataValue<=6',NULL,NULL,0,NULL,'2016-08-12 11:15:14'),(37,1,6627,0,'totalShoppingCount','dataValue != null && dataValue==1',NULL,NULL,0,NULL,'2016-08-08 03:46:37'),(38,1,6628,0,'totalShoppingCount','dataValue != null && dataValue==2',NULL,NULL,0,NULL,'2016-08-08 03:46:37'),(39,1,6629,0,'totalShoppingCount','dataValue != null && dataValue==3',NULL,NULL,0,NULL,'2016-08-08 03:46:37'),(40,1,6630,0,'totalShoppingCount','dataValue != null && dataValue>3',NULL,NULL,0,NULL,'2016-08-08 03:46:37'),(41,1,6634,0,'singleMonthShoppingCount','dataValue != null && dataValue<3',NULL,NULL,0,NULL,'2016-08-08 05:07:28'),(42,1,6635,0,'singleMonthShoppingCount','dataValue != null && dataValue>=3',NULL,NULL,0,NULL,'2016-08-08 05:07:28'),(43,1,6636,0,'totalIncome','dataValue != null && dataValue<=50',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(44,1,6637,0,'totalIncome','dataValue != null && dataValue>50&& dataValue<=100',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(45,1,6638,0,'totalIncome','dataValue != null && dataValue>100&& dataValue<=150',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(46,1,6639,0,'totalIncome','dataValue != null && dataValue>150&& dataValue<=200',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(47,1,6640,0,'totalIncome','dataValue != null && dataValue>200&& dataValue<=300',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(48,1,6641,0,'totalIncome','dataValue != null && dataValue>300',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(50,1,6642,0,'averageIncome','dataValue != null && dataValue<=50',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(51,1,6643,0,'averageIncome','dataValue != null && dataValue>100&& dataValue<=150',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(52,1,6644,0,'averageIncome','dataValue != null && dataValue != null && dataValue>50&& dataValue<=100',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(53,1,6645,0,'averageIncome','dataValue != null && dataValue>150&& dataValue<=200',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(54,1,6646,0,'averageIncome','dataValue != null && dataValue>200&& dataValue<=300',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(55,1,6647,0,'averageIncome','dataValue != null && dataValue>300',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(56,1,6652,0,'isShoppingUser','dataValue == null || dataValue!=true',NULL,NULL,0,NULL,'2016-08-19 10:02:18'),(57,1,6651,0,'isShoppingUser','dataValue != null && dataValue==true',NULL,NULL,0,NULL,'2016-08-08 05:10:23'),(58,1,6626,0,'weimob','dataValue == null || dataValue.contains(\"其它\")',NULL,NULL,0,NULL,'2016-08-29 08:13:32'),(59,1,6625,0,'weimob','dataValue != null && dataValue.contains(\"旺铺\")',NULL,NULL,0,NULL,'2016-08-29 03:13:41'),(60,1,6649,0,'orderStatus','dataValue != null && dataValue.contains(\"交易关闭\")',NULL,NULL,0,NULL,'2016-08-29 03:13:44'),(61,1,6648,0,'orderStatus','dataValue != null && dataValue.contains(\"交易完成\")',NULL,NULL,0,NULL,'2016-08-29 03:13:47'),(62,1,6650,0,'orderStatus','dataValue != null && dataValue.contains(\"待支付\")',NULL,NULL,0,NULL,'2016-08-29 03:13:51'),(63,0,5871,0,'gender','3',NULL,NULL,0,NULL,'2016-08-12 10:30:33'),(65,0,5871,0,'sex','3',NULL,NULL,0,NULL,'2016-08-20 04:55:15'),(66,0,6183,1,'provice','北京',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(67,0,6184,1,'provice','天津',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(68,0,6185,1,'provice','上海',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(69,0,6186,1,'provice','重庆',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(70,0,6187,1,'provice','河北省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(71,0,6188,1,'provice','山西省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(72,0,6189,1,'provice','台湾省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(73,0,6190,1,'provice','辽宁省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(74,0,6191,1,'provice','吉林省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(75,0,6192,1,'provice','黑龙江省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(76,0,6193,1,'provice','江苏省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(77,0,6194,1,'provice','浙江省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(78,0,6195,1,'provice','安徽省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(79,0,6196,1,'provice','福建省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(80,0,6197,1,'provice','江西省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(81,0,6198,1,'provice','山东省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(82,0,6199,1,'provice','河南省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(83,0,6200,1,'provice','湖北省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(84,0,6201,1,'provice','湖南省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(85,0,6202,1,'provice','广东省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(86,0,6203,1,'provice','甘肃省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(87,0,6204,1,'provice','四川省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(88,0,6205,1,'provice','贵州省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(89,0,6206,1,'provice','海南省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(90,0,6207,1,'provice','云南省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(91,0,6208,1,'provice','青海省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(92,0,6209,1,'provice','陕西省',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(93,0,6210,1,'provice','广西壮族自治区',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(94,0,6211,1,'provice','西藏自治区',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(95,0,6212,1,'provice','宁夏回族自治区',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(96,0,6213,1,'provice','新疆维吾尔自治区',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(97,0,6214,1,'provice','内蒙古自治区',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(98,0,6215,1,'provice','澳门特别行政区',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(99,0,6216,1,'provice','香港特别行政区',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(129,0,6217,1,'city','北京市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(130,0,6218,1,'city','天津市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(131,0,6219,1,'city','上海市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(132,0,6220,1,'city','重庆市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(133,0,6221,1,'city','石家庄市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(134,0,6222,1,'city','唐山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(135,0,6223,1,'city','秦皇岛市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(136,0,6224,1,'city','邯郸市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(137,0,6225,1,'city','邢台市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(138,0,6226,1,'city','保定市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(139,0,6227,1,'city','张家口市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(140,0,6228,1,'city','承德市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(141,0,6229,1,'city','沧州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(142,0,6230,1,'city','廊坊市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(143,0,6231,1,'city','衡水市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(144,0,6232,1,'city','太原市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(145,0,6233,1,'city','大同市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(146,0,6234,1,'city','阳泉市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(147,0,6235,1,'city','长治市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(148,0,6236,1,'city','晋城市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(149,0,6237,1,'city','朔州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(150,0,6238,1,'city','晋中市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(151,0,6239,1,'city','运城市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(152,0,6240,1,'city','忻州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(153,0,6241,1,'city','临汾市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(154,0,6242,1,'city','吕梁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(155,0,6243,1,'city','台北市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(156,0,6244,1,'city','高雄市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(157,0,6245,1,'city','基隆市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(158,0,6246,1,'city','台中市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(159,0,6247,1,'city','台南市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(160,0,6248,1,'city','新竹市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(161,0,6249,1,'city','嘉义市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(162,0,6250,1,'city','台北县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(163,0,6251,1,'city','宜兰县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(164,0,6252,1,'city','桃园县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(165,0,6253,1,'city','新竹县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(166,0,6254,1,'city','苗栗县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(167,0,6255,1,'city','台中县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(168,0,6256,1,'city','彰化县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(169,0,6257,1,'city','南投县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(170,0,6258,1,'city','云林县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(171,0,6259,1,'city','嘉义县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(172,0,6260,1,'city','台南县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(173,0,6261,1,'city','高雄县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(174,0,6262,1,'city','屏东县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(175,0,6263,1,'city','澎湖县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(176,0,6264,1,'city','台东县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(177,0,6265,1,'city','花莲县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(178,0,6266,1,'city','沈阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(179,0,6267,1,'city','大连市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(180,0,6268,1,'city','鞍山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(181,0,6269,1,'city','抚顺市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(182,0,6270,1,'city','本溪市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(183,0,6271,1,'city','丹东市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(184,0,6272,1,'city','锦州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(185,0,6273,1,'city','营口市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(186,0,6274,1,'city','阜新市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(187,0,6275,1,'city','辽阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(188,0,6276,1,'city','盘锦市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(189,0,6277,1,'city','铁岭市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(190,0,6278,1,'city','朝阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(191,0,6279,1,'city','葫芦岛市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(192,0,6280,1,'city','长春市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(193,0,6281,1,'city','吉林市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(194,0,6282,1,'city','四平市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(195,0,6283,1,'city','辽源市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(196,0,6284,1,'city','通化市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(197,0,6285,1,'city','白山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(198,0,6286,1,'city','松原市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(199,0,6287,1,'city','白城市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(200,0,6288,1,'city','延边朝鲜族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(201,0,6289,1,'city','哈尔滨市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(202,0,6290,1,'city','齐齐哈尔市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(203,0,6291,1,'city','鹤岗市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(204,0,6292,1,'city','双鸭山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(205,0,6293,1,'city','鸡西市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(206,0,6294,1,'city','大庆市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(207,0,6295,1,'city','伊春市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(208,0,6296,1,'city','牡丹江市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(209,0,6297,1,'city','佳木斯市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(210,0,6298,1,'city','七台河市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(211,0,6299,1,'city','黑河市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(212,0,6300,1,'city','绥化市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(213,0,6301,1,'city','大兴安岭地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(214,0,6302,1,'city','南京市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(215,0,6303,1,'city','无锡市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(216,0,6304,1,'city','徐州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(217,0,6305,1,'city','常州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(218,0,6306,1,'city','苏州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(219,0,6307,1,'city','南通市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(220,0,6308,1,'city','连云港市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(221,0,6309,1,'city','淮安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(222,0,6310,1,'city','盐城市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(223,0,6311,1,'city','扬州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(224,0,6312,1,'city','镇江市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(225,0,6313,1,'city','泰州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(226,0,6314,1,'city','宿迁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(227,0,6315,1,'city','杭州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(228,0,6316,1,'city','宁波市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(229,0,6317,1,'city','温州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(230,0,6318,1,'city','嘉兴市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(231,0,6319,1,'city','湖州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(232,0,6320,1,'city','绍兴市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(233,0,6321,1,'city','金华市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(234,0,6322,1,'city','衢州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(235,0,6323,1,'city','舟山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(236,0,6324,1,'city','台州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(237,0,6325,1,'city','丽水市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(238,0,6326,1,'city','合肥市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(239,0,6327,1,'city','芜湖市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(240,0,6328,1,'city','蚌埠市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(241,0,6329,1,'city','淮南市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(242,0,6330,1,'city','马鞍山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(243,0,6331,1,'city','淮北市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(244,0,6332,1,'city','铜陵市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(245,0,6333,1,'city','安庆市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(246,0,6334,1,'city','黄山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(247,0,6335,1,'city','滁州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(248,0,6336,1,'city','阜阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(249,0,6337,1,'city','宿州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(250,0,6338,1,'city','巢湖市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(251,0,6339,1,'city','六安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(252,0,6340,1,'city','亳州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(253,0,6341,1,'city','池州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(254,0,6342,1,'city','宣城市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(255,0,6343,1,'city','福州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(256,0,6344,1,'city','厦门市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(257,0,6345,1,'city','莆田市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(258,0,6346,1,'city','三明市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(259,0,6347,1,'city','泉州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(260,0,6348,1,'city','漳州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(261,0,6349,1,'city','南平市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(262,0,6350,1,'city','龙岩市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(263,0,6351,1,'city','宁德市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(264,0,6352,1,'city','南昌市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(265,0,6353,1,'city','景德镇市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(266,0,6354,1,'city','萍乡市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(267,0,6355,1,'city','九江市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(268,0,6356,1,'city','新余市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(269,0,6357,1,'city','鹰潭市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(270,0,6358,1,'city','赣州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(271,0,6359,1,'city','吉安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(272,0,6360,1,'city','宜春市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(273,0,6361,1,'city','抚州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(274,0,6362,1,'city','上饶市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(275,0,6363,1,'city','济南市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(276,0,6364,1,'city','青岛市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(277,0,6365,1,'city','淄博市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(278,0,6366,1,'city','枣庄市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(279,0,6367,1,'city','东营市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(280,0,6368,1,'city','烟台市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(281,0,6369,1,'city','潍坊市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(282,0,6370,1,'city','济宁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(283,0,6371,1,'city','泰安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(284,0,6372,1,'city','威海市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(285,0,6373,1,'city','日照市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(286,0,6374,1,'city','莱芜市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(287,0,6375,1,'city','临沂市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(288,0,6376,1,'city','德州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(289,0,6377,1,'city','聊城市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(290,0,6378,1,'city','滨州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(291,0,6379,1,'city','菏泽市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(292,0,6380,1,'city','郑州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(293,0,6381,1,'city','开封市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(294,0,6382,1,'city','洛阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(295,0,6383,1,'city','平顶山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(296,0,6384,1,'city','安阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(297,0,6385,1,'city','鹤壁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(298,0,6386,1,'city','新乡市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(299,0,6387,1,'city','焦作市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(300,0,6388,1,'city','濮阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(301,0,6389,1,'city','许昌市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(302,0,6390,1,'city','漯河市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(303,0,6391,1,'city','三门峡市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(304,0,6392,1,'city','南阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(305,0,6393,1,'city','商丘市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(306,0,6394,1,'city','信阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(307,0,6395,1,'city','周口市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(308,0,6396,1,'city','驻马店市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(309,0,6397,1,'city','济源市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(310,0,6398,1,'city','武汉市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(311,0,6399,1,'city','黄石市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(312,0,6400,1,'city','十堰市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(313,0,6401,1,'city','荆州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(314,0,6402,1,'city','宜昌市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(315,0,6403,1,'city','襄樊市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(316,0,6404,1,'city','鄂州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(317,0,6405,1,'city','荆门市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(318,0,6406,1,'city','孝感市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(319,0,6407,1,'city','黄冈市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(320,0,6408,1,'city','咸宁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(321,0,6409,1,'city','随州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(322,0,6410,1,'city','仙桃市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(323,0,6411,1,'city','天门市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(324,0,6412,1,'city','潜江市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(325,0,6413,1,'city','神农架林区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(326,0,6414,1,'city','恩施土家族苗族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(327,0,6415,1,'city','长沙市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(328,0,6416,1,'city','株洲市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(329,0,6417,1,'city','湘潭市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(330,0,6418,1,'city','衡阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(331,0,6419,1,'city','邵阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(332,0,6420,1,'city','岳阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(333,0,6421,1,'city','常德市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(334,0,6422,1,'city','张家界市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(335,0,6423,1,'city','益阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(336,0,6424,1,'city','郴州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(337,0,6425,1,'city','永州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(338,0,6426,1,'city','怀化市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(339,0,6427,1,'city','娄底市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(340,0,6428,1,'city','湘西土家族苗族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(341,0,6429,1,'city','广州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(342,0,6430,1,'city','深圳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(343,0,6431,1,'city','珠海市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(344,0,6432,1,'city','汕头市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(345,0,6433,1,'city','韶关市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(346,0,6434,1,'city','佛山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(347,0,6435,1,'city','江门市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(348,0,6436,1,'city','湛江市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(349,0,6437,1,'city','茂名市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(350,0,6438,1,'city','肇庆市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(351,0,6439,1,'city','惠州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(352,0,6440,1,'city','梅州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(353,0,6441,1,'city','汕尾市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(354,0,6442,1,'city','河源市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(355,0,6443,1,'city','阳江市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(356,0,6444,1,'city','清远市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(357,0,6445,1,'city','东莞市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(358,0,6446,1,'city','中山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(359,0,6447,1,'city','潮州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(360,0,6448,1,'city','揭阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(361,0,6449,1,'city','云浮市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(362,0,6450,1,'city','兰州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(363,0,6451,1,'city','金昌市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(364,0,6452,1,'city','白银市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(365,0,6453,1,'city','天水市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(366,0,6454,1,'city','嘉峪关市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(367,0,6455,1,'city','武威市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(368,0,6456,1,'city','张掖市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(369,0,6457,1,'city','平凉市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(370,0,6458,1,'city','酒泉市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(371,0,6459,1,'city','庆阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(372,0,6460,1,'city','定西市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(373,0,6461,1,'city','陇南市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(374,0,6462,1,'city','临夏回族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(375,0,6463,1,'city','甘南藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(376,0,6464,1,'city','成都市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(377,0,6465,1,'city','自贡市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(378,0,6466,1,'city','攀枝花市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(379,0,6467,1,'city','泸州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(380,0,6468,1,'city','德阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(381,0,6469,1,'city','绵阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(382,0,6470,1,'city','广元市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(383,0,6471,1,'city','遂宁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(384,0,6472,1,'city','内江市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(385,0,6473,1,'city','乐山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(386,0,6474,1,'city','南充市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(387,0,6475,1,'city','眉山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(388,0,6476,1,'city','宜宾市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(389,0,6477,1,'city','广安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(390,0,6478,1,'city','达州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(391,0,6479,1,'city','雅安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(392,0,6480,1,'city','巴中市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(393,0,6481,1,'city','资阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(394,0,6482,1,'city','阿坝藏族羌族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(395,0,6483,1,'city','甘孜藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(396,0,6484,1,'city','凉山彝族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(397,0,6485,1,'city','济南市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(398,0,6486,1,'city','青岛市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(399,0,6487,1,'city','淄博市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(400,0,6488,1,'city','枣庄市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(401,0,6489,1,'city','东营市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(402,0,6490,1,'city','烟台市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(403,0,6491,1,'city','潍坊市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(404,0,6492,1,'city','济宁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(405,0,6493,1,'city','泰安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(406,0,6494,1,'city','威海市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(407,0,6495,1,'city','日照市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(408,0,6496,1,'city','莱芜市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(409,0,6497,1,'city','临沂市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(410,0,6498,1,'city','德州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(411,0,6499,1,'city','聊城市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(412,0,6500,1,'city','滨州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(413,0,6501,1,'city','菏泽市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(414,0,6502,1,'city','贵阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(415,0,6503,1,'city','六盘水市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(416,0,6504,1,'city','遵义市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(417,0,6505,1,'city','安顺市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(418,0,6506,1,'city','铜仁地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(419,0,6507,1,'city','毕节地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(420,0,6508,1,'city','黔西南布依族苗族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(421,0,6509,1,'city','黔东南苗族侗族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(422,0,6510,1,'city','黔南布依族苗族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(423,0,6511,1,'city','海口市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(424,0,6512,1,'city','三亚市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(425,0,6513,1,'city','五指山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(426,0,6514,1,'city','琼海市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(427,0,6515,1,'city','儋州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(428,0,6516,1,'city','文昌市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(429,0,6517,1,'city','万宁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(430,0,6518,1,'city','东方市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(431,0,6519,1,'city','澄迈县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(432,0,6520,1,'city','定安县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(433,0,6521,1,'city','屯昌县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(434,0,6522,1,'city','临高县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(435,0,6523,1,'city','白沙黎族自治县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(436,0,6524,1,'city','昌江黎族自治县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(437,0,6525,1,'city','乐东黎族自治县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(438,0,6526,1,'city','陵水黎族自治县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(439,0,6527,1,'city','保亭黎族苗族自治县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(440,0,6528,1,'city','琼中黎族苗族自治县',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(441,0,6529,1,'city','昆明市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(442,0,6530,1,'city','曲靖市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(443,0,6531,1,'city','玉溪市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(444,0,6532,1,'city','保山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(445,0,6533,1,'city','昭通市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(446,0,6534,1,'city','丽江市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(447,0,6535,1,'city','思茅市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(448,0,6536,1,'city','临沧市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(449,0,6537,1,'city','文山壮族苗族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(450,0,6538,1,'city','红河哈尼族彝族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(451,0,6539,1,'city','西双版纳傣族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(452,0,6540,1,'city','楚雄彝族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(453,0,6541,1,'city','大理白族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(454,0,6542,1,'city','德宏傣族景颇族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(455,0,6543,1,'city','怒江傈傈族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(456,0,6544,1,'city','迪庆藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(457,0,6545,1,'city','西宁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(458,0,6546,1,'city','海东地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(459,0,6547,1,'city','海北藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(460,0,6548,1,'city','黄南藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(461,0,6549,1,'city','海南藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(462,0,6550,1,'city','果洛藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(463,0,6551,1,'city','玉树藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(464,0,6552,1,'city','海西蒙古族藏族自治州',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(465,0,6553,1,'city','西安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(466,0,6554,1,'city','铜川市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(467,0,6555,1,'city','宝鸡市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(468,0,6556,1,'city','咸阳市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(469,0,6557,1,'city','渭南市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(470,0,6558,1,'city','延安市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(471,0,6559,1,'city','汉中市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(472,0,6560,1,'city','榆林市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(473,0,6561,1,'city','安康市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(474,0,6562,1,'city','商洛市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(475,0,6563,1,'city','南宁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(476,0,6564,1,'city','柳州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(477,0,6565,1,'city','桂林市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(478,0,6566,1,'city','梧州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(479,0,6567,1,'city','北海市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(480,0,6568,1,'city','防城港市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(481,0,6569,1,'city','钦州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(482,0,6570,1,'city','贵港市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(483,0,6571,1,'city','玉林市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(484,0,6572,1,'city','百色市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(485,0,6573,1,'city','贺州市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(486,0,6574,1,'city','河池市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(487,0,6575,1,'city','来宾市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(488,0,6576,1,'city','崇左市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(489,0,6577,1,'city','拉萨市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(490,0,6578,1,'city','那曲地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(491,0,6579,1,'city','昌都地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(492,0,6580,1,'city','山南地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(493,0,6581,1,'city','日喀则地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(494,0,6582,1,'city','阿里地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(495,0,6583,1,'city','林芝地区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(496,0,6584,1,'city','银川市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(497,0,6585,1,'city','石嘴山市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(498,0,6586,1,'city','吴忠市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(499,0,6587,1,'city','固原市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(500,0,6588,1,'city','中卫市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(501,0,6589,1,'city','乌鲁木齐市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(502,0,6590,1,'city','克拉玛依市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(503,0,6591,1,'city','石河子市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(504,0,6592,1,'city','阿拉尔市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(505,0,6593,1,'city','图木舒克市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(506,0,6594,1,'city','五家渠市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(507,0,6595,1,'city','吐鲁番市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(508,0,6596,1,'city','阿克苏市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(509,0,6597,1,'city','喀什市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(510,0,6598,1,'city','哈密市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(511,0,6599,1,'city','和田市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(512,0,6600,1,'city','阿图什市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(513,0,6601,1,'city','库尔勒市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(514,0,6602,1,'city','昌吉市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(515,0,6603,1,'city','阜康市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(516,0,6604,1,'city','米泉市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(517,0,6605,1,'city','博乐市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(518,0,6606,1,'city','伊宁市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(519,0,6607,1,'city','奎屯市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(520,0,6608,1,'city','塔城市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(521,0,6609,1,'city','乌苏市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(522,0,6610,1,'city','阿勒泰市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(523,0,6611,1,'city','呼和浩特市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(524,0,6612,1,'city','包头市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(525,0,6613,1,'city','乌海市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(526,0,6614,1,'city','赤峰市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(527,0,6615,1,'city','通辽市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(528,0,6616,1,'city','鄂尔多斯市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(529,0,6617,1,'city','呼伦贝尔市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(530,0,6618,1,'city','巴彦淖尔市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(531,0,6619,1,'city','乌兰察布市',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(532,0,6620,1,'city','锡林郭勒盟',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(533,0,6621,1,'city','兴安盟',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(534,0,6622,1,'city','阿拉善盟',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(535,0,6623,1,'city','澳门特别行政区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(536,0,6624,1,'city','香港特别行政区',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(640,0,6068,1,'citizenship','中国',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(641,0,6069,1,'citizenship','美国',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(642,0,6070,1,'citizenship','日本',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(643,0,6071,1,'citizenship','英国',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(644,0,6072,1,'citizenship','德国',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(645,0,6073,1,'citizenship','韩国',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(646,0,6074,1,'citizenship','香港',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(647,0,6075,1,'citizenship','意大利',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(648,0,6076,1,'citizenship','加拿大',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(649,0,6077,1,'citizenship','澳大利亚',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(650,0,6078,1,'citizenship','法国',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(651,0,6079,1,'citizenship','菲律宾',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(652,0,6080,1,'citizenship','以色列',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(653,0,6081,1,'citizenship','西班牙',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(654,0,6082,1,'citizenship','台湾',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(655,0,6083,1,'citizenship','比利时',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(656,0,6084,1,'citizenship','新加坡',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(657,0,6085,1,'citizenship','芬兰',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(658,0,6086,1,'citizenship','荷兰',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(659,0,6087,1,'citizenship','波兰',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(660,0,6088,1,'citizenship','瑞士',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(661,0,6089,1,'citizenship','瑞典',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(662,0,6090,1,'citizenship','泰国',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(663,0,6091,1,'citizenship','葡萄牙',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(664,0,6092,1,'citizenship','智利',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(665,0,6093,1,'citizenship','新西兰',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(666,0,6094,1,'citizenship','巴西',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(667,0,6095,1,'citizenship','丹麦',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(668,0,6096,1,'citizenship','奥地利',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(669,0,6097,1,'citizenship','越南',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(670,0,6098,1,'citizenship','斯里兰卡',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(671,0,6099,1,'citizenship','马来西亚',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(672,0,6100,1,'citizenship','土耳其',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(673,0,6101,1,'citizenship','古巴',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(674,0,6102,1,'citizenship','直布罗陀',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(675,0,6103,1,'citizenship','乌克兰',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(676,0,6104,1,'citizenship','阿根廷',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(677,0,6105,1,'citizenship','俄罗斯',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(678,0,6106,1,'citizenship','希腊',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(679,0,6107,1,'citizenship','挪威',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(680,0,6108,1,'citizenship','南非',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(681,0,6109,1,'citizenship','墨西哥',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(682,0,6110,1,'citizenship','爱尔兰',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(683,0,6111,1,'citizenship','印度尼西亚',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(684,0,6112,1,'citizenship','印度',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(685,0,6113,1,'citizenship','乌拉圭',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(686,0,6114,1,'citizenship','阿联酋',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(687,0,6115,1,'citizenship','捷克',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(688,0,6116,1,'citizenship','阿曼',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(689,0,6117,1,'citizenship','塞浦路斯',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(690,0,6118,1,'citizenship','匈牙利',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(691,0,6119,1,'citizenship','罗马尼亚',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(692,0,6120,1,'citizenship','苏格兰',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(693,0,6121,1,'citizenship','保加利亚',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(694,0,6122,1,'citizenship','柬埔寨',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(695,0,6123,1,'citizenship','斯洛伐克',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(696,0,6124,1,'citizenship','拉脱维亚',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(697,0,6125,1,'citizenship','冰岛',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(698,0,6126,1,'citizenship','澳门',NULL,NULL,0,NULL,'2016-08-26 07:00:32');
/*!40000 ALTER TABLE `data_party_tag_rule_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_payment`
--

DROP TABLE IF EXISTS `data_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_payment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pay_channel` varchar(10) DEFAULT NULL COMMENT '支付渠道',
  `pay_acct` varchar(45) DEFAULT NULL COMMENT '支付账号',
  `pay_serial` varchar(45) DEFAULT NULL COMMENT '支付流水',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT '业务流水',
  `order_no` varchar(45) DEFAULT NULL COMMENT '订单编号',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `create_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL COMMENT '结束时间',
  `pay_status` varchar(45) DEFAULT NULL COMMENT '支付状态',
  `counter_acct` varchar(45) DEFAULT NULL COMMENT '对方账号',
  `income_amt` decimal(22,2) DEFAULT NULL COMMENT '收入金额',
  `paid_amt` decimal(22,2) DEFAULT NULL COMMENT '支出金额',
  `acct_amt` decimal(22,2) DEFAULT NULL COMMENT '账号余额',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_payment_pay_serial` (`pay_serial`) COMMENT '流水账号',
  UNIQUE KEY `idx_data_payment_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_payment`
--

LOCK TABLES `data_payment` WRITE;
/*!40000 ALTER TABLE `data_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_personal`
--

DROP TABLE IF EXISTS `data_personal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_personal` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `task_id` int(10) NOT NULL COMMENT '所在的任务Id',
  `open_id` int(15) DEFAULT NULL COMMENT 'openid',
  `personal_name` varchar(20) DEFAULT NULL COMMENT '个人号名称',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='微信个人号数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_personal`
--

LOCK TABLES `data_personal` WRITE;
/*!40000 ALTER TABLE `data_personal` DISABLE KEYS */;
INSERT INTO `data_personal` VALUES (3,0,NULL,NULL,NULL,NULL,NULL),(4,0,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `data_personal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_population`
--

DROP TABLE IF EXISTS `data_population`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_population` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '月收入',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '月均消费',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '婚姻状况',
  `education` varchar(45) DEFAULT NULL COMMENT '教育程度',
  `employment` varchar(45) DEFAULT NULL COMMENT '就业情况',
  `nationality` varchar(45) DEFAULT NULL COMMENT '民族',
  `blood_type` varchar(45) DEFAULT NULL COMMENT '血型',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '国籍',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  `nickname` varchar(128) DEFAULT NULL,
  `head_img_url` varchar(1000) DEFAULT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `language` varchar(100) DEFAULT NULL,
  `unionid` varchar(45) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人口属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_population`
--

LOCK TABLES `data_population` WRITE;
/*!40000 ALTER TABLE `data_population` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_population` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_public`
--

DROP TABLE IF EXISTS `data_public`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_public` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `task_id` int(10) NOT NULL COMMENT '所在的任务Id',
  `open_id` int(15) unsigned DEFAULT NULL COMMENT 'openid',
  `public_name` varchar(20) DEFAULT NULL COMMENT '公众号名称',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `icon_url` varchar(45) DEFAULT NULL COMMENT '头像url',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `area` varchar(20) DEFAULT NULL COMMENT '区域',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='微信公众号数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_public`
--

LOCK TABLES `data_public` WRITE;
/*!40000 ALTER TABLE `data_public` DISABLE KEYS */;
INSERT INTO `data_public` VALUES (3,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `data_public` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_shopping`
--

DROP TABLE IF EXISTS `data_shopping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_shopping` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `channel_type` varchar(45) DEFAULT NULL COMMENT '购物渠道分类',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '购物渠道ID',
  `channel_name` varchar(45) DEFAULT NULL COMMENT '购物渠道名称',
  `pay_type` varchar(45) DEFAULT NULL COMMENT '支付方式',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT '业务流水',
  `order_no` varchar(100) DEFAULT NULL COMMENT '商品订单号',
  `trans_time` datetime DEFAULT NULL COMMENT '消费时间',
  `product_id` varchar(100) DEFAULT NULL COMMENT '商品编号',
  `specification` varchar(45) DEFAULT NULL COMMENT '商品规格',
  `color` varchar(45) DEFAULT NULL COMMENT '颜色',
  `discount_type` varchar(45) DEFAULT NULL COMMENT '折扣类型',
  `discount_amt` decimal(22,2) DEFAULT NULL COMMENT '折扣金额',
  `price` decimal(22,2) DEFAULT NULL COMMENT '单价',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `inventory` int(11) DEFAULT NULL COMMENT '库存量',
  `brand_id` varchar(45) DEFAULT NULL COMMENT '品牌iD',
  `brand_name` varchar(45) DEFAULT NULL COMMENT '品牌名称',
  `class1_id` varchar(45) DEFAULT NULL COMMENT '一级品类ID',
  `class1_name` varchar(45) DEFAULT NULL COMMENT '一级品类名称',
  `class2_id` varchar(45) DEFAULT NULL COMMENT '二级品类ID',
  `class2_name` varchar(45) DEFAULT NULL COMMENT '二级品类名称',
  `class3_id` varchar(45) DEFAULT NULL COMMENT '三级品类ID',
  `class3_name` varchar(45) DEFAULT NULL COMMENT '三级品类名称',
  `class4_id` varchar(45) DEFAULT NULL COMMENT '四级品类ID',
  `class4_name` varchar(45) DEFAULT NULL COMMENT '二级品类名称',
  `sale_assist_id` varchar(45) DEFAULT NULL COMMENT '导购员编号',
  `sale_assistance` varchar(45) DEFAULT NULL COMMENT '导购员',
  `settlement_clerk_id` varchar(45) DEFAULT NULL COMMENT '结算人员编号',
  `settlement_clerk` varchar(45) DEFAULT NULL COMMENT '结算人员',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `order_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `delivery_way` varchar(10) DEFAULT NULL COMMENT '配送方式',
  `logistics_status` varchar(10) DEFAULT NULL COMMENT '物流状态',
  `shipping_fee` decimal(22,2) DEFAULT NULL COMMENT '运费',
  `shipping_way` varchar(10) DEFAULT NULL COMMENT '运送方式',
  `express_company` varchar(100) DEFAULT NULL COMMENT '快递公司',
  `express_order` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `consignee` varchar(50) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` varchar(20) DEFAULT NULL COMMENT '收货人电话',
  `consignee_addr` varchar(100) DEFAULT NULL COMMENT '收货地址',
  `buyer_comment` varchar(200) DEFAULT NULL COMMENT '买家备注',
  `wxmp_id` varchar(128) DEFAULT NULL COMMENT '公众号标识',
  `wx_code` varchar(128) DEFAULT NULL COMMENT 'openid',
  `product_name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_shopping_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_shopping`
--

LOCK TABLES `data_shopping` WRITE;
/*!40000 ALTER TABLE `data_shopping` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_shopping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datareport_columns`
--

DROP TABLE IF EXISTS `datareport_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datareport_columns` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `field_name` varchar(45) NOT NULL COMMENT '列名',
  `field_code` varchar(45) NOT NULL COMMENT '英文列名',
  `field_order` int(11) DEFAULT NULL COMMENT '列的顺序',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:未删除,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='数据质量报告中文列名';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datareport_columns`
--

LOCK TABLES `datareport_columns` WRITE;
/*!40000 ALTER TABLE `datareport_columns` DISABLE KEYS */;
INSERT INTO `datareport_columns` VALUES (1,'开始时间','import_start_time',1,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(2,'完成时间','import_end_time',2,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(3,'数据源','source',3,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(4,'合法记录','legal_rows',4,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(5,'非法记录','illegal_rows',5,0,'2016-06-12 16:15:42','2016-06-12 08:15:53'),(6,'修正记录','modify_rows',6,0,'2016-06-12 16:15:42','2016-06-12 08:15:42');
/*!40000 ALTER TABLE `datareport_columns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `default_contact_template`
--

DROP TABLE IF EXISTS `default_contact_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `default_contact_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_name` varchar(100) DEFAULT NULL,
  `field_code` varchar(100) DEFAULT NULL,
  `is_selected` tinyint(4) DEFAULT NULL COMMENT '0:未选中 1:选中',
  `is_required` tinyint(4) DEFAULT NULL COMMENT '0:非必须 1:必须',
  `is_checked` tinyint(4) DEFAULT NULL COMMENT '0:不需要校验 1:需要校验',
  `default_shown_seq` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `is_primary_key` tinyint(4) DEFAULT NULL COMMENT '0:不是主键 1:是主键',
  `field_type` int(11) DEFAULT '0' COMMENT '0:输入框 1:下拉框 2:日历选择 3:地区联动 4:手机(验证码)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `default_contact_template`
--

LOCK TABLES `default_contact_template` WRITE;
/*!40000 ALTER TABLE `default_contact_template` DISABLE KEYS */;
INSERT INTO `default_contact_template` VALUES (1,'姓名','name',1,1,1,1,0,1,0),(2,'性别','gender',1,1,0,2,0,0,1),(3,'生日','birthday',1,1,0,3,0,0,2),(4,'手机号码','mobile',1,1,1,4,0,1,0),(5,'固话号码','tel',1,1,1,5,0,1,0),(6,'邮箱地址','email',1,1,1,6,0,1,0),(7,'QQ号','qq',1,1,0,7,0,1,0),(8,'血型','blood_type',0,0,0,NULL,0,0,1),(9,'民族','nationality',0,0,0,NULL,0,0,0),(10,'国籍','citizenship',0,0,0,NULL,0,0,0),(11,'地区','city',0,0,0,NULL,0,0,3),(12,'月收入','monthly_income',0,0,0,NULL,0,0,0),(13,'月消费','monthly_consume',0,0,0,NULL,0,0,0),(14,'职业','job',0,0,0,NULL,0,0,0),(15,'教育程度','education',0,0,0,NULL,0,0,1),(16,'就业情况','employment',0,0,0,NULL,0,0,1),(17,'IQ','iq',0,0,1,NULL,0,0,0),(18,'身份证号','identify_no',0,0,1,NULL,0,1,0),(19,'驾驶证号','driving_license',0,0,1,NULL,0,1,0),(21,'婚否','marital_status',0,0,0,NULL,0,0,1);
/*!40000 ALTER TABLE `default_contact_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `illegal_data`
--

DROP TABLE IF EXISTS `illegal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `illegal_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` bigint(20) NOT NULL,
  `type` char(10) DEFAULT NULL COMMENT '数据类型： app,pos\n',
  `origin_data` varchar(10000) DEFAULT NULL COMMENT '原始文本数据',
  `create_time` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `head_type` varchar(1) DEFAULT NULL COMMENT '是否非法数据，0不是，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='非法数据记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `illegal_data`
--

LOCK TABLES `illegal_data` WRITE;
/*!40000 ALTER TABLE `illegal_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `illegal_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `img_text_asset`
--

DROP TABLE IF EXISTS `img_text_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `img_text_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(200) DEFAULT NULL COMMENT '图文名称',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图文资产表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `img_text_asset`
--

LOCK TABLES `img_text_asset` WRITE;
/*!40000 ALTER TABLE `img_text_asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `img_text_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_data_history`
--

DROP TABLE IF EXISTS `import_data_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_data_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '任务名称',
  `import_start_time` timestamp NULL DEFAULT NULL,
  `import_end_time` timestamp NULL DEFAULT NULL,
  `total_rows` int(11) DEFAULT NULL COMMENT '本批次数据接入总条数',
  `legal_rows` int(11) DEFAULT NULL COMMENT '本批次数据接入合法数据条数',
  `success` tinyint(4) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL COMMENT '数据源类型',
  `source_filename` varchar(128) DEFAULT NULL COMMENT '数据源文件名称',
  `download_filename` varchar(128) DEFAULT NULL,
  `email_rows` int(11) DEFAULT NULL COMMENT '非法email条数',
  `mobile_rows` int(11) DEFAULT NULL COMMENT '非法手机号条数',
  `duplicate_rows` int(11) DEFAULT NULL COMMENT '重复条数',
  `illegal_rows` int(11) DEFAULT NULL COMMENT '非法记录总条数',
  `summary` varchar(512) DEFAULT NULL,
  `no_recognize_property` varchar(1024) DEFAULT NULL,
  `file_unique` varchar(50) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:未删除,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `file_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_file_unique` (`file_unique`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据接入质量报告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_data_history`
--

LOCK TABLES `import_data_history` WRITE;
/*!40000 ALTER TABLE `import_data_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_data_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_data_modify_log`
--

DROP TABLE IF EXISTS `import_data_modify_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_data_modify_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `import_data_id` bigint(20) NOT NULL COMMENT 'import_data_history. id',
  `handle_time` timestamp NOT NULL DEFAULT '1970-06-30 16:00:00',
  `total_rows` int(11) DEFAULT NULL,
  `modify_rows` int(11) DEFAULT NULL,
  `illegal_rows` int(11) DEFAULT NULL,
  `modify_filename` varchar(128) DEFAULT NULL COMMENT '上传修正文件名称',
  `success` tinyint(4) DEFAULT NULL,
  `modify_download_filename` varchar(128) DEFAULT NULL COMMENT '上传修正文件名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_data_modify_log`
--

LOCK TABLES `import_data_modify_log` WRITE;
/*!40000 ALTER TABLE `import_data_modify_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_data_modify_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_template`
--

DROP TABLE IF EXISTS `import_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_template` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `templ_type` int(5) NOT NULL COMMENT '模板类型\n0-主数据\n1-人口属性\n2-客户标签\n3-埋点统计\n4-会员卡记录\n5-登录行为\n6-支付记录\n7-购物记录',
  `templ_name` varchar(45) NOT NULL DEFAULT '' COMMENT '模板名称',
  `field_name` varchar(45) NOT NULL COMMENT '列名',
  `field_code` varchar(45) NOT NULL COMMENT '英文列名，对应data_app等表的column',
  `selected` tinyint(1) NOT NULL DEFAULT '1' COMMENT '选中 0-未选中 1-选中',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_templ_name_field_name` (`field_name`,`templ_name`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8 COMMENT='导入模板表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_template`
--

LOCK TABLES `import_template` WRITE;
/*!40000 ALTER TABLE `import_template` DISABLE KEYS */;
INSERT INTO `import_template` VALUES (1,0,'主数据','手机号','mobile',1,0,NULL,'2016-07-22 09:54:00'),(2,0,'主数据','姓名','name',1,0,NULL,'2016-07-22 09:54:00'),(3,0,'主数据','性别','gender',1,0,NULL,'2016-07-22 09:54:00'),(4,0,'主数据','出生年月日','birthday',1,0,NULL,'2016-07-18 09:12:11'),(5,0,'主数据','省','provice',1,0,NULL,'2016-07-18 09:30:10'),(6,0,'主数据','市','city',1,0,NULL,'2016-07-22 09:54:00'),(7,0,'主数据','职业','job',1,0,NULL,'2016-07-22 09:54:00'),(8,0,'主数据','月收入','monthly_income',1,0,NULL,'2016-07-22 09:54:00'),(9,0,'主数据','会员等级','member_level',1,0,NULL,'2016-07-18 09:41:36'),(10,0,'主数据','会员积分','member_points',1,0,NULL,'2016-07-22 09:54:00'),(11,0,'主数据','数据来源','source',1,0,NULL,'2016-07-22 09:54:00'),(12,0,'主数据','月均消费','monthly_consume',1,0,NULL,'2016-07-22 09:54:00'),(13,0,'主数据','最后登录时间','last_login',0,0,NULL,'2016-07-22 07:06:06'),(14,0,'主数据','删除标记','status',0,0,NULL,'2016-07-22 07:06:06'),(15,0,'主数据','产生时间','create_time',0,0,NULL,'2016-07-22 09:54:00'),(16,0,'主数据','删除时间','update_time',0,0,NULL,'2016-07-19 08:27:38'),(17,1,'人口属性','手机号','mobile',0,0,NULL,'2016-08-10 07:12:24'),(18,1,'人口属性','姓名','name',0,0,NULL,'2016-08-10 07:12:24'),(19,1,'人口属性','性别','gender',0,0,NULL,'2016-08-10 07:12:24'),(20,1,'人口属性','生日','birthday',0,0,NULL,'2016-08-10 07:12:24'),(21,1,'人口属性','省','provice',1,0,NULL,'2016-07-08 11:48:20'),(22,1,'人口属性','市','city',1,0,NULL,'2016-07-08 11:48:20'),(23,1,'人口属性','职业','job',0,0,NULL,'2016-08-10 07:12:24'),(24,1,'人口属性','月收入','monthly_income',0,0,NULL,'2016-08-10 07:12:24'),(25,1,'人口属性','月消费','monthly_consume',0,0,NULL,'2016-08-10 07:12:24'),(26,1,'人口属性','婚否','marital_status',0,0,NULL,'2016-08-10 07:12:24'),(27,1,'人口属性','教育程度','education',0,0,NULL,'2016-08-10 07:12:24'),(28,1,'人口属性','就业情况','employment',0,0,NULL,'2016-08-10 07:12:24'),(29,1,'人口属性','民族','nationality',0,0,NULL,'2016-08-10 07:12:24'),(30,1,'人口属性','血型','blood_type',0,0,NULL,'2016-08-10 07:12:24'),(31,1,'人口属性','国籍','citizenship',1,0,NULL,'2016-07-19 08:28:28'),(32,1,'人口属性','IQ','iq',0,0,NULL,'2016-08-10 07:12:24'),(33,1,'人口属性','身份证号','identify_no',0,0,NULL,'2016-08-10 07:12:24'),(34,1,'人口属性','驾驶证号','driving_license',0,0,NULL,'2016-08-10 07:12:25'),(35,1,'人口属性','邮箱','email',0,0,NULL,'2016-08-10 07:12:25'),(36,1,'人口属性','固话号码','tel',0,0,NULL,'2016-08-10 07:12:25'),(37,1,'人口属性','QQ','qq',0,0,NULL,'2016-08-10 07:12:25'),(38,1,'人口属性','私有账号类型','acct_type',0,0,NULL,'2016-08-10 07:12:25'),(39,1,'人口属性','私有账号','acct_no',0,0,NULL,'2016-08-10 07:12:25'),(40,1,'人口属性','IDFA','idfa',0,0,NULL,'2016-08-05 08:34:09'),(41,1,'人口属性','IMEI','imei',0,0,NULL,'2016-08-05 08:34:18'),(42,1,'人口属性','unionid','udid',0,0,NULL,'2016-08-05 08:34:36'),(43,1,'人口属性','MAC','phone_mac',0,0,NULL,'2016-08-05 08:34:39'),(44,1,'人口属性','删除标记','status',0,0,NULL,'2016-06-23 06:18:27'),(45,1,'人口属性','创建时间','create_time',0,0,NULL,'2016-06-23 06:18:27'),(46,1,'人口属性','删除时间','update_time',0,0,NULL,'2016-06-23 06:18:27'),(47,1,'人口属性','数据来源','source',0,0,NULL,'2016-06-23 06:18:27'),(48,2,'客户标签','标签类型','tag_type',1,0,NULL,'2016-06-13 09:47:50'),(49,2,'客户标签','标签名称','tag_name',1,0,NULL,'2016-07-01 11:21:42'),(50,2,'客户标签','日期','birthday',0,0,NULL,'2016-08-08 08:39:53'),(51,2,'客户标签','身份证号','identify_no',0,0,NULL,'2016-07-07 11:22:35'),(52,2,'客户标签','驾驶证号','driving_license',1,0,NULL,'2016-07-07 11:31:13'),(53,2,'客户标签','邮箱','email',1,0,NULL,'2016-08-05 08:44:48'),(54,2,'客户标签','手机号','mobile',1,0,NULL,'2016-07-08 10:56:30'),(55,2,'客户标签','私有账号类型','acct_type',0,0,NULL,'2016-07-07 11:22:35'),(56,2,'客户标签','固话号码','tel',0,0,NULL,'2016-08-05 08:56:33'),(57,2,'客户标签','QQ','qq',0,0,NULL,'2016-06-30 06:42:40'),(58,2,'客户标签','私有账号','acct_no',0,0,NULL,'2016-06-28 07:09:34'),(59,2,'客户标签','IDFA','idfa',0,0,NULL,'2016-08-05 08:56:53'),(60,2,'客户标签','IMEI','imei',0,0,NULL,'2016-08-05 08:56:59'),(61,2,'客户标签','unionid','udid',0,0,NULL,'2016-08-05 08:57:09'),(62,2,'客户标签','MAC','phone_mac',0,0,NULL,'2016-08-05 08:57:13'),(63,2,'客户标签','删除标记','status',0,0,NULL,'2016-06-28 07:09:34'),(64,2,'客户标签','产生时间','create_time',0,0,NULL,'2016-06-28 07:09:34'),(65,2,'客户标签','删除时间','update_time',0,0,NULL,'2016-06-28 07:09:34'),(66,2,'客户标签','数据来源','source',0,0,NULL,'2016-06-28 07:09:34'),(67,3,'埋点统计','事件ID','event_id',1,0,NULL,'2016-06-24 11:50:41'),(68,3,'埋点统计','事件名称','event_name',1,0,NULL,'2016-06-24 11:50:41'),(69,3,'埋点统计','点击时间','click_time',1,0,NULL,'2016-06-24 11:50:41'),(70,3,'埋点统计','身份证号','identify_no',1,0,NULL,'2016-06-13 09:47:51'),(71,3,'埋点统计','驾驶证号','driving_license',1,0,NULL,'2016-06-13 09:47:51'),(72,3,'埋点统计','cookie','cookie',1,0,NULL,'2016-06-13 09:47:51'),(73,3,'埋点统计','邮箱','email',1,0,NULL,'2016-08-05 09:50:01'),(74,3,'埋点统计','手机号','mobile',1,0,NULL,'2016-06-13 09:47:51'),(75,3,'埋点统计','私有账号类型','acct_type',0,0,NULL,'2016-06-23 02:27:02'),(76,3,'埋点统计','固话号码','tel',0,0,NULL,'2016-08-05 09:50:34'),(77,3,'埋点统计','QQ','qq',0,0,NULL,'2016-06-23 02:27:02'),(78,3,'埋点统计','私有账号','acct_no',0,0,NULL,'2016-07-08 09:55:51'),(79,3,'埋点统计','iphone手机识别码','idfa',0,0,NULL,'2016-07-08 09:55:51'),(80,3,'埋点统计','手机识别码','imei',0,0,NULL,'2016-07-08 09:55:51'),(81,3,'埋点统计','unionid','udid',0,0,NULL,'2016-08-05 09:50:56'),(82,3,'埋点统计','手机网卡MAC','phone_mac',0,0,NULL,'2016-06-23 02:27:02'),(83,3,'埋点统计','删除标记','status',0,0,NULL,'2016-06-23 02:27:02'),(84,3,'埋点统计','创建时间','create_time',0,0,NULL,'2016-08-05 09:51:46'),(85,3,'埋点统计','删除时间','update_time',0,0,NULL,'2016-07-08 09:55:51'),(86,3,'埋点统计','数据来源','source',1,0,NULL,'2016-06-13 09:47:52'),(87,4,'会员卡记录','会员卡号','member_id',1,0,NULL,'2016-06-13 09:47:52'),(88,4,'会员卡记录','会员积分','member_points',0,0,NULL,'2016-06-27 09:51:00'),(89,4,'会员卡记录','会员等级','member_level',1,0,NULL,'2016-07-07 11:32:47'),(90,4,'会员卡记录','开卡时间','regist_time',1,0,NULL,'2016-06-13 09:47:52'),(91,4,'会员卡记录','卡内余额','card_amt',1,0,NULL,'2016-06-13 09:47:52'),(92,4,'会员卡记录','过期日','expire',1,0,NULL,'2016-06-13 09:47:52'),(93,4,'会员卡记录','身份证号','identify_no',1,0,NULL,'2016-06-13 09:47:52'),(94,4,'会员卡记录','驾驶证号','driving_license',1,0,NULL,'2016-06-13 09:47:52'),(95,4,'会员卡记录','cookie','cookie',1,0,NULL,'2016-06-13 09:47:52'),(96,4,'会员卡记录','电子邮件','email',1,0,NULL,'2016-06-13 09:47:52'),(97,4,'会员卡记录','手机号','mobile',1,0,NULL,'2016-06-13 09:47:52'),(98,4,'会员卡记录','私有账号类型','acct_type',1,0,NULL,'2016-06-13 09:47:52'),(99,4,'会员卡记录','固话号码','tel',1,0,NULL,'2016-08-05 09:53:37'),(100,4,'会员卡记录','QQ','qq',1,0,NULL,'2016-06-14 05:50:36'),(101,4,'会员卡记录','私有账号','acct_no',1,0,NULL,'2016-06-13 09:47:52'),(102,4,'会员卡记录','iphone手机识别码','idfa',1,0,NULL,'2016-06-14 05:50:36'),(103,4,'会员卡记录','手机识别码','imei',1,0,NULL,'2016-06-14 05:50:35'),(104,4,'会员卡记录','unionid','udid',1,0,NULL,'2016-08-05 09:53:57'),(105,4,'会员卡记录','手机网卡MAC','phone_mac',1,0,NULL,'2016-06-13 09:47:53'),(106,4,'会员卡记录','删除标记','status',1,0,NULL,'2016-06-13 09:47:53'),(107,4,'会员卡记录','产生时间','create_time',0,0,NULL,'2016-06-27 09:51:00'),(108,4,'会员卡记录','删除时间','update_time',1,0,NULL,'2016-06-13 09:47:53'),(109,4,'会员卡记录','数据来源','source',0,0,NULL,'2016-06-27 09:51:00'),(110,5,'登录行为','登录方式','login_type',1,0,NULL,'2016-06-13 09:47:53'),(111,5,'登录行为','登录时间','login_time',1,0,NULL,'2016-06-13 09:47:53'),(112,5,'登录行为','退出时间','logout_time',1,0,NULL,'2016-06-13 09:47:53'),(113,5,'登录行为','登录IP','login_ip',1,0,NULL,'2016-06-13 09:47:53'),(114,5,'登录行为','登录设备','login_device',1,0,NULL,'2016-06-13 09:47:53'),(115,5,'登录行为','分辨率','resolution_ratio',1,0,NULL,'2016-06-13 09:47:53'),(116,5,'登录行为','登录页面','login_url',1,0,NULL,'2016-06-13 09:47:53'),(117,5,'登录行为','身份证号','identify_no',1,0,NULL,'2016-06-13 09:47:53'),(118,5,'登录行为','驾驶证号','driving_license',1,0,NULL,'2016-06-13 09:47:53'),(119,5,'登录行为','cookie','cookie',1,0,NULL,'2016-06-13 09:47:53'),(120,5,'登录行为','电子邮件','email',1,0,NULL,'2016-06-13 09:47:53'),(121,5,'登录行为','手机号','mobile',1,0,NULL,'2016-06-13 09:47:53'),(122,5,'登录行为','私有账号类型','acct_type',1,0,NULL,'2016-06-13 09:47:53'),(123,5,'登录行为','固话号码','tel',1,0,NULL,'2016-08-05 09:54:51'),(124,5,'登录行为','QQ','qq',1,0,NULL,'2016-06-14 05:50:35'),(125,5,'登录行为','私有账号','acct_no',1,0,NULL,'2016-06-13 09:47:53'),(126,5,'登录行为','iphone手机识别码','idfa',1,0,NULL,'2016-06-14 05:50:35'),(127,5,'登录行为','手机识别码','imei',1,0,NULL,'2016-06-14 05:50:35'),(128,5,'登录行为','unionid','udid',1,0,NULL,'2016-08-05 09:55:05'),(129,5,'登录行为','手机网卡MAC','phone_mac',1,0,NULL,'2016-06-13 09:47:53'),(130,5,'登录行为','删除标记','status',1,0,NULL,'2016-06-13 09:47:53'),(131,5,'登录行为','产生时间','create_time',1,0,NULL,'2016-06-13 09:47:53'),(132,5,'登录行为','删除时间','update_time',1,0,NULL,'2016-06-13 09:47:53'),(133,5,'登录行为','数据来源','source',1,0,NULL,'2016-06-13 09:47:54'),(134,6,'支付记录','支付渠道','pay_channel',1,0,NULL,'2016-06-13 09:47:54'),(135,6,'支付记录','支付宝账号','pay_acct',1,0,NULL,'2016-07-21 10:11:32'),(136,6,'支付记录','账务流水号','pay_serial',0,0,NULL,'2016-07-21 11:09:57'),(137,6,'支付记录','业务流水号','trans_serial',0,0,NULL,'2016-07-21 10:14:24'),(138,6,'支付记录','商户订单号','order_no',0,0,NULL,'2016-07-21 10:14:38'),(139,6,'支付记录','商品名称','product_name',0,0,NULL,'2016-06-28 07:22:29'),(140,6,'支付记录','结束时间','complete_time',1,0,NULL,'2016-07-07 11:31:02'),(141,6,'支付记录','支付状态','pay_status',1,0,NULL,'2016-06-13 09:47:54'),(142,6,'支付记录','对方账号','counter_acct',0,0,NULL,'2016-06-28 07:22:29'),(143,6,'支付记录','收入金额','income_amt',0,0,NULL,'2016-06-28 07:22:29'),(144,6,'支付记录','支出金额','paid_amt',0,0,NULL,'2016-06-28 07:22:29'),(145,6,'支付记录','账户余额','acct_amt',0,0,NULL,'2016-07-21 11:10:25'),(146,6,'支付记录','备注','comments',1,0,NULL,'2016-06-13 09:47:54'),(147,6,'支付记录','身份证号','identify_no',0,0,NULL,'2016-07-07 09:27:52'),(148,6,'支付记录','驾驶证号','driving_license',0,0,NULL,'2016-07-07 09:27:52'),(149,6,'支付记录','cookie','cookie',0,0,NULL,'2016-06-28 07:22:29'),(150,6,'支付记录','邮箱','email',1,0,NULL,'2016-07-21 10:15:35'),(151,6,'支付记录','手机号','mobile',1,0,NULL,'2016-06-13 09:47:54'),(152,6,'支付记录','私有账号体系','acct_type',1,0,NULL,'2016-07-21 10:16:06'),(153,6,'支付记录','固话号码','tel',0,0,NULL,'2016-07-21 10:16:12'),(154,6,'支付记录','QQ','qq',0,0,NULL,'2016-06-28 07:22:29'),(155,6,'支付记录','私有账号','acct_no',0,0,NULL,'2016-06-28 07:22:29'),(156,6,'支付记录','IDFA','idfa',0,0,NULL,'2016-07-21 10:16:29'),(157,6,'支付记录','IMEI','imei',0,0,NULL,'2016-07-21 10:16:34'),(158,6,'支付记录','UDID','udid',0,0,NULL,'2016-06-28 07:22:29'),(159,6,'支付记录','MAC','phone_mac',0,0,NULL,'2016-07-21 10:16:54'),(160,6,'支付记录','删除标记','status',0,0,NULL,'2016-06-28 07:22:29'),(161,6,'支付记录','发生时间','create_time',0,0,NULL,'2016-07-21 10:17:14'),(162,6,'支付记录','删除时间','update_time',0,0,NULL,'2016-06-28 07:22:29'),(163,6,'支付记录','数据来源','source',0,0,NULL,'2016-06-28 07:22:29'),(164,7,'购物记录','渠道分类','channel_type',1,0,NULL,'2016-07-21 09:29:50'),(165,7,'购物记录','购物渠道ID','channel_id',1,0,NULL,'2016-06-13 09:47:55'),(166,7,'购物记录','购物渠道','channel_name',1,0,NULL,'2016-07-21 09:29:58'),(167,7,'购物记录','支付方式','pay_type',1,0,NULL,'2016-06-13 09:47:55'),(168,7,'购物记录','业务流水号','trans_serial',0,0,NULL,'2016-07-21 09:15:16'),(169,7,'购物记录','商户订单号','order_no',1,0,NULL,'2016-07-21 09:15:12'),(170,7,'购物记录','消费时间','trans_time',1,0,NULL,'2016-06-13 09:47:55'),(171,7,'购物记录','商品ID','product_id',1,0,NULL,'2016-07-21 09:30:43'),(172,7,'购物记录','规格','specification',1,0,NULL,'2016-07-21 09:30:46'),(173,7,'购物记录','颜色','color',1,0,NULL,'2016-06-13 09:47:55'),(174,7,'购物记录','折扣类型','discount_type',1,0,NULL,'2016-06-13 09:47:55'),(175,7,'购物记录','折扣金额','discount_amt',1,0,NULL,'2016-06-13 09:47:55'),(176,7,'购物记录','单价','price',1,0,NULL,'2016-06-13 09:47:55'),(177,7,'购物记录','数量','amount',1,0,NULL,'2016-06-13 09:47:55'),(178,7,'购物记录','库存量','inventory',1,0,NULL,'2016-06-13 09:47:55'),(179,7,'购物记录','品牌ID','brand_id',1,0,NULL,'2016-07-21 09:31:26'),(180,7,'购物记录','品牌','brand_name',1,0,NULL,'2016-07-21 09:41:53'),(181,7,'购物记录','一级品类名称ID','class1_id',1,0,NULL,'2016-07-21 09:31:32'),(182,7,'购物记录','一级品类名称','class1_name',1,0,NULL,'2016-06-13 09:47:55'),(183,7,'购物记录','二级品类名称ID','class2_id',1,0,NULL,'2016-07-21 09:31:39'),(184,7,'购物记录','二级品类名称','class2_name',1,0,NULL,'2016-06-13 09:47:56'),(185,7,'购物记录','三级品类名称ID','class3_id',1,0,NULL,'2016-07-21 09:31:50'),(186,7,'购物记录','三级品类名称','class3_name',1,0,NULL,'2016-06-13 09:47:56'),(187,7,'购物记录','四级品类名称ID','class4_id',1,0,NULL,'2016-07-21 09:31:57'),(188,7,'购物记录','四级品类名称','class4_name',1,0,NULL,'2016-06-13 09:47:56'),(189,7,'购物记录','导购人员ID','sale_assist_id',1,0,NULL,'2016-07-21 09:32:41'),(190,7,'购物记录','导购人员','sale_assistance',1,0,NULL,'2016-07-21 09:32:28'),(191,7,'购物记录','结算人员ID','settlement_clerk_id',1,0,NULL,'2016-07-21 09:32:54'),(192,7,'购物记录','结算人员','settlement_clerk',1,0,NULL,'2016-06-13 09:47:56'),(193,7,'购物记录','身份证号','identify_no',0,0,NULL,'2016-07-01 11:51:42'),(194,7,'购物记录','驾驶证号','driving_license',0,0,NULL,'2016-07-01 11:51:42'),(195,7,'购物记录','cookie','cookie',0,0,NULL,'2016-07-01 11:51:42'),(196,7,'购物记录','邮箱','email',0,0,NULL,'2016-07-21 09:33:54'),(197,7,'购物记录','手机号','mobile',1,0,NULL,'2016-06-13 09:47:56'),(198,7,'购物记录','私有账号体系','acct_type',0,0,NULL,'2016-07-21 09:34:12'),(199,7,'购物记录','固话号码','tel',0,0,NULL,'2016-07-21 09:34:05'),(200,7,'购物记录','QQ','qq',0,0,NULL,'2016-07-01 11:51:42'),(201,7,'购物记录','私有账号','acct_no',0,0,NULL,'2016-07-01 11:51:42'),(202,7,'购物记录','IDFA','idfa',0,0,NULL,'2016-07-21 09:43:41'),(203,7,'购物记录','IMEI','imei',0,0,NULL,'2016-07-21 09:43:36'),(204,7,'购物记录','UDID','udid',0,0,NULL,'2016-07-01 11:51:42'),(205,7,'购物记录','MAC','phone_mac',0,0,NULL,'2016-07-21 09:43:31'),(206,7,'购物记录','删除标记','status',0,0,NULL,'2016-07-01 11:51:42'),(207,7,'购物记录','产生时间','create_time',0,0,NULL,'2016-07-01 11:51:42'),(208,7,'购物记录','删除时间','update_time',0,0,NULL,'2016-07-01 11:51:42'),(209,7,'购物记录','数据来源','source',0,0,NULL,'2016-07-01 11:51:42'),(210,7,'购物记录','订单状态','order_status',0,0,NULL,'2016-07-19 02:50:22'),(211,7,'购物记录','配送方式','delivery_way',0,0,NULL,'2016-07-19 02:50:22'),(212,7,'购物记录','物流状态','logistics_status',0,0,NULL,'2016-07-19 02:50:22'),(213,7,'购物记录','运费','shipping_fee',0,0,NULL,'2016-07-19 02:50:22'),(214,7,'购物记录','运送方式','shipping_way',0,0,NULL,'2016-07-19 02:50:22'),(215,7,'购物记录','快递公司','express_company',0,0,NULL,'2016-07-19 02:50:22'),(216,7,'购物记录','快递单号','express_order',0,0,NULL,'2016-07-19 02:50:22'),(217,7,'购物记录','收货人','consignee',0,0,NULL,'2016-07-19 02:50:22'),(218,7,'购物记录','收货人电话','consignee_tel',0,0,NULL,'2016-07-19 02:50:22'),(219,7,'购物记录','收货地址','consignee_addr',0,0,NULL,'2016-07-19 02:50:22'),(220,7,'购物记录','买家备注','buyer_comment',0,0,NULL,'2016-07-19 02:50:22'),(221,7,'购物记录','公众号标识','wxmp_id',0,0,NULL,'2016-08-04 11:14:55'),(222,7,'购物记录','openid','wx_code',0,0,NULL,'2016-08-04 11:15:02'),(223,6,'支付记录','公众号标识','wxmp_id',0,0,NULL,'2016-08-04 11:12:59'),(224,6,'支付记录','openid','wx_code',0,0,NULL,'2016-08-04 11:13:02'),(225,7,'购物记录','商品名称','product_name',1,0,NULL,'2016-07-21 09:53:01'),(227,1,'人口属性','公众号标识','wxmp_id',1,0,NULL,'2016-08-05 09:57:54'),(228,2,'客户标签','公众号标识','wxmp_id',1,0,NULL,'2016-08-05 09:57:44'),(229,3,'埋点统计','公众号标识','wxmp_id',1,0,NULL,'2016-08-05 09:58:44'),(230,4,'会员卡记录','公众号标识','wxmp_id',1,0,NULL,'2016-08-05 09:59:06'),(231,5,'登录行为','公众号标识','wxmp_id',1,0,NULL,'2016-08-05 09:59:39'),(232,1,'人口属性','openid','wx_code',1,0,NULL,'2016-08-05 10:00:45'),(233,2,'客户标签','openid','wx_code',1,0,NULL,'2016-08-05 10:01:39'),(234,3,'埋点统计','openid','wx_code',1,0,NULL,'2016-08-05 10:01:57'),(235,4,'会员卡记录','openid','wx_code',1,0,NULL,'2016-08-05 10:02:14'),(236,5,'登录行为','openid','wx_code',1,0,NULL,'2016-08-05 10:02:33'),(237,1,'人口属性','昵称','nickname',1,0,NULL,'2016-08-10 06:54:44'),(238,1,'人口属性','头像','head_img_url',1,0,NULL,'2016-08-10 06:55:18'),(239,1,'人口属性','关注时间','subscribe_time',1,0,NULL,'2016-08-10 06:55:56'),(240,1,'人口属性','语言','language',1,0,NULL,'2016-08-10 06:56:35'),(241,1,'人口属性','微信unionid','unionid',1,0,NULL,'2016-08-10 06:57:14'),(242,1,'人口属性','评论','remark',1,0,NULL,'2016-08-10 06:57:32');
/*!40000 ALTER TABLE `import_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyid_map_block`
--

DROP TABLE IF EXISTS `keyid_map_block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyid_map_block` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field` varchar(20) NOT NULL,
  `field_name` varchar(60) NOT NULL,
  `seq` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `field` (`field`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='id mapping主键映射区域';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyid_map_block`
--

LOCK TABLES `keyid_map_block` WRITE;
/*!40000 ALTER TABLE `keyid_map_block` DISABLE KEYS */;
INSERT INTO `keyid_map_block` VALUES (1,'mobile','手机号',1),(2,'tel','固定电话',2),(3,'email','邮箱',3),(4,'qq','qq号',4),(5,'identify_no','身份证号',5),(6,'driving_license','驾驶证号',6),(7,'wxmp_id','微信公众号',7),(8,'wx_code','openid',8),(9,'wx_uin','微信个人号',9),(10,'wxperson_id','微信个人号下粉丝唯一标识',10),(11,'IDFA','IDFA',11),(12,'IMEI','IMEI',12),(13,'unionid','unionid',13),(14,'acct_no','私有账号',14),(15,'flag1','预留字段1',15),(16,'flag2','预留字段2',16),(17,'flag3','预留字段3',17);
/*!40000 ALTER TABLE `keyid_map_block` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_log`
--

DROP TABLE IF EXISTS `operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL,
  `operate_desc` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `operate_time` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_log`
--

LOCK TABLES `operation_log` WRITE;
/*!40000 ALTER TABLE `operation_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_arch_point`
--

DROP TABLE IF EXISTS `original_data_arch_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_arch_point` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `event_id` int(11) DEFAULT NULL COMMENT '事件ID',
  `event_name` varchar(45) DEFAULT NULL COMMENT '事件名称',
  `click_time` datetime DEFAULT NULL COMMENT '点击时间',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `cookie` varchar(128) DEFAULT NULL,
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_arch_point`
--

LOCK TABLES `original_data_arch_point` WRITE;
/*!40000 ALTER TABLE `original_data_arch_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_arch_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_customer_tags`
--

DROP TABLE IF EXISTS `original_data_customer_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_customer_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_type` varchar(1) DEFAULT NULL COMMENT '0 - 文本类型 , 1 - 日期类型',
  `tag_name` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `identify_no` varchar(19) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_customer_tags`
--

LOCK TABLES `original_data_customer_tags` WRITE;
/*!40000 ALTER TABLE `original_data_customer_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_customer_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_login`
--

DROP TABLE IF EXISTS `original_data_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_login` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_type` varchar(100) DEFAULT NULL COMMENT '登录方式',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `logout_time` datetime DEFAULT NULL COMMENT '退出时间',
  `login_ip` varchar(45) DEFAULT NULL COMMENT '登录IP',
  `login_device` varchar(45) DEFAULT NULL COMMENT '登录设备',
  `resolution_ratio` varchar(100) DEFAULT NULL COMMENT '分辨率',
  `login_url` varchar(100) DEFAULT NULL COMMENT '登录页面',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_login`
--

LOCK TABLES `original_data_login` WRITE;
/*!40000 ALTER TABLE `original_data_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_member`
--

DROP TABLE IF EXISTS `original_data_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_member` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` varchar(45) DEFAULT NULL COMMENT '会员卡号',
  `member_points` varchar(10) DEFAULT NULL COMMENT '会员积分',
  `member_level` varchar(45) DEFAULT NULL COMMENT '会员等级',
  `regist_time` date DEFAULT NULL COMMENT '开卡时间',
  `card_amt` decimal(22,2) DEFAULT NULL COMMENT '卡内余额',
  `expire` date DEFAULT NULL COMMENT '过期日',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_member`
--

LOCK TABLES `original_data_member` WRITE;
/*!40000 ALTER TABLE `original_data_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_payment`
--

DROP TABLE IF EXISTS `original_data_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_payment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pay_channel` varchar(10) DEFAULT NULL COMMENT '支付渠道',
  `pay_acct` varchar(45) DEFAULT NULL COMMENT '支付账号',
  `pay_serial` varchar(45) DEFAULT NULL COMMENT '支付流水',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT '业务流水',
  `order_no` varchar(45) DEFAULT NULL COMMENT '订单编号',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `create_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL COMMENT '结束时间',
  `pay_status` varchar(45) DEFAULT NULL COMMENT '支付状态',
  `counter_acct` varchar(45) DEFAULT NULL COMMENT '对方账号',
  `income_amt` decimal(22,2) DEFAULT NULL COMMENT '收入金额',
  `paid_amt` decimal(22,2) DEFAULT NULL COMMENT '支出金额',
  `acct_amt` decimal(22,2) DEFAULT NULL COMMENT '账号余额',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `wxmp_id` varchar(45) DEFAULT NULL COMMENT '微信公众号标识',
  `wx_code` varchar(45) DEFAULT NULL COMMENT '微信openid',
  `bitmap` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_payment`
--

LOCK TABLES `original_data_payment` WRITE;
/*!40000 ALTER TABLE `original_data_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_population`
--

DROP TABLE IF EXISTS `original_data_population`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_population` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '月收入',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '月均消费',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '婚姻状况',
  `education` varchar(45) DEFAULT NULL COMMENT '教育程度',
  `employment` varchar(45) DEFAULT NULL COMMENT '就业情况',
  `nationality` varchar(45) DEFAULT NULL COMMENT '民族',
  `blood_type` varchar(45) DEFAULT NULL COMMENT '血型',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '国籍',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_population`
--

LOCK TABLES `original_data_population` WRITE;
/*!40000 ALTER TABLE `original_data_population` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_population` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_shopping`
--

DROP TABLE IF EXISTS `original_data_shopping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_shopping` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `channel_type` varchar(45) DEFAULT NULL COMMENT '购物渠道分类',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '购物渠道ID',
  `channel_name` varchar(45) DEFAULT NULL COMMENT '购物渠道名称',
  `pay_type` varchar(45) DEFAULT NULL COMMENT '支付方式',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT '业务流水',
  `order_no` varchar(100) DEFAULT NULL COMMENT '商品订单号',
  `trans_time` datetime DEFAULT NULL COMMENT '消费时间',
  `product_id` varchar(100) DEFAULT NULL COMMENT '商品编号',
  `specification` varchar(45) DEFAULT NULL COMMENT '商品规格',
  `color` varchar(45) DEFAULT NULL COMMENT '颜色',
  `discount_type` varchar(45) DEFAULT NULL COMMENT '折扣类型',
  `discount_amt` decimal(22,2) DEFAULT NULL COMMENT '折扣金额',
  `price` decimal(22,2) DEFAULT NULL COMMENT '单价',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `inventory` int(11) DEFAULT NULL COMMENT '库存量',
  `brand_id` varchar(45) DEFAULT NULL COMMENT '品牌iD',
  `brand_name` varchar(45) DEFAULT NULL COMMENT '品牌名称',
  `class1_id` varchar(45) DEFAULT NULL COMMENT '一级品类ID',
  `class1_name` varchar(45) DEFAULT NULL COMMENT '一级品类名称',
  `class2_id` varchar(45) DEFAULT NULL COMMENT '二级品类ID',
  `class2_name` varchar(45) DEFAULT NULL COMMENT '二级品类名称',
  `class3_id` varchar(45) DEFAULT NULL COMMENT '三级品类ID',
  `class3_name` varchar(45) DEFAULT NULL COMMENT '三级品类名称',
  `class4_id` varchar(45) DEFAULT NULL COMMENT '四级品类ID',
  `class4_name` varchar(45) DEFAULT NULL COMMENT '二级品类名称',
  `sale_assist_id` varchar(45) DEFAULT NULL COMMENT '导购员编号',
  `sale_assistance` varchar(45) DEFAULT NULL COMMENT '导购员',
  `settlement_clerk_id` varchar(45) DEFAULT NULL COMMENT '结算人员编号',
  `settlement_clerk` varchar(45) DEFAULT NULL COMMENT '结算人员',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `order_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `delivery_way` varchar(10) DEFAULT NULL COMMENT '配送方式',
  `logistics_status` varchar(10) DEFAULT NULL COMMENT '物流状态',
  `shipping_fee` decimal(22,2) DEFAULT NULL COMMENT '运费',
  `shipping_way` varchar(10) DEFAULT NULL COMMENT '运送方式',
  `express_company` varchar(100) DEFAULT NULL COMMENT '快递公司',
  `express_order` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `consignee` varchar(50) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` varchar(20) DEFAULT NULL COMMENT '收货人电话',
  `consignee_addr` varchar(200) DEFAULT NULL COMMENT '收货地址',
  `buyer_comment` varchar(200) DEFAULT NULL COMMENT '买家备注',
  `wxmp_id` varchar(128) DEFAULT NULL COMMENT '公众号标识',
  `wx_code` varchar(128) DEFAULT NULL COMMENT 'openid',
  `product_name` varchar(200) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_shopping`
--

LOCK TABLES `original_data_shopping` WRITE;
/*!40000 ALTER TABLE `original_data_shopping` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_shopping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party_behavior`
--

DROP TABLE IF EXISTS `party_behavior`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_behavior` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_id` varchar(20) NOT NULL COMMENT '联系人ID',
  `behavior_type` varchar(45) DEFAULT NULL COMMENT '行为类型\n0-微信、1-web、2-营销活动',
  `behavior_name` varchar(45) DEFAULT NULL,
  `wechat_type` varchar(45) DEFAULT NULL,
  `wechat_name` varchar(45) DEFAULT NULL,
  `message` varchar(45) DEFAULT NULL,
  `createtime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party_behavior`
--

LOCK TABLES `party_behavior` WRITE;
/*!40000 ALTER TABLE `party_behavior` DISABLE KEYS */;
INSERT INTO `party_behavior` VALUES (1,'1','0','查看 ','0','公众号1','新产品满意度调查','2016-06-07 11:58:11'),(2,'193','1','查看 ','0','公众号2','3~24个月宝宝营养搭配微信群讲座','2016-06-07 10:32:13');
/*!40000 ALTER TABLE `party_behavior` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party_radar`
--

DROP TABLE IF EXISTS `party_radar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_radar` (
  `contact_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '联系人ID',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '联系人姓名',
  `recent_buy_time` datetime DEFAULT NULL COMMENT '	最近购买时间',
  `buy_rate` decimal(5,2) DEFAULT NULL COMMENT '消费频率',
  `goods_types` int(11) DEFAULT NULL COMMENT '购买商品品类数',
  `average_trans_amt` decimal(22,2) DEFAULT NULL COMMENT '平均交易金额',
  `top_trans_amt` decimal(22,2) DEFAULT NULL COMMENT '单次最高交易额',
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party_radar`
--

LOCK TABLES `party_radar` WRITE;
/*!40000 ALTER TABLE `party_radar` DISABLE KEYS */;
INSERT INTO `party_radar` VALUES ('1','张燕','2016-04-03 14:32:22',5.00,1,1002.56,458.21);
/*!40000 ALTER TABLE `party_radar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_id` varbinary(20) DEFAULT NULL,
  `resource_action` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL COMMENT '资源描述',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名',
  `branch_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `crated_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'管理员',1,0,'2016-05-17 07:51:01','2016-05-27 07:51:07');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_resource_map`
--

DROP TABLE IF EXISTS `role_resource_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_resource_map` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色-资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_resource_map`
--

LOCK TABLES `role_resource_map` WRITE;
/*!40000 ALTER TABLE `role_resource_map` DISABLE KEYS */;
INSERT INTO `role_resource_map` VALUES (1,1,1,0,'2016-05-04 15:55:52','2016-05-27 07:55:46'),(2,1,2,0,'2016-05-12 15:59:33','2016-05-20 07:59:37');
/*!40000 ALTER TABLE `role_resource_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segment`
--

DROP TABLE IF EXISTS `segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `segmentation_head_id` int(11) DEFAULT NULL,
  `data_id` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`segmentation_head_id`,`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='1.保存最近3小时的结果';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segment`
--

LOCK TABLES `segment` WRITE;
/*!40000 ALTER TABLE `segment` DISABLE KEYS */;
/*!40000 ALTER TABLE `segment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segment_distribution`
--

DROP TABLE IF EXISTS `segment_distribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segment_distribution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sagmentation_id` int(11) DEFAULT NULL,
  `audience_count` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='细分的覆盖人数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segment_distribution`
--

LOCK TABLES `segment_distribution` WRITE;
/*!40000 ALTER TABLE `segment_distribution` DISABLE KEYS */;
/*!40000 ALTER TABLE `segment_distribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segmentation_body`
--

DROP TABLE IF EXISTS `segmentation_body`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segmentation_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_id` int(11) DEFAULT NULL COMMENT '管理segmentation_head表的id',
  `tag_group_id` int(11) DEFAULT NULL COMMENT '标签所属分组的id',
  `tag_id` int(11) DEFAULT NULL COMMENT '标签id',
  `exclude` tinyint(4) DEFAULT '0' COMMENT '是否排除,0:不排除,1:排除',
  `group_index` int(11) DEFAULT NULL COMMENT '分组序号,0开始递增',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:未删除,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `group_seq` int(11) DEFAULT NULL COMMENT '组内顺序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='细分body表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segmentation_body`
--

LOCK TABLES `segmentation_body` WRITE;
/*!40000 ALTER TABLE `segmentation_body` DISABLE KEYS */;
/*!40000 ALTER TABLE `segmentation_body` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segmentation_head`
--

DROP TABLE IF EXISTS `segmentation_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segmentation_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '细分编号',
  `name` varchar(40) DEFAULT NULL COMMENT '细分名称',
  `publish_status` tinyint(4) DEFAULT '0' COMMENT '0:未生效,1:已生效',
  `oper` varchar(45) DEFAULT NULL COMMENT '操作者',
  `tag_ids` varchar(200) DEFAULT NULL COMMENT '关联cutom_tag表的id\n细分所关联的标签id，多个用逗号分隔',
  `refer_campaign_count` int(11) NOT NULL DEFAULT '0' COMMENT '被活动使用次数',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未删除,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='受众细分';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segmentation_head`
--

LOCK TABLES `segmentation_head` WRITE;
/*!40000 ALTER TABLE `segmentation_head` DISABLE KEYS */;
/*!40000 ALTER TABLE `segmentation_head` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签编号',
  `name` varchar(40) DEFAULT NULL COMMENT '标签名称',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tag_group_id` varchar(500) DEFAULT NULL COMMENT '标签所属组的id(所有父节点的id)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6653 DEFAULT CHARSET=utf8 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (5869,'男',0,'2016-08-08 11:40:47','2016-08-30 09:44:37','14069'),(5870,'女',0,'2016-08-08 11:40:47','2016-09-07 07:53:44','14069'),(5871,'新未知',0,'2016-08-08 11:40:47','2016-08-29 07:09:21','55'),(5872,'00后',0,'2016-08-08 11:40:47','2016-09-07 07:53:53','14069'),(5873,'90后',0,'2016-08-08 11:40:47','2016-08-30 09:47:57','14073'),(5874,'80后',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5875,'70后',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5876,'69前',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5877,'水瓶座',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5878,'双鱼座',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5879,'白羊座',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5880,'金牛座',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5881,'双子座',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5882,'巨蟹座',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5883,'狮子座',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5884,'处女座',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5885,'天秤座',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5886,'天蝎座',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5887,'射手座',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5888,'摩羯座',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5889,'已婚',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5890,'未婚',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5891,'未知',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5892,'A',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5893,'B',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5894,'AB',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5895,'O',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5896,'未知',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5897,'销售',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5898,'客服',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5899,'市场',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5900,'管理',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5901,'渠道',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5902,'行政',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5903,'商务',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5904,'运营',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5905,'数据分析',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5906,'售前',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5907,'售后',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5908,'产品经理',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5909,'品牌经理',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5910,'咨询',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5911,'策划',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5912,'文案',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5913,'公关',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5914,'媒介',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5915,'企划',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5916,'市场调研',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5917,'业务拓展',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5918,'广告',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5919,'会展',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5920,'客户经理',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5921,'创意',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5922,'设计',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5923,'美术',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5924,'策划',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5925,'财务',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5926,'审计',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5927,'税务',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5928,'人力资源',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5929,'会计',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5930,'出纳',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5931,'培训',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5932,'猎头',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5933,'后勤',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5934,'文秘',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5935,'前台',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5936,'项目管理',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5937,'IT',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5938,'证劵',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5939,'投资',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5940,'通信',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5941,'房地产',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5942,'保险',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5943,'汽车',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5944,'电子',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5945,'电器',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5946,'服装',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5947,'纺织',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5948,'能源',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5949,'矿产',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5950,'物流',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5951,'仓储',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5952,'医药',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5953,'化工',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5954,'安全管理',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5955,'化验',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5956,'认证',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5957,'供应商',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5958,'采购',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5959,'编辑',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5960,'互联网',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5961,'电商',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5962,'软件',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5963,'开发',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5964,'系统集成',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5965,'移动互联网',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5966,'UE设计',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5967,'UI设计',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5968,'移动互联网',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5969,'游戏策划',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5970,'游戏数值',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5971,'游戏开发',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5972,'游戏设计',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5973,'游戏测试',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5974,'硬件',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5975,'信息标准化',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5976,'网络安全',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5977,'电信',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5978,'房产',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5979,'建筑',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5980,'物业',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5981,'土木',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5982,'装修',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5983,'市政工程',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5984,'道路',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5985,'桥梁',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5986,'隧道工程',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5987,'水利',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5988,'港口',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5989,'排水',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5990,'管通',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5991,'空调',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5992,'园林景观设计',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5993,'城市规划设计',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5994,'银行',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5995,'进出口',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5996,'风险控制',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5997,'理财',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5998,'融资',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5999,'信托',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6000,'担保',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6001,'拍卖',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6002,'典当',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6003,'珠宝',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6004,'收藏',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6005,'贸易',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6006,'买手',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6007,'报关',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6008,'司机',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6009,'航空',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6010,'公交',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6011,'船舶',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6012,'列车',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6013,'快递',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6014,'搬家',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6015,'家政',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6016,'工业制造',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6017,'半导体',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6018,'传导器',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6019,'照明',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6020,'激光',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6021,'电器',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6022,'二手车',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6023,'4S店',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6024,'汽车维修',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6025,'汽车保养',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6026,'机械工程',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6027,'生物',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6028,'制药',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6029,'医疗器械',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6030,'传媒',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6031,'印刷',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6032,'影视',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6033,'媒体',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6034,'出版',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6035,'导演',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6036,'演员',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6037,'编剧',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6038,'记者',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6039,'作家',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6040,'网红',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6041,'配音',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6042,'法律',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6043,'教育',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6044,'翻译',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6045,'商超',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6046,'酒店',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6047,'娱乐',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6048,'旅游',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6049,'烹饪',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6050,'保健',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6051,'美容',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6052,'美发',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6053,'健身',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6054,'医疗',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6055,'农业',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6056,'科研',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6057,'环保',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6058,'公务员',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6059,'志愿者',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6060,'义工',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6061,'慈善',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6062,'体育',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6063,'外包服务',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6064,'快速消费品',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6065,'奢侈品',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6066,'重工业',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6067,'其他',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6068,'中国',0,'2016-08-08 11:40:53','2016-08-30 09:49:30','14274'),(6069,'美国',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6070,'日本',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6071,'英国',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6072,'德国',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6073,'韩国',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6074,'香港',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6075,'意大利',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6076,'加拿大',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6077,'澳大利亚',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6078,'法国',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6079,'菲律宾',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6080,'以色列',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6081,'西班牙',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6082,'台湾',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6083,'比利时',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6084,'新加坡',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6085,'芬兰',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6086,'荷兰',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6087,'波兰',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6088,'瑞士',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6089,'瑞典',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6090,'泰国',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6091,'葡萄牙',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6092,'智利',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6093,'新西兰',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6094,'巴西',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6095,'丹麦',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6096,'奥地利',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6097,'越南',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6098,'斯里兰卡',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6099,'马来西亚',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6100,'土耳其',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6101,'古巴',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6102,'直布罗陀',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6103,'乌克兰',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6104,'阿根廷',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6105,'俄罗斯',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6106,'希腊',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6107,'挪威',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6108,'南非',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6109,'墨西哥',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6110,'爱尔兰',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6111,'印度尼西亚',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6112,'印度',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6113,'乌拉圭',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6114,'阿联酋',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6115,'捷克',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6116,'阿曼',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6117,'塞浦路斯',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6118,'匈牙利',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6119,'罗马尼亚',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6120,'苏格兰',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6121,'保加利亚',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6122,'柬埔寨',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6123,'斯洛伐克',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6124,'拉脱维亚',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6125,'冰岛',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6126,'澳门',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6127,'汉族',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6128,'蒙古族',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6129,'回族',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6130,'藏族',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6131,'维吾尔族',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6132,'苗族',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6133,'彝族',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6134,'壮族',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6135,'布依族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6136,'朝鲜族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6137,'满族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6138,'侗族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6139,'瑶族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6140,'白族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6141,'土家族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6142,'哈尼族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6143,'哈萨克族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6144,'傣族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6145,'黎族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6146,'傈僳族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6147,'佤族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6148,'畲族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6149,'高山族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6150,'拉祜族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6151,'水族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6152,'东乡族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6153,'纳西族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6154,'景颇族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6155,'柯尔克孜族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6156,'土族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6157,'达斡尔族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6158,'仫佬族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6159,'羌族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6160,'布朗族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6161,'撒拉族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6162,'毛难族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6163,'仡佬族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6164,'锡伯族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6165,'阿昌族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6166,'普米族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6167,'塔吉克族',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6168,'怒族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6169,'乌孜别克族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6170,'俄罗斯族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6171,'鄂温克族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6172,'崩龙族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6173,'保安族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6174,'裕固族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6175,'京族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6176,'塔塔尔族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6177,'独龙族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6178,'鄂伦春族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6179,'赫哲族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6180,'门巴族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6181,'珞巴族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6182,'基诺族',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6183,'北京',0,'2016-08-08 11:40:58','2016-08-23 09:34:12',NULL),(6184,'天津',0,'2016-08-08 11:40:58','2016-08-23 09:34:13',NULL),(6185,'上海',0,'2016-08-08 11:40:58','2016-08-23 09:34:14',NULL),(6186,'重庆',0,'2016-08-08 11:40:58','2016-08-23 09:34:19',NULL),(6187,'河北省',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6188,'山西省',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6189,'台湾省',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6190,'辽宁省',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6191,'吉林省',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6192,'黑龙江省',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6193,'江苏省',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6194,'浙江省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6195,'安徽省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6196,'福建省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6197,'江西省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6198,'山东省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6199,'河南省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6200,'湖北省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6201,'湖南省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6202,'广东省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6203,'甘肃省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6204,'四川省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6205,'贵州省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6206,'海南省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6207,'云南省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6208,'青海省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6209,'陕西省',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6210,'广西壮族自治区',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6211,'西藏自治区',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6212,'宁夏回族自治区',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6213,'新疆维吾尔自治区',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6214,'内蒙古自治区',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6215,'澳门特别行政区',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6216,'香港特别行政区',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6217,'北京市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6218,'天津市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6219,'上海市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6220,'重庆市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6221,'石家庄市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6222,'唐山市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6223,'秦皇岛市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6224,'邯郸市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6225,'邢台市',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6226,'保定市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6227,'张家口市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6228,'承德市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6229,'沧州市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6230,'廊坊市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6231,'衡水市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6232,'太原市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6233,'大同市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6234,'阳泉市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6235,'长治市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6236,'晋城市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6237,'朔州市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6238,'晋中市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6239,'运城市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6240,'忻州市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6241,'临汾市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6242,'吕梁市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6243,'台北市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6244,'高雄市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6245,'基隆市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6246,'台中市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6247,'台南市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6248,'新竹市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6249,'嘉义市',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6250,'台北县',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6251,'宜兰县',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6252,'桃园县',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6253,'新竹县',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6254,'苗栗县',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6255,'台中县',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6256,'彰化县',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6257,'南投县',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6258,'云林县',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6259,'嘉义县',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6260,'台南县',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6261,'高雄县',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6262,'屏东县',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6263,'澎湖县',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6264,'台东县',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6265,'花莲县',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6266,'沈阳市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6267,'大连市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6268,'鞍山市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6269,'抚顺市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6270,'本溪市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6271,'丹东市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6272,'锦州市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6273,'营口市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6274,'阜新市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6275,'辽阳市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6276,'盘锦市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6277,'铁岭市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6278,'朝阳市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6279,'葫芦岛市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6280,'长春市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6281,'吉林市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6282,'四平市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6283,'辽源市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6284,'通化市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6285,'白山市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6286,'松原市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6287,'白城市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6288,'延边朝鲜族自治州',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6289,'哈尔滨市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6290,'齐齐哈尔市',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6291,'鹤岗市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6292,'双鸭山市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6293,'鸡西市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6294,'大庆市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6295,'伊春市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6296,'牡丹江市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6297,'佳木斯市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6298,'七台河市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6299,'黑河市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6300,'绥化市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6301,'大兴安岭地区',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6302,'南京市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6303,'无锡市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6304,'徐州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6305,'常州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6306,'苏州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6307,'南通市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6308,'连云港市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6309,'淮安市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6310,'盐城市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6311,'扬州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6312,'镇江市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6313,'泰州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6314,'宿迁市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6315,'杭州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6316,'宁波市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6317,'温州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6318,'嘉兴市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6319,'湖州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6320,'绍兴市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6321,'金华市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6322,'衢州市',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6323,'舟山市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6324,'台州市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6325,'丽水市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6326,'合肥市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6327,'芜湖市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6328,'蚌埠市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6329,'淮南市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6330,'马鞍山市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6331,'淮北市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6332,'铜陵市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6333,'安庆市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6334,'黄山市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6335,'滁州市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6336,'阜阳市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6337,'宿州市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6338,'巢湖市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6339,'六安市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6340,'亳州市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6341,'池州市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6342,'宣城市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6343,'福州市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6344,'厦门市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6345,'莆田市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6346,'三明市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6347,'泉州市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6348,'漳州市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6349,'南平市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6350,'龙岩市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6351,'宁德市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6352,'南昌市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6353,'景德镇市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6354,'萍乡市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6355,'九江市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6356,'新余市',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6357,'鹰潭市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6358,'赣州市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6359,'吉安市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6360,'宜春市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6361,'抚州市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6362,'上饶市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6363,'济南市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6364,'青岛市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6365,'淄博市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6366,'枣庄市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6367,'东营市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6368,'烟台市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6369,'潍坊市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6370,'济宁市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6371,'泰安市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6372,'威海市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6373,'日照市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6374,'莱芜市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6375,'临沂市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6376,'德州市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6377,'聊城市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6378,'滨州市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6379,'菏泽市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6380,'郑州市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6381,'开封市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6382,'洛阳市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6383,'平顶山市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6384,'安阳市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6385,'鹤壁市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6386,'新乡市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6387,'焦作市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6388,'濮阳市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6389,'许昌市',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6390,'漯河市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6391,'三门峡市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6392,'南阳市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6393,'商丘市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6394,'信阳市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6395,'周口市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6396,'驻马店市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6397,'济源市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6398,'武汉市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6399,'黄石市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6400,'十堰市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6401,'荆州市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6402,'宜昌市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6403,'襄樊市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6404,'鄂州市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6405,'荆门市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6406,'孝感市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6407,'黄冈市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6408,'咸宁市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6409,'随州市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6410,'仙桃市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6411,'天门市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6412,'潜江市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6413,'神农架林区',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6414,'恩施土家族苗族自治州',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6415,'长沙市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6416,'株洲市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6417,'湘潭市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6418,'衡阳市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6419,'邵阳市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6420,'岳阳市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6421,'常德市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6422,'张家界市',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6423,'益阳市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6424,'郴州市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6425,'永州市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6426,'怀化市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6427,'娄底市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6428,'湘西土家族苗族自治州',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6429,'广州市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6430,'深圳市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6431,'珠海市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6432,'汕头市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6433,'韶关市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6434,'佛山市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6435,'江门市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6436,'湛江市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6437,'茂名市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6438,'肇庆市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6439,'惠州市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6440,'梅州市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6441,'汕尾市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6442,'河源市',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6443,'阳江市',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6444,'清远市',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6445,'东莞市',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6446,'中山市',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6447,'潮州市',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6448,'揭阳市',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6449,'云浮市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6450,'兰州市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6451,'金昌市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6452,'白银市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6453,'天水市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6454,'嘉峪关市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6455,'武威市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6456,'张掖市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6457,'平凉市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6458,'酒泉市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6459,'庆阳市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6460,'定西市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6461,'陇南市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6462,'临夏回族自治州',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6463,'甘南藏族自治州',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6464,'成都市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6465,'自贡市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6466,'攀枝花市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6467,'泸州市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6468,'德阳市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6469,'绵阳市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6470,'广元市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6471,'遂宁市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6472,'内江市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6473,'乐山市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6474,'南充市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6475,'眉山市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6476,'宜宾市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6477,'广安市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6478,'达州市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6479,'雅安市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6480,'巴中市',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6481,'资阳市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6482,'阿坝藏族羌族自治州',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6483,'甘孜藏族自治州',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6484,'凉山彝族自治州',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6485,'济南市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6486,'青岛市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6487,'淄博市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6488,'枣庄市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6489,'东营市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6490,'烟台市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6491,'潍坊市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6492,'济宁市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6493,'泰安市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6494,'威海市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6495,'日照市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6496,'莱芜市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6497,'临沂市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6498,'德州市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6499,'聊城市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6500,'滨州市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6501,'菏泽市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6502,'贵阳市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6503,'六盘水市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6504,'遵义市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6505,'安顺市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6506,'铜仁地区',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6507,'毕节地区',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6508,'黔西南布依族苗族自治州',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6509,'黔东南苗族侗族自治州',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6510,'黔南布依族苗族自治州',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6511,'海口市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6512,'三亚市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6513,'五指山市',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6514,'琼海市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6515,'儋州市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6516,'文昌市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6517,'万宁市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6518,'东方市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6519,'澄迈县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6520,'定安县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6521,'屯昌县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6522,'临高县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6523,'白沙黎族自治县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6524,'昌江黎族自治县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6525,'乐东黎族自治县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6526,'陵水黎族自治县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6527,'保亭黎族苗族自治县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6528,'琼中黎族苗族自治县',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6529,'昆明市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6530,'曲靖市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6531,'玉溪市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6532,'保山市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6533,'昭通市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6534,'丽江市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6535,'思茅市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6536,'临沧市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6537,'文山壮族苗族自治州',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6538,'红河哈尼族彝族自治州',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6539,'西双版纳傣族自治州',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6540,'楚雄彝族自治州',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6541,'大理白族自治州',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6542,'德宏傣族景颇族自治州',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6543,'怒江傈傈族自治州',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6544,'迪庆藏族自治州',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6545,'西宁市',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6546,'海东地区',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6547,'海北藏族自治州',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6548,'黄南藏族自治州',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6549,'海南藏族自治州',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6550,'果洛藏族自治州',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6551,'玉树藏族自治州',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6552,'海西蒙古族藏族自治州',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6553,'西安市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6554,'铜川市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6555,'宝鸡市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6556,'咸阳市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6557,'渭南市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6558,'延安市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6559,'汉中市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6560,'榆林市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6561,'安康市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6562,'商洛市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6563,'南宁市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6564,'柳州市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6565,'桂林市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6566,'梧州市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6567,'北海市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6568,'防城港市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6569,'钦州市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6570,'贵港市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6571,'玉林市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6572,'百色市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6573,'贺州市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6574,'河池市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6575,'来宾市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6576,'崇左市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6577,'拉萨市',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6578,'那曲地区',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6579,'昌都地区',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6580,'山南地区',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6581,'日喀则地区',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6582,'阿里地区',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6583,'林芝地区',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6584,'银川市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6585,'石嘴山市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6586,'吴忠市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6587,'固原市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6588,'中卫市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6589,'乌鲁木齐市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6590,'克拉玛依市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6591,'石河子市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6592,'阿拉尔市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6593,'图木舒克市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6594,'五家渠市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6595,'吐鲁番市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6596,'阿克苏市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6597,'喀什市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6598,'哈密市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6599,'和田市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6600,'阿图什市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6601,'库尔勒市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6602,'昌吉市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6603,'阜康市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6604,'米泉市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6605,'博乐市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6606,'伊宁市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6607,'奎屯市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6608,'塔城市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6609,'乌苏市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6610,'阿勒泰市',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6611,'呼和浩特市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6612,'包头市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6613,'乌海市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6614,'赤峰市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6615,'通辽市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6616,'鄂尔多斯市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6617,'呼伦贝尔市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6618,'巴彦淖尔市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6619,'乌兰察布市',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6620,'锡林郭勒盟',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6621,'兴安盟',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6622,'阿拉善盟',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6623,'澳门特别行政区',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6624,'香港特别行政区',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6625,'旺铺',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6626,'其它',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6627,'1次',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6628,'2次',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6629,'3次',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6630,'4次以上',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6631,'本月内',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6632,'三个月内',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6633,'半年内',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6634,'1－2次',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6635,'3次以上',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6636,'50以下',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6637,'51-100',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6638,'101-150',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6639,'151-200',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6640,'201-300',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6641,'300以上',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6642,'50以下',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6643,'51-100',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6644,'101-150',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6645,'151-200',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6646,'201-300',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6647,'300以上',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6648,'交易完成',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6649,'交易关闭',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6650,'待支付',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6651,'是',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6652,'否',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_distribution`
--

DROP TABLE IF EXISTS `tag_distribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_distribution` (
  `id` int(11) NOT NULL,
  `tag_id` int(11) DEFAULT NULL,
  `coverage_percent` varchar(10) DEFAULT NULL,
  `inuse_tag_percent` varchar(10) DEFAULT NULL,
  `audience_count` char(10) DEFAULT NULL COMMENT '标签覆盖的人数',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签分布';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_distribution`
--

LOCK TABLES `tag_distribution` WRITE;
/*!40000 ALTER TABLE `tag_distribution` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_distribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_group_map`
--

DROP TABLE IF EXISTS `tag_group_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_group_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5568 DEFAULT CHARSET=utf8 COMMENT='标签和分组的关联关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_group_map`
--

LOCK TABLES `tag_group_map` WRITE;
/*!40000 ALTER TABLE `tag_group_map` DISABLE KEYS */;
INSERT INTO `tag_group_map` VALUES (4784,5869,14069,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4785,5870,14069,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4786,5871,14069,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4787,5872,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4788,5873,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4789,5874,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4790,5875,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4791,5876,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4792,5877,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4793,5878,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4794,5879,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4795,5880,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4796,5881,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4797,5882,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4798,5883,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4799,5884,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4800,5885,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4801,5886,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4802,5887,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4803,5888,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4804,5889,14092,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4805,5890,14092,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4806,5891,14092,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4807,5892,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4808,5893,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4809,5894,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4810,5895,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4811,5896,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4812,5897,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4813,5898,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4814,5899,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4815,5900,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4816,5901,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4817,5902,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4818,5903,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4819,5904,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4820,5905,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4821,5906,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4822,5907,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4823,5908,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4824,5909,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4825,5910,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4826,5911,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4827,5912,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4828,5913,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4829,5914,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4830,5915,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4831,5916,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4832,5917,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4833,5918,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4834,5919,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4835,5920,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4836,5921,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4837,5922,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4838,5923,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4839,5924,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4840,5925,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4841,5926,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4842,5927,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4843,5928,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4844,5929,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4845,5930,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4846,5931,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4847,5932,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4848,5933,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4849,5934,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4850,5935,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4851,5936,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4852,5937,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4853,5938,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4854,5939,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4855,5940,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4856,5941,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4857,5942,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4858,5943,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4859,5944,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4860,5945,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4861,5946,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4862,5947,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4863,5948,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4864,5949,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4865,5950,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4866,5951,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4867,5952,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4868,5953,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4869,5954,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4870,5955,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4871,5956,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4872,5957,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4873,5958,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4874,5959,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4875,5960,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4876,5961,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4877,5962,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4878,5963,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4879,5964,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4880,5965,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4881,5966,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4882,5967,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4883,5968,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4884,5969,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4885,5970,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4886,5971,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4887,5972,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4888,5973,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4889,5974,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4890,5975,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4891,5976,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4892,5977,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4893,5978,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4894,5979,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4895,5980,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4896,5981,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4897,5982,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4898,5983,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4899,5984,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4900,5985,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4901,5986,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4902,5987,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4903,5988,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4904,5989,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4905,5990,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4906,5991,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4907,5992,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4908,5993,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4909,5994,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4910,5995,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4911,5996,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4912,5997,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4913,5998,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4914,5999,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4915,6000,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4916,6001,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4917,6002,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4918,6003,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4919,6004,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4920,6005,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4921,6006,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4922,6007,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4923,6008,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4924,6009,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4925,6010,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4926,6011,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4927,6012,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4928,6013,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4929,6014,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4930,6015,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4931,6016,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4932,6017,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4933,6018,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4934,6019,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4935,6020,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4936,6021,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4937,6022,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4938,6023,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4939,6024,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4940,6025,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4941,6026,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4942,6027,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4943,6028,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4944,6029,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4945,6030,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4946,6031,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4947,6032,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4948,6033,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4949,6034,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4950,6035,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4951,6036,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4952,6037,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4953,6038,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4954,6039,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4955,6040,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4956,6041,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4957,6042,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4958,6043,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4959,6044,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4960,6045,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4961,6046,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4962,6047,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4963,6048,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4964,6049,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4965,6050,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4966,6051,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4967,6052,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4968,6053,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4969,6054,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4970,6055,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4971,6056,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4972,6057,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4973,6058,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4974,6059,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4975,6060,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4976,6061,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4977,6062,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4978,6063,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4979,6064,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4980,6065,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4981,6066,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4982,6067,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4983,6068,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4984,6069,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4985,6070,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4986,6071,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4987,6072,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4988,6073,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4989,6074,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4990,6075,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4991,6076,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4992,6077,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4993,6078,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4994,6079,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4995,6080,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4996,6081,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4997,6082,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4998,6083,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4999,6084,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5000,6085,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5001,6086,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5002,6087,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5003,6088,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5004,6089,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5005,6090,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5006,6091,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5007,6092,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5008,6093,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5009,6094,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5010,6095,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5011,6096,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5012,6097,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5013,6098,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5014,6099,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5015,6100,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5016,6101,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5017,6102,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5018,6103,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5019,6104,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5020,6105,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5021,6106,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5022,6107,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5023,6108,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5024,6109,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5025,6110,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5026,6111,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5027,6112,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5028,6113,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5029,6114,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5030,6115,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5031,6116,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5032,6117,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5033,6118,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5034,6119,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5035,6120,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5036,6121,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5037,6122,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5038,6123,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5039,6124,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5040,6125,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5041,6126,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5042,6127,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5043,6128,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5044,6129,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5045,6130,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5046,6131,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5047,6132,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5048,6133,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5049,6134,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5050,6135,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5051,6136,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5052,6137,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5053,6138,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5054,6139,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5055,6140,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5056,6141,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5057,6142,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5058,6143,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5059,6144,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5060,6145,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5061,6146,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5062,6147,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5063,6148,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5064,6149,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5065,6150,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5066,6151,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5067,6152,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5068,6153,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5069,6154,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5070,6155,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5071,6156,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5072,6157,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5073,6158,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5074,6159,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5075,6160,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5076,6161,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5077,6162,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5078,6163,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5079,6164,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5080,6165,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5081,6166,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5082,6167,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5083,6168,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5084,6169,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5085,6170,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5086,6171,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5087,6172,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5088,6173,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5089,6174,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5090,6175,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5091,6176,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5092,6177,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5093,6178,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5094,6179,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5095,6180,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5096,6181,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5097,6182,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5098,6183,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5099,6184,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5100,6185,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5101,6186,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5102,6187,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5103,6188,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5104,6189,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5105,6190,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5106,6191,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5107,6192,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5108,6193,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5109,6194,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5110,6195,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5111,6196,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5112,6197,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5113,6198,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5114,6199,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5115,6200,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5116,6201,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5117,6202,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5118,6203,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5119,6204,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5120,6205,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5121,6206,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5122,6207,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5123,6208,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5124,6209,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5125,6210,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5126,6211,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5127,6212,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5128,6213,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5129,6214,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5130,6215,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5131,6216,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5132,6217,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5133,6218,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5134,6219,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5135,6220,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5136,6221,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5137,6222,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5138,6223,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5139,6224,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5140,6225,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5141,6226,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5142,6227,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5143,6228,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5144,6229,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5145,6230,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5146,6231,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5147,6232,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5148,6233,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5149,6234,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5150,6235,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5151,6236,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5152,6237,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5153,6238,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5154,6239,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5155,6240,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5156,6241,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5157,6242,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5158,6243,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5159,6244,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5160,6245,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5161,6246,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5162,6247,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5163,6248,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5164,6249,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5165,6250,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5166,6251,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5167,6252,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5168,6253,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5169,6254,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5170,6255,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5171,6256,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5172,6257,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5173,6258,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5174,6259,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5175,6260,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5176,6261,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5177,6262,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5178,6263,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5179,6264,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5180,6265,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5181,6266,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5182,6267,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5183,6268,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5184,6269,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5185,6270,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5186,6271,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5187,6272,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5188,6273,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5189,6274,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5190,6275,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5191,6276,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5192,6277,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5193,6278,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5194,6279,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5195,6280,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5196,6281,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5197,6282,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5198,6283,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5199,6284,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5200,6285,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5201,6286,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5202,6287,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5203,6288,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5204,6289,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5205,6290,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5206,6291,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5207,6292,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5208,6293,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5209,6294,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5210,6295,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5211,6296,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5212,6297,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5213,6298,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5214,6299,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5215,6300,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5216,6301,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5217,6302,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5218,6303,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5219,6304,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5220,6305,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5221,6306,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5222,6307,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5223,6308,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5224,6309,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5225,6310,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5226,6311,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5227,6312,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5228,6313,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5229,6314,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5230,6315,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5231,6316,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5232,6317,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5233,6318,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5234,6319,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5235,6320,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5236,6321,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5237,6322,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5238,6323,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5239,6324,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5240,6325,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5241,6326,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5242,6327,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5243,6328,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5244,6329,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5245,6330,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5246,6331,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5247,6332,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5248,6333,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5249,6334,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5250,6335,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5251,6336,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5252,6337,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5253,6338,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5254,6339,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5255,6340,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5256,6341,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5257,6342,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5258,6343,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5259,6344,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5260,6345,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5261,6346,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5262,6347,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5263,6348,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5264,6349,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5265,6350,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5266,6351,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5267,6352,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5268,6353,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5269,6354,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5270,6355,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5271,6356,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5272,6357,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5273,6358,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5274,6359,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5275,6360,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5276,6361,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5277,6362,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5278,6363,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5279,6364,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5280,6365,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5281,6366,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5282,6367,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5283,6368,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5284,6369,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5285,6370,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5286,6371,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5287,6372,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5288,6373,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5289,6374,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5290,6375,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5291,6376,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5292,6377,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5293,6378,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5294,6379,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5295,6380,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5296,6381,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5297,6382,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5298,6383,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5299,6384,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5300,6385,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5301,6386,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5302,6387,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5303,6388,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5304,6389,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5305,6390,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5306,6391,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5307,6392,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5308,6393,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5309,6394,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5310,6395,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5311,6396,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5312,6397,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5313,6398,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5314,6399,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5315,6400,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5316,6401,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5317,6402,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5318,6403,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5319,6404,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5320,6405,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5321,6406,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5322,6407,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5323,6408,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5324,6409,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5325,6410,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5326,6411,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5327,6412,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5328,6413,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5329,6414,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5330,6415,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5331,6416,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5332,6417,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5333,6418,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5334,6419,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5335,6420,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5336,6421,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5337,6422,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5338,6423,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5339,6424,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5340,6425,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5341,6426,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5342,6427,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5343,6428,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5344,6429,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5345,6430,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5346,6431,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5347,6432,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5348,6433,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5349,6434,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5350,6435,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5351,6436,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5352,6437,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5353,6438,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5354,6439,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5355,6440,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5356,6441,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5357,6442,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5358,6443,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5359,6444,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5360,6445,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5361,6446,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5362,6447,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5363,6448,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5364,6449,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5365,6450,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5366,6451,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5367,6452,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5368,6453,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5369,6454,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5370,6455,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5371,6456,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5372,6457,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5373,6458,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5374,6459,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5375,6460,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5376,6461,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5377,6462,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5378,6463,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5379,6464,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5380,6465,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5381,6466,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5382,6467,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5383,6468,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5384,6469,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5385,6470,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5386,6471,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5387,6472,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5388,6473,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5389,6474,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5390,6475,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5391,6476,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5392,6477,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5393,6478,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5394,6479,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5395,6480,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5396,6481,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5397,6482,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5398,6483,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5399,6484,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5400,6485,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5401,6486,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5402,6487,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5403,6488,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5404,6489,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5405,6490,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5406,6491,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5407,6492,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5408,6493,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5409,6494,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5410,6495,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5411,6496,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5412,6497,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5413,6498,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5414,6499,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5415,6500,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5416,6501,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5417,6502,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5418,6503,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5419,6504,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5420,6505,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5421,6506,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5422,6507,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5423,6508,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5424,6509,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5425,6510,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5426,6511,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5427,6512,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5428,6513,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5429,6514,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5430,6515,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5431,6516,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5432,6517,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5433,6518,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5434,6519,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5435,6520,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5436,6521,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5437,6522,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5438,6523,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5439,6524,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5440,6525,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5441,6526,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5442,6527,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5443,6528,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5444,6529,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5445,6530,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5446,6531,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5447,6532,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5448,6533,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5449,6534,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5450,6535,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5451,6536,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5452,6537,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5453,6538,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5454,6539,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5455,6540,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5456,6541,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5457,6542,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5458,6543,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5459,6544,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5460,6545,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5461,6546,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5462,6547,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5463,6548,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5464,6549,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5465,6550,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5466,6551,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5467,6552,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5468,6553,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5469,6554,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5470,6555,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5471,6556,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5472,6557,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5473,6558,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5474,6559,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5475,6560,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5476,6561,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5477,6562,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5478,6563,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5479,6564,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5480,6565,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5481,6566,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5482,6567,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5483,6568,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5484,6569,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5485,6570,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5486,6571,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5487,6572,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5488,6573,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5489,6574,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5490,6575,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5491,6576,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5492,6577,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5493,6578,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5494,6579,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5495,6580,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5496,6581,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5497,6582,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5498,6583,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5499,6584,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5500,6585,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5501,6586,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5502,6587,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5503,6588,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5504,6589,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5505,6590,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5506,6591,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5507,6592,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5508,6593,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5509,6594,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5510,6595,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5511,6596,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5512,6597,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5513,6598,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5514,6599,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5515,6600,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5516,6601,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5517,6602,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5518,6603,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5519,6604,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5520,6605,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5521,6606,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5522,6607,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5523,6608,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5524,6609,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5525,6610,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5526,6611,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5527,6612,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5528,6613,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5529,6614,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5530,6615,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5531,6616,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5532,6617,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5533,6618,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5534,6619,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5535,6620,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5536,6621,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5537,6622,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5538,6623,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5539,6624,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5540,6625,14837,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5541,6626,14837,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5542,6627,14841,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5543,6628,14841,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5544,6629,14841,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5545,6630,14841,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5546,6631,14846,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5547,6632,14846,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5548,6633,14846,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5549,6634,14850,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5550,6635,14850,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5551,6636,14853,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5552,6637,14853,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5553,6638,14853,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5554,6639,14853,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5555,6640,14853,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5556,6641,14853,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5557,6642,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5558,6643,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5559,6644,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5560,6645,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5561,6646,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5562,6647,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5563,6648,14868,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5564,6649,14868,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5565,6650,14868,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5566,6651,14872,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5567,6652,14872,0,'2016-08-08 11:41:14','2016-08-08 03:41:14');
/*!40000 ALTER TABLE `tag_group_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_party_relation`
--

DROP TABLE IF EXISTS `tag_party_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_party_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) NOT NULL,
  `party_id` int(11) NOT NULL COMMENT '主数据(联系人)ID',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_party_relation`
--

LOCK TABLES `tag_party_relation` WRITE;
/*!40000 ALTER TABLE `tag_party_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_party_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_recommend`
--

DROP TABLE IF EXISTS `tag_recommend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_recommend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_group_id` int(11) DEFAULT NULL,
  `tag_group_name` varchar(200) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COMMENT='系统推荐标签组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_recommend`
--

LOCK TABLES `tag_recommend` WRITE;
/*!40000 ALTER TABLE `tag_recommend` DISABLE KEYS */;
INSERT INTO `tag_recommend` VALUES (153,14069,'自然人属性-个人信息-性别',0,'2016-08-08 11:40:47','2016-08-09 02:33:38'),(159,14274,'自然人属性-个人信息-国籍',0,'2016-08-08 11:40:53','2016-08-09 02:37:49'),(161,14392,'自然人属性-地理区域-省',0,'2016-08-08 11:40:58','2016-08-09 02:37:49'),(162,14427,'自然人属性-地理区域-市',0,'2016-08-08 17:48:00','2016-08-18 02:43:33'),(163,14837,'接触点偏好-交易渠道偏好-微盟渠道人群',0,'2016-08-08 11:41:13','2016-08-17 09:44:37'),(164,14841,'用户价值-购买价值(RFM)-总购买频次',0,'2016-08-08 11:41:13','2016-08-09 02:37:49'),(165,14846,'用户价值-购买价值(RFM)-最后一次购买',0,'2016-08-08 11:41:13','2016-08-09 02:37:49'),(166,14850,'用户价值-购买价值(RFM)-月均购买频次',0,'2016-08-08 11:41:13','2016-08-09 02:37:49'),(167,14853,'用户价值-购买价值(RFM)-总计交易金额',0,'2016-08-08 11:41:13','2016-08-09 02:37:49'),(168,14860,'用户价值-购买价值(RFM)-客单价',0,'2016-08-08 11:41:14','2016-08-09 02:37:49'),(169,14868,'品牌联系强度-用户流失概率-支付状态',0,'2016-08-08 11:41:14','2016-08-09 02:37:49'),(170,14872,'品牌联系强度-用户流失概率-购买人群',0,'2016-08-08 11:41:14','2016-08-17 09:44:52');
/*!40000 ALTER TABLE `tag_recommend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taggroup`
--

DROP TABLE IF EXISTS `taggroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taggroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签组ID',
  `name` varchar(40) DEFAULT NULL COMMENT '标签组名称',
  `parent_group_id` bigint(20) DEFAULT NULL COMMENT '父标签组ID',
  `level` int(11) DEFAULT NULL COMMENT '标签组层级 0-最未级 1-次级   n-最高级(最大分类)',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14875 DEFAULT CHARSET=utf8 COMMENT='标签组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taggroup`
--

LOCK TABLES `taggroup` WRITE;
/*!40000 ALTER TABLE `taggroup` DISABLE KEYS */;
INSERT INTO `taggroup` VALUES (14068,'自然人属性-个人信息',-1,0,0,'2016-08-08 11:40:47','2016-08-18 03:50:59'),(14069,'自然人属性-个人信息-性别',14068,1,0,'2016-08-08 11:40:47','2016-08-18 03:51:00'),(14070,'男',14069,2,0,'2016-08-08 11:40:47','2016-08-18 03:51:01'),(14071,'女',14069,2,0,'2016-08-08 11:40:47','2016-08-18 03:51:03'),(14072,'未知',14069,2,0,'2016-08-08 11:40:47','2016-08-18 03:51:05'),(14073,'自然人属性-个人信息-年龄段',14068,1,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14074,'00后',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14075,'90后',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14076,'80后',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14077,'70后',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14078,'69前',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14079,'自然人属性-个人信息-星座',14068,1,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14080,'水瓶座',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14081,'双鱼座',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14082,'白羊座',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14083,'金牛座',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14084,'双子座',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14085,'巨蟹座',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14086,'狮子座',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14087,'处女座',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14088,'天秤座',14079,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14089,'天蝎座',14079,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14090,'射手座',14079,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14091,'摩羯座',14079,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14092,'自然人属性-个人信息-婚姻状况',14068,1,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14093,'已婚',14092,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14094,'未婚',14092,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14095,'未知',14092,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14096,'自然人属性-个人信息-血型',14068,1,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14097,'A',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14098,'B',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14099,'AB',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14100,'O',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14101,'未知',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14102,'自然人属性-个人信息-职业',14068,1,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14103,'销售',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14104,'客服',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14105,'市场',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14106,'管理',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14107,'渠道',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14108,'行政',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14109,'商务',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14110,'运营',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14111,'数据分析',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14112,'售前',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14113,'售后',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14114,'产品经理',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14115,'品牌经理',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14116,'咨询',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14117,'策划',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14118,'文案',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14119,'公关',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14120,'媒介',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14121,'企划',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14122,'市场调研',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14123,'业务拓展',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14124,'广告',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14125,'会展',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14126,'客户经理',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14127,'创意',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14128,'设计',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14129,'美术',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14130,'策划',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14131,'财务',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14132,'审计',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14133,'税务',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14134,'人力资源',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14135,'会计',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14136,'出纳',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14137,'培训',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14138,'猎头',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14139,'后勤',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14140,'文秘',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14141,'前台',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14142,'项目管理',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14143,'IT',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14144,'证劵',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14145,'投资',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14146,'通信',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14147,'房地产',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14148,'保险',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14149,'汽车',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14150,'电子',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14151,'电器',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14152,'服装',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14153,'纺织',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14154,'能源',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14155,'矿产',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14156,'物流',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14157,'仓储',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14158,'医药',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14159,'化工',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14160,'安全管理',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14161,'化验',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14162,'认证',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14163,'供应商',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14164,'采购',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14165,'编辑',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14166,'互联网',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14167,'电商',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14168,'软件',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14169,'开发',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14170,'系统集成',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14171,'移动互联网',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14172,'UE设计',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14173,'UI设计',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14174,'移动互联网',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14175,'游戏策划',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14176,'游戏数值',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14177,'游戏开发',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14178,'游戏设计',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14179,'游戏测试',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14180,'硬件',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14181,'信息标准化',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14182,'网络安全',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14183,'电信',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14184,'房产',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14185,'建筑',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14186,'物业',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14187,'土木',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14188,'装修',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14189,'市政工程',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14190,'道路',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14191,'桥梁',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14192,'隧道工程',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14193,'水利',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14194,'港口',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14195,'排水',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14196,'管通',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14197,'空调',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14198,'园林景观设计',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14199,'城市规划设计',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14200,'银行',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14201,'进出口',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14202,'风险控制',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14203,'理财',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14204,'融资',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14205,'信托',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14206,'担保',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14207,'拍卖',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14208,'典当',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14209,'珠宝',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14210,'收藏',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14211,'贸易',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14212,'买手',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14213,'报关',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14214,'司机',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14215,'航空',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14216,'公交',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14217,'船舶',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14218,'列车',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14219,'快递',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14220,'搬家',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14221,'家政',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14222,'工业制造',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14223,'半导体',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14224,'传导器',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14225,'照明',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14226,'激光',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14227,'电器',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14228,'二手车',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14229,'4S店',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14230,'汽车维修',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14231,'汽车保养',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14232,'机械工程',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14233,'生物',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14234,'制药',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14235,'医疗器械',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14236,'传媒',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14237,'印刷',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14238,'影视',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14239,'媒体',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14240,'出版',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14241,'导演',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14242,'演员',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14243,'编剧',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14244,'记者',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14245,'作家',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14246,'网红',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14247,'配音',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14248,'法律',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14249,'教育',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14250,'翻译',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14251,'商超',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14252,'酒店',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14253,'娱乐',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14254,'旅游',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14255,'烹饪',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14256,'保健',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14257,'美容',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14258,'美发',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14259,'健身',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14260,'医疗',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14261,'农业',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14262,'科研',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14263,'环保',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14264,'公务员',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14265,'志愿者',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14266,'义工',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14267,'慈善',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14268,'体育',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14269,'外包服务',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14270,'快速消费品',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14271,'奢侈品',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14272,'重工业',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14273,'其他',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14274,'自然人属性-个人信息-国籍',14068,1,0,'2016-08-08 11:40:53','2016-08-18 06:04:53'),(14275,'中国',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14276,'美国',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14277,'日本',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14278,'英国',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14279,'德国',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14280,'韩国',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14281,'香港',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14282,'意大利',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14283,'加拿大',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14284,'澳大利亚',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14285,'法国',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14286,'菲律宾',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14287,'以色列',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14288,'西班牙',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14289,'台湾',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14290,'比利时',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14291,'新加坡',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14292,'芬兰',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14293,'荷兰',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14294,'波兰',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14295,'瑞士',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14296,'瑞典',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14297,'泰国',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14298,'葡萄牙',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14299,'智利',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14300,'新西兰',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14301,'巴西',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14302,'丹麦',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14303,'奥地利',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14304,'越南',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14305,'斯里兰卡',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14306,'马来西亚',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14307,'土耳其',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14308,'古巴',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14309,'直布罗陀',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14310,'乌克兰',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14311,'阿根廷',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14312,'俄罗斯',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14313,'希腊',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14314,'挪威',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14315,'南非',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14316,'墨西哥',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14317,'爱尔兰',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14318,'印度尼西亚',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14319,'印度',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14320,'乌拉圭',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14321,'阿联酋',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14322,'捷克',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14323,'阿曼',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14324,'塞浦路斯',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14325,'匈牙利',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14326,'罗马尼亚',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14327,'苏格兰',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14328,'保加利亚',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14329,'柬埔寨',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14330,'斯洛伐克',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14331,'拉脱维亚',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14332,'冰岛',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14333,'澳门',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14334,'自然人属性-个人信息-民族',14068,1,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14335,'汉族',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14336,'蒙古族',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14337,'回族',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14338,'藏族',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14339,'维吾尔族',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14340,'苗族',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14341,'彝族',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14342,'壮族',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14343,'布依族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14344,'朝鲜族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14345,'满族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14346,'侗族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14347,'瑶族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14348,'白族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14349,'土家族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14350,'哈尼族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14351,'哈萨克族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14352,'傣族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14353,'黎族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14354,'傈僳族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14355,'佤族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14356,'畲族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14357,'高山族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14358,'拉祜族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14359,'水族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14360,'东乡族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14361,'纳西族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14362,'景颇族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14363,'柯尔克孜族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14364,'土族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14365,'达斡尔族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14366,'仫佬族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14367,'羌族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14368,'布朗族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14369,'撒拉族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14370,'毛难族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14371,'仡佬族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14372,'锡伯族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14373,'阿昌族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14374,'普米族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14375,'塔吉克族',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14376,'怒族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14377,'乌孜别克族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14378,'俄罗斯族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14379,'鄂温克族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14380,'崩龙族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14381,'保安族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14382,'裕固族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14383,'京族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14384,'塔塔尔族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14385,'独龙族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14386,'鄂伦春族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14387,'赫哲族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14388,'门巴族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14389,'珞巴族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14390,'基诺族',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14391,'自然人属性-地理区域',-1,0,0,'2016-08-08 11:40:58','2016-08-18 03:55:32'),(14392,'自然人属性-地理区域-省',14391,1,0,'2016-08-08 11:40:58','2016-08-18 03:55:34'),(14393,'北京',14392,2,0,'2016-08-08 11:40:58','2016-08-23 09:35:05'),(14394,'天津',14392,2,0,'2016-08-08 11:40:58','2016-08-23 09:35:06'),(14395,'上海',14392,2,0,'2016-08-08 11:40:58','2016-08-23 09:35:06'),(14396,'重庆',14392,2,0,'2016-08-08 11:40:58','2016-08-23 09:35:09'),(14397,'河北省',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14398,'山西省',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14399,'台湾省',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14400,'辽宁省',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14401,'吉林省',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14402,'黑龙江省',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14403,'江苏省',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14404,'浙江省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14405,'安徽省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14406,'福建省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14407,'江西省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14408,'山东省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14409,'河南省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14410,'湖北省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14411,'湖南省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14412,'广东省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14413,'甘肃省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14414,'四川省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14415,'贵州省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14416,'海南省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14417,'云南省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14418,'青海省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14419,'陕西省',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14420,'广西壮族自治区',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14421,'西藏自治区',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14422,'宁夏回族自治区',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14423,'新疆维吾尔自治区',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14424,'内蒙古自治区',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14425,'澳门特别行政区',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14426,'香港特别行政区',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14427,'自然人属性-地理区域-市',14391,1,0,'2016-08-08 11:40:59','2016-08-18 03:55:48'),(14428,'北京市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14429,'天津市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14430,'上海市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14431,'重庆市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14432,'石家庄市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14433,'唐山市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14434,'秦皇岛市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14435,'邯郸市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14436,'邢台市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14437,'保定市',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14438,'张家口市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14439,'承德市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14440,'沧州市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14441,'廊坊市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14442,'衡水市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14443,'太原市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14444,'大同市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14445,'阳泉市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14446,'长治市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14447,'晋城市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14448,'朔州市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14449,'晋中市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14450,'运城市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14451,'忻州市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14452,'临汾市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14453,'吕梁市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14454,'台北市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14455,'高雄市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14456,'基隆市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14457,'台中市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14458,'台南市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14459,'新竹市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14460,'嘉义市',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14461,'台北县',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14462,'宜兰县',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14463,'桃园县',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14464,'新竹县',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14465,'苗栗县',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14466,'台中县',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14467,'彰化县',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14468,'南投县',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14469,'云林县',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14470,'嘉义县',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14471,'台南县',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14472,'高雄县',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14473,'屏东县',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14474,'澎湖县',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14475,'台东县',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14476,'花莲县',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14477,'沈阳市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14478,'大连市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14479,'鞍山市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14480,'抚顺市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14481,'本溪市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14482,'丹东市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14483,'锦州市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14484,'营口市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14485,'阜新市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14486,'辽阳市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14487,'盘锦市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14488,'铁岭市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14489,'朝阳市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14490,'葫芦岛市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14491,'长春市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14492,'吉林市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14493,'四平市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14494,'辽源市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14495,'通化市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14496,'白山市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14497,'松原市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14498,'白城市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14499,'延边朝鲜族自治州',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14500,'哈尔滨市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14501,'齐齐哈尔市',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14502,'鹤岗市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14503,'双鸭山市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14504,'鸡西市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14505,'大庆市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14506,'伊春市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14507,'牡丹江市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14508,'佳木斯市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14509,'七台河市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14510,'黑河市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14511,'绥化市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14512,'大兴安岭地区',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14513,'南京市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14514,'无锡市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14515,'徐州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14516,'常州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14517,'苏州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14518,'南通市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14519,'连云港市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14520,'淮安市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14521,'盐城市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14522,'扬州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14523,'镇江市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14524,'泰州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14525,'宿迁市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14526,'杭州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14527,'宁波市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14528,'温州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14529,'嘉兴市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14530,'湖州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14531,'绍兴市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14532,'金华市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14533,'衢州市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14534,'舟山市',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14535,'台州市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14536,'丽水市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14537,'合肥市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14538,'芜湖市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14539,'蚌埠市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14540,'淮南市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14541,'马鞍山市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14542,'淮北市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14543,'铜陵市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14544,'安庆市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14545,'黄山市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14546,'滁州市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14547,'阜阳市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14548,'宿州市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14549,'巢湖市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14550,'六安市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14551,'亳州市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14552,'池州市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14553,'宣城市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14554,'福州市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14555,'厦门市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14556,'莆田市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14557,'三明市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14558,'泉州市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14559,'漳州市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14560,'南平市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14561,'龙岩市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14562,'宁德市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14563,'南昌市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14564,'景德镇市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14565,'萍乡市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14566,'九江市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14567,'新余市',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14568,'鹰潭市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14569,'赣州市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14570,'吉安市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14571,'宜春市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14572,'抚州市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14573,'上饶市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14574,'济南市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14575,'青岛市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14576,'淄博市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14577,'枣庄市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14578,'东营市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14579,'烟台市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14580,'潍坊市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14581,'济宁市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14582,'泰安市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14583,'威海市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14584,'日照市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14585,'莱芜市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14586,'临沂市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14587,'德州市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14588,'聊城市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14589,'滨州市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14590,'菏泽市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14591,'郑州市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14592,'开封市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14593,'洛阳市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14594,'平顶山市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14595,'安阳市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14596,'鹤壁市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14597,'新乡市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14598,'焦作市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14599,'濮阳市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14600,'许昌市',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14601,'漯河市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14602,'三门峡市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14603,'南阳市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14604,'商丘市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14605,'信阳市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14606,'周口市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14607,'驻马店市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14608,'济源市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14609,'武汉市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14610,'黄石市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14611,'十堰市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14612,'荆州市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14613,'宜昌市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14614,'襄樊市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14615,'鄂州市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14616,'荆门市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14617,'孝感市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14618,'黄冈市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14619,'咸宁市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14620,'随州市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14621,'仙桃市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14622,'天门市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14623,'潜江市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14624,'神农架林区',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14625,'恩施土家族苗族自治州',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14626,'长沙市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14627,'株洲市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14628,'湘潭市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14629,'衡阳市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14630,'邵阳市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14631,'岳阳市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14632,'常德市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14633,'张家界市',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14634,'益阳市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14635,'郴州市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14636,'永州市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14637,'怀化市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14638,'娄底市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14639,'湘西土家族苗族自治州',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14640,'广州市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14641,'深圳市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14642,'珠海市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14643,'汕头市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14644,'韶关市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14645,'佛山市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14646,'江门市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14647,'湛江市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14648,'茂名市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14649,'肇庆市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14650,'惠州市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14651,'梅州市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14652,'汕尾市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14653,'河源市',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14654,'阳江市',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14655,'清远市',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14656,'东莞市',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14657,'中山市',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14658,'潮州市',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14659,'揭阳市',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14660,'云浮市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14661,'兰州市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14662,'金昌市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14663,'白银市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14664,'天水市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14665,'嘉峪关市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14666,'武威市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14667,'张掖市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14668,'平凉市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14669,'酒泉市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14670,'庆阳市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14671,'定西市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14672,'陇南市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14673,'临夏回族自治州',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14674,'甘南藏族自治州',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14675,'成都市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14676,'自贡市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14677,'攀枝花市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14678,'泸州市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14679,'德阳市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14680,'绵阳市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14681,'广元市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14682,'遂宁市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14683,'内江市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14684,'乐山市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14685,'南充市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14686,'眉山市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14687,'宜宾市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14688,'广安市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14689,'达州市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14690,'雅安市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14691,'巴中市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14692,'资阳市',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14693,'阿坝藏族羌族自治州',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14694,'甘孜藏族自治州',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14695,'凉山彝族自治州',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14696,'济南市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14697,'青岛市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14698,'淄博市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14699,'枣庄市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14700,'东营市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14701,'烟台市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14702,'潍坊市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14703,'济宁市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14704,'泰安市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14705,'威海市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14706,'日照市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14707,'莱芜市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14708,'临沂市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14709,'德州市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14710,'聊城市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14711,'滨州市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14712,'菏泽市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14713,'贵阳市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14714,'六盘水市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14715,'遵义市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14716,'安顺市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14717,'铜仁地区',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14718,'毕节地区',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14719,'黔西南布依族苗族自治州',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14720,'黔东南苗族侗族自治州',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14721,'黔南布依族苗族自治州',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14722,'海口市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14723,'三亚市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14724,'五指山市',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14725,'琼海市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14726,'儋州市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14727,'文昌市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14728,'万宁市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14729,'东方市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14730,'澄迈县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14731,'定安县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14732,'屯昌县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14733,'临高县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14734,'白沙黎族自治县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14735,'昌江黎族自治县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14736,'乐东黎族自治县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14737,'陵水黎族自治县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14738,'保亭黎族苗族自治县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14739,'琼中黎族苗族自治县',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14740,'昆明市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14741,'曲靖市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14742,'玉溪市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14743,'保山市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14744,'昭通市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14745,'丽江市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14746,'思茅市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14747,'临沧市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14748,'文山壮族苗族自治州',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14749,'红河哈尼族彝族自治州',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14750,'西双版纳傣族自治州',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14751,'楚雄彝族自治州',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14752,'大理白族自治州',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14753,'德宏傣族景颇族自治州',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14754,'怒江傈傈族自治州',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14755,'迪庆藏族自治州',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14756,'西宁市',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14757,'海东地区',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14758,'海北藏族自治州',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14759,'黄南藏族自治州',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14760,'海南藏族自治州',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14761,'果洛藏族自治州',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14762,'玉树藏族自治州',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14763,'海西蒙古族藏族自治州',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14764,'西安市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14765,'铜川市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14766,'宝鸡市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14767,'咸阳市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14768,'渭南市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14769,'延安市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14770,'汉中市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14771,'榆林市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14772,'安康市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14773,'商洛市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14774,'南宁市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14775,'柳州市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14776,'桂林市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14777,'梧州市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14778,'北海市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14779,'防城港市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14780,'钦州市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14781,'贵港市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14782,'玉林市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14783,'百色市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14784,'贺州市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14785,'河池市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14786,'来宾市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14787,'崇左市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14788,'拉萨市',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14789,'那曲地区',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14790,'昌都地区',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14791,'山南地区',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14792,'日喀则地区',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14793,'阿里地区',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14794,'林芝地区',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14795,'银川市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14796,'石嘴山市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14797,'吴忠市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14798,'固原市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14799,'中卫市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14800,'乌鲁木齐市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14801,'克拉玛依市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14802,'石河子市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14803,'阿拉尔市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14804,'图木舒克市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14805,'五家渠市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14806,'吐鲁番市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14807,'阿克苏市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14808,'喀什市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14809,'哈密市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14810,'和田市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14811,'阿图什市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14812,'库尔勒市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14813,'昌吉市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14814,'阜康市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14815,'米泉市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14816,'博乐市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14817,'伊宁市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14818,'奎屯市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14819,'塔城市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14820,'乌苏市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14821,'阿勒泰市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14822,'呼和浩特市',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14823,'包头市',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14824,'乌海市',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14825,'赤峰市',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14826,'通辽市',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14827,'鄂尔多斯市',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14828,'呼伦贝尔市',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14829,'巴彦淖尔市',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14830,'乌兰察布市',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14831,'锡林郭勒盟',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14832,'兴安盟',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14833,'阿拉善盟',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14834,'澳门特别行政区',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14835,'香港特别行政区',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14836,'接触点偏好-交易渠道偏好',-1,0,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14837,'接触点偏好-交易渠道偏好-微盟渠道人群',14836,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14838,'旺铺',14837,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14839,'其它',14837,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14840,'用户价值-购买价值(RFM)',-1,0,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14841,'用户价值-购买价值(RFM)-总购买频次',14840,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14842,'1次',14841,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14843,'2次',14841,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14844,'3次',14841,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14845,'4次以上',14841,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14846,'用户价值-购买价值(RFM)-最后一次购买',14840,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14847,'本月内',14846,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14848,'三个月内',14846,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14849,'半年内',14846,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14850,'用户价值-购买价值(RFM)-月均购买频次',14840,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14851,'1－2次',14850,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14852,'3次以上',14850,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14853,'用户价值-购买价值(RFM)-总计交易金额',14840,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14854,'50以下',14853,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14855,'51-100',14853,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14856,'101-150',14853,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14857,'151-200',14853,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14858,'201-300',14853,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14859,'300以上',14853,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14860,'用户价值-购买价值(RFM)-客单价',14840,1,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14861,'50以下',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14862,'51-100',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14863,'101-150',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14864,'151-200',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14865,'201-300',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14866,'300以上',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14867,'品牌联系强度-用户流失概率',-1,0,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14868,'品牌联系强度-用户流失概率-支付状态',14867,1,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14869,'交易完成',14868,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14870,'交易关闭',14868,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14871,'待支付',14868,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14872,'品牌联系强度-用户流失概率-购买人群',14867,1,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14873,'是',14872,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14874,'否',14872,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52');
/*!40000 ALTER TABLE `taggroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_run_log`
--

DROP TABLE IF EXISTS `task_run_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_run_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `task_name` varchar(80) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `start_time` datetime(6) DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `task_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '任务是否在前端显示,1:显示,2:不显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台任务执行日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_run_log`
--

LOCK TABLES `task_run_log` WRITE;
/*!40000 ALTER TABLE `task_run_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_run_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_schedule`
--

DROP TABLE IF EXISTS `task_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(45) DEFAULT NULL COMMENT '任务类型',
  `task_descript` varchar(45) DEFAULT NULL COMMENT '任务描述',
  `task_type` varchar(45) DEFAULT NULL COMMENT '任务类型',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间，为空表示立即启动',
  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间，为空表示没有结束时间',
  `interval_minutes` float DEFAULT NULL COMMENT '任务运行间隔时间,小于等于0则只运行1次',
  `schedule` varchar(45) DEFAULT NULL COMMENT '执行时间安排cron table,如果为空，表示只运行1次',
  `service_name` varchar(512) DEFAULT NULL COMMENT '定时执行的service类名',
  `task_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '任务状态,0:可运行,1:不可运行',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:正常,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `campaign_head_id` int(11) DEFAULT NULL COMMENT '关联campagin_head表的id',
  `campaign_item_id` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2678 DEFAULT CHARSET=utf8 COMMENT='任务编排表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_schedule`
--

LOCK TABLES `task_schedule` WRITE;
/*!40000 ALTER TABLE `task_schedule` DISABLE KEYS */;
INSERT INTO `task_schedule` VALUES (1082,'上传客户标签同步任务',NULL,NULL,NULL,NULL,1,NULL,'originalDataCustomTagScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1083,'上传埋点统计同步任务',NULL,NULL,NULL,NULL,1,NULL,'originalDataArchPointScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1084,'上传用户登录同步任务',NULL,NULL,NULL,NULL,1,NULL,'originalDataLoginScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1085,'上传会员信息同步任务',NULL,NULL,NULL,NULL,1,NULL,'originalDataMemberScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1086,'上传支付记录同步任务',NULL,NULL,NULL,NULL,1,NULL,'originalDataPaymentScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1087,'上传人口属性同步任务',NULL,NULL,NULL,NULL,1,NULL,'originalDataPopulationServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1088,'上传购物记录同步任务',NULL,NULL,NULL,NULL,1,NULL,'originalDataShoppingScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1089,'dataPartySyncTaskServiceImpl',NULL,NULL,NULL,NULL,1,NULL,'dataPartySyncTaskServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(1090,'dataPartySyncMongoTaskServiceImpl',NULL,NULL,NULL,NULL,1,NULL,'dataPartySyncMongoTaskServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(1091,'WechatMemberScheduleToPopulationServiceImpl',NULL,NULL,NULL,NULL,1,NULL,'WechatMemberScheduleToPopulationServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1092,'ImgtextAssetSyncServiceImplTask',NULL,NULL,NULL,NULL,1,'','ImgtextAssetSyncServiceImpl',1,1,NULL,'2016-09-05 09:36:08',NULL,NULL),(1098,'获取个人号列表任务启动',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetH5PersonalListImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1099,'GetPersonContactsListTask',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetPersonContactsList',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1100,'GetH5PersonalGroupListImplTask',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetH5PersonalGroupListImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1101,'WechatToWechatAssetSyncServiceImplTask',NULL,NULL,NULL,NULL,1,'','WechatToWechatAssetSyncServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1102,'GetH5PubListServiceImplTask',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetH5PubListServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1103,'GetPubFansListServiceImplTask',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetPubFansListServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1232,'DataPartyTagSyncMongoTaskImpl',NULL,NULL,NULL,NULL,2,NULL,'DataPartyTagSyncMongoTaskImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(1233,'DataSegmentSyncTaskServiceImpl',NULL,NULL,NULL,NULL,1,NULL,'DataSegmentSyncTaskServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1234,'DataSegmentClearTaskServiceImpl','每天凌晨1点清除mongodb的segment数据',NULL,NULL,NULL,NULL,'0 0 1 ? * *','DataSegmentClearTaskServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1304,'AudienceCountTaskImpl',NULL,NULL,NULL,NULL,1,NULL,'AudienceCountTaskImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1449,'CustomTagAudienceCountTask',NULL,NULL,NULL,NULL,1,NULL,'CustomTagAudienceCountTask',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1727,'上传文件自定义标签同步任务',NULL,NULL,NULL,NULL,1,NULL,'CustomTagMappingSyncTaskServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(2491,'缓存每天的公众号，订阅号的人数，个人号的好友数',NULL,NULL,'2016-07-26 00:00:00',NULL,1440,NULL,'CacheWechatMemberCountByTypeServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(2500,'consumptionLastTimeTask',NULL,NULL,NULL,NULL,1,'','consumptionLastTimeTagImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(2544,'TagDataIsShoppingUserServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 1/8 * * * *','TagDataIsShoppingUserServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2546,'TagDataLastTransTimeServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 2/8 * * * *','TagDataIsShoppingUserServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2547,'TagDataShoppingDataStatusServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 3/8 * * * *','TagDataShoppingDataStatusServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2548,'TagDataSingleMonthShoppingCountServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 4/8 * * * *','TagDataSingleMonthShoppingCountServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2549,'TagDataTotalIncomeAmountServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 5/8 * * * *','TagDataTotalIncomeAmountServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2550,'TagDataTotalShoppingCountServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 6/8 * * * *','TagDataTotalShoppingCountServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2551,'TagDataWeimobServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 7/8 * * * *','TagDataWeimobServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2552,'TagDataAverageIncomeAmountServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 8/8 * * * *','TagDataAverageIncomeAmountServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2602,NULL,NULL,NULL,'2016-08-15 14:56:00','2016-08-22 14:56:00',NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-08-15 14:57:55','2016-08-22 06:56:30',4,'1471244201145'),(2603,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-08-15 14:57:55','2016-08-22 06:56:30',4,'1471244203688'),(2604,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSaveAudienceTask',1,1,'2016-08-15 14:57:55','2016-08-22 06:56:30',4,'1471244206985'),(2617,'campaignActionPubWechatSendH5Task',NULL,NULL,NULL,NULL,1,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-08-15 14:59:43','2016-09-05 09:39:48',5,'1471244293612'),(2618,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSaveAudienceTask',1,1,'2016-08-15 14:59:43','2016-08-22 06:58:30',5,'1471244301610'),(2619,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-08-15 14:59:43','2016-08-22 06:58:30',5,'1471244304672'),(2620,NULL,NULL,NULL,'2016-08-15 14:58:00','2016-08-22 14:58:00',NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-08-15 14:59:43','2016-08-22 06:58:30',5,'1471244331664'),(2624,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-08-17 13:38:37','2016-08-26 12:57:09',10,'1471412145831'),(2625,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSaveAudienceTask',1,1,'2016-08-17 13:38:37','2016-08-26 12:57:09',10,'1471412195502'),(2626,NULL,NULL,NULL,NULL,NULL,1,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-08-17 13:38:37','2016-09-05 09:43:44',10,'1471412225493'),(2640,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-09-07 11:22:13','2016-09-07 03:22:16',1,'1473216720873'),(2641,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-09-07 11:22:13','2016-09-07 03:22:16',1,'1473218429180'),(2642,NULL,NULL,NULL,'2016-09-07 11:20:00','2016-09-14 11:20:00',NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-09-07 11:22:13','2016-09-07 03:22:13',1,'1473218443596'),(2643,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignAudienceTargetTask',1,1,'2016-09-08 18:17:53','2016-09-12 06:25:42',2,'1473329857332'),(2644,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-09-08 18:22:22','2016-09-08 10:22:24',4,'1473330142260'),(2645,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignAudienceTargetTask',1,1,'2016-09-09 11:13:41','2016-09-12 06:25:43',6,'1473390819119'),(2646,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-09-09 11:14:26','2016-09-12 06:25:44',7,'1473390865451'),(2647,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatReadTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:45',8,'1473390886031'),(2648,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatForwardTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:47',8,'1473390887262'),(2649,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatSubscribeTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:48',8,'1473390888027'),(2650,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatPrivFriendTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:49',8,'1473390888857'),(2651,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignDecisionTagTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:50',8,'1473390889760'),(2652,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPrvWechatSendInfoTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:50',8,'1473390904360'),(2653,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionWaitTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:51',8,'1473390905602'),(2654,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSaveAudienceTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:52',8,'1473390907313'),(2655,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSetTagTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:53',8,'1473390908559'),(2656,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:54',8,'1473390909888'),(2657,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionWechatSendH5Task',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:54',8,'1473390910945'),(2658,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPrvWechatSendInfoTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:55',8,'1473390912161'),(2660,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-09-09 11:40:38','2016-09-12 06:25:56',11,'1473392117431'),(2661,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignDecisionWechatForwardTask',1,1,'2016-09-09 11:40:38','2016-09-12 06:25:57',11,'1473392431784'),(2662,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignDecisionWechatReadTask',1,1,'2016-09-09 11:40:38','2016-09-12 06:25:58',11,'1473392435482'),(2663,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-09-09 11:40:38','2016-09-12 06:25:59',11,'1473392439809'),(2665,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatForwardTask',1,1,'2016-09-09 14:36:27','2016-09-09 06:36:27',12,'1473402894135'),(2666,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignAudienceTargetTask',1,1,'2016-09-09 14:36:27','2016-09-09 06:36:27',12,'1473402926413'),(2667,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-09-09 14:36:27','2016-09-09 06:36:27',12,'1473402971769'),(2668,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,0,'2016-09-13 09:06:12','2016-09-13 01:06:12',13,'1473728763367'),(2669,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignAudienceTargetTask',1,0,'2016-09-13 09:06:45','2016-09-13 01:06:45',14,'1473728803121'),(2670,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,0,'2016-09-13 10:16:16','2016-09-13 02:16:16',15,'1473732974467'),(2672,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,0,'2016-09-13 10:19:22','2016-09-13 02:19:22',16,'1473733090565'),(2676,NULL,NULL,NULL,'2016-09-13 00:00:00','2016-09-20 00:00:00',NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-09-13 10:51:50','2016-09-13 02:51:51',17,'1473735070428'),(2677,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-09-13 10:51:50','2016-09-13 02:51:52',17,'1473735078417');
/*!40000 ALTER TABLE `task_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_app`
--

DROP TABLE IF EXISTS `tb_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_app` (
  `id` varchar(32) NOT NULL,
  `app_name` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `port` varchar(255) DEFAULT NULL,
  `app_uri` longtext,
  `remark` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `use_yn` varchar(1) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `eicon` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `logout_url` varchar(255) DEFAULT NULL,
  `loginNotifyUrl` longtext,
  `loginFirstSendUrl` longtext,
  `maintenance` varchar(1) DEFAULT NULL,
  `requestStatus` varchar(1) DEFAULT NULL,
  `mt_info` varchar(255) DEFAULT NULL,
  `icon_url` longblob,
  `icon_name` varchar(50) DEFAULT NULL,
  `effect_iconCls` longblob,
  `effect_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_app`
--

LOCK TABLES `tb_app` WRITE;
/*!40000 ALTER TABLE `tb_app` DISABLE KEYS */;
INSERT INTO `tb_app` VALUES ('ff80808155fca4490155fcb0c1110002','DMP',NULL,NULL,NULL,'',1,NULL,'2016-07-18 14:28:10',NULL,'002001',NULL,NULL,NULL,'','DMP','',NULL,'0','0',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbb7f990016','市场营销',NULL,NULL,NULL,'',2,NULL,'2016-07-18 14:39:54',NULL,'002001',NULL,NULL,NULL,'','市场营销','',NULL,'0','0',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbc0b590018','数据洞察',NULL,NULL,NULL,'',3,NULL,'2016-07-18 14:40:30',NULL,'002001',NULL,NULL,NULL,'','数据洞察','',NULL,'0','0',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_blacklist_type`
--

DROP TABLE IF EXISTS `tb_blacklist_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_blacklist_type` (
  `id` varchar(32) NOT NULL,
  `black_type` varchar(50) NOT NULL,
  `login_prompt_message` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_blacklist_type`
--

LOCK TABLES `tb_blacklist_type` WRITE;
/*!40000 ALTER TABLE `tb_blacklist_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_blacklist_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ci_element`
--

DROP TABLE IF EXISTS `tb_ci_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_ci_element` (
  `id` varchar(32) NOT NULL,
  `element_key` varchar(50) NOT NULL,
  `element_name` varchar(50) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `element_value` varchar(50) DEFAULT NULL,
  `element_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ci_element`
--

LOCK TABLES `tb_ci_element` WRITE;
/*!40000 ALTER TABLE `tb_ci_element` DISABLE KEYS */;
INSERT INTO `tb_ci_element` VALUES ('007002','portal_toggle_show_flg','一级菜单选项卡是否显示说明信息','false:不显示 true:显示','true','boolean'),('007003','portal_one_tab_show_flg','一级菜单选项卡是否显示','false:显示两层 true:显示一层','false','boolean'),('007004','portal_page_show_type_flg','叶子节点选项卡是否显示','false:不使用 true:使用Tab','true','boolean'),('007005','portal_redis_deploy_flg','Portal系统是否使用redis缓存','false:不使用 true:使用  应用模块如果没有redis缓存，redis缓存即使设置为true也无法使用。','false','boolean'),('007007','portal_tree_view_app_flg','Portal左侧导航菜单是否显示一级菜单','false:不显示 true:显示  如果应用模块只有一个层级，不建议显示在导航中显示一级菜单，建议直接显示一级菜单目录下的应用节点。','true','boolean'),('007008','portal_login_send_request_flg','Portal登陆后是否默认发送APP请求','false:不使用 true:使用','true','boolean'),('007009','portal_login_send_request_way','Portal登陆后发送请求方式','false:JS端发送请求 true:JAVA端发送请求   注（portal_login_send_request_flg为true才生效）','false','boolean'),('007010','portal_foot_info_flg','Portal页脚是否显示','false:不使用 true:使用','true','boolean'),('007011','portal_users_registration_protocol_flg','Portal用户注册协议是否显示','false:不使用 true:使用','true','boolean'),('007012','portal_company_registration_flg','Portal企业注册是否使用','false:不使用 true:使用','true','boolean'),('007013','portal_personal_registration_flg','Portal个人注册是否使用','false:不使用 true:使用注（h5没有个人注册，h5使用为false）','true','boolean'),('007014','portal_login_way_with_colon_flg','Portal企业管理员登录方式是否加admin','false:不使用 true:使用 注（h5登录方式不需要冒号，h5使用为false）','false','boolean'),('007015','portal_company_registration_qy_flg','企业注册界面是否有企业二字','false:不显示 true:显示注（h5里企业注册指个人注册，h5使用为false）','false','boolean'),('007016','portal_tab_refresh_button_flg','Portal的tab标签是否有刷新按钮','false:不显示 true:显示注（默认为false，h5使用为true）','false','boolean'),('007017','portal_login_frist_send_url','Portal首次登陆是否发送请求','false:不发送 true:发送','false','boolean'),('007018','portal_black_list_flg','Portal黑名单是否使用','false:不使用 true:使用','false','boolean'),('007019','portal_data_mode_flg','Portal数据模型是否使用','false:不使用 true:使用','false','boolean'),('007020','portal_h5plus_create_flg','H5PLUS商城创建用户是否使用','false:不使用 true:使用','false','boolean');
/*!40000 ALTER TABLE `tb_ci_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company`
--

DROP TABLE IF EXISTS `tb_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company` (
  `id` varchar(255) NOT NULL,
  `company_id` int(11) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_sname` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `detail_address` varchar(255) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `regist_time` datetime DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_insertuser` varchar(255) DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `rx_updateuser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `companySname` (`company_sname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company`
--

LOCK TABLES `tb_company` WRITE;
/*!40000 ALTER TABLE `tb_company` DISABLE KEYS */;
INSERT INTO `tb_company` VALUES ('004001',1,'个人用户','personal',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company_association`
--

DROP TABLE IF EXISTS `tb_company_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company_association` (
  `id` varchar(32) NOT NULL,
  `company_id` varchar(50) NOT NULL,
  `column_code` varchar(50) DEFAULT NULL,
  `column_value` varchar(50) DEFAULT NULL,
  `column_length` int(11) DEFAULT NULL,
  `column_name` varchar(50) DEFAULT NULL,
  `column_type` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company_association`
--

LOCK TABLES `tb_company_association` WRITE;
/*!40000 ALTER TABLE `tb_company_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_company_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company_blacklist`
--

DROP TABLE IF EXISTS `tb_company_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company_blacklist` (
  `id` varchar(32) NOT NULL,
  `company_id` varchar(50) NOT NULL,
  `blacktype_id` varchar(50) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company_blacklist`
--

LOCK TABLES `tb_company_blacklist` WRITE;
/*!40000 ALTER TABLE `tb_company_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_company_blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company_function`
--

DROP TABLE IF EXISTS `tb_company_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company_function` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `portal` varchar(32) DEFAULT NULL,
  `use_yn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_nt6nucw4yi0biba4tj0m0tiih` (`company`),
  KEY `FK_5e8f8riu71pmlti6fbleh1ibu` (`portal`),
  CONSTRAINT `FK_5e8f8riu71pmlti6fbleh1ibu` FOREIGN KEY (`portal`) REFERENCES `tb_portal` (`id`),
  CONSTRAINT `FK_nt6nucw4yi0biba4tj0m0tiih` FOREIGN KEY (`company`) REFERENCES `tb_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company_function`
--

LOCK TABLES `tb_company_function` WRITE;
/*!40000 ALTER TABLE `tb_company_function` DISABLE KEYS */;
INSERT INTO `tb_company_function` VALUES ('07b411aa75894e3d847cba5aeae2ff21',NULL,'PORTAL',1,'003001','2016-08-05 15:00:57','2016-08-05 15:00:57','2016-08-05 15:00:57','004001','003001',NULL),('0a4dd6ee527d46038711a91e93ebe584','2306418c3cbb495094c80efd246575db','PAGE',3,'ff80808155fca4490155fcb2e888000a',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('1a27dbd5095f49c1a41a8c244f7ed3eb','813efce1e5054d739ab1fb14101a186e','PAGE',3,'ff80808155fca4490155fcbdd1c90022',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('20d7413afbb844638416a23af5607cf8','2f874e0be334417796f5a511873bcc09','PAGE',4,'ff80808155fca4490155fcc0f48a0032',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('2306418c3cbb495094c80efd246575db','07b411aa75894e3d847cba5aeae2ff21','APP',1,'ff80808155fca4490155fcb0c1110002',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('2a665bdc6d26437f9aebe776508c1a02','07b411aa75894e3d847cba5aeae2ff21','APP',1,'ff80808155fca4490155fcbc0b590018',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('2f874e0be334417796f5a511873bcc09','2a665bdc6d26437f9aebe776508c1a02','PAGE',2,'ff80808155fca4490155fcc0610a002e',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('45196e2f843b43e59a29f6702882fc7c','2f874e0be334417796f5a511873bcc09','PAGE',3,'ff80808155fca4490155fcc09e220030',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('4a9fb8bcb8c34dfebca664a21cbb8460','a620055a172e4066b943975f6f263d03','PAGE',11,'ff80808155fca4490155fcbd81b40020',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('5278bd909ac048ed97ccc830c8c587b2','0a4dd6ee527d46038711a91e93ebe584','PAGE',8,'ff80808155fca4490155fcb8f1a4000e',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('53a93d9f53974cba841be5a9e454afd9','d54332c919a74cd4a440f1f69f148e43','PAGE',10,'ff80808155fca4490155fcb27d7b0008',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('53d7280b78d749889db337b8c2a91286','a620055a172e4066b943975f6f263d03','PAGE',10,'ff80808155fca4490155fcbd28ac001e',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('5e7fb883761948af92ffa2e42d7240d9','2306418c3cbb495094c80efd246575db','PAGE',4,'ff80808155fca4490155fcb989ff0010',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('5ea382f515ef41f287b3344280ea9177','70a035a27ab64ef3827a5e91861e983a','PAGE',5,'ff80808155fca4490155fcbf4c61002a',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('70a035a27ab64ef3827a5e91861e983a','813efce1e5054d739ab1fb14101a186e','PAGE',4,'ff80808155fca4490155fcbefde50028',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('813efce1e5054d739ab1fb14101a186e','07b411aa75894e3d847cba5aeae2ff21','APP',1,'ff80808155fca4490155fcbb7f990016',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('91fd75eeeebb42c19b46d708bdd10642','1a27dbd5095f49c1a41a8c244f7ed3eb','PAGE',8,'ff80808155fca4490155fcbea6930026',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('94c5eeb8833441ebb1617ea111c35fd8','70a035a27ab64ef3827a5e91861e983a','PAGE',6,'ff80808155fca4490155fcbfc413002c',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('a620055a172e4066b943975f6f263d03','813efce1e5054d739ab1fb14101a186e','PAGE',2,'ff80808155fca4490155fcbc78c5001a',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('a8247629f59e418da5211ea300e9516b','1a27dbd5095f49c1a41a8c244f7ed3eb','PAGE',7,'ff80808155fca4490155fcbe29200024',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('aea3723a0cea4aa5a94f24c2a2104ebc','a620055a172e4066b943975f6f263d03','PAGE',9,'ff80808155fca4490155fcbcb44b001c',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('afba97f417d948ada9d5e66886924794','5e7fb883761948af92ffa2e42d7240d9','PAGE',6,'ff80808155fca4490155fcba83ae0014',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('bb26794800ef4d5b95fb3c4176f64d9f','0a4dd6ee527d46038711a91e93ebe584','PAGE',7,'ff80808155fca4490155fcb88cef000c',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('d54332c919a74cd4a440f1f69f148e43','2306418c3cbb495094c80efd246575db','PAGE',2,'ff80808155fca4490155fcb1b1d40004',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('e88de6b944624f17a5982ba79c8d2a54','d54332c919a74cd4a440f1f69f148e43','PAGE',9,'ff80808155fca4490155fcb1e9230006',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('f5c5550cbba34918b085f85941ddf1ff','5e7fb883761948af92ffa2e42d7240d9','PAGE',5,'ff80808155fca4490155fcba1edf0012',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL);
/*!40000 ALTER TABLE `tb_company_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_component`
--

DROP TABLE IF EXISTS `tb_component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_component` (
  `id` varchar(32) NOT NULL,
  `element_name` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `element_uri` varchar(255) DEFAULT NULL,
  `element_authority` varchar(2) DEFAULT '0',
  `element_type_id` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ml34q179ejba9uyabyms8a5ra` (`element_type_id`),
  CONSTRAINT `FK_ml34q179ejba9uyabyms8a5ra` FOREIGN KEY (`element_type_id`) REFERENCES `tb_element_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_component`
--

LOCK TABLES `tb_component` WRITE;
/*!40000 ALTER TABLE `tb_component` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_contact`
--

DROP TABLE IF EXISTS `tb_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_contact` (
  `id` varchar(32) NOT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `email_addr` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_contact`
--

LOCK TABLES `tb_contact` WRITE;
/*!40000 ALTER TABLE `tb_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_deploy_server_info`
--

DROP TABLE IF EXISTS `tb_deploy_server_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_deploy_server_info` (
  `id` varchar(255) NOT NULL,
  `uri` varchar(50) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_deploy_server_info`
--

LOCK TABLES `tb_deploy_server_info` WRITE;
/*!40000 ALTER TABLE `tb_deploy_server_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_deploy_server_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_element`
--

DROP TABLE IF EXISTS `tb_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_element` (
  `id` varchar(32) NOT NULL,
  `element_name` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `element_uri` varchar(255) DEFAULT NULL,
  `element_authority` varchar(2) DEFAULT '0',
  `remark` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `element_type_id` varchar(255) DEFAULT NULL,
  `component` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1f9p5st816cqcpntgakeiqks2` (`element_type_id`),
  KEY `FK_1mv3c4jpix6ljdlvieh3rv1vh` (`component`),
  CONSTRAINT `FK_1f9p5st816cqcpntgakeiqks2` FOREIGN KEY (`element_type_id`) REFERENCES `tb_element_type` (`id`),
  CONSTRAINT `FK_1mv3c4jpix6ljdlvieh3rv1vh` FOREIGN KEY (`component`) REFERENCES `tb_component` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_element`
--

LOCK TABLES `tb_element` WRITE;
/*!40000 ALTER TABLE `tb_element` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_element_type`
--

DROP TABLE IF EXISTS `tb_element_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_element_type` (
  `id` varchar(255) NOT NULL,
  `element_label` varchar(255) DEFAULT NULL,
  `element_value` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_element_type`
--

LOCK TABLES `tb_element_type` WRITE;
/*!40000 ALTER TABLE `tb_element_type` DISABLE KEYS */;
INSERT INTO `tb_element_type` VALUES ('008001','Checkbox','1',NULL,NULL,NULL,NULL),('008002','Radio Button','2',NULL,NULL,NULL,NULL),('008003','Text Field','3',NULL,NULL,NULL,NULL),('008004','Text Area','4',NULL,NULL,NULL,NULL),('008005','Droplist','5',NULL,NULL,NULL,NULL),('008006','List Box','6',NULL,NULL,NULL,NULL),('008007','HTML Button','7',NULL,NULL,NULL,NULL),('008008','模板','8',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_element_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_function`
--

DROP TABLE IF EXISTS `tb_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_function` (
  `id` varchar(32) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `app_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qgw2hcxtk82q9c4po8k56905m` (`app_id`),
  CONSTRAINT `FK_qgw2hcxtk82q9c4po8k56905m` FOREIGN KEY (`app_id`) REFERENCES `tb_app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_function`
--

LOCK TABLES `tb_function` WRITE;
/*!40000 ALTER TABLE `tb_function` DISABLE KEYS */;
INSERT INTO `tb_function` VALUES ('ff80808155fca4490155fcb0c13a0003',NULL,'APP',1,'ff80808155fca4490155fcb0c1110002',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb1b1db0005','ff80808155fca4490155fcb0c13a0003','PAGE',2,'ff80808155fca4490155fcb1b1d40004',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb1e9320007','ff80808155fca4490155fcb1b1db0005','PAGE',9,'ff80808155fca4490155fcb1e9230006',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb27d810009','ff80808155fca4490155fcb1b1db0005','PAGE',10,'ff80808155fca4490155fcb27d7b0008',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb2e8c3000b','ff80808155fca4490155fcb0c13a0003','PAGE',3,'ff80808155fca4490155fcb2e888000a',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb88cff000d','ff80808155fca4490155fcb2e8c3000b','PAGE',7,'ff80808155fca4490155fcb88cef000c',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb8f1aa000f','ff80808155fca4490155fcb2e8c3000b','PAGE',8,'ff80808155fca4490155fcb8f1a4000e',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb98a040011','ff80808155fca4490155fcb0c13a0003','PAGE',4,'ff80808155fca4490155fcb989ff0010',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcba1ee50013','ff80808155fca4490155fcb98a040011','PAGE',5,'ff80808155fca4490155fcba1edf0012',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcba83b50015','ff80808155fca4490155fcb98a040011','PAGE',6,'ff80808155fca4490155fcba83ae0014',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcbb7f9d0017',NULL,'APP',1,'ff80808155fca4490155fcbb7f990016',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbc0b5e0019',NULL,'APP',1,'ff80808155fca4490155fcbc0b590018',NULL,NULL,'ff80808155fca4490155fcbc0b590018'),('ff80808155fca4490155fcbc78cb001b','ff80808155fca4490155fcbb7f9d0017','PAGE',2,'ff80808155fca4490155fcbc78c5001a',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbcb451001d','ff80808155fca4490155fcbc78cb001b','PAGE',9,'ff80808155fca4490155fcbcb44b001c',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbd28b1001f','ff80808155fca4490155fcbc78cb001b','PAGE',10,'ff80808155fca4490155fcbd28ac001e',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbd81ba0021','ff80808155fca4490155fcbc78cb001b','PAGE',11,'ff80808155fca4490155fcbd81b40020',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbdd1ce0023','ff80808155fca4490155fcbb7f9d0017','PAGE',3,'ff80808155fca4490155fcbdd1c90022',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbe29270025','ff80808155fca4490155fcbdd1ce0023','PAGE',7,'ff80808155fca4490155fcbe29200024',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbea6980027','ff80808155fca4490155fcbdd1ce0023','PAGE',8,'ff80808155fca4490155fcbea6930026',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbefdeb0029','ff80808155fca4490155fcbb7f9d0017','PAGE',4,'ff80808155fca4490155fcbefde50028',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbf4c67002b','ff80808155fca4490155fcbefdeb0029','PAGE',5,'ff80808155fca4490155fcbf4c61002a',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbfc417002d','ff80808155fca4490155fcbefdeb0029','PAGE',6,'ff80808155fca4490155fcbfc413002c',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcc06110002f','ff80808155fca4490155fcbc0b5e0019','PAGE',2,'ff80808155fca4490155fcc0610a002e',NULL,NULL,'ff80808155fca4490155fcbc0b590018'),('ff80808155fca4490155fcc09e270031','ff80808155fca4490155fcc06110002f','PAGE',3,'ff80808155fca4490155fcc09e220030',NULL,NULL,'ff80808155fca4490155fcbc0b590018'),('ff80808155fca4490155fcc0f48e0033','ff80808155fca4490155fcc06110002f','PAGE',4,'ff80808155fca4490155fcc0f48a0032',NULL,NULL,'ff80808155fca4490155fcbc0b590018');
/*!40000 ALTER TABLE `tb_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_industry`
--

DROP TABLE IF EXISTS `tb_industry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_industry` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_industry`
--

LOCK TABLES `tb_industry` WRITE;
/*!40000 ALTER TABLE `tb_industry` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_industry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_job`
--

DROP TABLE IF EXISTS `tb_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_job` (
  `id` varchar(32) NOT NULL,
  `job_id` int(11) DEFAULT NULL,
  `job_name` varchar(50) NOT NULL,
  `team_id` varchar(32) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `rx_inserttime` datetime NOT NULL,
  `rx_updatetime` datetime NOT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ncaa48o1c78fm36bp9007qakg` (`team_id`),
  CONSTRAINT `FK_ncaa48o1c78fm36bp9007qakg` FOREIGN KEY (`team_id`) REFERENCES `tb_team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_job`
--

LOCK TABLES `tb_job` WRITE;
/*!40000 ALTER TABLE `tb_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_job_user`
--

DROP TABLE IF EXISTS `tb_job_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_job_user` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `job_id` varchar(32) DEFAULT NULL,
  `master_job_yn` varchar(1) NOT NULL,
  `rx_inserttime` datetime NOT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cwil3ukuxmbr60u5c17ch1w1s` (`user_id`),
  KEY `FK_is5aweliaumeeg541609ccmu9` (`job_id`),
  CONSTRAINT `FK_cwil3ukuxmbr60u5c17ch1w1s` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK_is5aweliaumeeg541609ccmu9` FOREIGN KEY (`job_id`) REFERENCES `tb_job` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_job_user`
--

LOCK TABLES `tb_job_user` WRITE;
/*!40000 ALTER TABLE `tb_job_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_job_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_login_page_manage_info`
--

DROP TABLE IF EXISTS `tb_login_page_manage_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_login_page_manage_info` (
  `id` varchar(255) NOT NULL,
  `login_url` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `logo_filename` varchar(50) DEFAULT NULL,
  `logo_image` longblob,
  `welcome_filename` varchar(50) DEFAULT NULL,
  `welcome_image` longblob,
  `footinfo` longtext,
  `insert_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `logo_bg_img` longblob,
  `logo_bg_imgName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_login_page_manage_info`
--

LOCK TABLES `tb_login_page_manage_info` WRITE;
/*!40000 ALTER TABLE `tb_login_page_manage_info` DISABLE KEYS */;
INSERT INTO `tb_login_page_manage_info` VALUES ('2c9485a8504a899401504a8aa2e40000','index.action','portal默认URL',NULL,NULL,NULL,NULL,NULL,'2014-09-22 00:00:00','2014-09-22 00:00:00','002001','002001',NULL,NULL);
/*!40000 ALTER TABLE `tb_login_page_manage_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_module`
--

DROP TABLE IF EXISTS `tb_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_module` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `rx_insert_time` datetime DEFAULT NULL,
  `rx_update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_module`
--

LOCK TABLES `tb_module` WRITE;
/*!40000 ALTER TABLE `tb_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_operate_history`
--

DROP TABLE IF EXISTS `tb_operate_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_operate_history` (
  `id` varchar(32) NOT NULL,
  `operate_module_id` varchar(32) DEFAULT NULL,
  `operate_module` varchar(32) DEFAULT NULL,
  `operate_type` varchar(32) DEFAULT NULL,
  `operate_time` datetime DEFAULT NULL,
  `data_id_info` longtext,
  `operate_user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_operate_history`
--

LOCK TABLES `tb_operate_history` WRITE;
/*!40000 ALTER TABLE `tb_operate_history` DISABLE KEYS */;
INSERT INTO `tb_operate_history` VALUES ('ff80808155fca4490155fcc2ffd40034','003001','PORTAL','ADD','2016-07-18 14:48:05','ff80808155fca4490155fcb0c1110002,ff80808155fca4490155fcb1b1d40004,ff80808155fca4490155fcb1e9230006,ff80808155fca4490155fcb27d7b0008,ff80808155fca4490155fcb2e888000a,ff80808155fca4490155fcb88cef000c,ff80808155fca4490155fcb8f1a4000e,ff80808155fca4490155fcb989ff0010,ff80808155fca4490155fcba1edf0012,ff80808155fca4490155fcba83ae0014,ff80808155fca4490155fcbb7f990016,ff80808155fca4490155fcbc78c5001a,ff80808155fca4490155fcbcb44b001c,ff80808155fca4490155fcbd28ac001e,ff80808155fca4490155fcbd81b40020,ff80808155fca4490155fcbdd1c90022,ff80808155fca4490155fcbe29200024,ff80808155fca4490155fcbea6930026,ff80808155fca4490155fcbefde50028,ff80808155fca4490155fcbf4c61002a,ff80808155fca4490155fcbfc413002c,ff80808155fca4490155fcbc0b590018,ff80808155fca4490155fcc0610a002e,ff80808155fca4490155fcc09e220030,ff80808155fca4490155fcc0f48a0032',NULL);
/*!40000 ALTER TABLE `tb_operate_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_page`
--

DROP TABLE IF EXISTS `tb_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_page` (
  `id` varchar(32) NOT NULL,
  `page_name` varchar(255) DEFAULT NULL,
  `page_uri` varchar(255) DEFAULT NULL,
  `data_level_id` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `eicon` varchar(255) DEFAULT NULL,
  `maintenance` varchar(1) DEFAULT NULL,
  `requestStatus` varchar(1) DEFAULT NULL,
  `mt_info` varchar(255) DEFAULT NULL,
  `icon_url` longblob,
  `icon_name` varchar(50) DEFAULT NULL,
  `effect_iconCls` longblob,
  `effect_name` varchar(50) DEFAULT NULL,
  `is_open_new` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_page`
--

LOCK TABLES `tb_page` WRITE;
/*!40000 ALTER TABLE `tb_page` DISABLE KEYS */;
INSERT INTO `tb_page` VALUES ('ff80808155fca4490155fcb1b1d40004','数据接入','',NULL,NULL,'2016-07-18 14:29:11',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb1e9230006','文件接入','http://mktpro.rc.dataengine.com/html/data-access/file.html',NULL,NULL,'2016-07-18 14:29:25',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb27d7b0008','微信接入','http://mktpro.rc.dataengine.com/html/data-access/weixin.html',NULL,NULL,'2016-07-18 14:30:03',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb2e888000a','数据管理','',NULL,NULL,'2016-07-18 14:30:31',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb88cef000c','数据质量报告','http://mktpro.rc.dataengine.com/html/data-supervise/quality-report.html',NULL,NULL,'2016-07-18 14:36:41',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb8f1a4000e','主数据管理','http://mktpro.rc.dataengine.com/html/data-supervise/master-data.html',NULL,NULL,'2016-07-18 14:37:06',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb989ff0010','标签管理','',NULL,NULL,'2016-07-18 14:37:45',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcba1edf0012','系统标签','http://mktpro.rc.dataengine.com/html/label-management/system.html',NULL,NULL,'2016-07-18 14:38:23',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcba83ae0014','自定义标签','http://mktpro.rc.dataengine.com/html/label-management/custom.html',NULL,NULL,'2016-07-18 14:38:49',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbc78c5001a','受众管理','',NULL,NULL,'2016-07-18 14:40:58',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbcb44b001c','细分管理','http://mktpro.rc.dataengine.com/html/audience/manage.html',NULL,NULL,'2016-07-18 14:41:13',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbd28ac001e','受众细分','http://mktpro.rc.dataengine.com/html/audience/segment.html',NULL,NULL,'2016-07-18 14:41:43',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbd81b40020','人群管理','http://mktpro.rc.dataengine.com/html/audience/crowd.html',NULL,NULL,'2016-07-18 14:42:05',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbdd1c90022','营销活动','',NULL,NULL,'2016-07-18 14:42:26',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbe29200024','活动编排','http://mktpro.rc.dataengine.com/html/activity/plan.html',NULL,NULL,'2016-07-18 14:42:48',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbea6930026','活动管理','http://mktpro.rc.dataengine.com/html/activity/supervise.html',NULL,NULL,'2016-07-18 14:43:20',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbefde50028','数字资产','',NULL,NULL,'2016-07-18 14:43:43',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbf4c61002a','图文资产','http://mktpro.rc.dataengine.com/html/asset/graphic.html',NULL,NULL,'2016-07-18 14:44:03',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbfc413002c','微信资产','http://mktpro.rc.dataengine.com/html/asset/weixin.html',NULL,NULL,'2016-07-18 14:44:33',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcc0610a002e','数据洞察','',NULL,NULL,'2016-07-18 14:45:14',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcc09e220030','综合分析','http://mktpro.rc.dataengine.com/html/data-lnsight/comprehensive-analysis.html',NULL,NULL,'2016-07-18 14:45:29',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcc0f48a0032','定制报表','http://mktpro.rc.dataengine.com/html/data-lnsight/custom-report.html',NULL,NULL,'2016-07-18 14:45:51',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_page_data_level`
--

DROP TABLE IF EXISTS `tb_page_data_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_page_data_level` (
  `id` varchar(32) NOT NULL,
  `data_level_name` varchar(100) NOT NULL,
  `use_yn` varchar(1) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `rx_inserttime` datetime NOT NULL,
  `rx_updatetime` datetime NOT NULL,
  `company_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_page_data_level`
--

LOCK TABLES `tb_page_data_level` WRITE;
/*!40000 ALTER TABLE `tb_page_data_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_page_data_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_portal`
--

DROP TABLE IF EXISTS `tb_portal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_portal` (
  `id` varchar(32) NOT NULL,
  `ssid` varchar(255) DEFAULT NULL,
  `portal_name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_portal`
--

LOCK TABLES `tb_portal` WRITE;
/*!40000 ALTER TABLE `tb_portal` DISABLE KEYS */;
INSERT INTO `tb_portal` VALUES ('003001','d39f769bea5c4a13a64e0ddd42b3c2f2','瑞雪','瑞雪','2014-09-22 00:00:00',NULL,'002001',NULL,NULL);
/*!40000 ALTER TABLE `tb_portal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_portal_function`
--

DROP TABLE IF EXISTS `tb_portal_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_portal_function` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `portal` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ieh0o2vnnlk41qlivo3w4s9h` (`portal`),
  CONSTRAINT `FK_ieh0o2vnnlk41qlivo3w4s9h` FOREIGN KEY (`portal`) REFERENCES `tb_portal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_portal_function`
--

LOCK TABLES `tb_portal_function` WRITE;
/*!40000 ALTER TABLE `tb_portal_function` DISABLE KEYS */;
INSERT INTO `tb_portal_function` VALUES ('0be3a358cbc94ebc826287090baf40e5','95e41bfd9bf64247b87d09dd00caeb69','PAGE',3,'ff80808155fca4490155fcc09e220030',NULL,NULL,'2016-08-05 15:00:57','003001'),('18431dd9aa3e43cfa9b6d630f098fbc3','52d052c6c08c4fcd85fb0a8160d772c7','APP',1,'ff80808155fca4490155fcbb7f990016',NULL,NULL,'2016-08-05 15:00:57','003001'),('23162ce577ea4fcf8c43fd2d88ed66ed','75660553e34e43e1bce07617a1c9c60d','PAGE',9,'ff80808155fca4490155fcbcb44b001c',NULL,NULL,'2016-08-05 15:00:57','003001'),('2b018dbdbdcd4cd09a55e726a4e3bffe','52d052c6c08c4fcd85fb0a8160d772c7','APP',1,'ff80808155fca4490155fcbc0b590018',NULL,NULL,'2016-08-05 15:00:57','003001'),('41cca3ffecdb4a54a1ce77a38a47d1ee','18431dd9aa3e43cfa9b6d630f098fbc3','PAGE',3,'ff80808155fca4490155fcbdd1c90022',NULL,NULL,'2016-08-05 15:00:57','003001'),('458a3fe3b5bb413faca3dfd5d26f9326','d3ebebca4ae24b50b30613354335a46d','PAGE',8,'ff80808155fca4490155fcb8f1a4000e',NULL,NULL,'2016-08-05 15:00:57','003001'),('4d47cacc5659443caf2a42009c93d17d','6d112ac4cc894ca199aa06bec74aae51','PAGE',10,'ff80808155fca4490155fcb27d7b0008',NULL,NULL,'2016-08-05 15:00:57','003001'),('52d052c6c08c4fcd85fb0a8160d772c7',NULL,'PORTAL',1,'003001',NULL,NULL,'2016-08-05 15:00:57','003001'),('5dbb35abaca447c3b9ea420528096439','d1a50df7fa214171ae0ab9cbaf582c6a','PAGE',6,'ff80808155fca4490155fcbfc413002c',NULL,NULL,'2016-08-05 15:00:57','003001'),('5fc7176d93f744b2babb9cbb8b00dde2','52d052c6c08c4fcd85fb0a8160d772c7','APP',1,'ff80808155fca4490155fcb0c1110002',NULL,NULL,'2016-08-05 15:00:57','003001'),('6d112ac4cc894ca199aa06bec74aae51','5fc7176d93f744b2babb9cbb8b00dde2','PAGE',2,'ff80808155fca4490155fcb1b1d40004',NULL,NULL,'2016-08-05 15:00:57','003001'),('749418a096b640a98e21d676d0e7c60b','d1a50df7fa214171ae0ab9cbaf582c6a','PAGE',5,'ff80808155fca4490155fcbf4c61002a',NULL,NULL,'2016-08-05 15:00:57','003001'),('75660553e34e43e1bce07617a1c9c60d','18431dd9aa3e43cfa9b6d630f098fbc3','PAGE',2,'ff80808155fca4490155fcbc78c5001a',NULL,NULL,'2016-08-05 15:00:57','003001'),('95e41bfd9bf64247b87d09dd00caeb69','2b018dbdbdcd4cd09a55e726a4e3bffe','PAGE',2,'ff80808155fca4490155fcc0610a002e',NULL,NULL,'2016-08-05 15:00:57','003001'),('a8c14010f2b149aea46a8c0c80bbd2bc','41cca3ffecdb4a54a1ce77a38a47d1ee','PAGE',8,'ff80808155fca4490155fcbea6930026',NULL,NULL,'2016-08-05 15:00:57','003001'),('b61b3d792f024574b3eb94e3b861a02e','75660553e34e43e1bce07617a1c9c60d','PAGE',10,'ff80808155fca4490155fcbd28ac001e',NULL,NULL,'2016-08-05 15:00:57','003001'),('cf2fcb1c9ea5463b8b1117e3650c22ea','d3ebebca4ae24b50b30613354335a46d','PAGE',7,'ff80808155fca4490155fcb88cef000c',NULL,NULL,'2016-08-05 15:00:57','003001'),('d1a50df7fa214171ae0ab9cbaf582c6a','18431dd9aa3e43cfa9b6d630f098fbc3','PAGE',4,'ff80808155fca4490155fcbefde50028',NULL,NULL,'2016-08-05 15:00:57','003001'),('d3ebebca4ae24b50b30613354335a46d','5fc7176d93f744b2babb9cbb8b00dde2','PAGE',3,'ff80808155fca4490155fcb2e888000a',NULL,NULL,'2016-08-05 15:00:57','003001'),('d639be0bab394ba5ba841d7a9efa6e92','ff27022bc93545128d7b3b57a799d3ff','PAGE',6,'ff80808155fca4490155fcba83ae0014',NULL,NULL,'2016-08-05 15:00:57','003001'),('dc92c7464cf74281b812d6b6feacb5bd','6d112ac4cc894ca199aa06bec74aae51','PAGE',9,'ff80808155fca4490155fcb1e9230006',NULL,NULL,'2016-08-05 15:00:57','003001'),('e808e4a54a784b22aede1fdd11d3bdce','75660553e34e43e1bce07617a1c9c60d','PAGE',11,'ff80808155fca4490155fcbd81b40020',NULL,NULL,'2016-08-05 15:00:57','003001'),('f6309ada530d4615b62d5f1c3d9ecf3c','95e41bfd9bf64247b87d09dd00caeb69','PAGE',4,'ff80808155fca4490155fcc0f48a0032',NULL,NULL,'2016-08-05 15:00:57','003001'),('f6466a3dc0fa43a8897332fae7eb3e76','41cca3ffecdb4a54a1ce77a38a47d1ee','PAGE',7,'ff80808155fca4490155fcbe29200024',NULL,NULL,'2016-08-05 15:00:57','003001'),('fd06b1d829ca44a2a67f0a270b3f1d14','ff27022bc93545128d7b3b57a799d3ff','PAGE',5,'ff80808155fca4490155fcba1edf0012',NULL,NULL,'2016-08-05 15:00:57','003001'),('ff27022bc93545128d7b3b57a799d3ff','5fc7176d93f744b2babb9cbb8b00dde2','PAGE',4,'ff80808155fca4490155fcb989ff0010',NULL,NULL,'2016-08-05 15:00:57','003001');
/*!40000 ALTER TABLE `tb_portal_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role` (
  `id` varchar(32) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `admin` bit(1) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4qtf6jv3emjrfpar67m64pur1` (`company_id`),
  CONSTRAINT `FK_4qtf6jv3emjrfpar67m64pur1` FOREIGN KEY (`company_id`) REFERENCES `tb_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES ('010001','commRole','',NULL,'2015-07-16 11:59:55','2015-07-16 11:59:55','002002','002002','004001'),('ff80808155fca4490155fcadb0c00001','tester','\0','测试角色','2016-07-18 14:24:49','2016-07-18 14:24:49','002002','002002','004001');
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_function`
--

DROP TABLE IF EXISTS `tb_role_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role_function` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `data_level_id` int(11) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ejvdsv86i87owr0p3i1i214ne` (`role`),
  CONSTRAINT `FK_ejvdsv86i87owr0p3i1i214ne` FOREIGN KEY (`role`) REFERENCES `tb_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_function`
--

LOCK TABLES `tb_role_function` WRITE;
/*!40000 ALTER TABLE `tb_role_function` DISABLE KEYS */;
INSERT INTO `tb_role_function` VALUES ('052f7be1bfde49fd9de1ced6af7c70f0','cd27715271ee40699f5d42b83ca39419',1,'PAGE',4,'ff80808155fca4490155fcb989ff0010','2016-08-05 15:00:57','010001'),('15372e00012d47878b1f8f4aeb57309d','95c75a90d0f04e8393bd5d9e0e431180',NULL,'APP',1,'ff80808155fca4490155fcbc0b590018','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('26251ed35e76487dae7f7326f6826582','39b2fa94a195481aafc4d069ff3cc491',1,'PAGE',7,'ff80808155fca4490155fcb88cef000c','2016-08-05 15:00:57','010001'),('2a2af738f86142338f3ea829d1409cea','57bae3cc027d42eba69a294bcd63ab40',1,'PAGE',5,'ff80808155fca4490155fcba1edf0012','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('2e87812263a24489b3a05de72f7b97ca','df81efcb45024c5c8e17b802ee53ba1f',1,'PAGE',9,'ff80808155fca4490155fcbcb44b001c','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('39b2fa94a195481aafc4d069ff3cc491','cd27715271ee40699f5d42b83ca39419',1,'PAGE',3,'ff80808155fca4490155fcb2e888000a','2016-08-05 15:00:57','010001'),('3b2837c77ea54b8198386e689912890d','81f7e938b25342e7984def4db87db2e1',1,'PAGE',2,'ff80808155fca4490155fcb1b1d40004','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('404bb26612d34fcabedbe9c11e3e77fb','acb88d4d08304e5288b4ac55a649bef9',1,'PAGE',8,'ff80808155fca4490155fcb8f1a4000e','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('43addd5f836642f6882824ffe1fa8da8','f8ed07e2ee9d40149edd71d9a607d980',1,'PAGE',10,'ff80808155fca4490155fcbd28ac001e','2016-08-05 15:00:57','010001'),('46ad3daa4c4d4c3da307d6bef155e065','f0c4ba2c431c41f3809af72f5c82b65d',1,'PAGE',9,'ff80808155fca4490155fcb1e9230006','2016-08-05 15:00:57','010001'),('4fe5e6e9a8a94c059d0db2d55af0008d','d52aed1242164946a73ee10690bca403',1,'PAGE',5,'ff80808155fca4490155fcbf4c61002a','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('50689d99c5474d6fa7a9603e25d57b99','f8ed07e2ee9d40149edd71d9a607d980',1,'PAGE',9,'ff80808155fca4490155fcbcb44b001c','2016-08-05 15:00:57','010001'),('57bae3cc027d42eba69a294bcd63ab40','81f7e938b25342e7984def4db87db2e1',1,'PAGE',4,'ff80808155fca4490155fcb989ff0010','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('5c4959bb796f408fa49f0acb647b4a5a','3b2837c77ea54b8198386e689912890d',1,'PAGE',10,'ff80808155fca4490155fcb27d7b0008','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('5d93603854ed4effaad2fd222cef806d','052f7be1bfde49fd9de1ced6af7c70f0',1,'PAGE',5,'ff80808155fca4490155fcba1edf0012','2016-08-05 15:00:57','010001'),('5f8672c9c662423aa940a1cbb8da260f','e2554769d5de403ab8055d1fa2c34133',1,'PAGE',4,'ff80808155fca4490155fcc0f48a0032','2016-08-05 15:00:57','010001'),('5f9498db19544bd9a9ac645bca01a257','d33c948e8b164e15bb7b98d7c962426c',1,'PAGE',3,'ff80808155fca4490155fcbdd1c90022','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('68d2a6e575a94402bc0534c2738f5fd5','null',NULL,'PORTAL',1,'003001','2016-08-05 15:00:57','010001'),('6ca13261f46e4e89b71282fc2be43cdc','052f7be1bfde49fd9de1ced6af7c70f0',1,'PAGE',6,'ff80808155fca4490155fcba83ae0014','2016-08-05 15:00:57','010001'),('7260393f0c4f4ee1ad4a40633b7f3c66','15372e00012d47878b1f8f4aeb57309d',1,'PAGE',2,'ff80808155fca4490155fcc0610a002e','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('7787de480ebc4e82b0172d3416f20c69','68d2a6e575a94402bc0534c2738f5fd5',NULL,'APP',1,'ff80808155fca4490155fcbb7f990016','2016-08-05 15:00:57','010001'),('7a925c7a5d2f46188e17db4ae593c57a','93339804204d4174a7eda74a44483852',1,'PAGE',8,'ff80808155fca4490155fcbea6930026','2016-08-05 15:00:57','010001'),('81f7e938b25342e7984def4db87db2e1','95c75a90d0f04e8393bd5d9e0e431180',NULL,'APP',1,'ff80808155fca4490155fcb0c1110002','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('8d4b66af69b74a64b8605d635bac16a8','f8ed07e2ee9d40149edd71d9a607d980',1,'PAGE',11,'ff80808155fca4490155fcbd81b40020','2016-08-05 15:00:57','010001'),('92dda046c34645898f20ec52bab5e645','f0c4ba2c431c41f3809af72f5c82b65d',1,'PAGE',10,'ff80808155fca4490155fcb27d7b0008','2016-08-05 15:00:57','010001'),('93339804204d4174a7eda74a44483852','7787de480ebc4e82b0172d3416f20c69',1,'PAGE',3,'ff80808155fca4490155fcbdd1c90022','2016-08-05 15:00:57','010001'),('95c75a90d0f04e8393bd5d9e0e431180',NULL,NULL,'PORTAL',1,'003001','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('9ad0e7468d2e459292fb1bb8768ce0ce','d939ebbca7f746a692d0ab03e7c64af2',1,'PAGE',6,'ff80808155fca4490155fcbfc413002c','2016-08-05 15:00:57','010001'),('9adc6fe6f29e4fcabea5d76cd7da146e','df81efcb45024c5c8e17b802ee53ba1f',1,'PAGE',10,'ff80808155fca4490155fcbd28ac001e','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('9d6123dca093473fbcadba002d37d093','df81efcb45024c5c8e17b802ee53ba1f',1,'PAGE',11,'ff80808155fca4490155fcbd81b40020','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('9ef382465c0640018e714c81e7983687','93339804204d4174a7eda74a44483852',1,'PAGE',7,'ff80808155fca4490155fcbe29200024','2016-08-05 15:00:57','010001'),('a77f067607be427ba1fc046a104b8b92','5f9498db19544bd9a9ac645bca01a257',1,'PAGE',8,'ff80808155fca4490155fcbea6930026','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('a8f8e198a662491d9d522c792418210d','3b2837c77ea54b8198386e689912890d',1,'PAGE',9,'ff80808155fca4490155fcb1e9230006','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('acb88d4d08304e5288b4ac55a649bef9','81f7e938b25342e7984def4db87db2e1',1,'PAGE',3,'ff80808155fca4490155fcb2e888000a','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('b7d8195085d94587a613ce7bfda28ffc','5f9498db19544bd9a9ac645bca01a257',1,'PAGE',7,'ff80808155fca4490155fcbe29200024','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('bea51741d7634b888ba506b2f6206d1a','d52aed1242164946a73ee10690bca403',1,'PAGE',6,'ff80808155fca4490155fcbfc413002c','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('bf87e5ab878143e7a1124e60432838e1','7260393f0c4f4ee1ad4a40633b7f3c66',1,'PAGE',4,'ff80808155fca4490155fcc0f48a0032','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('c6532a3317774ebe9b75e7838bc0673e','7260393f0c4f4ee1ad4a40633b7f3c66',1,'PAGE',3,'ff80808155fca4490155fcc09e220030','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('cc849e7163f346a095516f90b61dd82b','57bae3cc027d42eba69a294bcd63ab40',1,'PAGE',6,'ff80808155fca4490155fcba83ae0014','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('cd27715271ee40699f5d42b83ca39419','68d2a6e575a94402bc0534c2738f5fd5',NULL,'APP',1,'ff80808155fca4490155fcb0c1110002','2016-08-05 15:00:57','010001'),('cdad967383394ba1b64c19e73e3aeb40','e2554769d5de403ab8055d1fa2c34133',1,'PAGE',3,'ff80808155fca4490155fcc09e220030','2016-08-05 15:00:57','010001'),('d33c948e8b164e15bb7b98d7c962426c','95c75a90d0f04e8393bd5d9e0e431180',NULL,'APP',1,'ff80808155fca4490155fcbb7f990016','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('d52aed1242164946a73ee10690bca403','d33c948e8b164e15bb7b98d7c962426c',1,'PAGE',4,'ff80808155fca4490155fcbefde50028','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('d939ebbca7f746a692d0ab03e7c64af2','7787de480ebc4e82b0172d3416f20c69',1,'PAGE',4,'ff80808155fca4490155fcbefde50028','2016-08-05 15:00:57','010001'),('ddca1f6d5ccf420791f45a4f2bdc4029','d939ebbca7f746a692d0ab03e7c64af2',1,'PAGE',5,'ff80808155fca4490155fcbf4c61002a','2016-08-05 15:00:57','010001'),('df81efcb45024c5c8e17b802ee53ba1f','d33c948e8b164e15bb7b98d7c962426c',1,'PAGE',2,'ff80808155fca4490155fcbc78c5001a','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('e1268e33c0cd45d3a8cd3c0fc68e85a6','68d2a6e575a94402bc0534c2738f5fd5',NULL,'APP',1,'ff80808155fca4490155fcbc0b590018','2016-08-05 15:00:57','010001'),('e23c40cc394a424f993b9c8956d9d2f6','39b2fa94a195481aafc4d069ff3cc491',1,'PAGE',8,'ff80808155fca4490155fcb8f1a4000e','2016-08-05 15:00:57','010001'),('e2554769d5de403ab8055d1fa2c34133','e1268e33c0cd45d3a8cd3c0fc68e85a6',1,'PAGE',2,'ff80808155fca4490155fcc0610a002e','2016-08-05 15:00:57','010001'),('ef579e1a7e564abeba9b031f240ceb22','acb88d4d08304e5288b4ac55a649bef9',1,'PAGE',7,'ff80808155fca4490155fcb88cef000c','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('f0c4ba2c431c41f3809af72f5c82b65d','cd27715271ee40699f5d42b83ca39419',1,'PAGE',2,'ff80808155fca4490155fcb1b1d40004','2016-08-05 15:00:57','010001'),('f8ed07e2ee9d40149edd71d9a607d980','7787de480ebc4e82b0172d3416f20c69',1,'PAGE',2,'ff80808155fca4490155fcbc78c5001a','2016-08-05 15:00:57','010001');
/*!40000 ALTER TABLE `tb_role_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_user`
--

DROP TABLE IF EXISTS `tb_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role_user` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `rx_inserttime` datetime NOT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_olf42371gohdudfu8xmjrc9xf` (`role_id`),
  KEY `FK_fqson2ux2u8h8whbuht2ciphk` (`user_id`),
  CONSTRAINT `FK_fqson2ux2u8h8whbuht2ciphk` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK_olf42371gohdudfu8xmjrc9xf` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_user`
--

LOCK TABLES `tb_role_user` WRITE;
/*!40000 ALTER TABLE `tb_role_user` DISABLE KEYS */;
INSERT INTO `tb_role_user` VALUES ('010001','010001','002002','2014-09-22 00:00:00','002001'),('ff80808155fca4490155fcc4d2220035','ff80808155fca4490155fcadb0c00001','ff80808155fca4490155fcad0e310000','2016-07-18 14:50:05',NULL),('ff808081565980f9015659831e2c0007','ff80808155fca4490155fcadb0c00001','ff808081565980f901565981f1200000','2016-08-05 15:03:00',NULL),('ff808081565980f901565983363a0008','ff80808155fca4490155fcadb0c00001','ff808081565980f90156598218b80001','2016-08-05 15:03:06',NULL),('ff808081565980f9015659834d9e0009','ff80808155fca4490155fcadb0c00001','ff808081565980f9015659823e010002','2016-08-05 15:03:12',NULL),('ff808081565980f9015659836173000a','ff80808155fca4490155fcadb0c00001','ff808081565980f901565982688f0003','2016-08-05 15:03:17',NULL),('ff808081565980f90156598375c7000b','ff80808155fca4490155fcadb0c00001','ff808081565980f901565982b6aa0004','2016-08-05 15:03:22',NULL),('ff808081565980f9015659838b61000c','ff80808155fca4490155fcadb0c00001','ff808081565980f901565982dbee0005','2016-08-05 15:03:28',NULL),('ff808081565980f901565983a17c000d','ff80808155fca4490155fcadb0c00001','ff808081565980f901565982fe330006','2016-08-05 15:03:34',NULL);
/*!40000 ALTER TABLE `tb_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_team`
--

DROP TABLE IF EXISTS `tb_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_team` (
  `id` varchar(32) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `sys_yn` varchar(1) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `rx_inserttime` datetime NOT NULL,
  `rx_updatetime` datetime NOT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) NOT NULL,
  `father_team_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dr2ggg5p8g5rwe9cn9uuj63um` (`father_team_id`),
  CONSTRAINT `FK_dr2ggg5p8g5rwe9cn9uuj63um` FOREIGN KEY (`father_team_id`) REFERENCES `tb_team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_team`
--

LOCK TABLES `tb_team` WRITE;
/*!40000 ALTER TABLE `tb_team` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `id` varchar(32) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_code` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `qq` varchar(15) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `pwd` varchar(100) NOT NULL,
  `question1` varchar(100) DEFAULT NULL,
  `answer1` varchar(100) DEFAULT NULL,
  `question2` varchar(100) DEFAULT NULL,
  `answer2` varchar(100) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `regist_time` datetime DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `failure_validate_count` int(11) DEFAULT NULL,
  `h5_regist_flg` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code` (`user_code`,`company_id`),
  KEY `FK_ght6skmy57ndsswrvm2sxtuu9` (`company_id`),
  CONSTRAINT `FK_ght6skmy57ndsswrvm2sxtuu9` FOREIGN KEY (`company_id`) REFERENCES `tb_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES ('002001',1,'admin','管理员',NULL,NULL,NULL,'abc.1234',NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-22 13:44:15',NULL,NULL,NULL,NULL,NULL,0,'\0'),('002002',2,'admin','企业管理员',NULL,NULL,NULL,'abc.1234',NULL,NULL,NULL,NULL,NULL,NULL,'2016-08-05 15:00:56',NULL,NULL,NULL,NULL,'004001',0,'\0'),('ff80808155fca4490155fcad0e310000',3,'tester1','测试1','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,'2016-07-18 15:07:29','2016-07-18 14:24:07','2016-07-18 14:24:07',NULL,'002002','004001',0,'\0'),('ff808081565980f901565981f1200000',4,'tester2','测试2','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,'2016-08-05 15:03:48','2016-08-05 15:01:43','2016-08-05 15:01:43',NULL,'002002','004001',0,'\0'),('ff808081565980f90156598218b80001',5,'tester3','测试3','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:01:53','2016-08-05 15:01:53',NULL,'002002','004001',0,'\0'),('ff808081565980f9015659823e010002',6,'tester4','测试4','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:03','2016-08-05 15:02:03',NULL,'002002','004001',0,'\0'),('ff808081565980f901565982688f0003',7,'tester5','测试5','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:13','2016-08-05 15:02:13',NULL,'002002','004001',0,'\0'),('ff808081565980f901565982b6aa0004',8,'tester6','测试6','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:33','2016-08-05 15:02:33',NULL,'002002','004001',0,'\0'),('ff808081565980f901565982dbee0005',9,'tester7','测试7','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:43','2016-08-05 15:02:43',NULL,'002002','004001',0,'\0'),('ff808081565980f901565982fe330006',10,'tester8','测试8','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:52','2016-08-05 15:02:52',NULL,'002002','004001',0,'\0');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_association`
--

DROP TABLE IF EXISTS `tb_user_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_association` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `column_code` varchar(50) DEFAULT NULL,
  `column_value` varchar(50) DEFAULT NULL,
  `column_length` int(11) DEFAULT NULL,
  `column_name` varchar(50) DEFAULT NULL,
  `column_type` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_association`
--

LOCK TABLES `tb_user_association` WRITE;
/*!40000 ALTER TABLE `tb_user_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_blacklist`
--

DROP TABLE IF EXISTS `tb_user_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_blacklist` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `blacktype_id` varchar(50) NOT NULL,
  `company_mark` varchar(50) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_blacklist`
--

LOCK TABLES `tb_user_blacklist` WRITE;
/*!40000 ALTER TABLE `tb_user_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_login_frist_send_url_history`
--

DROP TABLE IF EXISTS `tb_user_login_frist_send_url_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_login_frist_send_url_history` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `url` longtext,
  `send_flg` bit(1) DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_login_frist_send_url_history`
--

LOCK TABLES `tb_user_login_frist_send_url_history` WRITE;
/*!40000 ALTER TABLE `tb_user_login_frist_send_url_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_login_frist_send_url_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenement`
--

DROP TABLE IF EXISTS `tenement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tenement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '企业名称',
  `shortname` varchar(50) DEFAULT NULL COMMENT '企业简称',
  `pid` varchar(45) NOT NULL COMMENT '企业注册H5plus的pid',
  `email` varchar(100) DEFAULT NULL COMMENT '企业邮箱',
  `mobile` varchar(45) DEFAULT NULL COMMENT '负责人手机',
  `province` varchar(100) DEFAULT NULL COMMENT '省',
  `city` varchar(100) DEFAULT NULL COMMENT '市',
  `area` varchar(100) DEFAULT NULL COMMENT '区',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `session_token` varchar(200) DEFAULT NULL,
  `bas_id` varchar(100) DEFAULT NULL,
  `register_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:代表注册微信pid的信息  1:代表注册bas_id的信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='企业客户(租户)表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenement`
--

LOCK TABLES `tenement` WRITE;
/*!40000 ALTER TABLE `tenement` DISABLE KEYS */;
INSERT INTO `tenement` VALUES (1,NULL,'h5','55cbf3a3986a9b483376f279',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'2016-06-16 08:09:15',NULL,NULL,0),(2,'TestBasTokenInMC9','tBASTokenInMC9','',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'2016-07-19 11:03:01','02o+Yn2wYHHsjQUd9iQXnIoCAM/zL+beaI9UeyaBWnBhqZpKbD/yJt0g==','3f64e87df34f4918b5e3da59a3c8b0ec',1);
/*!40000 ALTER TABLE `tenement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL COMMENT 'email/mobile',
  `role_id` bigint(20) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `passwd` varchar(64) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `created_time` datetime DEFAULT '0000-00-00 00:00:00',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Jason','123456',1,'sdfs','123456',0,'2016-05-04 16:17:12','2016-05-04 16:17:12');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_relation`
--

DROP TABLE IF EXISTS `user_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_relation` (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_relation`
--

LOCK TABLES `user_role_relation` WRITE;
/*!40000 ALTER TABLE `user_role_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webchart_event_msg_log`
--

DROP TABLE IF EXISTS `webchart_event_msg_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webchart_event_msg_log` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `msg_id` varchar(255) DEFAULT NULL,
  `from_user_name` varchar(255) DEFAULT NULL,
  `to_user_name` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `event` varchar(1024) DEFAULT NULL,
  `event_key` varchar(255) DEFAULT NULL,
  `ticket` text,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `precision` varchar(255) DEFAULT NULL COMMENT '地理位置精度',
  `menuId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webchart_event_msg_log`
--

LOCK TABLES `webchart_event_msg_log` WRITE;
/*!40000 ALTER TABLE `webchart_event_msg_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `webchart_event_msg_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webchat_auth_info`
--

DROP TABLE IF EXISTS `webchat_auth_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webchat_auth_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authorizer_appid` varchar(255) DEFAULT NULL,
  `authorizer_access_token` varchar(255) DEFAULT NULL,
  `authorizer_refresh_token` varchar(255) DEFAULT NULL,
  `expires_in` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webchat_auth_info`
--

LOCK TABLES `webchat_auth_info` WRITE;
/*!40000 ALTER TABLE `webchat_auth_info` DISABLE KEYS */;
INSERT INTO `webchat_auth_info` VALUES (1,'wxa5fb7dea54673299','NkZQUdGdGhyA96nE1uvcOyPabtCvPYmslQghHAzT-_Z_7rdnK0RH43kOzEO_NjhIWEhKqyo5pUK7M8gYCjHq13IOSVofLpX7tj7QauR3d_uylN9H6dfoChZ-nb4Fuw8JQBLeAHDYJT','refreshtoken@@@MD3IehgTfUpPdnJrz8E_qyeuYHeDQRTxG184-_UKJkI','7200'),(2,'wxeb10897c0cd98e36','If5p0tuPYEc2Zcb-VQ0BcS8JSVXb2ONlmOJsH_f1y0Cw0h0exu69KcTGfdpPn9WCZBxPjI7BuvyY57l-V90HJl2ePE0jX8UqodADMHr00wcTq1vyNafhqUTjgxXrcarbNQEdAGDSVJ','refreshtoken@@@kkMVjC90JS9ooW_zsUUhfvjFbwAlfvB9pmBTZArGYMM','7200'),(3,'wx1f363449a14a1ad8','OOoupTS7zzW1jXgIGZadH5Donw7VW5PLwe1UroCCXPPzudVkBAu0z5AETTILdZ1KzY6w-vuAGRfqGwUQeIq3fX52SXIxgS02ugBQaD9-uUL2taxbXcAaRm1sg6ZW-ju3JZBiAMDNDF','refreshtoken@@@gcxmruaeql5C84jx-VHSnt99pOxbEWycsHz7tKgL-ao','7200');
/*!40000 ALTER TABLE `webchat_auth_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webchat_component_verify_ticket`
--

DROP TABLE IF EXISTS `webchat_component_verify_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webchat_component_verify_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(256) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `info_type` varchar(255) DEFAULT NULL,
  `component_verify_ticket` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1556 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webchat_component_verify_ticket`
--

LOCK TABLES `webchat_component_verify_ticket` WRITE;
/*!40000 ALTER TABLE `webchat_component_verify_ticket` DISABLE KEYS */;
INSERT INTO `webchat_component_verify_ticket` VALUES (9,'wx00f7d56d549f82ce',20160827152326,'component_verify_ticket','ticket@@@86UEaYgnQWa5DXjOueoLsXTTq2SAomGW9xIOrbiWiLpaSjSHeYW8ckPpak5XBFVIaerwc41XXr_XIR6eycekLQ'),(10,'wx00f7d56d549f82ce',20160827153321,'component_verify_ticket','ticket@@@86UEaYgnQWa5DXjOueoLsXTTq2SAomGW9xIOrbiWiLpaSjSHeYW8ckPpak5XBFVIaerwc41XXr_XIR6eycekLQ'),(11,'wx00f7d56d549f82ce',20160827154324,'component_verify_ticket','ticket@@@86UEaYgnQWa5DXjOueoLsXTTq2SAomGW9xIOrbiWiLpaSjSHeYW8ckPpak5XBFVIaerwc41XXr_XIR6eycekLQ'),(12,'wx00f7d56d549f82ce',20160827155324,'component_verify_ticket','ticket@@@86UEaYgnQWa5DXjOueoLsXTTq2SAomGW9xIOrbiWiLpaSjSHeYW8ckPpak5XBFVIaerwc41XXr_XIR6eycekLQ'),(13,'wx00f7d56d549f82ce',20160827160323,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(14,'wx00f7d56d549f82ce',20160827161328,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(15,'wx00f7d56d549f82ce',20160827162325,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(16,'wx00f7d56d549f82ce',20160827163324,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(17,'wx00f7d56d549f82ce',20160827164321,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(18,'wx00f7d56d549f82ce',20160827165322,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(19,'wx00f7d56d549f82ce',20160827170326,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(20,'wx00f7d56d549f82ce',20160827171325,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(21,'wx00f7d56d549f82ce',20160827172322,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(22,'wx00f7d56d549f82ce',20160827173327,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(23,'wx00f7d56d549f82ce',20160827174325,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(24,'wx00f7d56d549f82ce',20160827175325,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(25,'wx00f7d56d549f82ce',20160827180327,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(26,'wx00f7d56d549f82ce',20160827181324,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(27,'wx00f7d56d549f82ce',20160827182325,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(28,'wx00f7d56d549f82ce',20160827183324,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(29,'wx00f7d56d549f82ce',20160827184323,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(30,'wx00f7d56d549f82ce',20160827185324,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(31,'wx00f7d56d549f82ce',20160827190322,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(32,'wx00f7d56d549f82ce',20160827191324,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(33,'wx00f7d56d549f82ce',20160827192326,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(34,'wx00f7d56d549f82ce',20160827193322,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(35,'wx00f7d56d549f82ce',20160827194325,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(36,'wx00f7d56d549f82ce',20160827195324,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(37,'wx00f7d56d549f82ce',20160827200325,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(38,'wx00f7d56d549f82ce',20160827201324,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(39,'wx00f7d56d549f82ce',20160827202327,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(40,'wx00f7d56d549f82ce',20160827203341,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(41,'wx00f7d56d549f82ce',20160827204341,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(42,'wx00f7d56d549f82ce',20160827205330,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(43,'wx00f7d56d549f82ce',20160827210408,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(44,'wx00f7d56d549f82ce',20160827211428,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(45,'wx00f7d56d549f82ce',20160827212429,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(46,'wx00f7d56d549f82ce',20160827213345,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(47,'wx00f7d56d549f82ce',20160827214331,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(48,'wx00f7d56d549f82ce',20160827215347,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(49,'wx00f7d56d549f82ce',20160827220401,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(50,'wx00f7d56d549f82ce',20160827221423,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(51,'wx00f7d56d549f82ce',20160827222413,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(52,'wx00f7d56d549f82ce',20160827223330,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(53,'wx00f7d56d549f82ce',20160827224335,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(54,'wx00f7d56d549f82ce',20160827225331,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(55,'wx00f7d56d549f82ce',20160827230339,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(56,'wx00f7d56d549f82ce',20160827231341,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(57,'wx00f7d56d549f82ce',20160827232329,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(58,'wx00f7d56d549f82ce',20160827233330,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(59,'wx00f7d56d549f82ce',20160827234333,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(60,'wx00f7d56d549f82ce',20160827235329,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(61,'wx00f7d56d549f82ce',20160828000330,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(62,'wx00f7d56d549f82ce',20160828001333,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(63,'wx00f7d56d549f82ce',20160828002329,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(64,'wx00f7d56d549f82ce',20160828003330,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(65,'wx00f7d56d549f82ce',20160828004323,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(66,'wx00f7d56d549f82ce',20160828005329,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(67,'wx00f7d56d549f82ce',20160828011323,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(68,'wx00f7d56d549f82ce',20160828012333,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(69,'wx00f7d56d549f82ce',20160828013325,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(70,'wx00f7d56d549f82ce',20160828014318,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(71,'wx00f7d56d549f82ce',20160828015320,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(72,'wx00f7d56d549f82ce',20160828020326,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(73,'wx00f7d56d549f82ce',20160828021323,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(74,'wx00f7d56d549f82ce',20160828022325,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(75,'wx00f7d56d549f82ce',20160828023321,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(76,'wx00f7d56d549f82ce',20160828024326,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(77,'wx00f7d56d549f82ce',20160828025326,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(78,'wx00f7d56d549f82ce',20160828030312,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(79,'wx00f7d56d549f82ce',20160828031322,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(80,'wx00f7d56d549f82ce',20160828032325,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(81,'wx00f7d56d549f82ce',20160828033322,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(82,'wx00f7d56d549f82ce',20160828034323,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(83,'wx00f7d56d549f82ce',20160828035326,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(84,'wx00f7d56d549f82ce',20160828040328,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(85,'wx00f7d56d549f82ce',20160828041330,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(86,'wx00f7d56d549f82ce',20160828042326,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(87,'wx00f7d56d549f82ce',20160828043323,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(88,'wx00f7d56d549f82ce',20160828044319,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(89,'wx00f7d56d549f82ce',20160828045323,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(90,'wx00f7d56d549f82ce',20160828050316,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(91,'wx00f7d56d549f82ce',20160828051319,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(92,'wx00f7d56d549f82ce',20160828052319,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(93,'wx00f7d56d549f82ce',20160828053318,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(94,'wx00f7d56d549f82ce',20160828054321,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(95,'wx00f7d56d549f82ce',20160828055319,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(96,'wx00f7d56d549f82ce',20160828060319,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(97,'wx00f7d56d549f82ce',20160828061320,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(98,'wx00f7d56d549f82ce',20160828062320,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(99,'wx00f7d56d549f82ce',20160828063318,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(100,'wx00f7d56d549f82ce',20160828064316,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(101,'wx00f7d56d549f82ce',20160828065316,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(102,'wx00f7d56d549f82ce',20160828070326,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(103,'wx00f7d56d549f82ce',20160828071320,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(104,'wx00f7d56d549f82ce',20160828072326,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(105,'wx00f7d56d549f82ce',20160828073326,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(106,'wx00f7d56d549f82ce',20160828074324,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(107,'wx00f7d56d549f82ce',20160828075320,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(108,'wx00f7d56d549f82ce',20160828080324,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(109,'wx00f7d56d549f82ce',20160828081325,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(110,'wx00f7d56d549f82ce',20160828082330,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(111,'wx00f7d56d549f82ce',20160828083327,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(112,'wx00f7d56d549f82ce',20160828084322,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(113,'wx00f7d56d549f82ce',20160828085313,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(114,'wx00f7d56d549f82ce',20160828090338,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(115,'wx00f7d56d549f82ce',20160828091327,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(116,'wx00f7d56d549f82ce',20160828092335,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(117,'wx00f7d56d549f82ce',20160828093335,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(118,'wx00f7d56d549f82ce',20160828094329,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(119,'wx00f7d56d549f82ce',20160828095350,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(120,'wx00f7d56d549f82ce',20160828100332,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(121,'wx00f7d56d549f82ce',20160828101338,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(122,'wx00f7d56d549f82ce',20160828102325,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(123,'wx00f7d56d549f82ce',20160828103329,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(124,'wx00f7d56d549f82ce',20160828104352,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(125,'wx00f7d56d549f82ce',20160828105333,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(126,'wx00f7d56d549f82ce',20160828110349,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(127,'wx00f7d56d549f82ce',20160828111341,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(128,'wx00f7d56d549f82ce',20160828112339,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(129,'wx00f7d56d549f82ce',20160828113346,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(130,'wx00f7d56d549f82ce',20160828114327,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(131,'wx00f7d56d549f82ce',20160828115338,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(132,'wx00f7d56d549f82ce',20160828120345,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(133,'wx00f7d56d549f82ce',20160828121400,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(134,'wx00f7d56d549f82ce',20160828122357,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(135,'wx00f7d56d549f82ce',20160828123342,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(136,'wx00f7d56d549f82ce',20160828124329,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(137,'wx00f7d56d549f82ce',20160828125329,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(138,'wx00f7d56d549f82ce',20160828130346,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(139,'wx00f7d56d549f82ce',20160828131400,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(140,'wx00f7d56d549f82ce',20160828132358,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(141,'wx00f7d56d549f82ce',20160828133329,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(142,'wx00f7d56d549f82ce',20160828134338,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(143,'wx00f7d56d549f82ce',20160828135337,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(144,'wx00f7d56d549f82ce',20160828140356,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(145,'wx00f7d56d549f82ce',20160828141346,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(146,'wx00f7d56d549f82ce',20160828142331,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(147,'wx00f7d56d549f82ce',20160828143327,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(148,'wx00f7d56d549f82ce',20160828144335,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(149,'wx00f7d56d549f82ce',20160828145335,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(150,'wx00f7d56d549f82ce',20160828150349,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(151,'wx00f7d56d549f82ce',20160828151326,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(152,'wx00f7d56d549f82ce',20160828152334,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(153,'wx00f7d56d549f82ce',20160828153336,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(154,'wx00f7d56d549f82ce',20160828154338,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(155,'wx00f7d56d549f82ce',20160828155333,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(156,'wx00f7d56d549f82ce',20160828160339,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(157,'wx00f7d56d549f82ce',20160828161337,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(158,'wx00f7d56d549f82ce',20160828162329,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(159,'wx00f7d56d549f82ce',20160828163332,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(160,'wx00f7d56d549f82ce',20160828164336,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(161,'wx00f7d56d549f82ce',20160828165325,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(162,'wx00f7d56d549f82ce',20160828170332,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(163,'wx00f7d56d549f82ce',20160828171344,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(164,'wx00f7d56d549f82ce',20160828172344,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(165,'wx00f7d56d549f82ce',20160828173332,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(166,'wx00f7d56d549f82ce',20160828174324,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(167,'wx00f7d56d549f82ce',20160828175342,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(168,'wx00f7d56d549f82ce',20160828180332,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(169,'wx00f7d56d549f82ce',20160828181350,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(170,'wx00f7d56d549f82ce',20160828182331,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(171,'wx00f7d56d549f82ce',20160828183327,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(172,'wx00f7d56d549f82ce',20160828184333,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(173,'wx00f7d56d549f82ce',20160828185327,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(174,'wx00f7d56d549f82ce',20160828190408,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(175,'wx00f7d56d549f82ce',20160828191342,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(176,'wx00f7d56d549f82ce',20160828192317,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(177,'wx00f7d56d549f82ce',20160828193331,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(178,'wx00f7d56d549f82ce',20160828194329,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(179,'wx00f7d56d549f82ce',20160828195356,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(180,'wx00f7d56d549f82ce',20160828200338,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(181,'wx00f7d56d549f82ce',20160828201348,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(182,'wx00f7d56d549f82ce',20160828202333,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(183,'wx00f7d56d549f82ce',20160828203346,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(184,'wx00f7d56d549f82ce',20160828204437,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(185,'wx00f7d56d549f82ce',20160828205337,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(186,'wx00f7d56d549f82ce',20160828210326,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(187,'wx00f7d56d549f82ce',20160828211324,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(188,'wx00f7d56d549f82ce',20160828212325,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(189,'wx00f7d56d549f82ce',20160828213325,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(190,'wx00f7d56d549f82ce',20160828214325,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(191,'wx00f7d56d549f82ce',20160828215326,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(192,'wx00f7d56d549f82ce',20160828220328,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(193,'wx00f7d56d549f82ce',20160828221328,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(194,'wx00f7d56d549f82ce',20160828222330,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(195,'wx00f7d56d549f82ce',20160828223325,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(196,'wx00f7d56d549f82ce',20160828224324,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(197,'wx00f7d56d549f82ce',20160828225323,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(198,'wx00f7d56d549f82ce',20160828230323,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(199,'wx00f7d56d549f82ce',20160828231328,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(200,'wx00f7d56d549f82ce',20160828232328,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(201,'wx00f7d56d549f82ce',20160828233325,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(202,'wx00f7d56d549f82ce',20160828234325,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(203,'wx00f7d56d549f82ce',20160828235324,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(204,'wx00f7d56d549f82ce',20160829000327,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(205,'wx00f7d56d549f82ce',20160829001318,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(206,'wx00f7d56d549f82ce',20160829002325,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(207,'wx00f7d56d549f82ce',20160829003325,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(208,'wx00f7d56d549f82ce',20160829004319,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(209,'wx00f7d56d549f82ce',20160829005319,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(210,'wx00f7d56d549f82ce',20160829010319,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(211,'wx00f7d56d549f82ce',20160829011321,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(212,'wx00f7d56d549f82ce',20160829012323,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(213,'wx00f7d56d549f82ce',20160829013324,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(214,'wx00f7d56d549f82ce',20160829014321,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(215,'wx00f7d56d549f82ce',20160829015303,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(216,'wx00f7d56d549f82ce',20160829020323,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(217,'wx00f7d56d549f82ce',20160829021324,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(218,'wx00f7d56d549f82ce',20160829022325,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(219,'wx00f7d56d549f82ce',20160829023324,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(220,'wx00f7d56d549f82ce',20160829024324,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(221,'wx00f7d56d549f82ce',20160829025322,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(222,'wx00f7d56d549f82ce',20160829030322,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(223,'wx00f7d56d549f82ce',20160829031320,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(224,'wx00f7d56d549f82ce',20160829032324,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(225,'wx00f7d56d549f82ce',20160829033319,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(226,'wx00f7d56d549f82ce',20160829034323,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(227,'wx00f7d56d549f82ce',20160829035324,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(228,'wx00f7d56d549f82ce',20160829040325,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(229,'wx00f7d56d549f82ce',20160829041330,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(230,'wx00f7d56d549f82ce',20160829042323,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(231,'wx00f7d56d549f82ce',20160829043327,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(232,'wx00f7d56d549f82ce',20160829044323,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(233,'wx00f7d56d549f82ce',20160829045306,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(234,'wx00f7d56d549f82ce',20160829050316,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(235,'wx00f7d56d549f82ce',20160829051315,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(236,'wx00f7d56d549f82ce',20160829052318,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(237,'wx00f7d56d549f82ce',20160829053317,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(238,'wx00f7d56d549f82ce',20160829054314,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(239,'wx00f7d56d549f82ce',20160829055319,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(240,'wx00f7d56d549f82ce',20160829060318,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(241,'wx00f7d56d549f82ce',20160829061317,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(242,'wx00f7d56d549f82ce',20160829062317,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(243,'wx00f7d56d549f82ce',20160829063319,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(244,'wx00f7d56d549f82ce',20160829064313,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(245,'wx00f7d56d549f82ce',20160829065313,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(246,'wx00f7d56d549f82ce',20160829070317,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(247,'wx00f7d56d549f82ce',20160829071317,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(248,'wx00f7d56d549f82ce',20160829072318,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(249,'wx00f7d56d549f82ce',20160829073320,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(250,'wx00f7d56d549f82ce',20160829074307,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(251,'wx00f7d56d549f82ce',20160829075304,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(252,'wx00f7d56d549f82ce',20160829080324,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(253,'wx00f7d56d549f82ce',20160829081318,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(254,'wx00f7d56d549f82ce',20160829082319,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(255,'wx00f7d56d549f82ce',20160829083326,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(256,'wx00f7d56d549f82ce',20160829084324,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(257,'wx00f7d56d549f82ce',20160829085324,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(258,'wx00f7d56d549f82ce',20160829090328,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(259,'wx00f7d56d549f82ce',20160829091326,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(260,'wx00f7d56d549f82ce',20160829092329,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(261,'wx00f7d56d549f82ce',20160829093334,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(262,'wx00f7d56d549f82ce',20160829094330,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(263,'wx00f7d56d549f82ce',20160829095330,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(264,'wx00f7d56d549f82ce',20160829100332,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(265,'wx00f7d56d549f82ce',20160829101330,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(266,'wx00f7d56d549f82ce',20160829102332,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(267,'wx00f7d56d549f82ce',20160829103332,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(268,'wx00f7d56d549f82ce',20160829104328,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(269,'wx00f7d56d549f82ce',20160829105329,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(270,'wx00f7d56d549f82ce',20160829110334,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(271,'wx00f7d56d549f82ce',20160829111334,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(272,'wx00f7d56d549f82ce',20160829112340,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(273,'wx00f7d56d549f82ce',20160829113333,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(274,'wx00f7d56d549f82ce',20160829114330,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(275,'wx00f7d56d549f82ce',20160829115328,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(276,'wx00f7d56d549f82ce',20160829120336,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(277,'wx00f7d56d549f82ce',20160829121340,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(278,'wx00f7d56d549f82ce',20160829122331,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(279,'wx00f7d56d549f82ce',20160829123325,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(280,'wx00f7d56d549f82ce',20160829124313,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(281,'wx00f7d56d549f82ce',20160829125330,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(282,'wx00f7d56d549f82ce',20160829130332,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(283,'wx00f7d56d549f82ce',20160829131330,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(284,'wx00f7d56d549f82ce',20160829132330,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(285,'wx00f7d56d549f82ce',20160829133329,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(286,'wx00f7d56d549f82ce',20160829134329,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(287,'wx00f7d56d549f82ce',20160829135329,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(288,'wx00f7d56d549f82ce',20160829140331,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(289,'wx00f7d56d549f82ce',20160829141330,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(290,'wx00f7d56d549f82ce',20160829142350,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(291,'wx00f7d56d549f82ce',20160829143407,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(292,'wx00f7d56d549f82ce',20160829144329,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(293,'wx00f7d56d549f82ce',20160829145331,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(294,'wx00f7d56d549f82ce',20160829150332,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(295,'wx00f7d56d549f82ce',20160829151328,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(296,'wx00f7d56d549f82ce',20160829152331,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(297,'wx00f7d56d549f82ce',20160829153329,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(298,'wx00f7d56d549f82ce',20160829154333,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(299,'wx00f7d56d549f82ce',20160829155331,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(300,'wx00f7d56d549f82ce',20160829160337,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(301,'wx00f7d56d549f82ce',20160829161333,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(302,'wx00f7d56d549f82ce',20160829162330,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(303,'wx00f7d56d549f82ce',20160829163332,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(304,'wx00f7d56d549f82ce',20160829164331,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(305,'wx00f7d56d549f82ce',20160829165330,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(306,'wx00f7d56d549f82ce',20160829170335,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(307,'wx00f7d56d549f82ce',20160829171316,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(308,'wx00f7d56d549f82ce',20160829172336,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(309,'wx00f7d56d549f82ce',20160829173338,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(310,'wx00f7d56d549f82ce',20160829174328,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(311,'wx00f7d56d549f82ce',20160829175330,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(312,'wx00f7d56d549f82ce',20160829180339,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(313,'wx00f7d56d549f82ce',20160829181330,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(314,'wx00f7d56d549f82ce',20160829182330,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(315,'wx00f7d56d549f82ce',20160829183326,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(316,'wx00f7d56d549f82ce',20160829184326,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(317,'wx00f7d56d549f82ce',20160829185326,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(318,'wx00f7d56d549f82ce',20160829190332,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(319,'wx00f7d56d549f82ce',20160829191326,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(320,'wx00f7d56d549f82ce',20160829192327,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(321,'wx00f7d56d549f82ce',20160829193330,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(322,'wx00f7d56d549f82ce',20160829194326,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(323,'wx00f7d56d549f82ce',20160829195326,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(324,'wx00f7d56d549f82ce',20160829200330,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(325,'wx00f7d56d549f82ce',20160829201327,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(326,'wx00f7d56d549f82ce',20160829202330,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(327,'wx00f7d56d549f82ce',20160829203327,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(328,'wx00f7d56d549f82ce',20160829204327,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(329,'wx00f7d56d549f82ce',20160829205330,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(330,'wx00f7d56d549f82ce',20160829210332,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(331,'wx00f7d56d549f82ce',20160829211333,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(332,'wx00f7d56d549f82ce',20160829212329,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(333,'wx00f7d56d549f82ce',20160829213328,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(334,'wx00f7d56d549f82ce',20160829214328,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(335,'wx00f7d56d549f82ce',20160829215326,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(336,'wx00f7d56d549f82ce',20160829220329,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(337,'wx00f7d56d549f82ce',20160829221320,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(338,'wx00f7d56d549f82ce',20160829222327,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(339,'wx00f7d56d549f82ce',20160829223328,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(340,'wx00f7d56d549f82ce',20160829224326,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(341,'wx00f7d56d549f82ce',20160829225326,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(342,'wx00f7d56d549f82ce',20160829230324,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(343,'wx00f7d56d549f82ce',20160829231327,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(344,'wx00f7d56d549f82ce',20160829232327,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(345,'wx00f7d56d549f82ce',20160829233327,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(346,'wx00f7d56d549f82ce',20160829234326,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(347,'wx00f7d56d549f82ce',20160829235325,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(348,'wx00f7d56d549f82ce',20160830000326,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(349,'wx00f7d56d549f82ce',20160830001327,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(350,'wx00f7d56d549f82ce',20160830002322,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(351,'wx00f7d56d549f82ce',20160830003312,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(352,'wx00f7d56d549f82ce',20160830004320,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(353,'wx00f7d56d549f82ce',20160830005320,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(354,'wx00f7d56d549f82ce',20160830010319,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(355,'wx00f7d56d549f82ce',20160830011320,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(356,'wx00f7d56d549f82ce',20160830012321,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(357,'wx00f7d56d549f82ce',20160830013323,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(358,'wx00f7d56d549f82ce',20160830014321,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(359,'wx00f7d56d549f82ce',20160830015310,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(360,'wx00f7d56d549f82ce',20160830020321,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(361,'wx00f7d56d549f82ce',20160830021323,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(362,'wx00f7d56d549f82ce',20160830022321,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(363,'wx00f7d56d549f82ce',20160830023323,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(364,'wx00f7d56d549f82ce',20160830024322,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(365,'wx00f7d56d549f82ce',20160830025317,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(366,'wx00f7d56d549f82ce',20160830030322,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(367,'wx00f7d56d549f82ce',20160830031321,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(368,'wx00f7d56d549f82ce',20160830032324,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(369,'wx00f7d56d549f82ce',20160830033323,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(370,'wx00f7d56d549f82ce',20160830034322,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(371,'wx00f7d56d549f82ce',20160830035322,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(372,'wx00f7d56d549f82ce',20160830040325,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(373,'wx00f7d56d549f82ce',20160830041324,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(374,'wx00f7d56d549f82ce',20160830042323,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(375,'wx00f7d56d549f82ce',20160830043322,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(376,'wx00f7d56d549f82ce',20160830044301,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(377,'wx00f7d56d549f82ce',20160830045320,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(378,'wx00f7d56d549f82ce',20160830050314,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(379,'wx00f7d56d549f82ce',20160830051322,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(380,'wx00f7d56d549f82ce',20160830052317,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(381,'wx00f7d56d549f82ce',20160830053307,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(382,'wx00f7d56d549f82ce',20160830054314,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(383,'wx00f7d56d549f82ce',20160830055316,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(384,'wx00f7d56d549f82ce',20160830060317,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(385,'wx00f7d56d549f82ce',20160830061321,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(386,'wx00f7d56d549f82ce',20160830062322,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(387,'wx00f7d56d549f82ce',20160830063319,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(388,'wx00f7d56d549f82ce',20160830064316,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(389,'wx00f7d56d549f82ce',20160830065314,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(390,'wx00f7d56d549f82ce',20160830070319,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(391,'wx00f7d56d549f82ce',20160830071319,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(392,'wx00f7d56d549f82ce',20160830072321,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(393,'wx00f7d56d549f82ce',20160830073322,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(394,'wx00f7d56d549f82ce',20160830074320,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(395,'wx00f7d56d549f82ce',20160830075321,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(396,'wx00f7d56d549f82ce',20160830080325,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(397,'wx00f7d56d549f82ce',20160830081323,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(398,'wx00f7d56d549f82ce',20160830082324,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(399,'wx00f7d56d549f82ce',20160830083328,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(400,'wx00f7d56d549f82ce',20160830084324,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(401,'wx00f7d56d549f82ce',20160830085326,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(402,'wx00f7d56d549f82ce',20160830090327,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(403,'wx00f7d56d549f82ce',20160830091327,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(404,'wx00f7d56d549f82ce',20160830092327,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(405,'wx00f7d56d549f82ce',20160830093332,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(406,'wx00f7d56d549f82ce',20160830094326,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(407,'wx00f7d56d549f82ce',20160830095327,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(408,'wx00f7d56d549f82ce',20160830100336,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(409,'wx00f7d56d549f82ce',20160830101332,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(410,'wx00f7d56d549f82ce',20160830102334,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(411,'wx00f7d56d549f82ce',20160830103331,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(412,'wx00f7d56d549f82ce',20160830104328,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(413,'wx00f7d56d549f82ce',20160830105332,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(414,'wx00f7d56d549f82ce',20160830110334,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(415,'wx00f7d56d549f82ce',20160830111333,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(416,'wx00f7d56d549f82ce',20160830112332,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(417,'wx00f7d56d549f82ce',20160830113328,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(418,'wx00f7d56d549f82ce',20160830114329,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(419,'wx00f7d56d549f82ce',20160830115329,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(420,'wx00f7d56d549f82ce',20160830120336,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(421,'wx00f7d56d549f82ce',20160830121325,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(422,'wx00f7d56d549f82ce',20160830122333,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(423,'wx00f7d56d549f82ce',20160830123331,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(424,'wx00f7d56d549f82ce',20160830124327,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(425,'wx00f7d56d549f82ce',20160830125330,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(426,'wx00f7d56d549f82ce',20160830130340,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(427,'wx00f7d56d549f82ce',20160830131329,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(428,'wx00f7d56d549f82ce',20160830132328,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(429,'wx00f7d56d549f82ce',20160830133328,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(430,'wx00f7d56d549f82ce',20160830134321,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(431,'wx00f7d56d549f82ce',20160830135327,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(432,'wx00f7d56d549f82ce',20160830140329,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(433,'wx00f7d56d549f82ce',20160830141329,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(434,'wx00f7d56d549f82ce',20160830142329,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(435,'wx00f7d56d549f82ce',20160830143330,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(436,'wx00f7d56d549f82ce',20160830144328,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(437,'wx00f7d56d549f82ce',20160830145326,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(438,'wx00f7d56d549f82ce',20160830150328,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(439,'wx00f7d56d549f82ce',20160830151329,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(440,'wx00f7d56d549f82ce',20160830152331,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(441,'wx00f7d56d549f82ce',20160830153331,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(442,'wx00f7d56d549f82ce',20160830154327,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(443,'wx00f7d56d549f82ce',20160830155330,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(444,'wx00f7d56d549f82ce',20160830160334,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(445,'wx00f7d56d549f82ce',20160830161332,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(446,'wx00f7d56d549f82ce',20160830162330,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(447,'wx00f7d56d549f82ce',20160830163330,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(448,'wx00f7d56d549f82ce',20160830164327,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(449,'wx00f7d56d549f82ce',20160830165331,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(450,'wx00f7d56d549f82ce',20160830170334,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(451,'wx00f7d56d549f82ce',20160830171333,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(452,'wx00f7d56d549f82ce',20160830172331,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(453,'wx00f7d56d549f82ce',20160830173330,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(454,'wx00f7d56d549f82ce',20160830174331,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(455,'wx00f7d56d549f82ce',20160830175329,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(456,'wx00f7d56d549f82ce',20160830180332,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(457,'wx00f7d56d549f82ce',20160830181330,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(458,'wx00f7d56d549f82ce',20160830182330,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(459,'wx00f7d56d549f82ce',20160830183328,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(460,'wx00f7d56d549f82ce',20160830184328,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(461,'wx00f7d56d549f82ce',20160830185327,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(462,'wx00f7d56d549f82ce',20160830190324,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(463,'wx00f7d56d549f82ce',20160830191330,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(464,'wx00f7d56d549f82ce',20160830192331,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(465,'wx00f7d56d549f82ce',20160830193330,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(466,'wx00f7d56d549f82ce',20160830194329,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(467,'wx00f7d56d549f82ce',20160830195327,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(468,'wx00f7d56d549f82ce',20160830200331,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(469,'wx00f7d56d549f82ce',20160830201330,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(470,'wx00f7d56d549f82ce',20160830202333,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(471,'wx00f7d56d549f82ce',20160830203330,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(472,'wx00f7d56d549f82ce',20160830204329,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(473,'wx00f7d56d549f82ce',20160830205331,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(474,'wx00f7d56d549f82ce',20160830210334,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(475,'wx00f7d56d549f82ce',20160830211332,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(476,'wx00f7d56d549f82ce',20160830212334,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(477,'wx00f7d56d549f82ce',20160830213329,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(478,'wx00f7d56d549f82ce',20160830214329,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(479,'wx00f7d56d549f82ce',20160830215329,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(480,'wx00f7d56d549f82ce',20160830220326,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(481,'wx00f7d56d549f82ce',20160830221331,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(482,'wx00f7d56d549f82ce',20160830222331,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(483,'wx00f7d56d549f82ce',20160830223330,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(484,'wx00f7d56d549f82ce',20160830224328,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(485,'wx00f7d56d549f82ce',20160830225326,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(486,'wx00f7d56d549f82ce',20160830230328,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(487,'wx00f7d56d549f82ce',20160830231329,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(488,'wx00f7d56d549f82ce',20160830232329,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(489,'wx00f7d56d549f82ce',20160830233328,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(490,'wx00f7d56d549f82ce',20160830234319,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(491,'wx00f7d56d549f82ce',20160830235326,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(492,'wx00f7d56d549f82ce',20160831000329,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(493,'wx00f7d56d549f82ce',20160831001328,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(494,'wx00f7d56d549f82ce',20160831002328,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(495,'wx00f7d56d549f82ce',20160831003327,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(496,'wx00f7d56d549f82ce',20160831004321,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(497,'wx00f7d56d549f82ce',20160831005317,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(498,'wx00f7d56d549f82ce',20160831010318,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(499,'wx00f7d56d549f82ce',20160831011319,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(500,'wx00f7d56d549f82ce',20160831012323,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(501,'wx00f7d56d549f82ce',20160831013324,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(502,'wx00f7d56d549f82ce',20160831014322,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(503,'wx00f7d56d549f82ce',20160831015321,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(504,'wx00f7d56d549f82ce',20160831020322,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(505,'wx00f7d56d549f82ce',20160831021324,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(506,'wx00f7d56d549f82ce',20160831022325,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(507,'wx00f7d56d549f82ce',20160831023324,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(508,'wx00f7d56d549f82ce',20160831024321,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(509,'wx00f7d56d549f82ce',20160831025321,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(510,'wx00f7d56d549f82ce',20160831030324,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(511,'wx00f7d56d549f82ce',20160831031322,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(512,'wx00f7d56d549f82ce',20160831032302,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(513,'wx00f7d56d549f82ce',20160831033322,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(514,'wx00f7d56d549f82ce',20160831034322,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(515,'wx00f7d56d549f82ce',20160831035324,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(516,'wx00f7d56d549f82ce',20160831040325,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(517,'wx00f7d56d549f82ce',20160831041330,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(518,'wx00f7d56d549f82ce',20160831042326,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(519,'wx00f7d56d549f82ce',20160831043321,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(520,'wx00f7d56d549f82ce',20160831044315,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(521,'wx00f7d56d549f82ce',20160831045313,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(522,'wx00f7d56d549f82ce',20160831050319,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(523,'wx00f7d56d549f82ce',20160831051325,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(524,'wx00f7d56d549f82ce',20160831052314,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(525,'wx00f7d56d549f82ce',20160831053315,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(526,'wx00f7d56d549f82ce',20160831054317,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(527,'wx00f7d56d549f82ce',20160831055317,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(528,'wx00f7d56d549f82ce',20160831060321,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(529,'wx00f7d56d549f82ce',20160831061325,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(530,'wx00f7d56d549f82ce',20160831062319,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(531,'wx00f7d56d549f82ce',20160831063331,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(532,'wx00f7d56d549f82ce',20160831064313,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(533,'wx00f7d56d549f82ce',20160831065315,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(534,'wx00f7d56d549f82ce',20160831070321,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(535,'wx00f7d56d549f82ce',20160831071323,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(536,'wx00f7d56d549f82ce',20160831072322,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(537,'wx00f7d56d549f82ce',20160831073322,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(538,'wx00f7d56d549f82ce',20160831074322,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(539,'wx00f7d56d549f82ce',20160831075323,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(540,'wx00f7d56d549f82ce',20160831080326,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(541,'wx00f7d56d549f82ce',20160831081326,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(542,'wx00f7d56d549f82ce',20160831082327,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(543,'wx00f7d56d549f82ce',20160831083327,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(544,'wx00f7d56d549f82ce',20160831084326,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(545,'wx00f7d56d549f82ce',20160831085325,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(546,'wx00f7d56d549f82ce',20160831090332,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(547,'wx00f7d56d549f82ce',20160831091330,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(548,'wx00f7d56d549f82ce',20160831092330,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(549,'wx00f7d56d549f82ce',20160831093330,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(550,'wx00f7d56d549f82ce',20160831094328,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(551,'wx00f7d56d549f82ce',20160831095330,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(552,'wx00f7d56d549f82ce',20160831100336,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(553,'wx00f7d56d549f82ce',20160831101333,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(554,'wx00f7d56d549f82ce',20160831102333,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(555,'wx00f7d56d549f82ce',20160831103330,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(556,'wx00f7d56d549f82ce',20160831104331,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(557,'wx00f7d56d549f82ce',20160831105333,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(558,'wx00f7d56d549f82ce',20160831110334,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(559,'wx00f7d56d549f82ce',20160831111333,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(560,'wx00f7d56d549f82ce',20160831112334,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(561,'wx00f7d56d549f82ce',20160831113333,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(562,'wx00f7d56d549f82ce',20160831114331,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(563,'wx00f7d56d549f82ce',20160831115329,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(564,'wx00f7d56d549f82ce',20160831120332,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(565,'wx00f7d56d549f82ce',20160831121332,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(566,'wx00f7d56d549f82ce',20160831122331,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(567,'wx00f7d56d549f82ce',20160831123331,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(568,'wx00f7d56d549f82ce',20160831124329,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(569,'wx00f7d56d549f82ce',20160831125330,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(570,'wx00f7d56d549f82ce',20160831130332,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(571,'wx00f7d56d549f82ce',20160831131329,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(572,'wx00f7d56d549f82ce',20160831132330,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(573,'wx00f7d56d549f82ce',20160831133329,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(574,'wx00f7d56d549f82ce',20160831134340,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(575,'wx00f7d56d549f82ce',20160831135317,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(576,'wx00f7d56d549f82ce',20160831140331,'component_verify_ticket','ticket@@@dJ9UwQuUJ4lyuG6TPX1jeDZZmpC_Yd8Ayi4NIeet0kFiIH4s3a6AKpj036m72CkgqhLYzSbzF0SrU6NE_LMlRQ'),(577,'wx00f7d56d549f82ce',20160831161332,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(578,'wx00f7d56d549f82ce',20160831162330,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(579,'wx00f7d56d549f82ce',20160831163330,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(580,'wx00f7d56d549f82ce',20160831164329,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(581,'wx00f7d56d549f82ce',20160831165328,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(582,'wx00f7d56d549f82ce',20160906180300,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(583,'wx00f7d56d549f82ce',20160906181259,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(584,'wx00f7d56d549f82ce',20160906182256,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(585,'wx00f7d56d549f82ce',20160906183256,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(586,'wx00f7d56d549f82ce',20160906184256,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(587,'wx00f7d56d549f82ce',20160906185254,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(588,'wx00f7d56d549f82ce',20160906190257,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(589,'wx00f7d56d549f82ce',20160906191255,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(590,'wx00f7d56d549f82ce',20160906192254,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(591,'wx00f7d56d549f82ce',20160906193254,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(592,'wx00f7d56d549f82ce',20160906194253,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(593,'wx00f7d56d549f82ce',20160906195255,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(594,'wx00f7d56d549f82ce',20160906200257,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(595,'wx00f7d56d549f82ce',20160906201256,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(596,'wx00f7d56d549f82ce',20160906202258,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(597,'wx00f7d56d549f82ce',20160906203258,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(598,'wx00f7d56d549f82ce',20160906204255,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(599,'wx00f7d56d549f82ce',20160906205256,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(600,'wx00f7d56d549f82ce',20160906210257,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(601,'wx00f7d56d549f82ce',20160906211255,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(602,'wx00f7d56d549f82ce',20160906212257,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(603,'wx00f7d56d549f82ce',20160906213257,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(604,'wx00f7d56d549f82ce',20160906214258,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(605,'wx00f7d56d549f82ce',20160906215256,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(606,'wx00f7d56d549f82ce',20160906220301,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(607,'wx00f7d56d549f82ce',20160906221257,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(608,'wx00f7d56d549f82ce',20160906222255,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(609,'wx00f7d56d549f82ce',20160906223252,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(610,'wx00f7d56d549f82ce',20160906224253,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(611,'wx00f7d56d549f82ce',20160906225251,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(612,'wx00f7d56d549f82ce',20160906230253,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(613,'wx00f7d56d549f82ce',20160906231256,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(614,'wx00f7d56d549f82ce',20160906232256,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(615,'wx00f7d56d549f82ce',20160906233255,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(616,'wx00f7d56d549f82ce',20160906234253,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(617,'wx00f7d56d549f82ce',20160906235252,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(618,'wx00f7d56d549f82ce',20160907000255,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(619,'wx00f7d56d549f82ce',20160907001253,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(620,'wx00f7d56d549f82ce',20160907002253,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(621,'wx00f7d56d549f82ce',20160907003249,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(622,'wx00f7d56d549f82ce',20160907004247,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(623,'wx00f7d56d549f82ce',20160907005247,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(624,'wx00f7d56d549f82ce',20160907010246,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(625,'wx00f7d56d549f82ce',20160907011249,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(626,'wx00f7d56d549f82ce',20160907012249,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(627,'wx00f7d56d549f82ce',20160907013250,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(628,'wx00f7d56d549f82ce',20160907014245,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(629,'wx00f7d56d549f82ce',20160907015248,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(630,'wx00f7d56d549f82ce',20160907020251,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(631,'wx00f7d56d549f82ce',20160907021249,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(632,'wx00f7d56d549f82ce',20160907022252,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(633,'wx00f7d56d549f82ce',20160907023248,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(634,'wx00f7d56d549f82ce',20160907024244,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(635,'wx00f7d56d549f82ce',20160907025250,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(636,'wx00f7d56d549f82ce',20160907030246,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(637,'wx00f7d56d549f82ce',20160907031250,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(638,'wx00f7d56d549f82ce',20160907032254,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(639,'wx00f7d56d549f82ce',20160907033252,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(640,'wx00f7d56d549f82ce',20160907034250,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(641,'wx00f7d56d549f82ce',20160907035251,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(642,'wx00f7d56d549f82ce',20160907040256,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(643,'wx00f7d56d549f82ce',20160907041253,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(644,'wx00f7d56d549f82ce',20160907042251,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(645,'wx00f7d56d549f82ce',20160907043250,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(646,'wx00f7d56d549f82ce',20160907044250,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(647,'wx00f7d56d549f82ce',20160907045255,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(648,'wx00f7d56d549f82ce',20160907050243,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(649,'wx00f7d56d549f82ce',20160907051248,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(650,'wx00f7d56d549f82ce',20160907052243,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(651,'wx00f7d56d549f82ce',20160907053242,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(652,'wx00f7d56d549f82ce',20160907054245,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(653,'wx00f7d56d549f82ce',20160907055234,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(654,'wx00f7d56d549f82ce',20160907060246,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(655,'wx00f7d56d549f82ce',20160907061249,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(656,'wx00f7d56d549f82ce',20160907062250,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(657,'wx00f7d56d549f82ce',20160907063249,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(658,'wx00f7d56d549f82ce',20160907064240,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(659,'wx00f7d56d549f82ce',20160907065242,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(660,'wx00f7d56d549f82ce',20160907070251,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(661,'wx00f7d56d549f82ce',20160907071245,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(662,'wx00f7d56d549f82ce',20160907072244,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(663,'wx00f7d56d549f82ce',20160907073247,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(664,'wx00f7d56d549f82ce',20160907074231,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(665,'wx00f7d56d549f82ce',20160907075248,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(666,'wx00f7d56d549f82ce',20160907080307,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(667,'wx00f7d56d549f82ce',20160907081251,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(668,'wx00f7d56d549f82ce',20160907082253,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(669,'wx00f7d56d549f82ce',20160907083235,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(670,'wx00f7d56d549f82ce',20160907084252,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(671,'wx00f7d56d549f82ce',20160907085251,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(672,'wx00f7d56d549f82ce',20160907090256,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(673,'wx00f7d56d549f82ce',20160907091253,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(674,'wx00f7d56d549f82ce',20160907092253,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(675,'wx00f7d56d549f82ce',20160907093254,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(676,'wx00f7d56d549f82ce',20160907094254,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(677,'wx00f7d56d549f82ce',20160907095256,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(678,'wx00f7d56d549f82ce',20160907100310,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(679,'wx00f7d56d549f82ce',20160907101259,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(680,'wx00f7d56d549f82ce',20160907102257,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(681,'wx00f7d56d549f82ce',20160907103255,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(682,'wx00f7d56d549f82ce',20160907104256,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(683,'wx00f7d56d549f82ce',20160907105254,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(684,'wx00f7d56d549f82ce',20160907110256,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(685,'wx00f7d56d549f82ce',20160907111257,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(686,'wx00f7d56d549f82ce',20160907112301,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(687,'wx00f7d56d549f82ce',20160907113258,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(688,'wx00f7d56d549f82ce',20160907114254,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(689,'wx00f7d56d549f82ce',20160907115257,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(690,'wx00f7d56d549f82ce',20160907120258,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(691,'wx00f7d56d549f82ce',20160907121256,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(692,'wx00f7d56d549f82ce',20160907122258,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(693,'wx00f7d56d549f82ce',20160907123256,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(694,'wx00f7d56d549f82ce',20160907124258,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(695,'wx00f7d56d549f82ce',20160907125256,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(696,'wx00f7d56d549f82ce',20160907130258,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(697,'wx00f7d56d549f82ce',20160907131256,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(698,'wx00f7d56d549f82ce',20160907132256,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(699,'wx00f7d56d549f82ce',20160907133255,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(700,'wx00f7d56d549f82ce',20160907134252,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(701,'wx00f7d56d549f82ce',20160907135253,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(702,'wx00f7d56d549f82ce',20160907140257,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(703,'wx00f7d56d549f82ce',20160907141254,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(704,'wx00f7d56d549f82ce',20160907142256,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(705,'wx00f7d56d549f82ce',20160907143250,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(706,'wx00f7d56d549f82ce',20160907144255,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(707,'wx00f7d56d549f82ce',20160907145255,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(708,'wx00f7d56d549f82ce',20160907150259,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(709,'wx00f7d56d549f82ce',20160907151258,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(710,'wx00f7d56d549f82ce',20160907152256,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(711,'wx00f7d56d549f82ce',20160907153254,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(712,'wx00f7d56d549f82ce',20160907154254,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(713,'wx00f7d56d549f82ce',20160907155255,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(714,'wx00f7d56d549f82ce',20160907160300,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(715,'wx00f7d56d549f82ce',20160907161258,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(716,'wx00f7d56d549f82ce',20160907162257,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(717,'wx00f7d56d549f82ce',20160907163255,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(718,'wx00f7d56d549f82ce',20160907164256,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(719,'wx00f7d56d549f82ce',20160907165256,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(720,'wx00f7d56d549f82ce',20160907170258,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(721,'wx00f7d56d549f82ce',20160907171257,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(722,'wx00f7d56d549f82ce',20160907172300,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(723,'wx00f7d56d549f82ce',20160907173258,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(724,'wx00f7d56d549f82ce',20160907174257,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(725,'wx00f7d56d549f82ce',20160907175257,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(726,'wx00f7d56d549f82ce',20160907180257,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(727,'wx00f7d56d549f82ce',20160907181258,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(728,'wx00f7d56d549f82ce',20160907182256,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(729,'wx00f7d56d549f82ce',20160907183258,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(730,'wx00f7d56d549f82ce',20160907184258,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(731,'wx00f7d56d549f82ce',20160907185254,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(732,'wx00f7d56d549f82ce',20160907190257,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(733,'wx00f7d56d549f82ce',20160907191256,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(734,'wx00f7d56d549f82ce',20160907192255,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(735,'wx00f7d56d549f82ce',20160907193255,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(736,'wx00f7d56d549f82ce',20160907194254,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(737,'wx00f7d56d549f82ce',20160907195254,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(738,'wx00f7d56d549f82ce',20160907200258,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(739,'wx00f7d56d549f82ce',20160907201258,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(740,'wx00f7d56d549f82ce',20160907202259,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(741,'wx00f7d56d549f82ce',20160907203301,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(742,'wx00f7d56d549f82ce',20160907204256,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(743,'wx00f7d56d549f82ce',20160907205255,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(744,'wx00f7d56d549f82ce',20160907210259,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(745,'wx00f7d56d549f82ce',20160907211256,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(746,'wx00f7d56d549f82ce',20160907212257,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(747,'wx00f7d56d549f82ce',20160907213257,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(748,'wx00f7d56d549f82ce',20160907214256,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(749,'wx00f7d56d549f82ce',20160907215258,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(750,'wx00f7d56d549f82ce',20160907220302,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(751,'wx00f7d56d549f82ce',20160907221247,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(752,'wx00f7d56d549f82ce',20160907222256,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(753,'wx00f7d56d549f82ce',20160907223253,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(754,'wx00f7d56d549f82ce',20160907224252,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(755,'wx00f7d56d549f82ce',20160907225251,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(756,'wx00f7d56d549f82ce',20160907230252,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(757,'wx00f7d56d549f82ce',20160907231255,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(758,'wx00f7d56d549f82ce',20160907232255,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(759,'wx00f7d56d549f82ce',20160907233256,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(760,'wx00f7d56d549f82ce',20160907234254,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(761,'wx00f7d56d549f82ce',20160907235253,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(762,'wx00f7d56d549f82ce',20160908000255,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(763,'wx00f7d56d549f82ce',20160908001249,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(764,'wx00f7d56d549f82ce',20160908002243,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(765,'wx00f7d56d549f82ce',20160908003253,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(766,'wx00f7d56d549f82ce',20160908004248,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(767,'wx00f7d56d549f82ce',20160908005247,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(768,'wx00f7d56d549f82ce',20160908010242,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(769,'wx00f7d56d549f82ce',20160908011249,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(770,'wx00f7d56d549f82ce',20160908012249,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(771,'wx00f7d56d549f82ce',20160908013249,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(772,'wx00f7d56d549f82ce',20160908014249,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(773,'wx00f7d56d549f82ce',20160908015242,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(774,'wx00f7d56d549f82ce',20160908020251,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(775,'wx00f7d56d549f82ce',20160908021250,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(776,'wx00f7d56d549f82ce',20160908022252,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(777,'wx00f7d56d549f82ce',20160908023251,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(778,'wx00f7d56d549f82ce',20160908024250,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(779,'wx00f7d56d549f82ce',20160908025250,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(780,'wx00f7d56d549f82ce',20160908030251,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(781,'wx00f7d56d549f82ce',20160908031252,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(782,'wx00f7d56d549f82ce',20160908032251,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(783,'wx00f7d56d549f82ce',20160908033252,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(784,'wx00f7d56d549f82ce',20160908034252,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(785,'wx00f7d56d549f82ce',20160908035251,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(786,'wx00f7d56d549f82ce',20160908040255,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(787,'wx00f7d56d549f82ce',20160908041252,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(788,'wx00f7d56d549f82ce',20160908042251,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(789,'wx00f7d56d549f82ce',20160908043252,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(790,'wx00f7d56d549f82ce',20160908044251,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(791,'wx00f7d56d549f82ce',20160908045244,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(792,'wx00f7d56d549f82ce',20160908050235,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(793,'wx00f7d56d549f82ce',20160908051242,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(794,'wx00f7d56d549f82ce',20160908052242,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(795,'wx00f7d56d549f82ce',20160908053244,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(796,'wx00f7d56d549f82ce',20160908054243,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(797,'wx00f7d56d549f82ce',20160908055249,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(798,'wx00f7d56d549f82ce',20160908060250,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(799,'wx00f7d56d549f82ce',20160908061249,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(800,'wx00f7d56d549f82ce',20160908062248,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(801,'wx00f7d56d549f82ce',20160908063255,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(802,'wx00f7d56d549f82ce',20160908064241,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(803,'wx00f7d56d549f82ce',20160908065242,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(804,'wx00f7d56d549f82ce',20160908070249,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(805,'wx00f7d56d549f82ce',20160908071243,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(806,'wx00f7d56d549f82ce',20160908072248,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(807,'wx00f7d56d549f82ce',20160908073242,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(808,'wx00f7d56d549f82ce',20160908074248,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(809,'wx00f7d56d549f82ce',20160908075247,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(810,'wx00f7d56d549f82ce',20160908080252,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(811,'wx00f7d56d549f82ce',20160908081251,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(812,'wx00f7d56d549f82ce',20160908082250,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(813,'wx00f7d56d549f82ce',20160908083248,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(814,'wx00f7d56d549f82ce',20160908084252,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(815,'wx00f7d56d549f82ce',20160908085255,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(816,'wx00f7d56d549f82ce',20160908090255,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(817,'wx00f7d56d549f82ce',20160908091255,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(818,'wx00f7d56d549f82ce',20160908092254,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(819,'wx00f7d56d549f82ce',20160908093252,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(820,'wx00f7d56d549f82ce',20160908094253,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(821,'wx00f7d56d549f82ce',20160908095255,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(822,'wx00f7d56d549f82ce',20160908100303,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(823,'wx00f7d56d549f82ce',20160908101303,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(824,'wx00f7d56d549f82ce',20160908102259,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(825,'wx00f7d56d549f82ce',20160908103255,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(826,'wx00f7d56d549f82ce',20160908104300,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(827,'wx00f7d56d549f82ce',20160908105256,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(828,'wx00f7d56d549f82ce',20160908111301,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(829,'wx00f7d56d549f82ce',20160908112258,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(830,'wx00f7d56d549f82ce',20160908113302,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(831,'wx00f7d56d549f82ce',20160908114248,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(832,'wx00f7d56d549f82ce',20160908115259,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(833,'wx00f7d56d549f82ce',20160908134242,'component_verify_ticket','ticket@@@TdXlecL9NeORB58HTK2md1-Pa67DVTz7aB-H0DIQkP80S4w5hYbvmpYYJhIr_Qn-L5gDSuCpvegoVOZGChFUVA'),(834,'wx00f7d56d549f82ce',20160908135254,'component_verify_ticket','ticket@@@TdXlecL9NeORB58HTK2md1-Pa67DVTz7aB-H0DIQkP80S4w5hYbvmpYYJhIr_Qn-L5gDSuCpvegoVOZGChFUVA'),(835,'wx00f7d56d549f82ce',20160908140255,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(836,'wx00f7d56d549f82ce',20160908142300,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(837,'wx00f7d56d549f82ce',20160908143243,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(838,'wx00f7d56d549f82ce',20160908144243,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(839,'wx00f7d56d549f82ce',20160908145246,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(840,'wx00f7d56d549f82ce',20160908150256,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(841,'wx00f7d56d549f82ce',20160908151255,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(842,'wx00f7d56d549f82ce',20160908152256,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(843,'wx00f7d56d549f82ce',20160908153256,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(844,'wx00f7d56d549f82ce',20160908154256,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(845,'wx00f7d56d549f82ce',20160908155255,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(846,'wx00f7d56d549f82ce',20160908160256,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(847,'wx00f7d56d549f82ce',20160908161257,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(848,'wx00f7d56d549f82ce',20160908162258,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(849,'wx00f7d56d549f82ce',20160908163255,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(850,'wx00f7d56d549f82ce',20160908164256,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(851,'wx00f7d56d549f82ce',20160908165256,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(852,'wx00f7d56d549f82ce',20160908170300,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(853,'wx00f7d56d549f82ce',20160908171256,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(854,'wx00f7d56d549f82ce',20160908172259,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(855,'wx00f7d56d549f82ce',20160908173258,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(856,'wx00f7d56d549f82ce',20160908174303,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(857,'wx00f7d56d549f82ce',20160908175258,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(858,'wx00f7d56d549f82ce',20160908180301,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(859,'wx00f7d56d549f82ce',20160908181257,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(860,'wx00f7d56d549f82ce',20160908182257,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(861,'wx00f7d56d549f82ce',20160908183256,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(862,'wx00f7d56d549f82ce',20160908184254,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(863,'wx00f7d56d549f82ce',20160908185254,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(864,'wx00f7d56d549f82ce',20160908190259,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(865,'wx00f7d56d549f82ce',20160908191300,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(866,'wx00f7d56d549f82ce',20160908192259,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(867,'wx00f7d56d549f82ce',20160908193301,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(868,'wx00f7d56d549f82ce',20160908194247,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(869,'wx00f7d56d549f82ce',20160908195254,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(870,'wx00f7d56d549f82ce',20160908200259,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(871,'wx00f7d56d549f82ce',20160908201256,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(872,'wx00f7d56d549f82ce',20160908202259,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(873,'wx00f7d56d549f82ce',20160908203259,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(874,'wx00f7d56d549f82ce',20160908204303,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(875,'wx00f7d56d549f82ce',20160908205302,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(876,'wx00f7d56d549f82ce',20160908210306,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(877,'wx00f7d56d549f82ce',20160908211303,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(878,'wx00f7d56d549f82ce',20160908212302,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(879,'wx00f7d56d549f82ce',20160908213259,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(880,'wx00f7d56d549f82ce',20160908214257,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(881,'wx00f7d56d549f82ce',20160908215257,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(882,'wx00f7d56d549f82ce',20160908220301,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(883,'wx00f7d56d549f82ce',20160908221302,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(884,'wx00f7d56d549f82ce',20160908222300,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(885,'wx00f7d56d549f82ce',20160908223237,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(886,'wx00f7d56d549f82ce',20160908224255,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(887,'wx00f7d56d549f82ce',20160908225251,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(888,'wx00f7d56d549f82ce',20160908230253,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(889,'wx00f7d56d549f82ce',20160908231256,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(890,'wx00f7d56d549f82ce',20160908232253,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(891,'wx00f7d56d549f82ce',20160908233255,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(892,'wx00f7d56d549f82ce',20160908234255,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(893,'wx00f7d56d549f82ce',20160908235252,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(894,'wx00f7d56d549f82ce',20160909000255,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(895,'wx00f7d56d549f82ce',20160909001253,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(896,'wx00f7d56d549f82ce',20160909002253,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(897,'wx00f7d56d549f82ce',20160909003253,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(898,'wx00f7d56d549f82ce',20160909004247,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(899,'wx00f7d56d549f82ce',20160909005247,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(900,'wx00f7d56d549f82ce',20160909010247,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(901,'wx00f7d56d549f82ce',20160909011250,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(902,'wx00f7d56d549f82ce',20160909012250,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(903,'wx00f7d56d549f82ce',20160909013249,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(904,'wx00f7d56d549f82ce',20160909014250,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(905,'wx00f7d56d549f82ce',20160909015250,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(906,'wx00f7d56d549f82ce',20160909020250,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(907,'wx00f7d56d549f82ce',20160909021247,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(908,'wx00f7d56d549f82ce',20160909022251,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(909,'wx00f7d56d549f82ce',20160909023250,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(910,'wx00f7d56d549f82ce',20160909024249,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(911,'wx00f7d56d549f82ce',20160909025253,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(912,'wx00f7d56d549f82ce',20160909030249,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(913,'wx00f7d56d549f82ce',20160909031251,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(914,'wx00f7d56d549f82ce',20160909032250,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(915,'wx00f7d56d549f82ce',20160909033250,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(916,'wx00f7d56d549f82ce',20160909034252,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(917,'wx00f7d56d549f82ce',20160909035252,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(918,'wx00f7d56d549f82ce',20160909040251,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(919,'wx00f7d56d549f82ce',20160909041253,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(920,'wx00f7d56d549f82ce',20160909042252,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(921,'wx00f7d56d549f82ce',20160909043250,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(922,'wx00f7d56d549f82ce',20160909044251,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(923,'wx00f7d56d549f82ce',20160909045257,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(924,'wx00f7d56d549f82ce',20160909050230,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(925,'wx00f7d56d549f82ce',20160909051242,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(926,'wx00f7d56d549f82ce',20160909052247,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(927,'wx00f7d56d549f82ce',20160909053245,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(928,'wx00f7d56d549f82ce',20160909054245,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(929,'wx00f7d56d549f82ce',20160909055248,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(930,'wx00f7d56d549f82ce',20160909060247,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(931,'wx00f7d56d549f82ce',20160909061248,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(932,'wx00f7d56d549f82ce',20160909062251,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(933,'wx00f7d56d549f82ce',20160909063248,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(934,'wx00f7d56d549f82ce',20160909064243,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(935,'wx00f7d56d549f82ce',20160909065241,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(936,'wx00f7d56d549f82ce',20160909070247,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(937,'wx00f7d56d549f82ce',20160909071250,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(938,'wx00f7d56d549f82ce',20160909072248,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(939,'wx00f7d56d549f82ce',20160909073248,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(940,'wx00f7d56d549f82ce',20160909074248,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(941,'wx00f7d56d549f82ce',20160909075249,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(942,'wx00f7d56d549f82ce',20160909080257,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(943,'wx00f7d56d549f82ce',20160909081250,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(944,'wx00f7d56d549f82ce',20160909082252,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(945,'wx00f7d56d549f82ce',20160909083252,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(946,'wx00f7d56d549f82ce',20160909084255,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(947,'wx00f7d56d549f82ce',20160909085255,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(948,'wx00f7d56d549f82ce',20160909090255,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(949,'wx00f7d56d549f82ce',20160909091257,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(950,'wx00f7d56d549f82ce',20160909092257,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(951,'wx00f7d56d549f82ce',20160909093315,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(952,'wx00f7d56d549f82ce',20160909094258,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(953,'wx00f7d56d549f82ce',20160909095300,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(954,'wx00f7d56d549f82ce',20160909100306,'component_verify_ticket','ticket@@@i2cJUETJg5IW6r9JrcUdQ1PehS_UkV8p1-pEUCyfkK-lOq2uCVkRGLNx-4cUYRtczbqWoGY6xIzRyOi4_TgIdQ'),(955,'wx00f7d56d549f82ce',20160909101303,'component_verify_ticket','ticket@@@i2cJUETJg5IW6r9JrcUdQ1PehS_UkV8p1-pEUCyfkK-lOq2uCVkRGLNx-4cUYRtczbqWoGY6xIzRyOi4_TgIdQ'),(956,'wx00f7d56d549f82ce',20160909102305,'component_verify_ticket','ticket@@@i2cJUETJg5IW6r9JrcUdQ1PehS_UkV8p1-pEUCyfkK-lOq2uCVkRGLNx-4cUYRtczbqWoGY6xIzRyOi4_TgIdQ'),(957,'wx00f7d56d549f82ce',20160909105302,'component_verify_ticket','ticket@@@i2cJUETJg5IW6r9JrcUdQ1PehS_UkV8p1-pEUCyfkK-lOq2uCVkRGLNx-4cUYRtczbqWoGY6xIzRyOi4_TgIdQ'),(958,'wx00f7d56d549f82ce',20160909110307,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(959,'wx00f7d56d549f82ce',20160909111301,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(960,'wx00f7d56d549f82ce',20160909112308,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(961,'wx00f7d56d549f82ce',20160909113308,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(962,'wx00f7d56d549f82ce',20160909114301,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(963,'wx00f7d56d549f82ce',20160909115259,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(964,'wx00f7d56d549f82ce',20160909120255,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(965,'wx00f7d56d549f82ce',20160909121257,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(966,'wx00f7d56d549f82ce',20160909122259,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(967,'wx00f7d56d549f82ce',20160909123319,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(968,'wx00f7d56d549f82ce',20160909124253,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(969,'wx00f7d56d549f82ce',20160909125254,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(970,'wx00f7d56d549f82ce',20160909130256,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(971,'wx00f7d56d549f82ce',20160909131301,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(972,'wx00f7d56d549f82ce',20160909132334,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(973,'wx00f7d56d549f82ce',20160909133254,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(974,'wx00f7d56d549f82ce',20160909134254,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(975,'wx00f7d56d549f82ce',20160909135252,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(976,'wx00f7d56d549f82ce',20160909140255,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(977,'wx00f7d56d549f82ce',20160909141334,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(978,'wx00f7d56d549f82ce',20160909142320,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(979,'wx00f7d56d549f82ce',20160909143258,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(980,'wx00f7d56d549f82ce',20160909144258,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(981,'wx00f7d56d549f82ce',20160909145321,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(982,'wx00f7d56d549f82ce',20160909150301,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(983,'wx00f7d56d549f82ce',20160909151305,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(984,'wx00f7d56d549f82ce',20160909152259,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(985,'wx00f7d56d549f82ce',20160909153251,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(986,'wx00f7d56d549f82ce',20160909154301,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(987,'wx00f7d56d549f82ce',20160909155254,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(988,'wx00f7d56d549f82ce',20160909160249,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(989,'wx00f7d56d549f82ce',20160909161302,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(990,'wx00f7d56d549f82ce',20160909162302,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(991,'wx00f7d56d549f82ce',20160909163307,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(992,'wx00f7d56d549f82ce',20160909164301,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(993,'wx00f7d56d549f82ce',20160909165304,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(994,'wx00f7d56d549f82ce',20160909170307,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(995,'wx00f7d56d549f82ce',20160909171259,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(996,'wx00f7d56d549f82ce',20160909172307,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(997,'wx00f7d56d549f82ce',20160909173313,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(998,'wx00f7d56d549f82ce',20160909174315,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(999,'wx00f7d56d549f82ce',20160909175308,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(1000,'wx00f7d56d549f82ce',20160909180314,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1001,'wx00f7d56d549f82ce',20160909181307,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1002,'wx00f7d56d549f82ce',20160909182308,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1003,'wx00f7d56d549f82ce',20160909183306,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1004,'wx00f7d56d549f82ce',20160909184303,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1005,'wx00f7d56d549f82ce',20160909185305,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1006,'wx00f7d56d549f82ce',20160909190312,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1007,'wx00f7d56d549f82ce',20160909191308,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1008,'wx00f7d56d549f82ce',20160909192311,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1009,'wx00f7d56d549f82ce',20160909193309,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1010,'wx00f7d56d549f82ce',20160909194302,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1011,'wx00f7d56d549f82ce',20160909195304,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1012,'wx00f7d56d549f82ce',20160909200315,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1013,'wx00f7d56d549f82ce',20160909201309,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1014,'wx00f7d56d549f82ce',20160909202311,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1015,'wx00f7d56d549f82ce',20160909203259,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1016,'wx00f7d56d549f82ce',20160909204309,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1017,'wx00f7d56d549f82ce',20160909205304,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1018,'wx00f7d56d549f82ce',20160909210317,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1019,'wx00f7d56d549f82ce',20160909211309,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1020,'wx00f7d56d549f82ce',20160909212309,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1021,'wx00f7d56d549f82ce',20160909213306,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1022,'wx00f7d56d549f82ce',20160909214307,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1023,'wx00f7d56d549f82ce',20160909215304,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1024,'wx00f7d56d549f82ce',20160909220313,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1025,'wx00f7d56d549f82ce',20160909221311,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1026,'wx00f7d56d549f82ce',20160909222309,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1027,'wx00f7d56d549f82ce',20160909223303,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1028,'wx00f7d56d549f82ce',20160909224259,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1029,'wx00f7d56d549f82ce',20160909225249,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1030,'wx00f7d56d549f82ce',20160909230304,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1031,'wx00f7d56d549f82ce',20160909231311,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1032,'wx00f7d56d549f82ce',20160909232313,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1033,'wx00f7d56d549f82ce',20160909233315,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1034,'wx00f7d56d549f82ce',20160909234311,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1035,'wx00f7d56d549f82ce',20160909235312,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1036,'wx00f7d56d549f82ce',20160910000301,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1037,'wx00f7d56d549f82ce',20160910001311,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1038,'wx00f7d56d549f82ce',20160910002310,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1039,'wx00f7d56d549f82ce',20160910003251,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1040,'wx00f7d56d549f82ce',20160910004258,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1041,'wx00f7d56d549f82ce',20160910005307,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1042,'wx00f7d56d549f82ce',20160910010307,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1043,'wx00f7d56d549f82ce',20160910011259,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1044,'wx00f7d56d549f82ce',20160910012257,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1045,'wx00f7d56d549f82ce',20160910013255,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1046,'wx00f7d56d549f82ce',20160910014255,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1047,'wx00f7d56d549f82ce',20160910015254,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1048,'wx00f7d56d549f82ce',20160910020257,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1049,'wx00f7d56d549f82ce',20160910021257,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1050,'wx00f7d56d549f82ce',20160910022257,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1051,'wx00f7d56d549f82ce',20160910023257,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1052,'wx00f7d56d549f82ce',20160910024258,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1053,'wx00f7d56d549f82ce',20160910025255,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1054,'wx00f7d56d549f82ce',20160910030258,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1055,'wx00f7d56d549f82ce',20160910031253,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1056,'wx00f7d56d549f82ce',20160910032251,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1057,'wx00f7d56d549f82ce',20160910033258,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1058,'wx00f7d56d549f82ce',20160910034258,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1059,'wx00f7d56d549f82ce',20160910035302,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1060,'wx00f7d56d549f82ce',20160910040300,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1061,'wx00f7d56d549f82ce',20160910041303,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1062,'wx00f7d56d549f82ce',20160910042259,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1063,'wx00f7d56d549f82ce',20160910043256,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1064,'wx00f7d56d549f82ce',20160910044301,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1065,'wx00f7d56d549f82ce',20160910045249,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1066,'wx00f7d56d549f82ce',20160910050247,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1067,'wx00f7d56d549f82ce',20160910051251,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1068,'wx00f7d56d549f82ce',20160910052251,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1069,'wx00f7d56d549f82ce',20160910053250,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1070,'wx00f7d56d549f82ce',20160910054240,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1071,'wx00f7d56d549f82ce',20160910055256,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1072,'wx00f7d56d549f82ce',20160910060247,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1073,'wx00f7d56d549f82ce',20160910061253,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1074,'wx00f7d56d549f82ce',20160910062255,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1075,'wx00f7d56d549f82ce',20160910063252,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1076,'wx00f7d56d549f82ce',20160910064251,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1077,'wx00f7d56d549f82ce',20160910065250,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1078,'wx00f7d56d549f82ce',20160910070256,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1079,'wx00f7d56d549f82ce',20160910071256,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1080,'wx00f7d56d549f82ce',20160910072256,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1081,'wx00f7d56d549f82ce',20160910073257,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1082,'wx00f7d56d549f82ce',20160910074253,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1083,'wx00f7d56d549f82ce',20160910075256,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1084,'wx00f7d56d549f82ce',20160910080318,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1085,'wx00f7d56d549f82ce',20160910081258,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1086,'wx00f7d56d549f82ce',20160910082257,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1087,'wx00f7d56d549f82ce',20160910083258,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1088,'wx00f7d56d549f82ce',20160910084259,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1089,'wx00f7d56d549f82ce',20160910085301,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1090,'wx00f7d56d549f82ce',20160910090304,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1091,'wx00f7d56d549f82ce',20160910091300,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1092,'wx00f7d56d549f82ce',20160910092301,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1093,'wx00f7d56d549f82ce',20160910093300,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1094,'wx00f7d56d549f82ce',20160910094300,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1095,'wx00f7d56d549f82ce',20160910095255,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1096,'wx00f7d56d549f82ce',20160910100305,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1097,'wx00f7d56d549f82ce',20160910101302,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1098,'wx00f7d56d549f82ce',20160910102305,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1099,'wx00f7d56d549f82ce',20160910103304,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1100,'wx00f7d56d549f82ce',20160910104304,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1101,'wx00f7d56d549f82ce',20160910105304,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1102,'wx00f7d56d549f82ce',20160910110306,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1103,'wx00f7d56d549f82ce',20160910111306,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1104,'wx00f7d56d549f82ce',20160910112313,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1105,'wx00f7d56d549f82ce',20160910113303,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1106,'wx00f7d56d549f82ce',20160910114301,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1107,'wx00f7d56d549f82ce',20160910115303,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1108,'wx00f7d56d549f82ce',20160910120307,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1109,'wx00f7d56d549f82ce',20160910121302,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1110,'wx00f7d56d549f82ce',20160910122304,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1111,'wx00f7d56d549f82ce',20160910123304,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1112,'wx00f7d56d549f82ce',20160910124242,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1113,'wx00f7d56d549f82ce',20160910125300,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1114,'wx00f7d56d549f82ce',20160910130306,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1115,'wx00f7d56d549f82ce',20160910131302,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1116,'wx00f7d56d549f82ce',20160910132304,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1117,'wx00f7d56d549f82ce',20160910133303,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1118,'wx00f7d56d549f82ce',20160910134258,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1119,'wx00f7d56d549f82ce',20160910135300,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1120,'wx00f7d56d549f82ce',20160910140301,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1121,'wx00f7d56d549f82ce',20160910141302,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1122,'wx00f7d56d549f82ce',20160910142304,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1123,'wx00f7d56d549f82ce',20160910143301,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1124,'wx00f7d56d549f82ce',20160910144301,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1125,'wx00f7d56d549f82ce',20160910145300,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1126,'wx00f7d56d549f82ce',20160910150305,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1127,'wx00f7d56d549f82ce',20160910151302,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1128,'wx00f7d56d549f82ce',20160910152302,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1129,'wx00f7d56d549f82ce',20160910153302,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1130,'wx00f7d56d549f82ce',20160910154259,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1131,'wx00f7d56d549f82ce',20160910155300,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1132,'wx00f7d56d549f82ce',20160910160305,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1133,'wx00f7d56d549f82ce',20160910161303,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1134,'wx00f7d56d549f82ce',20160910162307,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1135,'wx00f7d56d549f82ce',20160910163252,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1136,'wx00f7d56d549f82ce',20160910164300,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1137,'wx00f7d56d549f82ce',20160910165302,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1138,'wx00f7d56d549f82ce',20160910170304,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1139,'wx00f7d56d549f82ce',20160910171302,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1140,'wx00f7d56d549f82ce',20160910172300,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1141,'wx00f7d56d549f82ce',20160910173259,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1142,'wx00f7d56d549f82ce',20160910174300,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1143,'wx00f7d56d549f82ce',20160910175301,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1144,'wx00f7d56d549f82ce',20160910180307,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1145,'wx00f7d56d549f82ce',20160910181300,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1146,'wx00f7d56d549f82ce',20160910182300,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1147,'wx00f7d56d549f82ce',20160910183246,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1148,'wx00f7d56d549f82ce',20160910184302,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1149,'wx00f7d56d549f82ce',20160910185300,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1150,'wx00f7d56d549f82ce',20160910190311,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1151,'wx00f7d56d549f82ce',20160910191301,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1152,'wx00f7d56d549f82ce',20160910192303,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1153,'wx00f7d56d549f82ce',20160910193305,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1154,'wx00f7d56d549f82ce',20160910194300,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1155,'wx00f7d56d549f82ce',20160910195300,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1156,'wx00f7d56d549f82ce',20160910200310,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1157,'wx00f7d56d549f82ce',20160910201304,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1158,'wx00f7d56d549f82ce',20160910202308,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1159,'wx00f7d56d549f82ce',20160910203319,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1160,'wx00f7d56d549f82ce',20160910204309,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1161,'wx00f7d56d549f82ce',20160910205305,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1162,'wx00f7d56d549f82ce',20160910210502,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1163,'wx00f7d56d549f82ce',20160910211537,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1164,'wx00f7d56d549f82ce',20160910212351,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1165,'wx00f7d56d549f82ce',20160910213336,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1166,'wx00f7d56d549f82ce',20160910214331,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1167,'wx00f7d56d549f82ce',20160910215346,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1168,'wx00f7d56d549f82ce',20160910220430,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1169,'wx00f7d56d549f82ce',20160910221423,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1170,'wx00f7d56d549f82ce',20160910222403,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1171,'wx00f7d56d549f82ce',20160910223319,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1172,'wx00f7d56d549f82ce',20160910224309,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1173,'wx00f7d56d549f82ce',20160910225315,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1174,'wx00f7d56d549f82ce',20160910230319,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1175,'wx00f7d56d549f82ce',20160910231328,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1176,'wx00f7d56d549f82ce',20160910232319,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1177,'wx00f7d56d549f82ce',20160910233319,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1178,'wx00f7d56d549f82ce',20160910234319,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1179,'wx00f7d56d549f82ce',20160910235318,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1180,'wx00f7d56d549f82ce',20160911000311,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1181,'wx00f7d56d549f82ce',20160911001314,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1182,'wx00f7d56d549f82ce',20160911002313,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1183,'wx00f7d56d549f82ce',20160911003312,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1184,'wx00f7d56d549f82ce',20160911004319,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1185,'wx00f7d56d549f82ce',20160911005311,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1186,'wx00f7d56d549f82ce',20160911010320,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1187,'wx00f7d56d549f82ce',20160911011258,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1188,'wx00f7d56d549f82ce',20160911012300,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1189,'wx00f7d56d549f82ce',20160911013259,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1190,'wx00f7d56d549f82ce',20160911014257,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1191,'wx00f7d56d549f82ce',20160911015256,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1192,'wx00f7d56d549f82ce',20160911020259,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1193,'wx00f7d56d549f82ce',20160911021258,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1194,'wx00f7d56d549f82ce',20160911022258,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1195,'wx00f7d56d549f82ce',20160911023306,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1196,'wx00f7d56d549f82ce',20160911024257,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1197,'wx00f7d56d549f82ce',20160911025300,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1198,'wx00f7d56d549f82ce',20160911030259,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1199,'wx00f7d56d549f82ce',20160911031303,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1200,'wx00f7d56d549f82ce',20160911032301,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1201,'wx00f7d56d549f82ce',20160911033253,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1202,'wx00f7d56d549f82ce',20160911034300,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1203,'wx00f7d56d549f82ce',20160911035302,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1204,'wx00f7d56d549f82ce',20160911040257,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1205,'wx00f7d56d549f82ce',20160911041259,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1206,'wx00f7d56d549f82ce',20160911042303,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1207,'wx00f7d56d549f82ce',20160911043258,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1208,'wx00f7d56d549f82ce',20160911044258,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1209,'wx00f7d56d549f82ce',20160911045259,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1210,'wx00f7d56d549f82ce',20160911050256,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1211,'wx00f7d56d549f82ce',20160911051303,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1212,'wx00f7d56d549f82ce',20160911052252,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1213,'wx00f7d56d549f82ce',20160911053253,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1214,'wx00f7d56d549f82ce',20160911054250,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1215,'wx00f7d56d549f82ce',20160911055257,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1216,'wx00f7d56d549f82ce',20160911060253,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1217,'wx00f7d56d549f82ce',20160911061301,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1218,'wx00f7d56d549f82ce',20160911062258,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1219,'wx00f7d56d549f82ce',20160911063256,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1220,'wx00f7d56d549f82ce',20160911064248,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1221,'wx00f7d56d549f82ce',20160911065249,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1222,'wx00f7d56d549f82ce',20160911070258,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1223,'wx00f7d56d549f82ce',20160911071256,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1224,'wx00f7d56d549f82ce',20160911072258,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1225,'wx00f7d56d549f82ce',20160911073256,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1226,'wx00f7d56d549f82ce',20160911074259,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1227,'wx00f7d56d549f82ce',20160911075253,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1228,'wx00f7d56d549f82ce',20160911080302,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1229,'wx00f7d56d549f82ce',20160911081303,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1230,'wx00f7d56d549f82ce',20160911082304,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1231,'wx00f7d56d549f82ce',20160911083302,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1232,'wx00f7d56d549f82ce',20160911084300,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1233,'wx00f7d56d549f82ce',20160911085259,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1234,'wx00f7d56d549f82ce',20160911090304,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1235,'wx00f7d56d549f82ce',20160911091309,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1236,'wx00f7d56d549f82ce',20160911092304,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1237,'wx00f7d56d549f82ce',20160911093306,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1238,'wx00f7d56d549f82ce',20160911094310,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1239,'wx00f7d56d549f82ce',20160911095306,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1240,'wx00f7d56d549f82ce',20160911100334,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1241,'wx00f7d56d549f82ce',20160911101257,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1242,'wx00f7d56d549f82ce',20160911102315,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1243,'wx00f7d56d549f82ce',20160911103309,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1244,'wx00f7d56d549f82ce',20160911104306,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1245,'wx00f7d56d549f82ce',20160911105328,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1246,'wx00f7d56d549f82ce',20160911110322,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1247,'wx00f7d56d549f82ce',20160911111414,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1248,'wx00f7d56d549f82ce',20160911112310,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1249,'wx00f7d56d549f82ce',20160911113304,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1250,'wx00f7d56d549f82ce',20160911114313,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1251,'wx00f7d56d549f82ce',20160911115311,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1252,'wx00f7d56d549f82ce',20160911120345,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1253,'wx00f7d56d549f82ce',20160911121325,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1254,'wx00f7d56d549f82ce',20160911122342,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1255,'wx00f7d56d549f82ce',20160911123320,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1256,'wx00f7d56d549f82ce',20160911124319,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1257,'wx00f7d56d549f82ce',20160911125348,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1258,'wx00f7d56d549f82ce',20160911130313,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1259,'wx00f7d56d549f82ce',20160911131314,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1260,'wx00f7d56d549f82ce',20160911132316,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1261,'wx00f7d56d549f82ce',20160911133313,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1262,'wx00f7d56d549f82ce',20160911134308,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1263,'wx00f7d56d549f82ce',20160911135309,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1264,'wx00f7d56d549f82ce',20160911140322,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1265,'wx00f7d56d549f82ce',20160911141314,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1266,'wx00f7d56d549f82ce',20160911142318,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1267,'wx00f7d56d549f82ce',20160911143302,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1268,'wx00f7d56d549f82ce',20160911144302,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1269,'wx00f7d56d549f82ce',20160911145311,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1270,'wx00f7d56d549f82ce',20160911150312,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1271,'wx00f7d56d549f82ce',20160911151325,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1272,'wx00f7d56d549f82ce',20160911152306,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1273,'wx00f7d56d549f82ce',20160911153310,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1274,'wx00f7d56d549f82ce',20160911154303,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1275,'wx00f7d56d549f82ce',20160911155302,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1276,'wx00f7d56d549f82ce',20160911160315,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1277,'wx00f7d56d549f82ce',20160911161309,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1278,'wx00f7d56d549f82ce',20160911162312,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1279,'wx00f7d56d549f82ce',20160911163304,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1280,'wx00f7d56d549f82ce',20160911164302,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1281,'wx00f7d56d549f82ce',20160911165303,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1282,'wx00f7d56d549f82ce',20160911170310,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1283,'wx00f7d56d549f82ce',20160911171310,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1284,'wx00f7d56d549f82ce',20160911172259,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1285,'wx00f7d56d549f82ce',20160911173304,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1286,'wx00f7d56d549f82ce',20160911174259,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1287,'wx00f7d56d549f82ce',20160911175300,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1288,'wx00f7d56d549f82ce',20160911180312,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1289,'wx00f7d56d549f82ce',20160911181302,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1290,'wx00f7d56d549f82ce',20160911182317,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1291,'wx00f7d56d549f82ce',20160911183255,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1292,'wx00f7d56d549f82ce',20160911184305,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1293,'wx00f7d56d549f82ce',20160911185256,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1294,'wx00f7d56d549f82ce',20160911190258,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1295,'wx00f7d56d549f82ce',20160911191308,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1296,'wx00f7d56d549f82ce',20160911192300,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1297,'wx00f7d56d549f82ce',20160911193306,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1298,'wx00f7d56d549f82ce',20160911194255,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1299,'wx00f7d56d549f82ce',20160911195306,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1300,'wx00f7d56d549f82ce',20160911200358,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1301,'wx00f7d56d549f82ce',20160911201313,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1302,'wx00f7d56d549f82ce',20160911202408,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1303,'wx00f7d56d549f82ce',20160911203341,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1304,'wx00f7d56d549f82ce',20160911204322,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1305,'wx00f7d56d549f82ce',20160911205320,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1306,'wx00f7d56d549f82ce',20160911210331,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1307,'wx00f7d56d549f82ce',20160911211301,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1308,'wx00f7d56d549f82ce',20160911212256,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1309,'wx00f7d56d549f82ce',20160911213257,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1310,'wx00f7d56d549f82ce',20160911214256,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1311,'wx00f7d56d549f82ce',20160911215256,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1312,'wx00f7d56d549f82ce',20160911220255,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1313,'wx00f7d56d549f82ce',20160911221253,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1314,'wx00f7d56d549f82ce',20160911222254,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1315,'wx00f7d56d549f82ce',20160911223254,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1316,'wx00f7d56d549f82ce',20160911224249,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1317,'wx00f7d56d549f82ce',20160911225251,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1318,'wx00f7d56d549f82ce',20160911230253,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1319,'wx00f7d56d549f82ce',20160911231255,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1320,'wx00f7d56d549f82ce',20160911232254,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1321,'wx00f7d56d549f82ce',20160911233256,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1322,'wx00f7d56d549f82ce',20160911234254,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1323,'wx00f7d56d549f82ce',20160911235244,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1324,'wx00f7d56d549f82ce',20160912000254,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1325,'wx00f7d56d549f82ce',20160912001237,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1326,'wx00f7d56d549f82ce',20160912002246,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1327,'wx00f7d56d549f82ce',20160912003252,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1328,'wx00f7d56d549f82ce',20160912004247,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1329,'wx00f7d56d549f82ce',20160912005247,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1330,'wx00f7d56d549f82ce',20160912010247,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1331,'wx00f7d56d549f82ce',20160912011248,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1332,'wx00f7d56d549f82ce',20160912012249,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1333,'wx00f7d56d549f82ce',20160912013249,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1334,'wx00f7d56d549f82ce',20160912014249,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1335,'wx00f7d56d549f82ce',20160912015246,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1336,'wx00f7d56d549f82ce',20160912020252,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1337,'wx00f7d56d549f82ce',20160912021251,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1338,'wx00f7d56d549f82ce',20160912022252,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1339,'wx00f7d56d549f82ce',20160912023250,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1340,'wx00f7d56d549f82ce',20160912024250,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1341,'wx00f7d56d549f82ce',20160912025250,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1342,'wx00f7d56d549f82ce',20160912030252,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1343,'wx00f7d56d549f82ce',20160912031252,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1344,'wx00f7d56d549f82ce',20160912032251,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1345,'wx00f7d56d549f82ce',20160912033251,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1346,'wx00f7d56d549f82ce',20160912034251,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1347,'wx00f7d56d549f82ce',20160912035252,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1348,'wx00f7d56d549f82ce',20160912040256,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1349,'wx00f7d56d549f82ce',20160912041300,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1350,'wx00f7d56d549f82ce',20160912042254,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1351,'wx00f7d56d549f82ce',20160912043246,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1352,'wx00f7d56d549f82ce',20160912044252,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1353,'wx00f7d56d549f82ce',20160912045251,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1354,'wx00f7d56d549f82ce',20160912050247,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1355,'wx00f7d56d549f82ce',20160912051245,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1356,'wx00f7d56d549f82ce',20160912052242,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1357,'wx00f7d56d549f82ce',20160912053243,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1358,'wx00f7d56d549f82ce',20160912054241,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1359,'wx00f7d56d549f82ce',20160912055245,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1360,'wx00f7d56d549f82ce',20160912060250,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1361,'wx00f7d56d549f82ce',20160912061250,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1362,'wx00f7d56d549f82ce',20160912062249,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1363,'wx00f7d56d549f82ce',20160912063245,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1364,'wx00f7d56d549f82ce',20160912064240,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1365,'wx00f7d56d549f82ce',20160912065240,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1366,'wx00f7d56d549f82ce',20160912070247,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1367,'wx00f7d56d549f82ce',20160912071248,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1368,'wx00f7d56d549f82ce',20160912072248,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1369,'wx00f7d56d549f82ce',20160912073243,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1370,'wx00f7d56d549f82ce',20160912074241,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1371,'wx00f7d56d549f82ce',20160912075241,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1372,'wx00f7d56d549f82ce',20160912080313,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1373,'wx00f7d56d549f82ce',20160912081252,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1374,'wx00f7d56d549f82ce',20160912082251,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1375,'wx00f7d56d549f82ce',20160912083252,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1376,'wx00f7d56d549f82ce',20160912084253,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1377,'wx00f7d56d549f82ce',20160912085252,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1378,'wx00f7d56d549f82ce',20160912090301,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1379,'wx00f7d56d549f82ce',20160912091257,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1380,'wx00f7d56d549f82ce',20160912092256,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1381,'wx00f7d56d549f82ce',20160912093317,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1382,'wx00f7d56d549f82ce',20160912094300,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1383,'wx00f7d56d549f82ce',20160912095259,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1384,'wx00f7d56d549f82ce',20160912100339,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1385,'wx00f7d56d549f82ce',20160912101328,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1386,'wx00f7d56d549f82ce',20160912102301,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1387,'wx00f7d56d549f82ce',20160912103318,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1388,'wx00f7d56d549f82ce',20160912104300,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1389,'wx00f7d56d549f82ce',20160912105259,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1390,'wx00f7d56d549f82ce',20160912110313,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1391,'wx00f7d56d549f82ce',20160912111312,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1392,'wx00f7d56d549f82ce',20160912112317,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1393,'wx00f7d56d549f82ce',20160912113300,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1394,'wx00f7d56d549f82ce',20160912114259,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1395,'wx00f7d56d549f82ce',20160912115258,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1396,'wx00f7d56d549f82ce',20160912120305,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1397,'wx00f7d56d549f82ce',20160912121259,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1398,'wx00f7d56d549f82ce',20160912122258,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1399,'wx00f7d56d549f82ce',20160912123258,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1400,'wx00f7d56d549f82ce',20160912124258,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1401,'wx00f7d56d549f82ce',20160912125259,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1402,'wx00f7d56d549f82ce',20160912130316,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1403,'wx00f7d56d549f82ce',20160912131302,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1404,'wx00f7d56d549f82ce',20160912132300,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1405,'wx00f7d56d549f82ce',20160912133300,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1406,'wx00f7d56d549f82ce',20160912134257,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1407,'wx00f7d56d549f82ce',20160912135306,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1408,'wx00f7d56d549f82ce',20160912140301,'component_verify_ticket','ticket@@@VD_VqJivn7sZ75XxVbuZBG7tbP5McqhSnFoeMWKokVWsauq_T7OHZfdFh8UHdkJrGYm6eD0xkO_KyNndjk43sA'),(1409,'wx00f7d56d549f82ce',20160912145258,'component_verify_ticket','ticket@@@VD_VqJivn7sZ75XxVbuZBG7tbP5McqhSnFoeMWKokVWsauq_T7OHZfdFh8UHdkJrGYm6eD0xkO_KyNndjk43sA'),(1410,'wx00f7d56d549f82ce',20160912150302,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1411,'wx00f7d56d549f82ce',20160912151302,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1412,'wx00f7d56d549f82ce',20160912152302,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1413,'wx00f7d56d549f82ce',20160912153304,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1414,'wx00f7d56d549f82ce',20160912154257,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1415,'wx00f7d56d549f82ce',20160912155255,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1416,'wx00f7d56d549f82ce',20160912160245,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1417,'wx00f7d56d549f82ce',20160912161258,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1418,'wx00f7d56d549f82ce',20160912162300,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1419,'wx00f7d56d549f82ce',20160912163303,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1420,'wx00f7d56d549f82ce',20160912164300,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1421,'wx00f7d56d549f82ce',20160912165300,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1422,'wx00f7d56d549f82ce',20160912170304,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1423,'wx00f7d56d549f82ce',20160912171643,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1424,'wx00f7d56d549f82ce',20160912172304,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1425,'wx00f7d56d549f82ce',20160912173301,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1426,'wx00f7d56d549f82ce',20160912174300,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1427,'wx00f7d56d549f82ce',20160912175302,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1428,'wx00f7d56d549f82ce',20160912180304,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1429,'wx00f7d56d549f82ce',20160912181302,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1430,'wx00f7d56d549f82ce',20160912182259,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1431,'wx00f7d56d549f82ce',20160912183255,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1432,'wx00f7d56d549f82ce',20160912184255,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1433,'wx00f7d56d549f82ce',20160912185256,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1434,'wx00f7d56d549f82ce',20160912190258,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1435,'wx00f7d56d549f82ce',20160912191258,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1436,'wx00f7d56d549f82ce',20160912192258,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1437,'wx00f7d56d549f82ce',20160912193259,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1438,'wx00f7d56d549f82ce',20160912194606,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1439,'wx00f7d56d549f82ce',20160912195359,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1440,'wx00f7d56d549f82ce',20160912200259,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1441,'wx00f7d56d549f82ce',20160912201303,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1442,'wx00f7d56d549f82ce',20160912202253,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1443,'wx00f7d56d549f82ce',20160912203255,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1444,'wx00f7d56d549f82ce',20160912204255,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1445,'wx00f7d56d549f82ce',20160912205609,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1446,'wx00f7d56d549f82ce',20160912210429,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1447,'wx00f7d56d549f82ce',20160912211301,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1448,'wx00f7d56d549f82ce',20160912212300,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1449,'wx00f7d56d549f82ce',20160912213300,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1450,'wx00f7d56d549f82ce',20160912214259,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1451,'wx00f7d56d549f82ce',20160912215255,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1452,'wx00f7d56d549f82ce',20160912220307,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1453,'wx00f7d56d549f82ce',20160912221257,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1454,'wx00f7d56d549f82ce',20160912222257,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1455,'wx00f7d56d549f82ce',20160912223255,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1456,'wx00f7d56d549f82ce',20160912224256,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1457,'wx00f7d56d549f82ce',20160912225254,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1458,'wx00f7d56d549f82ce',20160912230255,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1459,'wx00f7d56d549f82ce',20160912231257,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1460,'wx00f7d56d549f82ce',20160912232255,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1461,'wx00f7d56d549f82ce',20160912233257,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1462,'wx00f7d56d549f82ce',20160912234254,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1463,'wx00f7d56d549f82ce',20160912235251,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1464,'wx00f7d56d549f82ce',20160913000257,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1465,'wx00f7d56d549f82ce',20160913001254,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1466,'wx00f7d56d549f82ce',20160913002253,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1467,'wx00f7d56d549f82ce',20160913003255,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1468,'wx00f7d56d549f82ce',20160913004253,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1469,'wx00f7d56d549f82ce',20160913005250,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1470,'wx00f7d56d549f82ce',20160913010248,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1471,'wx00f7d56d549f82ce',20160913011250,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1472,'wx00f7d56d549f82ce',20160913012251,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1473,'wx00f7d56d549f82ce',20160913013251,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1474,'wx00f7d56d549f82ce',20160913014250,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1475,'wx00f7d56d549f82ce',20160913015250,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1476,'wx00f7d56d549f82ce',20160913020254,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1477,'wx00f7d56d549f82ce',20160913021252,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1478,'wx00f7d56d549f82ce',20160913022250,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1479,'wx00f7d56d549f82ce',20160913023249,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1480,'wx00f7d56d549f82ce',20160913024242,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1481,'wx00f7d56d549f82ce',20160913025249,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1482,'wx00f7d56d549f82ce',20160913030252,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1483,'wx00f7d56d549f82ce',20160913031249,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1484,'wx00f7d56d549f82ce',20160913032250,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1485,'wx00f7d56d549f82ce',20160913033251,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1486,'wx00f7d56d549f82ce',20160913034250,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1487,'wx00f7d56d549f82ce',20160913035249,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1488,'wx00f7d56d549f82ce',20160913040248,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1489,'wx00f7d56d549f82ce',20160913041252,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1490,'wx00f7d56d549f82ce',20160913042251,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1491,'wx00f7d56d549f82ce',20160913043250,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1492,'wx00f7d56d549f82ce',20160913044250,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1493,'wx00f7d56d549f82ce',20160913045251,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1494,'wx00f7d56d549f82ce',20160913050247,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1495,'wx00f7d56d549f82ce',20160913051252,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1496,'wx00f7d56d549f82ce',20160913052244,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1497,'wx00f7d56d549f82ce',20160913053246,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1498,'wx00f7d56d549f82ce',20160913054242,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1499,'wx00f7d56d549f82ce',20160913055249,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1500,'wx00f7d56d549f82ce',20160913060247,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1501,'wx00f7d56d549f82ce',20160913061249,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1502,'wx00f7d56d549f82ce',20160913062248,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1503,'wx00f7d56d549f82ce',20160913063249,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1504,'wx00f7d56d549f82ce',20160913064241,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1505,'wx00f7d56d549f82ce',20160913065240,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1506,'wx00f7d56d549f82ce',20160913070249,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1507,'wx00f7d56d549f82ce',20160913071248,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1508,'wx00f7d56d549f82ce',20160913072241,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1509,'wx00f7d56d549f82ce',20160913073248,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1510,'wx00f7d56d549f82ce',20160913074248,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1511,'wx00f7d56d549f82ce',20160913075248,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1512,'wx00f7d56d549f82ce',20160913080255,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1513,'wx00f7d56d549f82ce',20160913081251,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1514,'wx00f7d56d549f82ce',20160913082252,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1515,'wx00f7d56d549f82ce',20160913083254,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1516,'wx00f7d56d549f82ce',20160913084253,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1517,'wx00f7d56d549f82ce',20160913085243,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1518,'wx00f7d56d549f82ce',20160913090307,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1519,'wx00f7d56d549f82ce',20160913091256,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1520,'wx00f7d56d549f82ce',20160913092256,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1521,'wx00f7d56d549f82ce',20160913093300,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1522,'wx00f7d56d549f82ce',20160913094300,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1523,'wx00f7d56d549f82ce',20160913095255,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1524,'wx00f7d56d549f82ce',20160913100304,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1525,'wx00f7d56d549f82ce',20160913101258,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1526,'wx00f7d56d549f82ce',20160913102259,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1527,'wx00f7d56d549f82ce',20160913103259,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1528,'wx00f7d56d549f82ce',20160913104256,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1529,'wx00f7d56d549f82ce',20160913105256,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1530,'wx00f7d56d549f82ce',20160913110253,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1531,'wx00f7d56d549f82ce',20160913111302,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1532,'wx00f7d56d549f82ce',20160913112302,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1533,'wx00f7d56d549f82ce',20160913113300,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1534,'wx00f7d56d549f82ce',20160913114255,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1535,'wx00f7d56d549f82ce',20160913115256,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1536,'wx00f7d56d549f82ce',20160913120303,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1537,'wx00f7d56d549f82ce',20160913121300,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1538,'wx00f7d56d549f82ce',20160913122256,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1539,'wx00f7d56d549f82ce',20160913123258,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1540,'wx00f7d56d549f82ce',20160913124258,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1541,'wx00f7d56d549f82ce',20160913125256,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1542,'wx00f7d56d549f82ce',20160913130303,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1543,'wx00f7d56d549f82ce',20160913131246,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1544,'wx00f7d56d549f82ce',20160913132259,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1545,'wx00f7d56d549f82ce',20160913133256,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1546,'wx00f7d56d549f82ce',20160913134248,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1547,'wx00f7d56d549f82ce',20160913135248,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1548,'wx00f7d56d549f82ce',20160913140256,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1549,'wx00f7d56d549f82ce',20160913141256,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1550,'wx00f7d56d549f82ce',20160913142252,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1551,'wx00f7d56d549f82ce',20160913143257,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1552,'wx00f7d56d549f82ce',20160913144255,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1553,'wx00f7d56d549f82ce',20160913145255,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1554,'wx00f7d56d549f82ce',20160913150301,'component_verify_ticket','ticket@@@3RBhGHNDeWt3Gbn_FluHunFKAlN1U2q8D4bHMbXL0LMuaKwqiTG3KYtzeE4sPltxN8rXwQtUdfKehiy9gGJfKg'),(1555,'wx00f7d56d549f82ce',20160913151259,'component_verify_ticket','ticket@@@3RBhGHNDeWt3Gbn_FluHunFKAlN1U2q8D4bHMbXL0LMuaKwqiTG3KYtzeE4sPltxN8rXwQtUdfKehiy9gGJfKg');
/*!40000 ALTER TABLE `webchat_component_verify_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_asset`
--

DROP TABLE IF EXISTS `wechat_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL DEFAULT '0',
  `asset_type` int(11) NOT NULL DEFAULT '0' COMMENT '0:服务号 1:个人号 2:订阅号',
  `asset_name` varchar(500) DEFAULT NULL COMMENT '资产名称',
  `nickname` varchar(500) DEFAULT NULL COMMENT '昵称',
  `wx_acct` varchar(200) DEFAULT NULL COMMENT '微信账号',
  `consignation_time` datetime DEFAULT NULL,
  `total_count` int(11) DEFAULT NULL,
  `group_ids` varchar(1000) DEFAULT NULL,
  `imgfile_url` varchar(1000) DEFAULT NULL COMMENT '头像缩略图',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `app_id` varchar(22) NOT NULL,
  `wechat_qrcode` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_wx_acct` (`wx_acct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_asset`
--

LOCK TABLES `wechat_asset` WRITE;
/*!40000 ALTER TABLE `wechat_asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_asset_group`
--

DROP TABLE IF EXISTS `wechat_asset_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_asset_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `import_group_id` bigint(20) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL COMMENT '群组名称',
  `members` int(11) DEFAULT NULL COMMENT '群组人数',
  `wx_acct` varchar(500) DEFAULT NULL COMMENT '对应wechat_asset.wx_acct',
  `is_sys_group` int(11) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_asset_group`
--

LOCK TABLES `wechat_asset_group` WRITE;
/*!40000 ALTER TABLE `wechat_asset_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_asset_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_channel`
--

DROP TABLE IF EXISTS `wechat_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '渠道编号',
  `ch_name` varchar(100) NOT NULL COMMENT '渠道名称',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '状态\n0-正常\n1-删除',
  `type` int(11) DEFAULT '0' COMMENT '渠道类型\n0-系统默认渠道\n1-自定义渠道',
  `is_removed` int(11) DEFAULT '0' COMMENT '0-不可删除\n1-可以删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ch_name` (`ch_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信二维码渠道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_channel`
--

LOCK TABLES `wechat_channel` WRITE;
/*!40000 ALTER TABLE `wechat_channel` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_group`
--

DROP TABLE IF EXISTS `wechat_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信群组信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_group`
--

LOCK TABLES `wechat_group` WRITE;
/*!40000 ALTER TABLE `wechat_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_member`
--

DROP TABLE IF EXISTS `wechat_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wx_code` varchar(200) DEFAULT NULL COMMENT '成员微信账号：\n公众号：openid\n个人号 ：ucode',
  `wx_name` varchar(200) DEFAULT NULL COMMENT '成员微信名称',
  `wx_group_id` varchar(200) DEFAULT NULL COMMENT '所属微信群组的ID，wechat_group.id',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信成员(好友、粉丝)信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_member`
--

LOCK TABLES `wechat_member` WRITE;
/*!40000 ALTER TABLE `wechat_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_member_count_cache`
--

DROP TABLE IF EXISTS `wechat_member_count_cache`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_member_count_cache` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wechat_asset_type` int(11) NOT NULL,
  `wechat_asset_type_member_count` int(11) NOT NULL DEFAULT '0',
  `count_time` varchar(10) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_member_count_cache`
--

LOCK TABLES `wechat_member_count_cache` WRITE;
/*!40000 ALTER TABLE `wechat_member_count_cache` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_member_count_cache` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_personal_uuid`
--

DROP TABLE IF EXISTS `wechat_personal_uuid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_personal_uuid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) NOT NULL,
  `status` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000' COMMENT 'uuid的状态，有效为0，无效为1',
  `uin` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_personal_uuid`
--

LOCK TABLES `wechat_personal_uuid` WRITE;
/*!40000 ALTER TABLE `wechat_personal_uuid` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_personal_uuid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode`
--

DROP TABLE IF EXISTS `wechat_qrcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_name` varchar(100) DEFAULT NULL COMMENT '微信公众号名称',
  `wx_acct` varchar(500) DEFAULT NULL COMMENT '微信账号\n个人号： uin\n服务号/订阅号：weixin_id',
  `ch_code` int(11) DEFAULT NULL COMMENT '渠道编号\nwechat_channel.id',
  `is_audience` tinyint(4) DEFAULT NULL COMMENT '是否新建固定人群\n0-否\n1-是',
  `audience_name` varchar(100) DEFAULT NULL COMMENT '固定人群名称\n',
  `related_tags` varchar(2000) DEFAULT NULL COMMENT '关联系统标签ID，多个标签ID，以;分隔，例如：\n101;102;103',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `expiration_time` datetime DEFAULT NULL COMMENT '失效时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态\n0-未用\n1-已用\n2-删除3-过期',
  `qrcode_pic` varchar(128) DEFAULT NULL COMMENT '二维码图片文件名称',
  `qrcode_url` varchar(128) DEFAULT NULL COMMENT '二维码url',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '批次号：批量产生二维码的编号',
  `ticket` text,
  `qrcode_name` varchar(128) DEFAULT NULL COMMENT '二维码名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信公众号二维码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode`
--

LOCK TABLES `wechat_qrcode` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode_focus`
--

DROP TABLE IF EXISTS `wechat_qrcode_focus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_focus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qrcode_id` varchar(45) DEFAULT NULL COMMENT '二维码编号',
  `wx_name` varchar(100) DEFAULT NULL COMMENT '微信公众号名称',
  `ch_code` int(11) DEFAULT NULL COMMENT '渠道编号\nwechat_channel.id',
  `openid` varchar(128) DEFAULT NULL COMMENT '关注者openid',
  `focus_datetime` datetime DEFAULT NULL COMMENT '关注时间',
  `unfocus_datetime` datetime DEFAULT NULL COMMENT '取消关注时间',
  `focus_status` tinyint(4) DEFAULT NULL COMMENT '0:关注时间 1:取消关注时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注二维码记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode_focus`
--

LOCK TABLES `wechat_qrcode_focus` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode_focus` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode_focus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode_log`
--

DROP TABLE IF EXISTS `wechat_qrcode_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_filename` varchar(128) DEFAULT NULL COMMENT '数据源文件名称',
  `total_rows` int(11) DEFAULT NULL COMMENT '总条数',
  `success` tinyint(4) DEFAULT NULL COMMENT '成功条数',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-失败\n1-成功',
  `create_time` datetime DEFAULT NULL,
  `batch_id` varchar(45) DEFAULT NULL COMMENT '批次号：批量产生二维码的编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode_log`
--

LOCK TABLES `wechat_qrcode_log` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode_scan`
--

DROP TABLE IF EXISTS `wechat_qrcode_scan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_scan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL COMMENT '用户ID',
  `user_host` varchar(500) DEFAULT NULL COMMENT '扫描用户手机host name',
  `qrcode_id` int(11) DEFAULT NULL COMMENT '二维码场景编号，wechat_qrcode.id',
  `create_time` datetime DEFAULT NULL COMMENT '扫描时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录扫描次数和人数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode_scan`
--

LOCK TABLES `wechat_qrcode_scan` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode_scan` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode_scan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode_ticket`
--

DROP TABLE IF EXISTS `wechat_qrcode_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ticket` text COMMENT '获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码',
  `url` varchar(128) DEFAULT NULL COMMENT '二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片',
  `scene_id` int(11) DEFAULT NULL COMMENT '场景值ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode_ticket`
--

LOCK TABLES `wechat_qrcode_ticket` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_register`
--

DROP TABLE IF EXISTS `wechat_register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_register`
--

LOCK TABLES `wechat_register` WRITE;
/*!40000 ALTER TABLE `wechat_register` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_register` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-13 15:14:03
-- 根据Jira MCPRO-125修改客户标签模板(修改original_data_custom表结构，data_custom表结构，import_template的数据)
DROP TABLE IF EXISTS `original_data_customer_tags`;

CREATE TABLE `original_data_customer_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_source` varchar(45) DEFAULT NULL COMMENT '标签来源',
  `tag_type_layer_one` varchar(100) DEFAULT NULL COMMENT '一级标签分类', 
  `tag_type_layer_two` varchar(100) DEFAULT NULL COMMENT '二级标签分类', 
  `tag_type_layer_three` varchar(100) DEFAULT NULL COMMENT '三级标签分类', 
  `tag_name` varchar(100) DEFAULT NULL COMMENT '标签名称',
  `identify_no` varchar(19) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固话号码',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `unionid` varchar(45) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL COMMENT '公众号标识',
  `wx_code` varchar(128) DEFAULT NULL COMMENT 'openId',
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `data_customer_tags`;

CREATE TABLE `data_customer_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_source` varchar(45) DEFAULT NULL COMMENT '标签来源',
  `tag_type_layer_one` varchar(100) DEFAULT NULL COMMENT '一级标签分类', 
  `tag_type_layer_two` varchar(100) DEFAULT NULL COMMENT '二级标签分类', 
  `tag_type_layer_three` varchar(100) DEFAULT NULL COMMENT '三级标签分类', 
  `tag_name` varchar(45) DEFAULT NULL COMMENT '标签名称',
  `identify_no` varchar(19) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固话号码',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `unionid` varchar(45) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL COMMENT '公众号标识',
  `wx_code` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '1970-07-01 00:00:00' COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户标签';

DELETE FROM import_template WHERE templ_type = 2;

INSERT INTO `import_template`(templ_type,templ_name,field_name,field_code,selected,status) VALUES 
(2,'客户标签','标签来源','tag_source',1,0),
(2,'客户标签','一级标签分类','tag_type_layer_one',1,0),
(2,'客户标签','二级标签分类','tag_type_layer_two',1,0),
(2,'客户标签','三级标签分类','tag_type_layer_three',1,0),
(2,'客户标签','标签名称','tag_name',1,0),
(2,'客户标签','身份证号','identify_no',0,0),
(2,'客户标签','驾驶证号','driving_license',1,0),
(2,'客户标签','邮箱','email',1,0),
(2,'客户标签','手机号','mobile',1,0),
(2,'客户标签','固话号码','tel',0,0),
(2,'客户标签','QQ','qq',0,0),
(2,'客户标签','私有账号类型','acct_type',0,0),
(2,'客户标签','私有账号','acct_no',0,0),
(2,'客户标签','IDFA','idfa',0,0),
(2,'客户标签','IMEI','imei',0,0),
(2,'客户标签','unionid','unionid',0,0),
(2,'客户标签','MAC','phone_mac',0,0),
(2,'客户标签','删除标记','status',0,0),
(2,'客户标签','产生时间','create_time',0,0),
(2,'客户标签','删除时间','update_time',0,0),
(2,'客户标签','数据来源','source',0,0);
