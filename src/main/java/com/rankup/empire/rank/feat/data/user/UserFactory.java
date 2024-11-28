package com.rankup.empire.rank.feat.data.user;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;



@RequiredArgsConstructor
public class UserFactory {

    private final UserCache userCache;
    private final UserStorage userStorage;

    public void create(Player player) {
        final User find = userStorage.find(player.getName());
        if (find != null) {
            userCache.addCachedElement(find);
            return;
        }

        final User user = new User(
                player.getName(),
                1,
                0,
                0
        );

        userStorage.insert(user);
        userCache.addCachedElement(user);
    }

    public void remove(Player player) {
        final User find = userCache.getByUser(player.getName());
        if (find == null) {
            return;
        }

        userStorage.update(find);
    }


    public void update(User user) {
        if (user == null) return;

        userStorage.update(user);
    }
}
