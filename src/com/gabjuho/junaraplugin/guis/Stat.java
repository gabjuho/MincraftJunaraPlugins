package com.gabjuho.junaraplugin.guis;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Stat {

    ItemStack item1,item2,item3;
    ItemMeta meta1,meta2,meta3;
    static DataManager data = DataManager.getInstance(); //데이터매니저 싱글톤 객체로 불러옴

    public void open(Player player) {
        item1 = new ItemStack(Material.DIAMOND_SWORD);
        meta1 = item1.getItemMeta();
        meta1.setDisplayName("공격력");
        meta1.setLore(Arrays.asList(ChatColor.GREEN + "현재 공격력 스텟: ", "스텟당 공격력이 0.1 증가합니다.", ChatColor.WHITE + "가진 스텟포인트: " + data.getConfig().get("player." + player.getUniqueId() + ".statPoint")));
        item1.setItemMeta(meta1);

        item2 = new ItemStack(Material.TOTEM_OF_UNDYING);
        meta2 = item2.getItemMeta();
        meta2.setDisplayName("체력");
        meta2.setLore(Arrays.asList(ChatColor.GREEN + "현재 체력 스텟: ", "스텟당 체력이 0.5 증가합니다.", ChatColor.WHITE + "가진 스텟포인트: " + data.getConfig().get("player." + player.getUniqueId() + ".statPoint")));
        item2.setItemMeta(meta2);

        item3 = new ItemStack(Material.DIAMOND_CHESTPLATE);
        meta3 = item3.getItemMeta();
        meta3.setDisplayName("방어력");
        meta3.setLore(Arrays.asList(ChatColor.GREEN + "현재 방어력 스텟: ", "스텟당 방어력이 0.1 증가합니다.", ChatColor.WHITE + "가진 스텟포인트: " + data.getConfig().get("player." + player.getUniqueId() + ".statPoint")));
        item3.setItemMeta(meta3);

        Inventory inv = Bukkit.createInventory(null, 9, "스텟");
        inv.setItem(1, item1);
        inv.setItem(4, item2);
        inv.setItem(7, item3);

        player.openInventory(inv);
    }
}
