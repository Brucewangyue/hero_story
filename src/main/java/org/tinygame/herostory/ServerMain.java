package org.tinygame.herostory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.tinygame.herostory.cmdHandler.CmdHandlerFactory;
import org.tinygame.herostory.utils.DbHelper;
import org.tinygame.herostory.utils.RedisUtil;

public class ServerMain {
    public static void main(String[] args) throws Exception{
        CmdHandlerFactory.init();
        MessageRecognizer.init();
        DbHelper.init();
        RedisUtil.init();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        // 服务器信道的处理方式
        b.channel(NioServerSocketChannel.class);
        // 客户端信道的处理器方式
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        // Http 服务器编解码器
                        new HttpServerCodec(),
                        // 内容长度限制
                        new HttpObjectAggregator(65535),
                        // WebSocket 协议处理器, 在这里处理握手、ping、pong 等消息
                        new WebSocketServerProtocolHandler("/websocket"),
                        // 自定义消息处理器
                        new GameMsgDecoder(),
                        new GameMsgEncoder(),
                        new GameMessageHandler());
            }
        });

        try {
            ChannelFuture f = b.bind(12345).sync();

            if (f.isSuccess())
                System.out.println("服务器启动成功");

            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
