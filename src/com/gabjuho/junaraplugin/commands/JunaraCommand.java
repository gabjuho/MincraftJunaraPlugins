package com.gabjuho.junaraplugin.commands;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class JunaraCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage("오직 플레이어만 명령어를 사용할 수 있습니다.");
            return true;
        }
        Player player = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("tpWindow")){
            Inventory inv = Bukkit.createInventory(null, 27, ChatColor.WHITE + "七七七七七七七七ゐ");
            player.openInventory(inv);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setGUIAll")) {
            ItemStack backpack = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("backpack.item")));
            ItemStack stat = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("stat.item")));
            ItemMeta bMeta = backpack.getItemMeta();
            ItemMeta sMeta = stat.getItemMeta();

            if(bMeta != null) {
                bMeta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("backpack.name")));
                bMeta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("backpack.description"))));
                bMeta.setCustomModelData(DataManager.getInstance().getConfig().getInt("backpack.custom-model-data"));
                backpack.setItemMeta(bMeta);
            }
            else{
                sender.sendMessage("가방 GUI의 아이템 정보를 가져올 수 없습니다.");
            }

            if(sMeta != null) {
                sMeta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("stat.name")));
                sMeta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("stat.description"))));
                sMeta.setCustomModelData(DataManager.getInstance().getConfig().getInt("stat.custom-model-data"));
                stat.setItemMeta(sMeta);
            }
            else {
                sender.sendMessage("스텟 GUI의 아이템 정보를 가져올 수 없습니다.");
            }

            for(Player online: Bukkit.getServer().getOnlinePlayers())
            {
                if(online.getInventory().contains(backpack))
                    online.getInventory().remove(backpack);
                online.getInventory().setItem(DataManager.getInstance().getConfig().getInt("backpack.inventory-placing"), backpack);

                if(online.getInventory().contains(stat))
                    online.getInventory().remove(stat);
                online.getInventory().setItem(DataManager.getInstance().getConfig().getInt("stat.inventory-placing"), stat);

                sender.sendMessage(ChatColor.GREEN + "모든 gui창이 온라인 유저에게 세팅되었습니다.");
            }
        }

        return true;
    }
}