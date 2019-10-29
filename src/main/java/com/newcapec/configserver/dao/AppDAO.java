package com.newcapec.configserver.dao;

import com.newcapec.configserver.pojo.model.App;

import java.util.List;

/**
 * @author jqq
 * @description 应用dao层
 * @date 2019/6/18 10:13
 * @copyright 2016-2019 新开普 - Powered By 研发中心
 * @version 1.0
 */
public interface AppDAO extends BaseDao<App, Integer> {

    List<App> query(App app);

    List<App> queryAll();

    List<App> check(App app);

}