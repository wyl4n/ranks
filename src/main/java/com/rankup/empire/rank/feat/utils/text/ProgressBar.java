package com.rankup.empire.rank.feat.utils.text;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.util.Arrays;

public class ProgressBar {


    public static String create(
            double current,
            double max,
            String symbol
    ) {
        double progress = 10 * current / max;

        ChatColor color = ChatColor.DARK_GRAY;
        if (progress < 5) {
            color = ChatColor.YELLOW;
        }
        if (progress >= 5) {
            color = ChatColor.GREEN;
        }
        final StringBuilder bar = new StringBuilder(color.toString());
        for (int i = 0; i < 10; ++i) {
            if (i >= progress && Arrays.asList(ChatColor.YELLOW, ChatColor.GREEN).contains(color)) {
                color = ChatColor.DARK_GRAY;
                bar.append(color);
            }
            bar.append(symbol);
        }
        return bar.toString();
    }

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.##");

    public static String percentage(double current, double max) {
        double value = (current / max) * 100;

        if (value > 100) {
            value = 100.0;
        }

        return String.format("%s", decimalFormat.format(value));
    }
}

