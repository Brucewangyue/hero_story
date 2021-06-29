package org.tinygame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import org.tinygame.herostory.api.GameMsgProtocol;

import java.util.HashMap;
import java.util.Map;

public final class CmdHandlerFactory {
    private CmdHandlerFactory() {
    }

    static private Map<Class<?>, CmdHandler<?>> handlerMap = new HashMap<>();

    static public void init() {
        handlerMap.put(GameMsgProtocol.UserEntryCmd.class, new UserEntryCmdHandler());
        handlerMap.put(GameMsgProtocol.WhoElseIsHereCmd.class, new WhoElseIsHereCmdHandler());
        handlerMap.put(GameMsgProtocol.UserMoveToCmd.class, new UserMoveToCmdHandler());
    }

    static public CmdHandler<? extends GeneratedMessageV3> create(Class<?> cmdClazz) {
        return handlerMap.get(cmdClazz);
    }
}
