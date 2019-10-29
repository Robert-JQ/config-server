package com.newcapec.configserver.controller;

import com.newcapec.configserver.pojo.common.ResultBean;
import com.newcapec.configserver.pojo.model.Environment;
import com.newcapec.configserver.service.EnvironmentService;
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
 * @description 应用环境controller
 * @date 2019/6/17 11:43
 **/
@RestController
@RequestMapping("/env")
@Validated
public class EnvironmentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EnvironmentService environmentService;

    @PostMapping("/add")
    public ResultBean add(@RequestBody @Valid Environment environment) {
        ResultBean result = environmentService.add(environment);
        logger.info("[新建环境,入参:{},出参{}]", environment, result);
        return result;
    }

    @PostMapping("/query")
    public ResultBean query(@RequestBody Environment environment) {
        ResultBean result = environmentService.query(environment);
        logger.info("[查询环境,入参:{},出参:{}]", environment, result);
        return result;
    }

    @PutMapping("/modify")
    public ResultBean modify(@RequestBody Environment environment) {
        ResultBean result = environmentService.modify(environment);
        logger.info("[更新环境,入参:{},出参:{}]", environment, result);
        return result;
    }

    @DeleteMapping("/delete")
    public ResultBean delete(@RequestParam @Min(value = 1, message = "id不能小于1") Integer id) {
        ResultBean result = environmentService.delete(id);
        logger.info("[删除环境,入参:{},出参:{}]", id, result);
        return result;
    }
}
