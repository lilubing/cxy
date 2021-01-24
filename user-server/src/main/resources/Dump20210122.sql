-- MySQL dump 10.13  Distrib 5.7.23, for macos10.13 (x86_64)
--
-- Host: 127.0.0.1    Database: cxy
-- ------------------------------------------------------
-- Server version	5.7.23-log

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
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键，必须唯一，不能为空。用于唯一标识每一个客户端(client)；注册时必须填写(也可以服务端自动生成)，这个字段是必须的，实际应用也有叫app_key	',
  `resource_ids` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '不能为空，用逗号分隔。客户端能访问的资源id集合，注册客户端时，根据实际需要可选择资源id，也可以根据不同的额注册流程，赋予对应的额资源id',
  `client_secret` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '必须填写。注册填写或者服务端自动生成，实际应用也有叫app_secret, 必须要有前缀代表加密方式',
  `scope` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '不能为空，用逗号分隔。指定client的权限范围，比如读写权限，比如移动端还是web端权限',
  `authorized_grant_types` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '不能为空。可选值 授权码模式:authorization_code,密码模式:password,刷新token: refresh_token, 隐式模式: implicit: 客户端模式: client_credentials。支持多个用逗号分隔',
  `web_server_redirect_uri` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '可为空。客户端重定向uri，authorization_code和implicit需要该值进行校验，注册时填写，',
  `authorities` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '可为空。指定用户的权限范围，如果授权的过程需要用户登陆，该字段不生效，implicit和client_credentials需要',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '可空。设置access_token的有效时间(秒),默认(606012,12小时)',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '可空。设置refresh_token有效期(秒)，默认(606024*30, 30填)',
  `additional_information` varchar(4096) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '可空。值必须是json格式',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `archived` tinyint(4) DEFAULT NULL,
  `trusted` tinyint(4) DEFAULT NULL,
  `autoapprove` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'false/true/read/write。默认false,适用于authorization_code模式,设置用户是否自动approval操作,设置true跳过用户确认授权操作页面，直接跳到redirect_uri	',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='接入客户端信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` VALUES ('c1','res1','$2a$10$oKUXJBo07rH5J.b0Y0fMGeW7uqoXFrQaBJ19jug4F/pjWig5aRYEK','ROLE_ADMIN,ROLE_USER,ROLE_API','client_credentials,password,authorization_code,implicit,refresh_token','http://www.baidu.com',NULL,7200,259200,NULL,'2019-11-18 06:15:06',0,0,'false'),('c2','res2','$2a$10$lEyJwkP.Ws6qYM8IcpNyC.mr1N7E8WD4QWbmchAPHMhCDxAEN59te','ROLE_API','client_credentials,password,authorization_code,implicit,refresh_token','http://www.51ab.com:8090/mybatis-web/login',NULL,7200,259200,NULL,'2021-01-17 01:12:10',0,0,'ROLE_API'),('CouponSystem','','$2a$10$dYRcFip80f0jIKGzRGulFelK12036xWQKgajanfxT65QB4htsEXNK','user_info','authorization_code','http://www.51ab.com:8090/cxy/login',NULL,NULL,NULL,NULL,'2019-11-18 23:56:31',NULL,NULL,'user_info'),('MemberSystem','res1','$2a$10$oKUXJBo07rH5J.b0Y0fMGeW7uqoXFrQaBJ19jug4F/pjWig5aRYEK','ROLE_API','client_credentials,password,authorization_code,refresh_token','http://www.51ab.com:8090/cxy/login',NULL,NULL,NULL,NULL,'2019-11-19 03:12:50',NULL,NULL,'ROLE_API');
/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_code`
--

DROP TABLE IF EXISTS `oauth_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_code` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `code` varchar(255) DEFAULT NULL,
  `authentication` blob,
  KEY `code_index` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_code`
--

LOCK TABLES `oauth_code` WRITE;
/*!40000 ALTER TABLE `oauth_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu_button`
--

DROP TABLE IF EXISTS `sys_menu_button`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu_button` (
  `menu_button_id` bigint(20) NOT NULL COMMENT '权限id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级权限id',
  `btn_id` varchar(30) DEFAULT '0' COMMENT '按钮id',
  `name` varchar(32) DEFAULT NULL COMMENT '权限名称',
  `orders` smallint(6) DEFAULT NULL COMMENT '排序',
  `type` tinyint(4) DEFAULT '1' COMMENT '类型(0.菜单1.按钮)',
  `menu_button_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `uri` varchar(50) DEFAULT '' COMMENT '路径',
  `icon` varchar(50) DEFAULT '' COMMENT '图标',
  `state` tinyint(4) DEFAULT '1' COMMENT '状态(0:禁用,1:正常)',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`menu_button_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu_button`
--

LOCK TABLES `sys_menu_button` WRITE;
/*!40000 ALTER TABLE `sys_menu_button` DISABLE KEYS */;
INSERT INTO `sys_menu_button` VALUES (1,0,'msystem','系统管理',1,0,'sys:system:read','/sysbak/system','',1,1534819317),(2,1,'0','用户管理',1,0,'sys:user:read','/mybatis-web/users','',1,1534819317),(3,1,'0','角色管理',2,0,'sys:role:read','/mybatis-web/roles','',0,1534819317),(4,1,'0','菜单按钮管理',3,0,'sys:menuButton:read','/mybatis-web/menuButtons','',0,1534819317),(5,802117135508832256,'0','获取当前用户登陆信息',2,1,'sys:user:extra','/mybatis-web/user/extra','',1,1534819317),(801021872182525952,2,'sysUserAdd','新建用户',2,1,'sys:user:add','/mybatis-web/users','',1,1611020096),(801395839703580672,2,'sysUserEdit','修改用户',3,1,'sys:user:edit','/mybatis-web/users','',1,1611109257),(802108663786504192,2,'sysUserDelete','删除用户',4,1,'sys:user:delete','/mybatis-web/users','',1,1611279207),(802109185474035712,2,'','根据ID获取用户信息',6,1,'','/mybatis-web/users/id','',1,1611279332),(802109332274675712,2,'sysUserSelect','根据条件分页查询用户',7,1,'sys:user:select','/mybatis-web/users','',1,1611279367),(802109680980721664,3,'sysRoleAdd','新建角色',2,1,'sys:role:add','/mybatis-web/roles','',1,1611279450),(802109889886420992,3,'sysRoleEdit','修改角色',3,1,'sys:role:edit','/mybatis-web/roles','',1,1611279500),(802110148427513856,3,'sysRoleDelete','删除角色',4,1,'sys:role:delete','/mybatis-web/roles','',1,1611279561),(802110366585847808,3,'','根据id查询角色',5,1,'','/mybatis-web/roles/id','',1,1611279613),(802110604902006784,3,'sysRoleSelect','根据条件分页查询角色',6,1,'sys:role:select','/mybatis-web/roles','',1,1611279670),(802113572070096896,4,'sysMenuButtonAdd','菜单按钮添加',2,1,'sys:menuButton:add','/mybatis-web/menuButtons','',1,1611280378),(802113772088066048,4,'sysMenuButtonEdit','菜单按钮修改',3,1,'sys:menuButton:edit','/mybatis-web/menuButtons','',1,1611280425),(802114003525566464,4,'sysMenuButtonDelete','菜单按钮删除',4,1,'sys:menuButton:delete','/mybatis-web/menuButtons','',1,1611280480),(802115070900436992,4,'','根据id查询菜单按钮',5,1,'','/mybatis-web/menuButtons/id','',1,1611280735),(802115237414305792,4,'','查询树结构菜单按钮',6,1,'','/mybatis-web/menuButtons','',1,1611280775),(802115668777500672,802109889886420992,'','给角色菜单按钮权限添加',3,1,'','/mybatis-web/menuButtons/saveMenuButtonAndRoleAss','',1,1611280878),(802116253631250432,802109889886420992,'','根据角色Id查询菜单按钮',2,1,'','/mybatis-web/menuButtons/menuButtonsTreeByRoleId','',1,1611281017),(802117030961610752,802117135508832256,'','根据用户Id查询菜单',3,1,'','/mybatis-web/menuButtons/user_menu','',1,1611281202),(802117135508832256,1,'','用户登陆用',4,0,'','','',1,1611281227),(802198829003833344,802117135508832256,'','根据用户Id查询按钮',4,1,'','/mybatis-web/menuButtons/user_button','',1,1611300704);
/*!40000 ALTER TABLE `sys_menu_button` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(50) DEFAULT '' COMMENT '描述',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父角色id',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人',
  `last_modify_user_id` bigint(20) DEFAULT '0' COMMENT '删除人',
  `create_time` bigint(10) DEFAULT '0' COMMENT '创建时间',
  `last_modify_time` bigint(10) DEFAULT '0' COMMENT '删除时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除标志default 0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (110,'admin','超级管理员',0,11011,0,1532139233,1532139233,0),(111,'guest','普通用户',110,11011,0,1532139233,1532139233,0),(112,'票务部','票务部',NULL,11011,0,1541551741,0,1),(113,'结算部','结算部',NULL,11011,0,1541551645,0,1),(114,'财务部','财务部',NULL,11011,0,1541551664,0,1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu_button`
--

DROP TABLE IF EXISTS `sys_role_menu_button`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu_button` (
  `rp_id` bigint(20) NOT NULL COMMENT '角色权限表id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_button_id` bigint(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`rp_id`) USING BTREE,
  KEY `sys_role_permission_roleid` (`role_id`) USING BTREE,
  KEY `sys_role_permission_permission_id` (`menu_button_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu_button`
--

LOCK TABLES `sys_role_menu_button` WRITE;
/*!40000 ALTER TABLE `sys_role_menu_button` DISABLE KEYS */;
INSERT INTO `sys_role_menu_button` VALUES (1,110,1),(3,110,3),(801021872312549376,110,5),(802108084729282560,110,2),(802108202102685696,110,4),(802108663908139008,110,802108663786504192),(802109185490812928,110,802109185474035712),(802109332291452928,110,802109332274675712),(802109681001693184,110,802109680980721664),(802109889911586816,110,802109889886420992),(802110148444291072,110,802110148427513856),(802110366602625024,110,802110366585847808),(802110604914589696,110,802110604902006784),(802113572082679808,110,802113572070096896),(802113772117426176,110,802113772088066048),(802114003542343680,110,802114003525566464),(802115071038849024,110,802115070900436992),(802115237431083008,110,802115237414305792),(802115668802666496,110,802115668777500672),(802116253652221952,110,802116253631250432),(802117030986776576,110,802117030961610752),(802117135525609472,110,802117135508832256),(802117573259952128,110,801021872182525952),(802117574719569920,110,801395839703580672),(802118241244807168,111,5),(802118243748806656,111,802117030961610752),(802118247028752384,111,802117135508832256),(802198829024804864,110,802198829003833344),(802199416768430080,111,802198829003833344);
/*!40000 ALTER TABLE `sys_role_menu_button` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_user`
--

DROP TABLE IF EXISTS `sys_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_user` (
  `ru_id` bigint(20) NOT NULL COMMENT '角色用户表id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`ru_id`) USING BTREE,
  KEY `sys_role_user_roleid` (`role_id`) USING BTREE,
  KEY `sys_role_user_userid` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_user`
--

LOCK TABLES `sys_role_user` WRITE;
/*!40000 ALTER TABLE `sys_role_user` DISABLE KEYS */;
INSERT INTO `sys_role_user` VALUES (654246188308103168,110,11011),(699923545437896704,111,11011),(802194858377871360,111,801001766031327232),(802194978527903744,111,800999678115184640);
/*!40000 ALTER TABLE `sys_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '用户登录名',
  `password` varchar(68) NOT NULL DEFAULT '' COMMENT '密码',
  `other_id` varchar(45) DEFAULT '' COMMENT '微信的openid',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态：0启用，1锁定，2逾期',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '0正常 1删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_user_login` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'1','1',NULL,0,0),(2,'test1','','',0,0),(11011,'admin','$2a$10$fyKNHs2cATlenEPqGxcdtOuVeXT03eNNZPSSL3fOyz6yj6hAilFFC','0',0,0),(800655165140107264,'2','1',NULL,0,0),(800655438810054656,'3','1',NULL,0,0),(800656062314315776,'6','1',NULL,0,0),(800694607473147904,'8','1',NULL,0,0),(800695484204318720,'9','1',NULL,0,0),(800695853047218176,'10','1',NULL,0,0),(800696147948732416,'11','1',NULL,0,0),(800708189816487936,'9','1',NULL,0,1),(800999678115184640,'123','$2a$10$XoEFWYG.gXGFv2zqlMR9puNKHK.TJUdkbBs.K3bj6M1NOxmnoMO/C',NULL,0,0),(801001766031327232,'321','$2a$10$3uXSnIAIZPwbhRfB9GljCO5q9XRtyq7ryMxzPbvheLghgRlwySdlC',NULL,0,0),(801396202758340608,'101','$2a$10$lOqC93K1dv5BpbJJxTLq4Oj78FyGvIGkMXVBIxEkabIgvQFjHl4.S',NULL,0,1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_info`
--

DROP TABLE IF EXISTS `sys_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_info` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `user_name` varchar(30) NOT NULL COMMENT '用户登录名',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实名称',
  `org_id` bigint(20) DEFAULT '0' COMMENT '机构id',
  `org_name` varchar(300) DEFAULT '' COMMENT '机构名称',
  `email` varchar(50) DEFAULT '' COMMENT '电子邮箱',
  `mobile_phone` varchar(20) DEFAULT '0' COMMENT '个人电话',
  `office_phone` varchar(20) DEFAULT '0' COMMENT '办公电话',
  `qq` bigint(20) DEFAULT '0' COMMENT 'qq',
  `we_chat` varchar(50) DEFAULT NULL COMMENT '微信号',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别0：男,1：女',
  `home_address` varchar(40) DEFAULT '' COMMENT '家庭地址',
  `office_address` varchar(40) DEFAULT '' COMMENT '办公地址',
  `default_address` tinyint(4) DEFAULT '0' COMMENT '默认地址:0办公地址，1家庭地址',
  `py_full` varchar(60) DEFAULT '' COMMENT '姓名拼音',
  `py_short` varchar(20) DEFAULT '' COMMENT '名:首字母,字:首字母',
  `position` varchar(10) DEFAULT '' COMMENT '职位',
  `job_number` varchar(15) DEFAULT '' COMMENT '职工号',
  `nationality` varchar(10) DEFAULT '' COMMENT '国籍',
  `birth_date` date DEFAULT NULL COMMENT '出生年月日',
  `last_login_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后登录时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '用户状态：0启用，1锁定，2逾期',
  `activate_status` tinyint(2) DEFAULT '0' COMMENT '激活状态:0未激活 1激活',
  `create_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建用户id',
  `create_time` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `last_modify_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改用户id',
  `last_modify_time` int(11) NOT NULL DEFAULT '0' COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '0正常 1删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_user_info_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_info`
--

LOCK TABLES `sys_user_info` WRITE;
/*!40000 ALTER TABLE `sys_user_info` DISABLE KEYS */;
INSERT INTO `sys_user_info` VALUES (2,'test1','测试2',1,'测试机构1','8366412@qq.com','13606480542','',0,'',0,'','',0,'LiLuBing','llb','测试','123','中国','2020-08-18',0,0,0,0,1597731887,11011,1610930354,0),(11011,'admin','超级管理员',0,'0','8366419@qq.com','0','0',0,NULL,0,'','',0,'admin','','','','',NULL,0,0,0,1,1597731887,1,1595840692,0),(800655165140107264,'2','2',NULL,NULL,'2@qq.com','13606480548',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1610932666,0,1,11011,1610932666,11011,1610932666,0),(800655438810054656,'3','3',NULL,NULL,'3@qq.com','13606480548',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1610932732,0,1,11011,1610932732,11011,1610932732,0),(800656062314315776,'6','66',NULL,NULL,'66@qq.com','13606480546',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1610932880,0,1,11011,1610932880,11011,1610933370,0),(800694607473147904,'8','8',NULL,NULL,'8@qq.com','13333333333',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1610942070,0,1,11011,1610942070,11011,1610942070,0),(800695484204318720,'9','9',NULL,NULL,'9@qq.com','13333333333',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1610942279,0,1,11011,1610942279,11011,1610942279,0),(800695853047218176,'10','10',NULL,NULL,'9@qq.com','13333333333',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1610942367,0,1,11011,1610942367,11011,1610942367,0),(800696147948732416,'11','11',NULL,NULL,'9@qq.com','13333333333',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1610942437,0,1,11011,1610942437,11011,1610942437,0),(800708189816487936,'9','9',NULL,NULL,'9@qq.com','13699999999',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1610945308,0,1,11011,1610945308,11011,1610945308,1),(800999678115184640,'123','123',NULL,NULL,'123@qq.com','13333333333',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1611014805,0,1,11011,1611014805,11011,1611299786,0),(801001766031327232,'321','321',NULL,NULL,'321@qq.com','13333333331',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1611015302,0,1,11011,1611015302,11011,1611299758,0),(801396202758340608,'101','1101',NULL,NULL,'101@qq.com','13333333333',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1611109343,0,1,11011,1611109343,11011,1611109364,1);
/*!40000 ALTER TABLE `sys_user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-22 20:15:29
