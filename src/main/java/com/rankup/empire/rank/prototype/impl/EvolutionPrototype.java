package com.rankup.empire.rank.prototype.impl;

import com.rankup.empire.rank.RankPlugin;
import com.rankup.empire.rank.feat.data.rank.Rank;
import com.rankup.empire.rank.feat.data.user.User;
import com.rankup.empire.rank.feat.data.user.UserCache;
import com.rankup.empire.rank.feat.data.rank.RankCache;
import lombok.RequiredArgsConstructor;
import com.rankup.empire.rank.prototype.Prototype;
import com.rankup.empire.rank.feat.utils.text.ActionBarUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

@RequiredArgsConstructor
public class EvolutionPrototype implements Prototype<Player> {

    private final RankPlugin plugin;
    private final UserCache userCache;
    private final RankCache rankCache;

    @Override
    public void execute(Player player) {
        final Configuration config = plugin.getConfig();

        final User user = userCache.getByUser(player.getName());
        if (user == null) return;

        final Rank rank = rankCache.getRank(user.getRankPosition());
        if (rank == null) return;

        final Rank nextRank = rankCache.getRank(user.getRankPosition() + 1);
        if (nextRank == null) return;

        if (user.getRank() < rank.getPrice()) return;
        if (user.getRankPosition() > rankCache.size()) return;

        user.evolution();
        user.removeRank(rank.getPrice());


        getServer().getScheduler().runTask(plugin, () -> {
            for (String command : rank.getCommands()) {
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        command.replace("%player%", player.getName())
                );
            }
        });

        player.sendTitle(
                config.getString("evolution.title")
                        .replace("&", "§"),
                config.getString("evolution.subtitle")
                        .replace("&", "§")
                        .replace("%rank_prefix%", nextRank.getPrefix())
                        .replace("%rank_suffix%", nextRank.getSuffix()
        ));

        Bukkit.getOnlinePlayers().forEach(op -> ActionBarUtils.send(op, config.getString("evolution.actionbar")
                .replace("&", "§")
                .replace("%rank_suffix%", nextRank.getSuffix())
                .replace("%jogador%", player.getName())
        ));

        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
    }
}