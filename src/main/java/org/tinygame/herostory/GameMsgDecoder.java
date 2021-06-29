package org.tinygame.herostory;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        Message.Builder messageBuilder = MessageRecognizer.getBuilderByMsgCode(msgCode);
        if (messageBuilder == null) {
            logger.error("无法识别的消息代码：" + msgCode);
            return;
        }

        byte[] msgBody = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(msgBody);

        Message message = messageBuilder
                .clear()
                .mergeFrom(msgBody)
                .build();

        // 责任链模式
        if (null != message)
            ctx.fireChannelRead(message);
    }
}
