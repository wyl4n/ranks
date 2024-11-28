package com.rankup.empire.rank;

import com.rankup.empire.rank.commands.RankCommand;
import com.rankup.empire.rank.feat.data.user.UserStorage;
import com.rankup.empire.rank.feat.hook.PlaceholderHook;
import com.rankup.empire.rank.prototype.impl.EvolutionPrototype;
import com.rankup.empire.rank.feat.task.UserTask;
import com.rankup.empire.rank.feat.task.VerifyEvolutionTask;
import com.rankup.empire.rank.view.RankUPView;
import com.rankup.empire.rank.commands.PrestigeCommand;
import com.rankup.empire.rank.feat.data.rank.RankCache;
import com.rankup.empire.rank.feat.data.rank.RankFactory;
import com.rankup.empire.rank.feat.data.ranking.UserRankingCache;
import com.rankup.empire.rank.feat.data.user.User;
import com.rankup.empire.rank.feat.data.user.UserCache;
import com.rankup.empire.rank.feat.data.user.UserFactory;
import com.rankup.empire.rank.feat.database.MySQLProvider;
import com.rankup.empire.rank.feat.listener.GeralListener;
import com.rankup.empire.rank.feat.plugin.CustomPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class RankPlugin extends CustomPlugin {

    private MySQLProvider mySQLProvider;
    private UserCache userCache;
    private UserStorage userStorage;
    private UserFactory userFactory;
    private UserRankingCache userRankingCache;
    private RankCache rankCache;
    private RankFactory rankFactory;
    private EvolutionPrototype evolutionPrototype;

    @Override
    public void onLoad() {
        mySQLProvider = new MySQLProvider(
                getConfig().getString("mysql.host"),
                getConfig().getInt("mysql.port"),
                getConfig().getString("mysql.database"),
                getConfig().getString("mysql.username"),
                getConfig().getString("mysql.password")
        );
        rankCache = new RankCache();
        rankFactory = new RankFactory(this);
        userCache = new UserCache();
        userStorage = new UserStorage();
        userFactory = new UserFactory(
                userCache,
                userStorage
        );
        userRankingCache = new UserRankingCache();
        evolutionPrototype = new EvolutionPrototype(
                this,
                userCache,
                rankCache
        );
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        rankFactory.loadConfiguration(getConfig());

        mySQLProvider.init();

        getServer().getScheduler().runTaskLater(
                this,
                new UserTask(
                        userCache,
                        userStorage,
                        userFactory,
                        userRankingCache
                ),
                200L
        );

        getServer().getScheduler().runTaskTimerAsynchronously(
                this,
                new UserTask(
                        userCache,
                        userStorage,
                        userFactory,
                        userRankingCache
                ),
                0, 20L * 60L * 5L
        );

        loadRegistry();
    }


    @Override
    public void onDisable() {

        Bukkit.getOnlinePlayers().forEach(player -> {
            final User user = userCache.getByUser(player.getName());
            if (user == null) return;

            userStorage.update(user);
        });

        mySQLProvider.closeConnection();
    }

    public void loadRegistry() {
        new PlaceholderHook(userCache, rankCache).register();
        new VerifyEvolutionTask(this, evolutionPrototype).run();

        registerCommands(
                new RankCommand(this, userCache),
                new PrestigeCommand(this, userCache, rankCache)
        );

        registerListener(
                new GeralListener(
                        userFactory,
                        evolutionPrototype
                )
        );

        registerViews(
                new RankUPView(
                        userCache,
                        userRankingCache,
                        rankCache
                )
        );
    }

    public static RankPlugin getInstance() {
        return getPlugin(RankPlugin.class);
    }
}
