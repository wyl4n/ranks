package com.rankup.empire.rank.feat.listener;

import com.rankup.empire.rank.prototype.impl.EvolutionPrototype;
import com.rankup.empire.rank.feat.data.user.UserFactory;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class GeralListener implements Listener {

    private final UserFactory userFactory;
    private final EvolutionPrototype evolutionPrototype;

    @EventHandler
    void join(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        userFactory.create(player);
    }


    @EventHandler
    void quit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        userFactory.remove(player);
    }

    @EventHandler
    void command(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();

        evolutionPrototype.execute(player);
    }
}
