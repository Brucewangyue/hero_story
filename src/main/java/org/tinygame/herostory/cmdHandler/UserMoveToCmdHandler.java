package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.tinygame.herostory.BroadCaster;
import org.tinygame.herostory.api.GameMsgProtocol;

/**
 * 用户移动命令处理器
 */
public class UserMoveToCmdHandler implements CmdHandler<GameMsgProtocol.UserMoveToCmd> {
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserMoveToCmd cmd) {
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (null == userId)
            return;

        GameMsgProtocol.UserMoveToResult result = GameMsgProtocol.UserMoveToResult.newBuilder()
                .setMoveToPosX(cmd.getMoveToPosX())
                .setMoveToPosY(cmd.getMoveToPosY())
                .setMoveUserId(userId)
                .build();

        BroadCaster.broadCaste(result);
    }
}
