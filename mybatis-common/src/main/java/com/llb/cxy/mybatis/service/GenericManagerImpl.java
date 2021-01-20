package com.llb.cxy.mybatis.service;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.llb.cxy.core.SystemContext;
import com.llb.cxy.mybatis.MyMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * description: GenericManagerImpl <br>
 * date: 2020/3/25 17:05 <br>
 * author: LiLuBing <br>
 * version: 1.0 <br>
 * @author lilubing
 */
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * GenericDao instance, set by constructor of child classes
     */
    protected MyMapper<T, PK> mapper;

    public GenericManagerImpl() {}

    public GenericManagerImpl(MyMapper<T, PK> mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(PK id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public List<T> getAll() {
        return mapper.selectAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer save(T object) {
        return mapper.insert(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer update(T object) {
        return mapper.updateByPrimaryKey(object);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Integer delete(PK id) {
        return mapper.deleteByPrimaryKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Integer deletes(String ids) {
        Integer result = 0;
        String[] arr = ids.split(",");
        for (String id : arr) {
            if(null != id && "" != id) {
                result += mapper.deleteByPrimaryKey((PK) Long.valueOf(id));
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageInfo<T> searchPaginated(String conditions) {
        return null;
    }

    /**
     * 获取分页信息
     * @return Map
     */
    public Map<String, Object> getPageInfoMap() {
        Map<String,Object> data = Maps.newHashMap();
        int startNum = (SystemContext.getOffset() - 1) * SystemContext.getPageSize();
        if(startNum < 0) {
            startNum = 0;
        }
        int endNum = SystemContext.getPageSize();
        data.put("pageNum", startNum);
        data.put("pageSize", endNum);
        return data;
    }
}