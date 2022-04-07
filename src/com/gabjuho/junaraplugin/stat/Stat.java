package com.gabjuho.junaraplugin.stat;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Stat {
    static DataManager dataManager = DataManager.getInstance(); //데이터매니저 싱글톤 객체로 불러옴


    public void open(Player player) {

        ItemStack stat1,stat2,stat3,stat4,stat5;
        ItemMeta meta1,meta2,meta3,meta4,meta5;

        ItemStack background;
        ItemMeta metaBackground;

        background = new ItemStack(Material.GLASS_PANE);
        metaBackground = background.getItemMeta();
        metaBackground.setDisplayName("");
        background.setItemMeta(metaBackground);

        stat1 = new ItemStack(Material.DIAMOND_SWORD);
        meta1 = stat1.getItemMeta();
        meta1.setDisplayName("공격력");
        meta1.setLore(Arrays.asList(ChatColor.GREEN + "현재 공격력 스텟: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".공격력포인트"), "스텟당 공격력이 0.1 증가합니다.", ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트")));
        stat1.setItemMeta(meta1);

        stat2 = new ItemStack(Material.TOTEM_OF_UNDYING);
        meta2 = stat2.getItemMeta();
        meta2.setDisplayName("체력");
        meta2.setLore(Arrays.asList(ChatColor.GREEN + "현재 체력 스텟: "+ dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".체력포인트"), "스텟당 체력이 0.5 증가합니다.", ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트")));
        stat2.setItemMeta(meta2);

        stat3 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        meta3 = stat3.getItemMeta();
        meta3.setDisplayName("방어력");
        meta3.setLore(Arrays.asList(ChatColor.GREEN + "현재 방어력 스텟: "+ dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".방어력포인트"), "스텟당 방어력이 0.1 증가합니다.", ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트")));
        stat3.setItemMeta(meta3);

        stat4 = new ItemStack(Material.FEATHER);
        meta4 = stat4.getItemMeta();
        meta4.setDisplayName("공격속도");
        meta4.setLore(Arrays.asList(ChatColor.GREEN + "현재 공격속도 스텟: "+ dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".공격속도포인트"), "스텟당 공격속도가 0.1 증가합니다.", ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트")));
        stat4.setItemMeta(meta4);

        stat5 = new ItemStack(Material.GOLDEN_BOOTS);
        meta5 = stat5.getItemMeta();
        meta5.setDisplayName("이동속도");
        meta5.setLore(Arrays.asList(ChatColor.GREEN + "현재 이동속도 스텟: "+ dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".이동속도포인트"), "스텟당 이동속도가 0.1 증가합니다.", ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트")));
        stat5.setItemMeta(meta5);

        Inventory inv = Bukkit.createInventory(null, 27, "스텟");

        for(int i=0; i<27 ; i++)
        {
            inv.setItem(i,background);
        }

        inv.setItem(1, stat1);
        inv.setItem(4, stat2);
        inv.setItem(7, stat3);
        inv.setItem(21,stat4);
        inv.setItem(23,stat5);

        player.openInventory(inv);
    }

    @EventHandler
    public static void onCloseStatWindow(InventoryCloseEvent event)
    {
        if(event.getView().getTitle().equals("스텟"))
        {
            dataManager.saveDataConfig();
        }
    }
}
