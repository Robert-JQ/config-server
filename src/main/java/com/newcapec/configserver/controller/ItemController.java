package com.newcapec.configserver.controller;

import com.newcapec.configserver.pojo.common.ResultBean;
import com.newcapec.configserver.pojo.model.Item;
import com.newcapec.configserver.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @description 配置项controller
 * @date 2019/6/17 11:42
 **/
@RestController
@RequestMapping("/item")
@Validated
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    public ResultBean add(@RequestBody @Valid Item item) {
        ResultBean result = itemService.add(item);
        logger.info("[新建配置项,入参:{},出参{}]", item, result);
        return result;
    }

    @PostMapping("/query")
    public ResultBean query(@RequestBody Item item) {
        ResultBean result = itemService.query(item);
        logger.info("[查询配置项,入参:{},出参:{}]", item, result);
        return result;
    }

    @PutMapping("/modify")
    public ResultBean modify(@RequestBody Item item) {
        ResultBean result = itemService.modify(item);
        logger.info("[更新配置项,入参:{},出参:{}]", item, result);
        return result;
    }

    @DeleteMapping("/delete")
    public ResultBean delete(@RequestParam @Min(value = 1, message = "id不能小于1") Integer id) {
        ResultBean result = itemService.delete(id);
        logger.info("[删除配置项,入参:{},出参:{}]", id, result);
        return result;
    }

    @GetMapping("/page")
    public ResultBean page(Integer page, Integer limit, String appid, Integer envid, String key) {
        ResultBean result = itemService.page(page, limit, appid, envid, key);
        logger.info("[配置项分页查询,页码:{},每页条数:{},应用id:{},环境id:{},key:{},出参:{}]",
                page, limit, appid, envid, key, result);
        return result;
    }
}
