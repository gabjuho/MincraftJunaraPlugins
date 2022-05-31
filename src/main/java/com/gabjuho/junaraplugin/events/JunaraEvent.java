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

public class JunaraEvent implements Listener {

    static Backpack backpack = new Backpack();
    static FileConfiguration config = DataManager.getInstance().getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) //플레이어 들어왔을 때 자동 gui 세팅
    {
        Player player = event.getPlayer();

        ItemStack stat, backpack, background;
        stat = Util.makeItem(config.getString("stat.name"), config.getString("stat.item"), config.getInt("stat.custom-model-data"), config.getString("stat.description"));
        backpack = Util.makeItem(config.getString("backpack.name"), config.getString("backpack.item"), config.getInt("backpack.custom-model-data"), config.getString("backpack.description")); //makeItem에서 meta를 못받아올 때 예외 처리하기
        background = Util.makeItem(" ", config.getString("prohibit-item.item"), config.getInt("prohibit-item.custom-model-data"));

        if (player.getInventory().contains(stat))
            player.getInventory().remove(stat);
        if (player.getInventory().contains(backpack))
            player.getInventory().remove(backpack);
        if (player.getInventory().contains(background))
            player.getInventory().remove(background);

        if (config.getBoolean("prohibit-inv")) {
            for (int i = 9; i <= 35; i++)
                player.getInventory().setItem(i, background);
        }
        if(config.getBoolean("automatic-gui-setting.stat"))
            player.getInventory().setItem(config.getInt("stat.inventory-placing"), stat);
        if(config.getBoolean("automatic-gui-setting.backpack"))
            player.getInventory().setItem(config.getInt("backpack.inventory-placing"), backpack);
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
            }else if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals(" ") && DataManager.getInstance().getConfig().getBoolean("prohibit-inv")){
                event.setCancelled(true);
                if(player.getGameMode() == GameMode.CREATIVE) {
                    event.setCancelled(false);
                    player.sendMessage(ChatColor.RED + "[System]: GUI는 서바이벌 상태에서만 클릭해주세요.");
                }
            }
        }
    }
}
