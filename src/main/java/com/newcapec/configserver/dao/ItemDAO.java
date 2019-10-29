package com.newcapec.configserver.dao;

import com.newcapec.configserver.pojo.model.Item;

import java.util.List;

/**
 * @author jqq
 * @description ItemDAO继承基类
 * @date 2019/6/17 11:31
 * @copyright 2016-2019 新开普 - Powered By 研发中心
 * @version 1.0
 */
public interface ItemDAO extends BaseDao<Item, Integer> {

    List<Item> query(Item item);

    List<Item> check(Item item);

}