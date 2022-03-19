package com.gabjuho.junaraplugin.backpack;

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

        if (cmd.getName().equalsIgnoreCase("backpack")){
            ItemStack item = new ItemStack(Material.BUNDLE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE+"가방");
            meta.setLore(Arrays.asList(ChatColor.WHITE+"아이템을 넣을 수 있는 가방이다."));
            item.setItemMeta(meta);
            player.getInventory().setItem(10,item);
            sender.sendMessage("가방창이 세팅되었습니다.");
            return true;
        }

        return true;
    }
}
