package com.gabjuho.junaraplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

        if (cmd.getName().equalsIgnoreCase("stat")){
            ItemStack item = new ItemStack(Material.GLASS_PANE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN+"스텟");
            meta.setLore(Arrays.asList(ChatColor.WHITE+"자신의 능력치를 확인할 수 있는 명함이다."));
            item.setItemMeta(meta);
            player.getInventory().setItem(9,item);
            sender.sendMessage("스텟창이 세팅되었습니다.");
        }
        return true;
    }
}