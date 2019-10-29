package com.newcapec.configserver.dao;

import java.io.Serializable;

/**
 * @author jqq
 * @description DAO公共基类
 * @param <M> The M Class 这里是泛型不是M类
 * @param <P> The Primary Key Class 如果是无主键，则可以用M来跳过，如果是多主键则是Key类
 * @date 2019/6/17 11:22
 * @copyright 2016-2019 新开普 - Powered By 研发中心
 * @version 1.0
 */
public interface BaseDao<M, P extends Serializable> {
    int deleteByPrimaryKey(P id);

    int insert(M record);

    int insertSelective(M record);

    M selectByPrimaryKey(P id);

    int updateByPrimaryKeySelective(M record);

    int updateByPrimaryKey(M record);
}