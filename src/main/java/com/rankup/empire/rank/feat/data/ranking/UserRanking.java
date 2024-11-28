package com.rankup.empire.rank.feat.data.ranking;


import lombok.Data;

@Data(staticConstructor = "of")
public class UserRanking {

    private final String name;
    private final int position;
    private final double prestige;

}
