package com.newcapec.configserver.service.impl;

import com.newcapec.configserver.constant.HttpEnum;
import com.newcapec.configserver.constant.IsDeletedEnum;
import com.newcapec.configserver.dao.AppDAO;
import com.newcapec.configserver.pojo.common.ResultBean;
import com.newcapec.configserver.pojo.model.App;
import com.newcapec.configserver.service.AppService;
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
 * @date 2019/6/17 13:37
 **/
@Service
public class AppServiceImpl implements AppService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AppDAO appDAO;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean add(App app) {
        app.setIsDeleted(IsDeletedEnum.NO.code());
        List<App> existedApp = appDAO.check(app);
        if (CollectionUtils.isNotEmpty(existedApp)) {
            return ResultBean.fail(HttpEnum.BAD_REQUEST.code(), "该应用已经存在");
        }
        appDAO.insertSelective(app);
        return ResultBean.ok(app);
    }

    @Override
    public ResultBean query(App app) {
        app.setIsDeleted(IsDeletedEnum.NO.code());
        return ResultBean.ok(appDAO.query(app));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean modify(App app) {
        appDAO.updateByPrimaryKeySelective(app);
        return ResultBean.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean delete(Integer id) {
        App app = new App();
        app.setId(id);
        app.setIsDeleted(IsDeletedEnum.YES.code());
        appDAO.updateByPrimaryKeySelective(app);
        return ResultBean.ok();
    }

    @Override
    public ResultBean queryAll() {
        return ResultBean.ok(appDAO.queryAll());
    }
}
