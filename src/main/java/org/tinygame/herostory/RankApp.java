package org.tinygame.herostory;

import org.tinygame.herostory.mq.RankConsumer;
import org.tinygame.herostory.utils.RedisUtil;

/**
 * 通过消息中间件实现了排行榜功能与游戏主服务器解耦
 *
 * 如果排行榜功能变更只需要单独重新部署排行榜服务，而不需要重启整个游戏服务器
 */
public class RankApp {
    public static void main(String[] args) {
        RedisUtil.init();

        RankConsumer.consumer();
    }
}
