package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tinygame.herostory.api.GameMsgProtocol;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息识别器
 */
public final class MessageRecognizer {
    static private Logger logger = LogManager.getLogger();
    static private Map<Integer, GeneratedMessageV3> msgCodeAndBuilderMap = new HashMap<>();
    static private Map<Class<?>, Integer> msgClassAndMsgCodeMap = new HashMap<>();

    private MessageRecognizer() {
    }

    static public void init() {
        Class<?>[] innerClasses = GameMsgProtocol.class.getDeclaredClasses();
        for (Class<?> innerClazz : innerClasses) {
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClazz))
                continue;

            String innerClazzName = innerClazz.getSimpleName().toLowerCase();

            // values 遍历枚举
            for (GameMsgProtocol.MsgCode msgCode : GameMsgProtocol.MsgCode.values()) {
                String msgCodeName = msgCode.name()
                        .replace("_", "")
                        .toLowerCase();

                if (msgCodeName.contains(innerClazzName)) {
                    try {
                        Object newMsg = innerClazz.getDeclaredMethod("getDefaultInstance").invoke(innerClazz);
                        msgCodeAndBuilderMap.put(msgCode.getNumber(), (GeneratedMessageV3) newMsg);
                        msgClassAndMsgCodeMap.put(innerClazz, msgCode.getNumber());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static public Message.Builder getBuilderByMsgCode(int msgCode) {
        if (msgCode < 0) return null;

        GeneratedMessageV3 msg = msgCodeAndBuilderMap.get(msgCode);
        return null == msg ? null : msg.newBuilderForType();
    }

    static public int getMsgCodeByMessage(Class<?> msgClazz) {
        if (null == msgClazz)
            return -1;

        Integer msgCode = msgClassAndMsgCodeMap.get(msgClazz);

        return null == msgCode
                ? -1
                : msgCode;
    }
}
