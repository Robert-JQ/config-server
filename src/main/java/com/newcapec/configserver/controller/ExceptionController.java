package com.newcapec.configserver.controller;

import com.newcapec.configserver.constant.HttpEnum;
import com.newcapec.configserver.pojo.common.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jqq
 * @version 1.0
 * @description 统一异常处理controller
 * @date 2019/6/17 13:59
 **/
@RestControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @author jqq
     * @description 全局统一异常处理
     * @date 2019/6/17 14:04
     * @param e
     * @return com.newcapec.configserver.pojo.common.ResultBean
     */
    @ExceptionHandler(Exception.class)
    public ResultBean globalException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultBean.fail(HttpEnum.INTERNAL_SERVER_ERROR.code(), HttpEnum.INTERNAL_SERVER_ERROR.msg());
    }

    /**
     * @author jqq
     * @description http请求方法不支持异常处理
     * @date 2019/6/18 11:11
     * @param e
     * @return com.newcapec.configserver.pojo.common.ResultBean
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBean methodNotSupported(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return ResultBean.fail(HttpEnum.METHOD_NOT_ALLOWED.code(), HttpEnum.METHOD_NOT_ALLOWED.msg());
    }

    /**
     * @author jqq
     * @description 请求参数丢失，主要因为requestbody注解的参数没有传
     * @date 2019/6/18 11:16
     * @param e
     * @return com.newcapec.configserver.pojo.common.ResultBean
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultBean paramNotExisted(HttpMessageNotReadableException e) {
        logger.error(e.getMessage(), e);
        return ResultBean.fail(HttpEnum.BAD_REQUEST.code(), "请求参数不存在或参数体为空");
    }

    /**
     * @author jqq
     * @description 请求参数丢失，主要因为requestparam注解的参数没有传
     * @date 2019/6/18 11:16
     * @param e
     * @return com.newcapec.configserver.pojo.common.ResultBean
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultBean paramMiss(MissingServletRequestParameterException e) {
        logger.error(e.getMessage(), e);
        return ResultBean.fail(HttpEnum.BAD_REQUEST.code(), "请求参数不存在");
    }

    /**
     * @author jqq
     * @description 请求参数丢失，主要因为requestbody注解的参数字段未通过校验
     * @date 2019/6/18 11:16
     * @param e
     * @return com.newcapec.configserver.pojo.common.ResultBean
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBean paramError(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        String msg = "请求参数错误";
        if (e.getBindingResult().hasErrors()) {
            msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        return ResultBean.fail(HttpEnum.BAD_REQUEST.code(), msg);
    }

    /**
     * @author jqq
     * @description 请求参数字段未通过校验
     * @date 2019/6/18 11:16
     * @param e
     * @return com.newcapec.configserver.pojo.common.ResultBean
     */
    @ExceptionHandler(ValidationException .class)
    public ResultBean paramNotValid(ValidationException e) {
        logger.error(e.getMessage(), e);
        String msg = "";
        if(e instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            Iterator<ConstraintViolation<?>> iterator = violations.iterator();
            if (iterator.hasNext()) {
                msg = iterator.next().getMessage();
            }
        }
        return ResultBean.fail(HttpEnum.BAD_REQUEST.code(), msg);
    }

}
