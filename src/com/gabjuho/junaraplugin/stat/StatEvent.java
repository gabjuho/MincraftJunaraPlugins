package com.gabjuho.junaraplugin.stat;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class StatEvent implements Listener {

    static Stat stat = new Stat();
    static DataManager dataManager = DataManager.getInstance();

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if(!(dataManager.getConfig().contains("player." + player.getUniqueId().toString())))
        {
            dataManager.getConfig().set("player." + player.getUniqueId() + ".스텟포인트",0);
            dataManager.getConfig().set("player." + player.getUniqueId() + ".공격력포인트",0);
            dataManager.getConfig().set("player." + player.getUniqueId() + ".체력포인트",0);
            dataManager.getConfig().set("player." + player.getUniqueId() + ".방어력포인트",0);
            dataManager.getConfig().set("player." + player.getUniqueId() + ".공격속도포인트",0);
            dataManager.getConfig().set("player." + player.getUniqueId() + ".이동속도포인트",0);
            dataManager.saveConfig();
        }
    }

    @EventHandler
    public static void onStatPointClick(InventoryClickEvent event) // 스텟 GUI 구성
    {
        if(event.getView().getTitle().equals("스텟"))
        {
            Player player = (Player)event.getWhoClicked();

            int statPoint = dataManager.getConfig().getInt("player." + player.getUniqueId() + ".스텟포인트");
            int point = 0;

            if(event.getView().getTitle().equals("스텟") && event.getCurrentItem() != null)
            {
                event.setCancelled(true);

                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("공격력"))
                {
                    if(statPoint > 0)
                    {
                        point = dataManager.getConfig().getInt("player." + player.getUniqueId() + ".공격력포인트");
                        point++;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".공격력포인트", point);
                        statPoint--;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
                        stat.open(player);
                        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(point + 1);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
                    }
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("체력"))
                {
                    if(statPoint > 0)
                    {
                        point = dataManager.getConfig().getInt("player." + player.getUniqueId() + ".체력포인트");
                        point++;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".체력포인트", point);
                        statPoint--;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
                        stat.open(player);
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(point + 20);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
                    }
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("방어력"))
                {
                    if(statPoint > 0)
                    {
                        point = dataManager.getConfig().getInt("player." + player.getUniqueId() + ".방어력포인트");
                        point++;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".방어력포인트", point);
                        statPoint--;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
                        stat.open(player);
                        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(point);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
                    }
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("공격속도"))
                {
                    if(statPoint > 0)
                    {
                        point = dataManager.getConfig().getInt("player." + player.getUniqueId() + ".공격속도포인트");
                        point++;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".공격속도포인트", point);
                        statPoint--;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
                        stat.open(player);
                        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4+point);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
                    }
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("이동속도"))
                {
                    if(statPoint > 0)
                    {
                        point = dataManager.getConfig().getInt("player." + player.getUniqueId() + ".이동속도포인트");
                        point++;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".이동속도포인트", point);
                        statPoint--;
                        dataManager.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
                        stat.open(player);
                        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(point * 0.1 + 0.1);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
                    }
                }
            }
        }
    }
}
