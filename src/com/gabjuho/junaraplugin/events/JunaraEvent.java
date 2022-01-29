package com.gabjuho.junaraplugin.events;

import com.gabjuho.junaraplugin.guis.Stat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class JunaraEvent implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.GREEN+"주나라에 오신 것을 환영합니다!");
    }
    @EventHandler
    public static void onStatPointClick(InventoryClickEvent event)
    {
        if(event.getView().getTitle().equals("스텟"))
        {
            if(event.getCurrentItem().getType() ==  Material.DIAMOND_SWORD)
            {
                event.setCancelled(true);
            }
            else if(event.getCurrentItem().getType() ==  Material.TOTEM_OF_UNDYING)
            {
                event.setCancelled(true);
            }
            else if(event.getCurrentItem().getType() ==  Material.DIAMOND_CHESTPLATE)
            {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public static void onStatClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = new ItemStack(Material.GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"스텟");
        meta.setLore(Arrays.asList(ChatColor.WHITE+"자신의 능력치를 확인할 수 있는 명함이다."));
        item.setItemMeta(meta);

        Stat stat = new Stat();

        if(event.getCurrentItem().getItemMeta().equals(meta))
        {
            event.setCancelled(true);

            if(event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                stat.open(player);
        }
    }
    @EventHandler
    public static void onOpenMyInventory(InventoryOpenEvent event)
    {
        Player player = (Player) event.getPlayer();
        ItemStack item = new ItemStack(Material.GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"스텟");
        meta.setLore(Arrays.asList(ChatColor.WHITE+"자신의 능력치를 확인할 수 있는 명함이다."));
        item.setItemMeta(meta);

        player.setItemOnCursor(null);
    }
}
