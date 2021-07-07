package org.tinygame.herostory.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public final class RankProducer {
    static private Logger logger = LogManager.getLogger();

    static private DefaultMQProducer producer;

    private RankProducer() {
    }

    static public void init() {
        producer = new DefaultMQProducer("pgp");
        producer.setNamesrvAddr("192.168.177.151::9876");
        try {
            producer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void send(String topic, Object meg) {
        try {
            Message message = new Message(topic, JSONObject.toJSONBytes(meg));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("Rank消息发送成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    logger.error("Rank消息发送失败", throwable);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
