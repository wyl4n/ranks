package com.rankup.empire.rank.feat.utils.text;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.UUID;


public class LuckPermUtil {

    public static final LuckPerms LuckPerms = net.luckperms.api.LuckPermsProvider.get();
    public static final GroupManager groupManager = LuckPerms.getGroupManager();
    public static final UserManager userManager = LuckPerms.getUserManager();

    public static User loadLuckPermUser(UUID id) {
        return userManager.loadUser(id).join();
    }

    public static User getLuckPermUser(OfflinePlayer player) {
        return player.isOnline() ? userManager.getUser(player.getUniqueId()) : loadLuckPermUser(player.getUniqueId());
    }

    public static Group getLuckPermGroup(OfflinePlayer player) {
        User user = getLuckPermUser(player);
        return groupManager.getGroup(user.getPrimaryGroup());
    }

    public static CachedMetaData getLuckPermMetadata(OfflinePlayer player) {
        Group group = getLuckPermGroup(player);
        return group != null ? group.getCachedData().getMetaData(QueryOptions.nonContextual()) : null;
    }

    public static String getPrefix(OfflinePlayer player) {
        CachedMetaData metadata = getLuckPermMetadata(player);
        String prefix = metadata != null ? metadata.getPrefix() : null;
        return prefix != null ? ChatColor.translateAlternateColorCodes('&', prefix) : "";
    }

    public static String getColor(OfflinePlayer player) {
        CachedMetaData metadata = getLuckPermMetadata(player);
        String prefix = metadata != null ? metadata.getPrefix() : null;
        return prefix != null ? ChatColor.translateAlternateColorCodes('&', prefix.substring(0, 2)) : "";
    }

    public static String getPrefixColored(OfflinePlayer player) {
        return getColor(player) + " " + player.getName();
    }

    public static String getPrefixNamed(OfflinePlayer player) {
        return getPrefix(player) + " " + player.getName();
    }

    public static String getSuffix(OfflinePlayer player) {
        CachedMetaData metadata = getLuckPermMetadata(player);
        String suffix = metadata != null ? metadata.getSuffix() : null;
        return suffix != null ? ChatColor.translateAlternateColorCodes('&', suffix) : "";
    }

    public static String getSuffixNamed(OfflinePlayer player) {
        return player.getName() + " " + getSuffix(player);
    }
}
