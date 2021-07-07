package org.tinygame.herostory.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import org.tinygame.herostory.api.GameMsgProtocol;
import org.tinygame.herostory.entity.RankItem;
import org.tinygame.herostory.entity.User;
import org.tinygame.herostory.entity.UserManager;
import org.tinygame.herostory.service.RankService;

import java.util.ArrayList;
import java.util.List;

public class GetRankCmdHandler implements CmdHandler<GameMsgProtocol.GetRankCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.GetRankCmd cmd) {

        RankService.getInstance().getRank(rankItems -> {
            List<GameMsgProtocol.GetRankResult.RankItem> rankItemList = new ArrayList<>();

            for (RankItem rankItem : rankItems) {
                User user = UserManager.getById(rankItem.getUserId());

                GameMsgProtocol.GetRankResult.RankItem rankItemResult = GameMsgProtocol.GetRankResult.RankItem.newBuilder()
                        .setHeroAvatar(user.getHeroAvatar())
                        .setRankId(rankItem.getRankId())
                        .setUserId(rankItem.getUserId())
                        .setWin(rankItem.getWin())
                        .setUserName("null")
                        .build();

                rankItemList.add(rankItemResult);
            }

            GameMsgProtocol.GetRankResult result = GameMsgProtocol.GetRankResult.newBuilder()
                    .addAllRankItems(rankItemList)
                    .build();

            ctx.writeAndFlush(result);
            return null;
        });
    }
}
