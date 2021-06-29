package org.tinygame.herostory;

import com.google.protobuf.Message;
import org.tinygame.herostory.api.GameMsgProtocol;

/**
 * 消息识别器
 */
public final class MessageRecognizer {
    private MessageRecognizer() {
    }

    static public Message.Builder getBuilderByMsgCode(int msgCode) {
        switch (msgCode) {
            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
                return GameMsgProtocol.UserEntryCmd.newBuilder();
            case GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE:
                return GameMsgProtocol.WhoElseIsHereCmd.newBuilder();
            case GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE:
                return GameMsgProtocol.UserMoveToCmd.newBuilder();
            default:
                return null;
        }
    }
}
