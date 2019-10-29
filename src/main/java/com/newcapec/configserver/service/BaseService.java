package com.newcapec.configserver.service;

import com.newcapec.configserver.pojo.common.ResultBean;

/**
 * @author jqq
 * @version 1.0
 * @description
 * @date 2019/6/20 19:57
 **/
public interface BaseService<T> {

    ResultBean add(T app);

    ResultBean query(T app);

    ResultBean modify(T app);

    ResultBean delete(Integer id);
}
