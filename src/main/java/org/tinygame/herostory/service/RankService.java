package org.tinygame.herostory.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tinygame.herostory.async.AsyncOperation;
import org.tinygame.herostory.async.AsyncThreadProcessor;
import org.tinygame.herostory.entity.RankItem;
import org.tinygame.herostory.utils.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class RankService {
    static private final Logger logger = LogManager.getLogger();
    static private final RankService instance = new RankService();

    private RankService() {
    }

    static public RankService getInstance() {
        return instance;
    }

    public void getRank(Function<List<RankItem>, Void> callback) {
        if (null == callback) return;

        AsyncOperation asyncOperation = new AsyncOperation() {
            private List<RankItem> rankItemList;

            @Override
            public void doAsync() {
                // 从redis获取排行榜数据
                try (Jedis jedis = RedisUtil.getClient()) {
                    rankItemList = new ArrayList<>();

                    Set<Tuple> ranks = jedis.zrangeWithScores("Rank", 0, 9);
                    int index = 1;
                    for (Tuple rank : ranks) {
                        int userId = Integer.parseInt(rank.getElement());
                        RankItem rankItem = new RankItem();
                        rankItem.setRankId(index);
                        rankItem.setUserId(userId);
                        rankItem.setWin( (int) rank.getScore());

                        // 从redis 获取用户信息
//                        String userBasicInfoStr = jedis.hget("User" + userId, "BasicInfo");
//                        JSONObject jsonObject = JSONObject.parseObject(userBasicInfoStr);
//                        String heroAvatar = jsonObject.getString("heroAvatar");

                        rankItemList.add(rankItem);

                        index++;
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

            @Override
            public void doFinishSync() {
                callback.apply(rankItemList);
            }
        };

        AsyncThreadProcessor.process(asyncOperation);
    }
}
