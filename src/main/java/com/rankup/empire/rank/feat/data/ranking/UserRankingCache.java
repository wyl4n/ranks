package com.rankup.empire.rank.feat.data.ranking;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

@Getter
public class UserRankingCache {

    private final List<UserRanking> rankingList = Lists.newLinkedList();

    public UserRanking getUser(String name) {
        return rankingList.stream().filter(userRanking -> userRanking.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
