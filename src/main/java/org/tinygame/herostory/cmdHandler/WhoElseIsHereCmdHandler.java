package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import org.tinygame.herostory.api.GameMsgProtocol;
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

            GameMsgProtocol.WhoElseIsHereResult.UserInfo userInfoResult = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder()
                    .setUserId(user.getUserId())
                    .setHeroAvatar(user.getHeroAvatar())
                    .build();

            whoElseIsHereResultBuilder.addUserInfo(userInfoResult);
        }
        ctx.writeAndFlush(whoElseIsHereResultBuilder.build());
    }
}
