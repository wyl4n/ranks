package com.rankup.empire.rank.feat.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {

    private ItemStack itemStack;

    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount, int data) {
        itemStack = new ItemStack(material, amount, (byte) data);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack otherItem) {
        itemStack = otherItem;
        itemMeta = otherItem.getItemMeta();
    }

    public ItemBuilder(String url) {
        itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        itemMeta = itemStack.getItemMeta();

        final SkullMeta skullMeta = (SkullMeta) itemMeta;

        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));

        try {
            final Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException |
                 IllegalAccessException exception) {
            exception.printStackTrace();
        }

        itemStack.setItemMeta(skullMeta);
    }

    public ItemBuilder itemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemBuilder texture(String url) {
        itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        final SkullMeta skullMeta = (SkullMeta) itemMeta;

        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));

        try {
            final Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException |
                 IllegalAccessException exception) {
            return new ItemBuilder(Material.BARRIER);
        }

        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder itemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    public ItemBuilder material(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addLoreLine(String... line) {
        List<String> lore = itemMeta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.addAll(Arrays.asList(line));

        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addLoreLineIf(boolean b, String... line) {
        if (b) {
            addLoreLine(line);
        }

        return this;
    }

    public ItemBuilder durability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder data(MaterialData materialData) {
        itemStack.setData(materialData);
        return this;
    }

    public ItemBuilder acceptItemStack(Consumer<ItemStack> consumer) {
        consumer.accept(itemStack);
        return this;
    }

    public ItemBuilder acceptItemMeta(Consumer<ItemMeta> consumer) {
        consumer.accept(itemMeta);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder unbreakable() {
        itemMeta.spigot().setUnbreakable(true);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder hideEnchantments() {
        addFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder skullOwner(String owner) {
        SkullMeta skull = (SkullMeta) itemMeta;
        itemStack.setDurability((short) 3);
        skull.setOwner(owner);
        return this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    private String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private String[] colorize(String... lore) {
        Arrays.setAll(lore, i -> colorize(lore[i]));
        return lore;
    }

    private List<String> colorize(List<String> lore) {
        return lore.stream().map(this::colorize).collect(Collectors.toList());
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}