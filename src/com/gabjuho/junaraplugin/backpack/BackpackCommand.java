package com.gabjuho.junaraplugin.backpack;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.utils.Util;
import org.bukkit.Bukkit;
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

        Backpack backpack = new Backpack();

        if(!(sender instanceof Player)) {
            sender.sendMessage("오직 플레이어만 명령어를 사용할 수 있습니다.");
            return true;
        }
        Player player = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("backpack")) {
            if(args.length == 0)
                backpack.open(player);
            else
                sender.sendMessage(ChatColor.RED + "명령어 형식: /backpack");
            return true;
        }

        return true;
    }
}
