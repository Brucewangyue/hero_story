package org.tinygame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import org.tinygame.herostory.utils.PackageUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class CmdHandlerFactory {
    private CmdHandlerFactory() {
    }

    static private Map<Class<?>, CmdHandler<?>> handlerMap = new HashMap<>();

    static public void init() throws Exception {
        // 扫描指定目录下所有文件名，
        // 加载资源
        // 判断资源是否继承

        Set<Class<?>> classes = PackageUtil.listSubClazz(
                CmdHandlerFactory.class.getPackage().getName(),
                true,
                CmdHandler.class);

        for (Class<?> clazz : classes) {
            // 抽象类判断
            if ((clazz.getModifiers() & Modifier.ABSTRACT)  != 0)
                continue;

            for (Method handleMethod : clazz.getDeclaredMethods()) {
                if (!handleMethod.getName().equals("handle")) continue;

                Class<?>[] parameterTypes = handleMethod.getParameterTypes();
                if (parameterTypes.length != 2)
                    continue;

                Object handlerObj = clazz.newInstance();
                handlerMap.put(parameterTypes[1], (CmdHandler<?>) handlerObj);
            }
        }

//        handlerMap.put(GameMsgProtocol.UserEntryCmd.class, new UserEntryCmdHandler());
//        handlerMap.put(GameMsgProtocol.WhoElseIsHereCmd.class, new WhoElseIsHereCmdHandler());
//        handlerMap.put(GameMsgProtocol.UserMoveToCmd.class, new UserMoveToCmdHandler());
    }

    static public CmdHandler<? extends GeneratedMessageV3> create(Class<?> cmdClazz) {
        return handlerMap.get(cmdClazz);
    }
}
