package com.newcapec.configserver.controller;

import com.newcapec.configserver.pojo.common.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jqq
 * @version 1.0
 * @description
 * @date 2019/7/2 16:31
 **/
@Controller
public class CommonController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/index");
        return modelAndView;
    }

    @RequestMapping("/refresh")
    @ResponseBody
    public ResultBean flushCache() {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
        return ResultBean.ok();
    }
}
