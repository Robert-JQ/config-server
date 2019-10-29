package com.newcapec.configserver.controller;

import com.newcapec.configserver.pojo.common.ResultBean;
import com.newcapec.configserver.pojo.model.App;
import com.newcapec.configserver.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author jqq
 * @version 1.0
 * @description 应用controller
 * @date 2019/6/17 11:41
 **/
@RestController
@RequestMapping("/app")
@Validated
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppService appService;

    @PostMapping("/add")
    public ResultBean add(@RequestBody @Valid App app) {
        ResultBean result = appService.add(app);
        logger.info("[新建项目,入参:{},出参:{}]", app, result);
        return result;
    }

    @PostMapping("/query")
    public ResultBean query(@RequestBody App app) {
        ResultBean result = appService.query(app);
        logger.info("[查询项目,入参:{},出参:{}]", app, result);
        return result;
    }

    @PutMapping("/modify")
    public ResultBean modify(@RequestBody App app) {
        ResultBean result = appService.modify(app);
        logger.info("[更新项目,入参:{},出参:{}]", app, result);
        return result;
    }

    @DeleteMapping("/delete")
    public ResultBean delete(@RequestParam @Min(value = 1, message = "id不能小于1") Integer id) {
        ResultBean result = appService.delete(id);
        logger.info("[删除项目,入参:{},出参:{}]", id, result);
        return result;
    }
}
