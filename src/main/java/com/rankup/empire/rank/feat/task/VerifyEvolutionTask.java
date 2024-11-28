package com.rankup.empire.rank.feat.task;

import com.rankup.empire.rank.prototype.impl.EvolutionPrototype;
import com.rankup.empire.rank.RankPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;


@RequiredArgsConstructor
public class VerifyEvolutionTask {

    private final RankPlugin plugin;
    private final EvolutionPrototype evolutionPrototype;

    public void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,
                () -> Bukkit.getOnlinePlayers().forEach(evolutionPrototype::execute),
                0, 100
        );
    }
}
