package com.gabjuho.junaraplugin.backpack;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BackpackCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage("오직 플레이어만 명령어를 사용할 수 있습니다.");
            return true;
        }
        Player player = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("setBackpack")){
            ItemStack item = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("backpack.item")));
            ItemMeta meta = item.getItemMeta();
            if(meta != null) {
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + DataManager.getInstance().getConfig().getString("backpack.name"));
                meta.setLore(Arrays.asList(ChatColor.WHITE + DataManager.getInstance().getConfig().getString("backpack.description")));
                meta.setCustomModelData(DataManager.getInstance().getConfig().getInt("backpack.custom-model-data"));
                item.setItemMeta(meta);
                player.getInventory().setItem(DataManager.getInstance().getConfig().getInt("backpack.inventory-placing"), item);
                sender.sendMessage("가방창이 세팅되었습니다.");
            }
            else{
                sender.sendMessage("가방 GUI의 아이템 정보를 가져올 수 없습니다.");
            }
        }

        return true;
    }
}
