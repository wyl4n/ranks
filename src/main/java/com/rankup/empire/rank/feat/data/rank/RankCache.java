package com.rankup.empire.rank.feat.data.rank;

import com.rankup.empire.rank.feat.utils.Cache;

public class RankCache extends Cache<Rank> {

    public Rank getRank(int position) {
        for (Rank rank : getCachedElements()) {
            if (rank.getPosition() == position) {
                return rank;
            }
        }
        return null;
    }


}
