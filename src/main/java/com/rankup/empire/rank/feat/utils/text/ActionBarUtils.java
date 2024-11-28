package com.rankup.empire.rank.feat.utils.text;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBarUtils {

    public static void send(Player player, String input) {
        final PacketPlayOutChat packet = new PacketPlayOutChat(
                new ChatComponentText(input),
                (byte) 2
        );

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
