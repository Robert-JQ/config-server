package com.newcapec.configserver.service;

import com.newcapec.configserver.pojo.common.ResultBean;
import com.newcapec.configserver.pojo.model.App;

/**
 * @author jqq
 * @version 1.0
 * @description
 * @date 2019/6/17 11:46
 **/
public interface AppService extends BaseService<App>{

    ResultBean queryAll();

}
