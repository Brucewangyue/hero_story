package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tinygame.herostory.cmdHandler.CmdHandler;
import org.tinygame.herostory.cmdHandler.CmdHandlerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThreadProcessor {
    private MainThreadProcessor() {
    }

    static private Logger logger = LogManager.getLogger();
    /**
     * 全局单线程线程池
     */
    static private ExecutorService es = Executors.newSingleThreadExecutor();
    /**
     * 单例对象
     */
    static private MainThreadProcessor instance = new MainThreadProcessor();

    static public MainThreadProcessor getInstance() {
        return instance;
    }

    public void process(ChannelHandlerContext ctx, Object msg) {
        if (null == ctx || null == msg)
            return;
        System.out.println("当前处理线程1：" + Thread.currentThread().getName());

        es.submit(() -> {
            System.out.println("当前处理线程2：" + Thread.currentThread().getName());
            CmdHandler<?> cmdHandler = CmdHandlerFactory.create(msg.getClass());

            if (null == cmdHandler) {
                logger.error("获取消息处理器失败");
                return;
            }

            try {
                // 防止异常导致线程中断
                cmdHandler.handle(ctx, cast(msg));
            } catch (Exception e) {
                logger.error("异常：" + e.getMessage());
            }
        });
    }

    /**
     * 强转
     *
     * @param obj
     * @param <E>
     * @return
     */
    private <E extends GeneratedMessageV3> E cast(Object obj) {
        if (null == obj)
            return null;

        return (E) obj;
    }
}