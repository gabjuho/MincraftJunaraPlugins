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
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class BackpackEvent implements Listener {

    static DataManager dataManager = DataManager.getInstance();
    static Backpack backpack = new Backpack();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"가방"); // owner를 player에서 null로 변경

        if(dataManager.getConfig().contains("backpacks."+player.getUniqueId()))
        {
            for(String item:dataManager.getConfig().getConfigurationSection("backpacks." + player.getUniqueId()).getKeys(false))
            {
                inv.addItem(backpack.loadItem(Objects.requireNonNull(dataManager.getConfig().getConfigurationSection("backpacks." + player.getUniqueId() + "." + item))));
            }
        }
        backpack.getBackpackHashMap().put(player.getUniqueId(),inv);
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

        dataManager.getConfig().set("backpacks."+player.getUniqueId(),null);

        for(ItemStack itemStack: backpack.getBackpackHashMap().get(player.getUniqueId())) // 서버 리로드 시 해쉬맵이 초기화되서, get에서 null이 반환됨
        {
            if(itemStack != null)
            {
                backpack.saveItem(DataManager.getInstance().getConfig().createSection("backpacks."+player.getUniqueId() + "." + c++),itemStack);
                //아이템을 넣는 건 저장이 되는데, 빼는 건 저장이 안됨. -> 해결됨
            }
        }

        dataManager.saveConfig();
    }
}
