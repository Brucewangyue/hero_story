package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import org.tinygame.herostory.api.GameMsgProtocol;
import org.tinygame.herostory.entity.MoveState;
import org.tinygame.herostory.entity.User;
import org.tinygame.herostory.entity.UserManager;

/**
 * 用户进入命令处理器
 */
public class WhoElseIsHereCmdHandler implements CmdHandler<GameMsgProtocol.WhoElseIsHereCmd> {
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.WhoElseIsHereCmd cmd) {
        GameMsgProtocol.WhoElseIsHereResult.Builder whoElseIsHereResultBuilder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();

        // 查询所有在线用户推送
        for (User user : UserManager.list()) {
            if (null == user)
                continue;
            // 查询在房间内的所有人的最后一步移动轨迹坐标
            MoveState moveState = user.getMoveState();
            System.out.println(moveState);
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.MoveState moveStateResult = GameMsgProtocol.WhoElseIsHereResult.UserInfo.MoveState.newBuilder()
                    .setFromPosX(moveState.getFromPosX())
                    .setFromPosY(moveState.getFromPosY())
                    .setToPosX(moveState.getToPosX())
                    .setToPosY(moveState.getToPosY())
                    .setStartTime(moveState.getStartTime())
                    .build();

            GameMsgProtocol.WhoElseIsHereResult.UserInfo userInfoResult = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder()
                    .setUserId(user.getUserId())
                    .setHeroAvatar(user.getHeroAvatar())
                    .setMoveState(moveStateResult)
                    .build();

            whoElseIsHereResultBuilder.addUserInfo(userInfoResult);
        }

        ctx.writeAndFlush(whoElseIsHereResultBuilder.build());
    }
}
