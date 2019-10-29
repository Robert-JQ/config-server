package com.newcapec.configserver.configuration;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @author jqq
 * @version 1.0
 * @description
 * @date 2019/6/24 11:48
 **/
@Component
public class RocketMQProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${rocketmq.producer-id}")
    private String producerId;

    @Value("${rocketmq.access-key}")
    private String accessKey;

    @Value("${rocketmq.secret-key}")
    private String secretKey;

    @Value("${rocketmq.ons-addr}")
    private String onsAddr;

    @Value("${rocketmq.send-msg-timeout-millis}")
    private String sendMsgTimeoutMillis;

    @Value("${rocketmq.topic}")
    private String topic;

    private static Producer producer;

    @PostConstruct
    public void init() {
        logger.info("初始化生产者！");
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        producerProperties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        producerProperties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, sendMsgTimeoutMillis);
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, onsAddr);
        producerProperties.setProperty(PropertyKeyConst.GROUP_ID, producerId);
        producer = ONSFactory.createProducer(producerProperties);
        producer.start();
    }

    public Producer getProducer() {
        return producer;
    }
}
