package com.rankup.empire.rank.feat.data.rank;

import com.rankup.empire.rank.RankPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@RequiredArgsConstructor
public class RankFactory {

    private final RankPlugin plugin;

    public void loadConfiguration(FileConfiguration config) {
        final ConfigurationSection section = config.getConfigurationSection("ranks");
        final AtomicInteger position = new AtomicInteger(1);

        for (String key : section.getKeys(false)) {
            final ConfigurationSection value = section.getConfigurationSection(key);

            final RankCache rankCache = plugin.getRankCache();

            rankCache.addCachedElements(Rank.of(
                    position.getAndIncrement(),
                    key,
                    value.getString("prefix")
                            .replace("&", "§"),
                    value.getString("suffix")
                            .replace("&", "§"),
                    value.getDouble("price"),
                    value.getStringList("commands")
            ));
        }
    }
}
