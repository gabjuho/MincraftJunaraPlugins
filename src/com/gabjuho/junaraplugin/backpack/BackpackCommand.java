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

        if (cmd.getName().equalsIgnoreCase("setBackpack")){
            if(args.length == 1) {
                Player argPlayer = Bukkit.getPlayerExact(args[0]);
                if(argPlayer != null) {
                    ItemStack item = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("backpack.item")));
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("backpack.name")));
                        meta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("backpack.description"))));
                        meta.setCustomModelData(DataManager.getInstance().getConfig().getInt("backpack.custom-model-data"));
                        item.setItemMeta(meta);

                        if (argPlayer.getInventory().contains(item))
                            argPlayer.getInventory().remove(item);
                        argPlayer.getInventory().setItem(DataManager.getInstance().getConfig().getInt("backpack.inventory-placing"), item);

                        sender.sendMessage("가방창이 세팅되었습니다.");
                        argPlayer.sendMessage(ChatColor.GREEN + player.getName() + "님에 의해 가방창이 세팅되었습니다.");
                    } else {
                        sender.sendMessage("가방 GUI의 아이템 정보를 가져올 수 없습니다.");
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /setbackpack <플레이어 이름>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("backpack")) {
            if(args.length == 0)
                backpack.open(player);
            else
                sender.sendMessage(ChatColor.RED + "명령어 형식: /backpack");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setBackpackAll")) {
            if (args.length == 0) {
                ItemStack bItem = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("backpack.item")));
                ItemMeta bMeta = bItem.getItemMeta();
                if (bMeta != null) {
                    bMeta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("backpack.name")));
                    bMeta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("backpack.description"))));
                    bMeta.setCustomModelData(DataManager.getInstance().getConfig().getInt("backpack.custom-model-data"));
                    bItem.setItemMeta(bMeta);
                } else {
                    sender.sendMessage("가방 GUI의 아이템 정보를 가져올 수 없습니다.");
                    return false;
                }
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.getInventory().contains(bItem))
                        online.getInventory().remove(bItem);
                    online.getInventory().setItem(DataManager.getInstance().getConfig().getInt("backpack.inventory-placing"), bItem);
                }
                sender.sendMessage(ChatColor.GREEN + "가방 gui가 모든 온라인 유저에게 세팅되었습니다.");
            }
            else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /setbackpackall");
            }
        }
        return true;
    }
}
