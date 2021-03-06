package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.tinygame.herostory.BroadCaster;
import org.tinygame.herostory.api.GameMsgProtocol;
import org.tinygame.herostory.entity.User;
import org.tinygame.herostory.entity.UserManager;

/**
 * 用户进入命令处理器
 */
public class UserEntryCmdHandler implements CmdHandler<GameMsgProtocol.UserEntryCmd> {
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd cmd) {
        if (null == cmd)
            return;

        // 登录逻辑
        int userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        User user = UserManager.getById(userId);
        String heroAvatar = user.getHeroAvatar();

        // 广播用户进入房间消息
        GameMsgProtocol.UserEntryResult result = GameMsgProtocol.UserEntryResult.newBuilder()
                .setUserId(userId)
                .setHeroAvatar(heroAvatar)
                .build();
        BroadCaster.broadCaste(result);
    }
}
