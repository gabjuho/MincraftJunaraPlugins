package com.gabjuho.junaraplugin.events;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.guis.Stat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class JunaraEvent implements Listener {

    static DataManager data = DataManager.getInstance(); // 데이터매니저 싱글톤 객체로 불러옴
    static Stat stat = new Stat();

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if(!(data.getConfig().contains("player." + player.getUniqueId().toString())))
        {
            data.getConfig().set("player." + player.getUniqueId() + ".스텟포인트",0);
            data.getConfig().set("player." + player.getUniqueId() + ".공격력포인트",0);
            data.getConfig().set("player." + player.getUniqueId() + ".체력포인트",0);
            data.getConfig().set("player." + player.getUniqueId() + ".방어력포인트",0);
            data.getConfig().set("player." + player.getUniqueId() + ".공격속도포인트",0);
            data.getConfig().set("player." + player.getUniqueId() + ".이동속도포인트",0);
            data.saveConfig();
        }
    }
    @EventHandler
    public static void onStatPointClick(InventoryClickEvent event) // 스텟 GUI 구성
    {
        if(event.getView().getTitle().equals("스텟"))
        {
            Player player = (Player)event.getWhoClicked();

            int statPoint = data.getConfig().getInt("player." + player.getUniqueId() + ".스텟포인트");
            int point = 0;

            if(event.getView().getTitle().equals("스텟") && event.getCurrentItem() != null)
            {
                event.setCancelled(true);

                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("공격력"))
                {
                    if(statPoint > 0)
                    {
                        point = data.getConfig().getInt("player." + player.getUniqueId() + ".공격력포인트");
                        point++;
                        data.getConfig().set("player." + player.getUniqueId() + ".공격력포인트", point);
                        statPoint--;
                        data.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
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
                        point = data.getConfig().getInt("player." + player.getUniqueId() + ".체력포인트");
                        point++;
                        data.getConfig().set("player." + player.getUniqueId() + ".체력포인트", point);
                        statPoint--;
                        data.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
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
                        point = data.getConfig().getInt("player." + player.getUniqueId() + ".방어력포인트");
                        point++;
                        data.getConfig().set("player." + player.getUniqueId() + ".방어력포인트", point);
                        statPoint--;
                        data.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
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
                        point = data.getConfig().getInt("player." + player.getUniqueId() + ".공격속도포인트");
                        point++;
                        data.getConfig().set("player." + player.getUniqueId() + ".공격속도포인트", point);
                        statPoint--;
                        data.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
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
                        point = data.getConfig().getInt("player." + player.getUniqueId() + ".이동속도포인트");
                        point++;
                        data.getConfig().set("player." + player.getUniqueId() + ".이동속도포인트", point);
                        statPoint--;
                        data.getConfig().set("player." + player.getUniqueId() + ".스텟포인트", statPoint);
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
    @EventHandler
    public static void onStatClick(InventoryClickEvent event) //인벤토리에서 스텟창 클릭
    {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = new ItemStack(Material.GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"스텟");
        meta.setLore(Arrays.asList(ChatColor.WHITE+"자신의 능력치를 확인할 수 있는 명함이다."));
        item.setItemMeta(meta);

        if(event.getCurrentItem() != null)
        {
            if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN+"스텟")) {
                event.setCancelled(true);

                if (event.getCursor() == null || event.getCursor().getType() == Material.AIR)
                    stat.open(player);
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
    public static void onCloseStatWindow(InventoryCloseEvent event)
    {
        if(event.getView().getTitle().equals("스텟"))
        {
            data.saveConfig();
        }
    }

}
