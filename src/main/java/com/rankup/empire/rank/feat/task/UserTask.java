package com.rankup.empire.rank.feat.task;

import com.rankup.empire.rank.feat.data.user.UserStorage;
import com.rankup.empire.rank.feat.data.ranking.UserRankingCache;
import com.rankup.empire.rank.feat.data.user.User;
import com.rankup.empire.rank.feat.data.user.UserCache;
import com.rankup.empire.rank.feat.data.user.UserFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserTask implements Runnable {

    private final UserCache userCache;
    private final UserStorage userStorage;
    private final UserFactory userFactory;
    private final UserRankingCache userRankingCache;

    @Override
    public void run() {
        for (User user : userCache.getCachedElements()) {
            userFactory.update(user);
        }

        userRankingCache.getRankingList().clear();
        userRankingCache.getRankingList().addAll(userStorage
                .fetchRanking());
    }
}
