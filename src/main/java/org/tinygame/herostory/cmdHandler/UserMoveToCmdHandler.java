package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.tinygame.herostory.BroadCaster;
import org.tinygame.herostory.api.GameMsgProtocol;
import org.tinygame.herostory.entity.MoveState;
import org.tinygame.herostory.entity.User;
import org.tinygame.herostory.entity.UserManager;

/**
 * 用户移动命令处理器
 */
public class UserMoveToCmdHandler implements CmdHandler<GameMsgProtocol.UserMoveToCmd> {
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserMoveToCmd cmd) {
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (null == userId)
            return;

        // 保存用户最后一步移动轨迹坐标
        User user = UserManager.getById(userId);
        MoveState moveState = user.getMoveState();
        moveState.setFromPosX(cmd.getMoveFromPosX());
        moveState.setFromPosY(cmd.getMoveFromPosY());
        moveState.setToPosX(cmd.getMoveToPosX());
        moveState.setToPosY(cmd.getMoveToPosY());
        moveState.setStartTime(System.currentTimeMillis());

        // 广播用户移动轨迹坐标
        GameMsgProtocol.UserMoveToResult result = GameMsgProtocol.UserMoveToResult.newBuilder()
                .setMoveToPosX(cmd.getMoveToPosX())
                .setMoveToPosY(cmd.getMoveToPosY())
                .setMoveFromPosX(cmd.getMoveFromPosX())
                .setMoveFromPosY(cmd.getMoveFromPosY())
                .setMoveStartTime(moveState.getStartTime())
                .setMoveUserId(userId)
                .build();

        BroadCaster.broadCaste(result);
    }
}
