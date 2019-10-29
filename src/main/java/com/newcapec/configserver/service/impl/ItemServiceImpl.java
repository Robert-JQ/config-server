package com.newcapec.configserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newcapec.configserver.configuration.RocketMQProducer;
import com.newcapec.configserver.constant.GlobalConstant;
import com.newcapec.configserver.constant.HttpEnum;
import com.newcapec.configserver.constant.IsDeletedEnum;
import com.newcapec.configserver.constant.MessageConstant;
import com.newcapec.configserver.dao.ItemDAO;
import com.newcapec.configserver.pojo.common.MessageBody;
import com.newcapec.configserver.pojo.common.ResultBean;
import com.newcapec.configserver.pojo.model.Item;
import com.newcapec.configserver.service.ItemService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jqq
 * @version 1.0
 * @description
 * @date 2019/6/17 13:39
 **/
@Service
public class ItemServiceImpl implements ItemService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ItemDAO itemDAO;

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @Value("${rocketmq.topic}")
    private String topic;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean add(Item item) {
        item.setIsDeleted(IsDeletedEnum.NO.code());
        List<Item> existedItem = itemDAO.check(item);
        if (CollectionUtils.isNotEmpty(existedItem)) {
            return ResultBean.fail(HttpEnum.BAD_REQUEST.code(), "该配置项已经存在");
        }
        itemDAO.insertSelective(item);
        sendMessage(item, MessageConstant.OPERATION_ADD);
        return ResultBean.ok(item);
    }

    @Override
    public ResultBean query(Item item) {
        item.setIsDeleted(IsDeletedEnum.NO.code());
        return ResultBean.ok(itemDAO.query(item));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean modify(Item item) {
        itemDAO.updateByPrimaryKeySelective(item);
        sendMessage(item, MessageConstant.OPERATION_UPDATE);
        return ResultBean.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBean delete(Integer id) {
        Item item = itemDAO.selectByPrimaryKey(id);
        if (item == null) {
            return ResultBean.fail(HttpEnum.INTERNAL_SERVER_ERROR.code(), "未查询到配置项");
        }
        item.setIsDeleted(IsDeletedEnum.YES.code());
        itemDAO.updateByPrimaryKeySelective(item);
        sendMessage(item, MessageConstant.OPERATION_DELETE);
        return ResultBean.ok();
    }

    private void sendMessage(Item item, String operation) {
        Producer producer = rocketMQProducer.getProducer();
        String tag = item.getAppid() + MessageConstant.TAG_SPLITER + item.getEnvid();
        MessageBody body = MessageBody.newBody(operation, item.getKey(), item.getValue());
        try {
            Message message = new Message(topic, tag, JSON.toJSONString(body).getBytes(GlobalConstant.CHARSET));
            SendResult sendResult = producer.send(message);
            if (sendResult == null) {
                logger.error("发送消息失败,{}配置项:{}", operation, item);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("发行消息失败,{}配置项,不支持的字符集编码:{}", operation, GlobalConstant.CHARSET);
        }
    }

    @Override
    public ResultBean page(Integer page, Integer limit, String appid, Integer envid, String key) {
        PageHelper.startPage(page, limit);
        Item param = new Item();
        param.setAppid(StringUtils.isEmpty(appid) ? null : appid);
        param.setEnvid(envid);
        param.setKey(StringUtils.isEmpty(key) ? null : key);
        List<Item> itemList = itemDAO.query(param);
        PageInfo<Item> pageInfo = new PageInfo<>(itemList);
        Map<String, Object> data = new HashMap<>();
        data.put("count", pageInfo.getTotal());
        data.put("items", pageInfo.getList());
        return ResultBean.ok(data);
    }
}
