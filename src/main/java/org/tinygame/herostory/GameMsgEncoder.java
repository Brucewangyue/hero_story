package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.tinygame.herostory.api.GameMsgProtocol;

public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (null == msg || !(msg instanceof GeneratedMessageV3)) {
            super.write(ctx, msg, promise);
            return;
        }

        int msgCode = -1;
        ByteBuf byteBuf = ctx.alloc().buffer();
        if (msg instanceof GameMsgProtocol.UserEntryResult)
            msgCode = GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE;
        else if (msg instanceof GameMsgProtocol.WhoElseIsHereResult)
            msgCode = GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE;
        else if (msg instanceof GameMsgProtocol.UserMoveToResult)
            msgCode = GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE;
        else if (msg instanceof GameMsgProtocol.UserQuitResult)
            msgCode = GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE;

        byte[] msgBody = ((GeneratedMessageV3) msg).toByteArray();
        byteBuf.writeShort((short) 0);
        byteBuf.writeShort(msgCode);
        byteBuf.writeBytes(msgBody);

        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(byteBuf);

        //
        super.write(ctx, frame, promise);
    }
}
