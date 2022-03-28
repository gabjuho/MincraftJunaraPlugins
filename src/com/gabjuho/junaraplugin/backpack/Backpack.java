package com.gabjuho.junaraplugin.backpack;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Backpack {

    private static final HashMap<UUID,Inventory> backpacks = new HashMap<>();

    public void open(Player player)
    {
        player.openInventory(backpacks.get(player.getUniqueId()));
    }

    public void saveItem(ConfigurationSection section,ItemStack itemStack)
    {
        section.set("type",itemStack.getType().name());
        section.set("amount",itemStack.getAmount());
    }

    public HashMap<UUID,Inventory> getBackpackHashMap()
    {
        return backpacks;
    }

    public ItemStack loadItem(ConfigurationSection section)
    {
        return new ItemStack(Material.valueOf(section.getString("type")),section.getInt("amount"));
    }
}