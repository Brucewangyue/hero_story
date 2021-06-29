package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tinygame.herostory.api.GameMsgProtocol;

public class GameMsgDecoder extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof BinaryWebSocketFrame))
            return;

        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
        ByteBuf byteBuf = frame.content();

        short msgLength = byteBuf.readShort();
        int msgCode = byteBuf.readShort();
        System.out.println(msgCode);

        byte[] msgBody = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(msgBody);

        GeneratedMessageV3 cmd = null;
        switch (msgCode) {
            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
                cmd = GameMsgProtocol.UserEntryCmd.parseFrom(msgBody);
                break;
            case GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE:
                cmd = GameMsgProtocol.WhoElseIsHereCmd.parseFrom(msgBody);
                break;
            case GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE:
                cmd = GameMsgProtocol.UserMoveToCmd.parseFrom(msgBody);
                break;
        }

        // 责任链模式
        if (null != cmd)
            ctx.fireChannelRead(cmd);
    }
}
