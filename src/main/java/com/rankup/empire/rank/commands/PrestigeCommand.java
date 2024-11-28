package com.rankup.empire.rank.commands;

import com.rankup.empire.rank.RankPlugin;
import com.rankup.empire.rank.feat.data.rank.Rank;
import com.rankup.empire.rank.feat.data.rank.RankCache;
import com.rankup.empire.rank.feat.data.user.User;
import com.rankup.empire.rank.feat.data.user.UserCache;
import com.rankup.empire.rank.feat.utils.text.ActionBarUtils;
import com.rankup.empire.rank.feat.utils.text.NumberFormatter;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class PrestigeCommand {

    private final RankPlugin plugin;
    private final UserCache userCache;
    private final RankCache rankCache;

    @Command(
            name = "prestigio",
            aliases = {"prestige"}
    )

    public void execute(Context<CommandSender> context) {
        final Configuration config = plugin.getConfig();

        final Player player = (Player) context.getSender();
        final User user = userCache.getByUser(player.getName());
        if (user == null) return;

        final Rank rank = rankCache.getRank(user.getRankPosition());
        if (user.getRankPosition() < plugin.getRankCache().size()) return;
        if (user.getRank() < rank.getPrice()) return;

        user.prestige();
        user.removeRank(rank.getPrice());

        player.sendTitle(
                config.getString("prestige.title")
                        .replace("&", "§"),
                config.getString("prestige.subtitle")
                        .replace("&", "§")
                        .replace("%rank_prestige%", NumberFormatter.format(user.getPrestige()))
        );

        Bukkit.getOnlinePlayers().forEach(op -> ActionBarUtils.send(op, config.getString("prestige.actionbar")
                .replace("&", "§")
                .replace("%player%", player.getName())
                .replace("%rank_prestige%", NumberFormatter.format(user.getPrestige()))
        ));
    }
}
