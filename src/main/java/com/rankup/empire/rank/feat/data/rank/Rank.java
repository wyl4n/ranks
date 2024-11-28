package com.rankup.empire.rank.feat.data.rank;

import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class Rank {

    private final int position;
    private final String id, prefix, suffix;
    private final double price;

    private final List<String> commands;

}
