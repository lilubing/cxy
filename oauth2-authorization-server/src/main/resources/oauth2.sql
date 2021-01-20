DROP TABLE IF EXISTS `oauth_client_details`;
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
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `archived` tinyint(4) NULL DEFAULT NULL,
  `trusted` tinyint(4) NULL DEFAULT NULL,
  `autoapprove` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'false/true/read/write。默认false,适用于authorization_code模式,设置用户是否自动approval操作,设置true跳过用户确认授权操作页面，直接跳到redirect_uri	',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT = '接入客户端信息' ROW_FORMAT = Dynamic;



INSERT INTO `oauth_client_details` (`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`create_time`,`archived`,`trusted`,`autoapprove`) VALUES ('c1','res1','$2a$10$oKUXJBo07rH5J.b0Y0fMGeW7uqoXFrQaBJ19jug4F/pjWig5aRYEK','ROLE_ADMIN,ROLE_USER,ROLE_API','client_credentials,password,authorization_code,implicit,refresh_token','http://www.baidu.com',NULL,7200,259200,NULL,'2019-11-18 14:15:06',0,0,'false');
INSERT INTO `oauth_client_details` (`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`create_time`,`archived`,`trusted`,`autoapprove`) VALUES ('c2','res2','$2a$10$lEyJwkP.Ws6qYM8IcpNyC.mr1N7E8WD4QWbmchAPHMhCDxAEN59te','ROLE_API','client_credentials,password,authorization_code,implicit,refresh_token','http://www.51ab.com:8090/cxy-web/login',NULL,7200,259200,NULL,'2020-02-28 15:50:25',0,0,'ROLE_API');
INSERT INTO `oauth_client_details` (`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`create_time`,`archived`,`trusted`,`autoapprove`) VALUES ('CouponSystem','','$2a$10$dYRcFip80f0jIKGzRGulFelK12036xWQKgajanfxT65QB4htsEXNK','user_info','authorization_code','http://www.51ab.com:8090/cxy/login',NULL,NULL,NULL,NULL,'2019-11-19 07:56:31',NULL,NULL,'user_info');
INSERT INTO `oauth_client_details` (`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`create_time`,`archived`,`trusted`,`autoapprove`) VALUES ('MemberSystem','res1','$2a$10$oKUXJBo07rH5J.b0Y0fMGeW7uqoXFrQaBJ19jug4F/pjWig5aRYEK','ROLE_API','client_credentials,password,authorization_code,refresh_token','http://www.51ab.com:8090/cxy/login',NULL,NULL,NULL,NULL,'2019-11-19 11:12:50',NULL,NULL,'ROLE_API');

DROP TABLE IF EXISTS `auth_code`;
CREATE TABLE `oauth_code`  (
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  INDEX `code_index`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

{bcrypt}$2a$10$lEyJwkP.Ws6qYM8IcpNyC.mr1N7E8WD4QWbmchAPHMhCDxAEN59te

select * from oauth_client_details;
select * from oauth_code;
