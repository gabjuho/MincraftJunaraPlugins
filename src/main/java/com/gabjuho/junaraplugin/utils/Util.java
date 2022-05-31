package com.gabjuho.junaraplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static String format(String msg)
    {
        return ChatColor.translateAlternateColorCodes('&',msg);
    }
    public static ItemStack makeItem(String name, String material, int customModelData, String lore)
    {
        if(name == null || material == null || lore == null)
            return null;

        ItemStack item = new ItemStack(Material.valueOf(material));
        ItemMeta meta = item.getItemMeta();

        if(meta == null)
            return null;

        meta.setDisplayName(format(name));
        meta.setCustomModelData(customModelData);

        List<String> description = new ArrayList<>();
        description.add(lore); // 아직은 한줄밖에 추가가 안됨.
        meta.setLore(description);
        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack makeItem(String name, String material, int customModelData)
    {
        if(material == null)
            return null;

        ItemStack item = new ItemStack(Material.valueOf(material));
        ItemMeta meta = item.getItemMeta();

        if(meta == null)
            return null;

        meta.setCustomModelData(customModelData);
        meta.setDisplayName(format(name));
        item.setItemMeta(meta);

        return item;
    }
}
