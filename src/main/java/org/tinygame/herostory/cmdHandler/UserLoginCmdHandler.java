package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tinygame.herostory.api.GameMsgProtocol;
import org.tinygame.herostory.entity.User;
import org.tinygame.herostory.entity.UserManager;
import org.tinygame.herostory.service.AccountService;

public class UserLoginCmdHandler implements CmdHandler<GameMsgProtocol.UserLoginCmd> {
    static private Logger logger = LogManager.getLogger();

    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserLoginCmd cmd) {
        String username = cmd.getUserName();
        String password = cmd.getPassword();
        if (username.equals("") || password.equals("")) {
            logger.error("未提供用户名或密码");
            return;
        }
        AccountService.getInstance().selectByUsername(username, account -> {
            System.out.println("线程获取结果，处理简单运算逻辑：" + Thread.currentThread().getName());
            if (account == null) {
                logger.error("无效的用户名：" + username);
                return null;
            }

            if (!account.getPassword().equals(password)) {
                logger.error("密码不正确");
                return null;
            }

            User user = new User();
            user.setUserId(account.getId());
            user.setHeroAvatar(account.getHeroAvatar());
            user.setHP(100);
            UserManager.add(user);
            ctx.channel().attr(AttributeKey.valueOf("userId")).set(account.getId());

            GameMsgProtocol.UserLoginResult result = GameMsgProtocol.UserLoginResult.newBuilder()
                    .setUserId(account.getId())
                    .setUserName(account.getLoginName())
                    .setHeroAvatar(account.getHeroAvatar())
                    .build();

            ctx.writeAndFlush(result);

            return null;
        });

        System.out.println("线程处理完毕：" + Thread.currentThread().getName());
    }

}
