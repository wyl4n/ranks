package com.rankup.empire.rank.feat.hook;

import com.rankup.empire.rank.feat.data.rank.Rank;
import com.rankup.empire.rank.feat.data.user.User;
import com.rankup.empire.rank.feat.data.user.UserCache;
import com.rankup.empire.rank.feat.data.rank.RankCache;
import com.rankup.empire.rank.feat.utils.text.ProgressBar;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class PlaceholderHook extends PlaceholderExpansion {

    private final UserCache userCache;
    private final RankCache rankCache;

    @Override
    public @NotNull String getIdentifier() {
        return "rank";
    }

    @Override
    public @NotNull String getAuthor() {
        return "wylan";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        final User user = userCache.getByUser(player.getName());
        if (user == null) return "";

        final Rank rank = rankCache.getRank(user.getRankPosition());
        if (rank == null) return "";


        if (params.equalsIgnoreCase("prefix")) {
            return rank.getPrefix();
        }

        if (params.equalsIgnoreCase("suffix")) {
            return rank.getSuffix();
        }

        if (params.equalsIgnoreCase("progressBar")) {
            return ProgressBar.create(
                    (int) user.getRank(),
                    (int) rank.getPrice(),
                    "▪"
            );
        }

        if (params.equalsIgnoreCase("prestige")) {
            return String.valueOf(user.getPrestige());
        }

        if (params.equalsIgnoreCase("percent")) {
            return ProgressBar.percentage(user.getRank(), rank.getPrice());
        }

        return "";
    }
}
