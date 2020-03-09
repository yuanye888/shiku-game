package com.shiku.robot.shikugame.untils;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @Author lijun
 * @Description 分页类
 * @Date 2019-12-22 5:03 下午
 **/

public class PageFatherUtil<T> {

    public List<T> find(MongoTemplate mongoTemplate, Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz);
    }

    public List<T> find(MongoTemplate mongoTemplate, Query query, Class<T> clazz, String collectionName) {
        return mongoTemplate.find(query, clazz, collectionName);
    }

    /**
     * 通过条件查询,查询分页结果
     *
     * @param PageUtil 分页信息
     * @param query  查询条件
     * @param clazz  clazz
     * @return 分页数据
     */
    public PageUtil<T> getPage(MongoTemplate mongoTemplate, PageUtil<T> PageUtil, Query query, Class<T> clazz) {
        //查询总条数
        long totalCount = mongoTemplate.count(query, clazz);
        //设置总条数
        PageUtil.setTotalCount(totalCount);
        //skip相当于从那条记录开始
        query.skip(PageUtil.getFirstResult());
        //从skip开始,取多少条记录
        query.limit(PageUtil.getPageSize());
        List<T> list = this.find(mongoTemplate, query, clazz);
        PageUtil.setData(list);
        return PageUtil;
    }

    /**
     * 通过条件查询,查询分页结果
     *
     * @param PageUtil         分页信息
     * @param query          查询条件
     * @param clazz          clazz
     * @param collectionName 集合名称
     * @return 分页数据
     */
    public PageUtil<T> getPage(MongoTemplate mongoTemplate, PageUtil<T> PageUtil, Query query, Class<T> clazz, String collectionName) {
        //查询总条数
        long totalCount = mongoTemplate.count(query, clazz, collectionName);
        //设置总条数
        PageUtil.setTotalCount(totalCount);
        //skip相当于从那条记录开始
        query.skip(PageUtil.getFirstResult());
        //从skip开始,取多少条记录
        query.limit(PageUtil.getPageSize());
        List<T> list = this.find(mongoTemplate, query, clazz, collectionName);
        PageUtil.setData(list);
        return PageUtil;
    }
}
