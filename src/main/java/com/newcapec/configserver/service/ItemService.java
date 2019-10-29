package com.newcapec.configserver.service;

import com.newcapec.configserver.pojo.common.ResultBean;
import com.newcapec.configserver.pojo.model.Item;

/**
 * @author jqq
 * @version 1.0
 * @description
 * @date 2019/6/17 13:38
 **/
public interface ItemService extends BaseService<Item>{

    ResultBean page(Integer page, Integer limit, String appid, Integer envid, String key);

}
