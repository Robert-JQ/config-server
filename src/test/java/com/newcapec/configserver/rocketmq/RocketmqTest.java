package com.newcapec.configserver.rocketmq;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.producer.SendCallback;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.producer.SendResult;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.Message;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author jqq
 * @version 1.0
 * @description
 * @date 2019/6/14 11:22
 **/
public class RocketmqTest {

    @Test
    public void syncProducerTest() {
        DefaultMQProducer producer =  new DefaultMQProducer("test");
        producer.setNamesrvAddr("localhost:9876");
        try {
            producer.start();
            for (int i = 0; i < 100; i++) {
                Message msg = new Message("TopicTest", "TagA",
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

    @Test
    public void asynProducerTest() {
        DefaultMQProducer producer = new DefaultMQProducer("test");
        producer.setNamesrvAddr("localhost:9876");
        try {
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(0);
            for (int i = 0; i < 100; i++) {
                final int index = i;
                Message msg = new Message("TopicTest", "TagA",
                        "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                    }
                });
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

    @Test
    public void onewayProducerTest() {
        DefaultMQProducer producer = new DefaultMQProducer("test");
        producer.setNamesrvAddr("localhost:9876");
        try {
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(0);
            for (int i = 0; i < 100; i++) {
                final int index = i;
                Message msg = new Message("TopicTest", "TagA",
                         "Hello RocketMQ".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.sendOneway(msg);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

    @Test
    public void consumerTest() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test");
        consumer.setNamesrvAddr("localhost:9876");
        try {
            consumer.subscribe("TopicTest", "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, consumeConcurrentlyContext) -> {
                System.out.printf("%s Receive New Message: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            System.out.printf("Consumer Started.%n");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
