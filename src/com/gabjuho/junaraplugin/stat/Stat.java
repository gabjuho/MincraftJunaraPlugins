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

import java.util.ArrayList;
import java.util.List;

public class Stat {
    static DataManager dataManager = DataManager.getInstance(); //데이터매니저 싱글톤 객체로 불러옴

    private ItemStack makeStatGUIItemStack(Material material, int customModelData, String displayName, List<String> Lore)
    {
        ItemStack itemStack;
        ItemMeta meta;

        itemStack = makeStatGUIItemStack(material,customModelData,displayName);
        meta = itemStack.getItemMeta();
        meta.setLore(Lore);

//        itemStack = new ItemStack(material);
//        meta = itemStack.getItemMeta();
//        meta.setCustomModelData(customModelData);
//        meta.setDisplayName(displayName);
//        meta.setLore(Arrays.asList(ChatColor.GREEN + Lore));
//        itemStack.setItemMeta(meta);
//        위에 문장이 더 가독성적으로 좋은지 아래 문장이 가독성적으로 좋은지 비교하기 위해 주석처리해놓음
        
        itemStack.setItemMeta(meta);

        return itemStack;
    }
    private ItemStack makeStatGUIItemStack(Material material, int customModelData, String displayName)
    {
        ItemStack itemStack;
        ItemMeta meta;

        itemStack = new ItemStack(material);
        meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);

        return itemStack;
    }


    public void open(Player player) {

        ItemStack attackStat,healthStat,defenseStat,attackSpeedStat,movementSpeedStat;
        ItemStack background;

        background = makeStatGUIItemStack(Material.GLASS_PANE,200,"");

        List<String> attackLore = new ArrayList<>();
        attackLore.add(ChatColor.GREEN+"현재 공격력 스텟: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".공격력포인트"));
        attackLore.add("스텟당 공격력이 0.1 증가합니다.");
        attackLore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트"));
        attackStat = makeStatGUIItemStack(Material.DIAMOND_SWORD,200,"공격력",attackLore);

        List<String> healthLore = new ArrayList<>();
        healthLore.add(ChatColor.GREEN+"현재 체력 스텟: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".체력포인트"));
        healthLore.add("스텟당 체력이 0.5 증가합니다.");
        healthLore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트"));
        healthStat = makeStatGUIItemStack(Material.TOTEM_OF_UNDYING,200,"체력",healthLore);

        List<String> defenseLore = new ArrayList<>();
        defenseLore.add(ChatColor.GREEN+"현재 방어력 스텟: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".방어력포인트"));
        defenseLore.add("스텟당 방어력이 0.1 증가합니다.");
        defenseLore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트"));
        defenseStat = makeStatGUIItemStack(Material.DIAMOND_CHESTPLATE,200,"방어력",defenseLore);

        List<String> attackSpeedLore = new ArrayList<>();
        attackSpeedLore.add(ChatColor.GREEN+"현재 공격속도 스텟: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".공격속도포인트"));
        attackSpeedLore.add("스텟당 공격속도가 0.1 증가합니다.");
        attackSpeedLore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트"));
        attackSpeedStat = makeStatGUIItemStack(Material.FEATHER,200,"공격속도",attackSpeedLore);

        List<String> movementSpeedLore = new ArrayList<>();
        movementSpeedLore.add(ChatColor.GREEN+"현재 이동속도 스텟: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".이동속도포인트"));
        movementSpeedLore.add("스텟당 이동속도가 0.1 증가합니다.");
        movementSpeedLore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataManager.getDataConfig().get("stat." + player.getUniqueId() + ".스텟포인트"));
        movementSpeedStat = makeStatGUIItemStack(Material.GOLDEN_BOOTS,200,"이동속도",movementSpeedLore);

        Inventory inv = Bukkit.createInventory(null, 27, "스텟");

        for(int i=0; i<27 ; i++)
        {
            inv.setItem(i,background);
        }

        inv.setItem(1, attackStat);
        inv.setItem(4, healthStat);
        inv.setItem(7, defenseStat);
        inv.setItem(21,attackSpeedStat);
        inv.setItem(23,movementSpeedStat);

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
