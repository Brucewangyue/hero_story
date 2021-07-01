package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.tinygame.herostory.api.GameMsgProtocol;
import org.tinygame.herostory.cmdHandler.*;
import org.tinygame.herostory.entity.UserManager;

/**
 * 自定义消息处理器
 */
public class GameMessageHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        BroadCaster.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        BroadCaster.remove(ctx.channel());

        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (null == userId)
            return;

        UserManager.removeById(userId);

        GameMsgProtocol.UserQuitResult result = GameMsgProtocol.UserQuitResult.newBuilder()
                .setQuitUserId(userId)
                .build();

        BroadCaster.broadCaste(result);
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        MainThreadProcessor.getInstance().process(ctx,msg);
    }
}
