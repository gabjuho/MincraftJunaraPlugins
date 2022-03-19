package com.gabjuho.junaraplugin.backpack;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class BackpackEvent implements Listener {

    static DataManager dataManager = DataManager.getInstance();
    static Backpack backpack = new Backpack();


    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Inventory inv = Bukkit.createInventory(player, InventoryType.CHEST,"가방");

        if(dataManager.getConfig().contains("backpacks."+player.getUniqueId()))
        {
            for(String item:dataManager.getConfig().getConfigurationSection("backpacks." + player.getUniqueId()).getKeys(false))
            {
                inv.addItem(backpack.loadItem(Objects.requireNonNull(dataManager.getConfig().getConfigurationSection("backpacks." + player.getUniqueId() + "." + item))));
            }
        }
        backpack.getBackpacks().put(player.getUniqueId(),inv);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if(!dataManager.getConfig().contains("backpacks."+player.getUniqueId()))
        {
            dataManager.getConfig().createSection("backpacks."+player.getUniqueId());
        }

        char c = 'a';

        dataManager.saveConfig();
    }
}
