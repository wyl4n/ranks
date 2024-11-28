package com.rankup.empire.rank.feat.data.user;

import com.rankup.empire.rank.feat.utils.Cache;

public class UserCache extends Cache<User> {

    public User getByUser(String name) {
        return getCached(user -> user.getName().equalsIgnoreCase(name));
    }



}
