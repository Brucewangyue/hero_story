package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import org.tinygame.herostory.BroadCaster;
import org.tinygame.herostory.api.GameMsgProtocol;

public class SelectHeroCmdHandler implements CmdHandler<GameMsgProtocol.SelectHeroCmd> {

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.SelectHeroCmd cmd) {
        if (null == cmd)
            return;

        String heroAvatar = cmd.getHeroAvatar();

        GameMsgProtocol.SelectHeroResult result = GameMsgProtocol.SelectHeroResult.newBuilder()
                .setHeroAvatar(heroAvatar)
                .build();

        BroadCaster.broadCaste(result);
    }
}
