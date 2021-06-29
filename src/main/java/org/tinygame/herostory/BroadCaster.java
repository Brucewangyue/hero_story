package org.tinygame.herostory;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class BroadCaster {
    /**
     * 客户端通信信道
     */
    static private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private BroadCaster() {
    }

    /**
     * 添加客户端
     *
     * @param channel
     */
    static public void add(Channel channel) {
        channelGroup.add(channel);
    }

    /**
     * 移除客户端
     *
     * @param channel
     */
    static public void remove(Channel channel) {
        channelGroup.remove(channel);
    }

    /**
     * 广播消息
     * @param msg
     */
    static public void broadCaste(Object msg) {
        channelGroup.writeAndFlush(msg);
    }
}
