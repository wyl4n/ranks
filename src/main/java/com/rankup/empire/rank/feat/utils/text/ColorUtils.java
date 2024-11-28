package com.rankup.empire.rank.feat.utils.text;


import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;

public class ColorUtils {

    public static String colored( String message) {
        return (message == null) ? "" : ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] colored(String... messages) {
        for (int i = 0; i < messages.length; i++)
            messages[i] = colored(messages[i]);
        return messages;
    }

    public static List<String> colored(List<String> description) {
        return description.stream().map(ColorUtils::colored).collect(Collectors.toList());
    }
    public static Color getColorByHex(String hex) {
        return Color.decode(hex);
    }


    public static org.bukkit.Color getBukkitColorByHex(String hex) {
        Color decode = getColorByHex(hex);
        return org.bukkit.Color.fromRGB(decode.getRed(), decode.getGreen(), decode.getBlue());
    }
}

