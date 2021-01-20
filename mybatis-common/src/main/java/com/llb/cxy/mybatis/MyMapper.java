package com.llb.cxy.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * description: 继承自己的MyMapper <br>
 * date: 2020/3/25 10:26 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public interface MyMapper<T, PK> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}