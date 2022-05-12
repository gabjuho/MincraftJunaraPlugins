package com.gabjuho.junaraplugin.events;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.Main;
import com.gabjuho.junaraplugin.backpack.Backpack;
import com.gabjuho.junaraplugin.stat.Stat;
import com.gabjuho.junaraplugin.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class JunaraEvent implements Listener {

    static Backpack backpack = new Backpack();
    static FileConfiguration config = DataManager.getInstance().getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) //플레이어 들어왔을 때 자동 gui 세팅
    {
        if(config.getBoolean("automatic-gui-setting"))
        {
            Player player = event.getPlayer();

            ItemStack bItem = new ItemStack(Material.valueOf(config.getString("backpack.item")));
            ItemStack sItem = new ItemStack(Material.valueOf(config.getString("stat.item")));
            ItemMeta bMeta = bItem.getItemMeta();
            ItemMeta sMeta = sItem.getItemMeta();

            if(bMeta != null) {
                bMeta.setDisplayName(Util.format(config.getString("backpack.name")));
                bMeta.setLore(Arrays.asList(Util.format(config.getString("backpack.description"))));
                bMeta.setCustomModelData(config.getInt("backpack.custom-model-data"));
                bItem.setItemMeta(bMeta);
            }
            else{
                player.sendMessage("가방 GUI의 아이템 정보를 가져올 수 없습니다.");
                return;
            }


            if(sMeta != null) {
                sMeta.setDisplayName(Util.format(config.getString("stat.name")));
                sMeta.setLore(Arrays.asList(Util.format(config.getString("stat.description"))));
                sMeta.setCustomModelData(config.getInt("stat.custom-model-data"));
                sItem.setItemMeta(sMeta);
            }
            else {
                player.sendMessage("스텟 GUI의 아이템 정보를 가져올 수 없습니다.");
                return;
            }

            if (player.getInventory().contains(bItem))
                player.getInventory().remove(bItem);
            if (player.getInventory().contains(sItem))
                player.getInventory().remove(sItem);

            player.getInventory().setItem(config.getInt("backpack.inventory-placing"), bItem);
            player.getInventory().setItem(config.getInt("stat.inventory-placing"), sItem);
        }
    }

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) //플레이어 인벤토리에서 GUI 클릭
    {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals(Util.format(config.getString("stat.name")))){ // **이름 변경하다보면 겹칠 수 있음
                event.setCancelled(true);
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> Stat.open(player));
                } else {
                    event.setCancelled(false);
                    player.sendMessage(ChatColor.RED + "[System]: GUI는 서바이벌 상태에서만 클릭해주세요.");
                }
            } else if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals(Util.format(config.getString("backpack.name")))) {
                event.setCancelled(true);
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                        Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> backpack.open(player));
                } else {
                    event.setCancelled(false);
                    player.sendMessage(ChatColor.RED + "[System]: GUI는 서바이벌 상태에서만 클릭해주세요.");
                }
            }
        }
    }
}
