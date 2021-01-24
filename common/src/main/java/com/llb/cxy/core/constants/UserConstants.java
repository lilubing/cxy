package com.llb.cxy.core.constants;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.core.constants
 * @Description: 用户常量
 * @ClassName: UserConstants
 * @date 2021-01-21 上午9:08
 * @ProjectName cxy
 * @Version V1.0
 */
public class UserConstants {
    /**
     * redis使用的时候注意命名空间，一个项目一个命名空间，项目内业务不同命名空间也不同。
     *
     * 一般情况下：
     *
     *   1) 第一段放置项目名或缩写 如 project
     *
     *   1) 第二段把表名转换为key前缀 如, user:
     *
     *   2) 第三段放置用于区分区key的字段,对应mysql中的主键的列名,如userid
     *
     *   3) 第四段放置主键值,如18,16
     *
     * 结合起来  PRO:USER:UID:18  是不是很清晰
     *
     * 常见的设置登录token
     *
     * key：  PRO:USER:LOGINNAME:373166324
     *
     * value：12kd-dsj5ce-d4445-h4sd472
     **/
    public static final String REDIS_USER_PRIVILEGES_KEY_PREFIX = "oauth2:user:privileges";
}
