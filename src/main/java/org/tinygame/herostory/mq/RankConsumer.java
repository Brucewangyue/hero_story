package org.tinygame.herostory.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.tinygame.herostory.utils.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

public final class RankConsumer {
    static public void consumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cgp");
        consumer.setNamesrvAddr("192.168.177.151::9876");
        try {
            consumer.subscribe("rank", "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    try (Jedis jedis = RedisUtil.getClient()) {
                        // 写入排行榜数据
                        for (MessageExt messageExt : list) {
                            AttackResultMessage attackResultMessage = JSONObject.parseObject(messageExt.getBody(), AttackResultMessage.class);

                            jedis.zincrby("Rank", 1, String.valueOf(attackResultMessage.getWinnerId()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
