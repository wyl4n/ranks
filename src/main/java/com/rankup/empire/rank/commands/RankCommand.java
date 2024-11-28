package com.rankup.empire.rank.commands;

import com.rankup.empire.rank.view.RankUPView;
import com.rankup.empire.rank.feat.data.user.User;
import com.rankup.empire.rank.feat.data.user.UserCache;
import com.rankup.empire.rank.feat.utils.text.NumberFormatter;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class RankCommand {

    private final com.rankup.empire.rank.RankPlugin plugin;
    private final UserCache userCache;

    private final String LOG_RANK_PERMISSION = "server.log";


    @Command(
            name = "rank",
            aliases = {"ranks", "rankup"}
    )

    public void execute(Context<CommandSender> context) {
        final Player player = (Player) context.getSender();

        plugin.getViewFrame().open(RankUPView.class, player);
    }


    @Command(
            name = "setrank",
            permission = "rank.admin"
    )

    public void executeSet(Context<CommandSender> context, Player target, double value) {
        final CommandSender sender = context.getSender();
        final User user = userCache.getByUser(target.getName());

        if (user == null) return;
        if (value <= 0) return;


        user.setRank(value);
        sender.sendMessage("§eVocê setou §f" + NumberFormatter.format(value) +
                " §eranks-xp para o jogador §f" +
                target.getName() + "§e.");

        for (Player team : Bukkit.getOnlinePlayers()) {
            if (team.hasPermission(LOG_RANK_PERMISSION)) {
                team.sendMessage("§8[LOG] " + sender.getName() + " setou " + NumberFormatter.format(value) + " ranks-xp para o jogador " + target.getName() + ".");
            }
        }

    }

    @Command(
            name = "giverank",
            permission = "rank.admin"
    )

    public void executeAdd(Context<CommandSender> context, Player target, double value) {
        final CommandSender sender = context.getSender();
        final User user = userCache.getByUser(target.getName());

        if (user == null) return;
        if (value <= 0) return;

        user.addRank(value);
        sender.sendMessage("§eVocê deu §f" + NumberFormatter.format(value) +
                " §eranks-xp para o jogador §f" +
                target.getName() + "§e.");

        for (Player team : Bukkit.getOnlinePlayers()) {
            if (team.hasPermission(LOG_RANK_PERMISSION)) {
                team.sendMessage("§8[LOG] " + sender.getName() + " deu " + NumberFormatter.format(value) + " ranks-xp para o jogador " + target.getName() + ".");
            }
        }
    }
}
