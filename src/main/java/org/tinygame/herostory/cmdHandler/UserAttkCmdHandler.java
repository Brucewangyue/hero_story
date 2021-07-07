package org.tinygame.herostory.cmdHandler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.tinygame.herostory.BroadCaster;
import org.tinygame.herostory.api.GameMsgProtocol;
import org.tinygame.herostory.entity.User;
import org.tinygame.herostory.entity.UserManager;
import org.tinygame.herostory.mq.AttackResultMessage;
import org.tinygame.herostory.mq.RankProducer;

/**
 * 用户攻击
 */
public class UserAttkCmdHandler implements CmdHandler<GameMsgProtocol.UserAttkCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserAttkCmd cmd) {
        if (null == cmd)
            return;

        int userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        int targetUserId = cmd.getTargetUserId();

        int subHp = 10;
        User user = UserManager.getById(userId);

        GameMsgProtocol.UserAttkResult attackResult = GameMsgProtocol.UserAttkResult.newBuilder()
                .setAttkUserId(userId)
                .setTargetUserId(targetUserId)
                .build();
        BroadCaster.broadCaste(attackResult);

        if (user.getHP() > 0) {
            user.setHP(user.getHP() - subHp);
            GameMsgProtocol.UserSubtractHpResult subtractHpResult = GameMsgProtocol.UserSubtractHpResult.newBuilder()
                    .setTargetUserId(targetUserId)
                    .setSubtractHp(subHp)
                    .build();
            BroadCaster.broadCaste(subtractHpResult);

            // 死亡后记录消息
            if (user.getHP() <= 0) {
                user.setDied(true);
                AttackResultMessage attackResultMessage = new AttackResultMessage();
                attackResultMessage.setWinnerId(userId);
                attackResultMessage.setLoserId(targetUserId);
                RankProducer.send("Rank", attackResultMessage);
            }
        }

        if (user.getHP() <= 0) {
            GameMsgProtocol.UserDieResult userDieResult = GameMsgProtocol.UserDieResult.newBuilder()
                    .setTargetUserId(targetUserId)
                    .build();
            BroadCaster.broadCaste(userDieResult);
        }
    }
}
