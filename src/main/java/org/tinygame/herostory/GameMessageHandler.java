package org.tinygame.herostory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 自定义消息处理器
 */
public class GameMessageHandler extends SimpleChannelInboundHandler<Object> {
    static private final Logger logger = LogManager.getLogger();

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("收到客户端消息：" + msg);

        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
        ByteBuf byteBuf = frame.content();

        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        for (byte b : bytes) {
            System.out.print(b);
            System.out.print(",");
        }

        System.out.println("");
    }
}
