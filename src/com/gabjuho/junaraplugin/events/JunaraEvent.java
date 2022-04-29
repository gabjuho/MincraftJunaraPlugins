package com.gabjuho.junaraplugin.events;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.backpack.Backpack;
import com.gabjuho.junaraplugin.stat.Stat;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class JunaraEvent implements Listener {

    static Backpack backpack = new Backpack();
    static FileConfiguration config = DataManager.getInstance().getConfig();

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) //플레이어 인벤토리에서 GUI 클릭
    {
        Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem() != null)
        {
            if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+config.getString("stat.name"))) // **이름 변경하다보면 겹칠 수 있음
            {
                event.setCancelled(true);
                if(player.getGameMode() == GameMode.SURVIVAL)
                {
                    if (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                        Stat.open(player);
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "[System]: GUI창은 서바이벌 상태에서만 클릭해주세요. (gui창이 복제됩니다.)");
                }
            }
            else if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE+config.getString("backpack.name")))
            {
                event.setCancelled(true);
                if(player.getGameMode() == GameMode.SURVIVAL)
                {
                    if (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                        backpack.open(player);
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "[System]: GUI창은 서바이벌 상태에서만 클릭해주세요. (gui창이 복제됩니다.)");
                }
            }
        }
    }
    @EventHandler
    public static void onOpenMyInventory(InventoryOpenEvent event)
    {
        Player player = (Player) event.getPlayer();
        player.setItemOnCursor(null);
    }
}
