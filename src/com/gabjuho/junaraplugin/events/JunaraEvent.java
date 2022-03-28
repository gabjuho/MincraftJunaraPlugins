package com.gabjuho.junaraplugin.events;

import com.gabjuho.junaraplugin.backpack.Backpack;
import com.gabjuho.junaraplugin.stat.Stat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class JunaraEvent implements Listener {

    static Stat stat = new Stat();
    static Backpack backpack = new Backpack();

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) //플레이어 인벤토리에서 GUI 클릭
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = new ItemStack(Material.GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"스텟");
        meta.setLore(Arrays.asList(ChatColor.WHITE+"자신의 능력치를 확인할 수 있는 명함이다."));
        item.setItemMeta(meta);

        if(event.getCurrentItem() != null)
        {
            if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"스텟"))
            {
                event.setCancelled(true);

                if (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                    stat.open(player);
            }
            else if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+"가방"))
            {
                event.setCancelled(true);

                if (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                    backpack.open(player);
            }
        }
    }
    @EventHandler
    public static void onOpenMyInventory(InventoryOpenEvent event)
    {
        Player player = (Player) event.getPlayer();
        player.setItemOnCursor(null);
    }

    @EventHandler
    public static void onGetItem(EntityPickupItemEvent event)
    {
        Player player = (Player) event.getEntity();
        ItemStack item = event.getItem().getItemStack();

        player.sendMessage(item.getType().name());
    }
}
