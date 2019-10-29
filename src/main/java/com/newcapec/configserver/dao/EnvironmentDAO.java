package com.newcapec.configserver.dao;

import com.newcapec.configserver.pojo.model.Environment;

import java.util.List;

/**
 * @author jqq
 * @description EnvironmentDAO继承基类
 * @date 2019/6/17 11:26
 * @copyright 2016-2019 新开普 - Powered By 研发中心
 * @version 1.0
 */
public interface EnvironmentDAO extends BaseDao<Environment, Integer> {

    List<Environment> query(Environment environment);

    List<Environment> check(Environment environment);

}