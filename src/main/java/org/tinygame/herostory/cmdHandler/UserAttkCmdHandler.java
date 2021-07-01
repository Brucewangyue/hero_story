package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.tinygame.herostory.BroadCaster;
import org.tinygame.herostory.api.GameMsgProtocol;

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

        GameMsgProtocol.UserAttkResult attackResult = GameMsgProtocol.UserAttkResult.newBuilder()
                .setAttkUserId(userId)
                .setTargetUserId(targetUserId)
                .build();

        GameMsgProtocol.UserSubtractHpResult subtractHpResult =  GameMsgProtocol.UserSubtractHpResult.newBuilder()
                .setTargetUserId(targetUserId)
                .setSubtractHp(10)
                .build();

        BroadCaster.broadCaste(attackResult);
        BroadCaster.broadCaste(subtractHpResult);
    }
}
