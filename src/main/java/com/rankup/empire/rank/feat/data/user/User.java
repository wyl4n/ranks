package com.rankup.empire.rank.feat.data.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class User {

    private final String name;
    private int rankPosition;
    private double rank;
    private int prestige;


    public void evolution() {
        ++rankPosition;
    }

    public void prestige() {
        ++prestige;
        rankPosition = 1;
    }

    public void addRank(double amount) {
        rank += amount;
    }

    public void removeRank(double amount) {
        rank -= amount;
    }
}
