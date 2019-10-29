package com.newcapec.configserver.service.impl;

import com.newcapec.configserver.constant.HttpEnum;
import com.newcapec.configserver.constant.IsDeletedEnum;
import com.newcapec.configserver.dao.EnvironmentDAO;
import com.newcapec.configserver.pojo.common.ResultBean;
import com.newcapec.configserver.pojo.model.Environment;
import com.newcapec.configserver.service.EnvironmentService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jqq
 * @version 1.0
 * @description
 * @date 2019/6/17 13:40
 **/
@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EnvironmentDAO environmentDAO;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean add(Environment environment) {
        environment.setIsDeleted(IsDeletedEnum.NO.code());
        List<Environment> existedEnvironment = environmentDAO.check(environment);
        if (CollectionUtils.isNotEmpty(existedEnvironment)) {
            return ResultBean.fail(HttpEnum.BAD_REQUEST.code(), "该环境已经存在");
        }
        environmentDAO.insertSelective(environment);
        return ResultBean.ok(environment);
    }

    @Override
    public ResultBean query(Environment environment) {
        environment.setIsDeleted(IsDeletedEnum.NO.code());
        return ResultBean.ok(environmentDAO.query(environment));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean modify(Environment environment) {
        environmentDAO.updateByPrimaryKeySelective(environment);
        return ResultBean.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean delete(Integer id) {
        Environment environment = new Environment();
        environment.setId(id);
        environment.setIsDeleted(IsDeletedEnum.YES.code());
        environmentDAO.updateByPrimaryKeySelective(environment);
        return ResultBean.ok();
    }
}
