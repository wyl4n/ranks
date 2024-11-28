package com.rankup.empire.rank.view;

import com.rankup.empire.rank.RankPlugin;
import com.rankup.empire.rank.feat.data.rank.Rank;
import com.rankup.empire.rank.feat.data.rank.RankCache;
import com.rankup.empire.rank.feat.data.ranking.UserRanking;
import com.rankup.empire.rank.feat.data.ranking.UserRankingCache;
import com.rankup.empire.rank.feat.data.user.User;
import com.rankup.empire.rank.feat.data.user.UserCache;
import com.rankup.empire.rank.feat.utils.ItemBuilder;
import com.rankup.empire.rank.feat.utils.text.LuckPermUtil;
import com.rankup.empire.rank.feat.utils.text.NumberFormatter;
import lombok.RequiredArgsConstructor;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RankUPView extends View {


    private final UserCache userCache;
    private final UserRankingCache userRankingCache;
    private final RankCache rankCache;

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Sistema de ranks")
                .size(3)
                .cancelOnClick()
                .cancelOnDrag()
                .cancelOnPickup()
                .cancelOnDrop();
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        final Player player = render.getPlayer();

        final User user = userCache.getByUser(player.getName());
        final Rank rank = rankCache.getRank(user.getRankPosition());

        render.slot(12, getRanking(user));
        render.slot(14, getInformation(rank));
    }

    public ItemStack getInformation(Rank rank) {
        return new ItemBuilder(Material.BOOK)
                .name("§6O que são os ranks?")
                .lore(
                        "§7Ranks em nosso servidor são",
                        "§7upados automaticamente após você",
                        "§7concluir a barra de progresso.",
                        "",
                        " §7➟ §fRank atual: §8" + rank.getPrefix(),
                        ""
                ).build();
    }


    public ItemStack getRanking(User user) {
        final ItemBuilder itemRanking = new ItemBuilder(Material.ARMOR_STAND)
                .name("§6Ranking de Prestígio")
                .lore(
                        "§7Veja abaixo os ranking dos jogadores",
                        "§7com mais prestígio em nosso rankup.",
                        ""

                );

        final List<UserRanking> users = RankPlugin.getInstance().getUserRankingCache()
                .getRankingList()
                .stream()
                .sorted((o1, o2) -> Double.compare(o2.getPrestige(), o1.getPrestige()))
                .collect(Collectors.toList());

        int position = 1;
        for (UserRanking ranks : users) {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(ranks.getName());

            if (position <= 5) {
                itemRanking.addLoreLine("§7" + position + "°: §7" + LuckPermUtil.getPrefixColored(offlinePlayer) + " §8▸ " + NumberFormatter.format(ranks.getPrestige()));
                position++;
            }
        }

        final UserRanking userRanking = userRankingCache.getUser(user.getName());

        int positionRanking = users.indexOf(userRanking);
        itemRanking.addLoreLine(
                "§8...",
                "§7" + (positionRanking + 1) + "º: §7" + LuckPermUtil.getPrefixColored(Bukkit.getOfflinePlayer(user.getName())) + " §8▸ " + NumberFormatter.format(user.getPrestige())
        );

        return itemRanking.build();
    }
}
