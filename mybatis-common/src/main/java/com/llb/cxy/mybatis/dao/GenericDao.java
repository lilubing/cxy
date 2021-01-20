package com.llb.cxy.mybatis.dao;

import com.github.pagehelper.PageInfo;
import com.llb.cxy.mybatis.MyMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: GenericDao
* @Description: TODO(这里用一句话描述这个类的作用)
* @author LiLuBing
* @date 2015年4月7日 上午11:22:35
*
* @param <T>
* @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> extends MyMapper<T, PK> {

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see
     */
    //T get(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the persisted object
     */
    //T save(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    //void remove(PK id);

	/**
	 * SQL无缓存查询 查询元数据表信息
	 * @param conditions
	 * @return
	 */
	//PageInfo<T> searchPaginated(Map conditions);

	/**
	 * 分页查询返回Object
	 * @param sql
	 * @param params
	 * @return
	 */
	//PageInfo<Object> searchPaginatedObjectBySql(String sql, Object... params);

}