package com.gabjuho.junaraplugin.commands;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class JunaraCommand implements CommandExecutor {

    FileConfiguration config = DataManager.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("오직 플레이어만 명령어를 사용할 수 있습니다.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setGUI")) {
            if(args.length == 1) {
                Player argPlayer = Bukkit.getPlayerExact(args[0]);
                if(argPlayer == null) {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                    return false;
                }
                int placing = config.getInt("stat.inventory-placing");
                int customModelData = config.getInt("stat.custom-model-data");
                String name = config.getString("stat.name");
                String material =config.getString("stat.item");
                String lore = config.getString("stat.description");

                if (argPlayer.getInventory().contains(Util.makeItem(name, material, customModelData, lore)))
                    argPlayer.getInventory().remove(Util.makeItem(name, material, customModelData, lore));

                if(config.getBoolean("set-gui-setting.stat"))
                    argPlayer.getInventory().setItem(placing, Util.makeItem(name, material, customModelData, lore));

                placing = config.getInt("backpack.inventory-placing");
                customModelData = config.getInt("backpack.custom-model-data");
                name = config.getString("backpack.name");
                material =config.getString("backpack.item");
                lore = config.getString("backpack.description");

                if (argPlayer.getInventory().contains(Util.makeItem(name, material, customModelData, lore)))
                    argPlayer.getInventory().remove(Util.makeItem(name, material, customModelData, lore));

                if(config.getBoolean("set-gui-setting.backpack"))
                    argPlayer.getInventory().setItem(placing, Util.makeItem(name, material, customModelData, lore)); //makeItem에서 meta를 못받아올 때 예외 처리하기

                sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 GUI가 세팅되었습니다.");
                argPlayer.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 GUI가 세팅되었습니다.");
            }
            else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /setgui <플레이어 이름>");
            }
        }

        if (cmd.getName().equalsIgnoreCase("setGUIAll")) {
            if(args.length == 0) {
                ItemStack stat, backpack;

                int customModelData = config.getInt("stat.custom-model-data");
                String name = config.getString("stat.name");
                String material = config.getString("stat.item");
                String lore = config.getString("stat.description");
                stat = Util.makeItem(name, material, customModelData, lore);

                customModelData = config.getInt("backpack.custom-model-data");
                name = config.getString("backpack.name");
                material = config.getString("backpack.item");
                lore = config.getString("backpack.description");
                backpack = Util.makeItem(name, material, customModelData, lore); //makeItem에서 meta를 못받아올 때 예외 처리하기

                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.getInventory().contains(backpack))
                        online.getInventory().remove(backpack);
                    if (online.getInventory().contains(stat))
                        online.getInventory().remove(stat);
                    if (config.getBoolean("set-gui-setting.backpack"))
                        online.getInventory().setItem(config.getInt("backpack.inventory-placing"), backpack);
                    if(config.getBoolean("set-gui-setting.stat"))
                        online.getInventory().setItem(config.getInt("stat.inventory-placing"), stat);
                }
                sender.sendMessage(ChatColor.GREEN + "GUI가 모든 온라인 플레이어에게 세팅되었습니다.");
            }
            else{
                sender.sendMessage(ChatColor.RED + "명령어 형식: /setguiall");
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("removeGUI")) {
            if (args.length == 1) {
                Player argPlayer = Bukkit.getPlayerExact(args[0]);
                if (argPlayer == null) {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                    return false;
                }
                ItemStack stat = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("stat.item")));
                ItemStack backpack = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("backpack.item")));
                ItemMeta sMeta = stat.getItemMeta();
                ItemMeta bMeta = backpack.getItemMeta();

                if (sMeta != null) {
                    sMeta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("stat.name")));
                    sMeta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("stat.description"))));
                    sMeta.setCustomModelData(DataManager.getInstance().getConfig().getInt("stat.custom-model-data"));
                    stat.setItemMeta(sMeta);
                } else {
                    sender.sendMessage("삭제할 스텟 GUI의 아이템 정보를 가져올 수 없습니다. (config.yml 파일을 확인해주세요.)");
                    return false;
                }
                if (bMeta != null) {
                    bMeta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("backpack.name")));
                    bMeta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("backpack.description"))));
                    bMeta.setCustomModelData(DataManager.getInstance().getConfig().getInt("backpack.custom-model-data"));
                    backpack.setItemMeta(bMeta);
                } else {
                    sender.sendMessage("삭제할 가방 GUI의 아이템 정보를 가져올 수 없습니다. (config.yml 파일을 확인해주세요.)");
                    return false;
                }

                if (argPlayer.getInventory().contains(stat))
                    argPlayer.getInventory().remove(stat);
                if (argPlayer.getInventory().contains(backpack))
                    argPlayer.getInventory().remove(backpack);

                sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 GUI를 삭제했습니다.");
                argPlayer.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 당신의 GUI가 삭제되었습니다.");
            } else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /removegui <플레이어 이름>");
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("removeGUIAll")) {
            if(args.length == 0) {
                ItemStack stat = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("stat.item")));
                ItemStack backpack = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("backpack.item")));
                ItemMeta sMeta = stat.getItemMeta();
                ItemMeta bMeta = backpack.getItemMeta();

                if (sMeta != null) {
                    sMeta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("stat.name")));
                    sMeta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("stat.description"))));
                    sMeta.setCustomModelData(DataManager.getInstance().getConfig().getInt("stat.custom-model-data"));
                    stat.setItemMeta(sMeta);
                } else {
                    sender.sendMessage("삭제할 스텟 GUI의 아이템 정보를 가져올 수 없습니다. (config.yml 파일을 확인해주세요.)");
                    return false;
                }
                if (bMeta != null) {
                    bMeta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("backpack.name")));
                    bMeta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("backpack.description"))));
                    bMeta.setCustomModelData(DataManager.getInstance().getConfig().getInt("backpack.custom-model-data"));
                    backpack.setItemMeta(bMeta);
                } else {
                    sender.sendMessage("삭제할 가방 GUI의 아이템 정보를 가져올 수 없습니다. (config.yml 파일을 확인해주세요.)");
                    return false;
                }

                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.getInventory().contains(stat))
                        online.getInventory().remove(stat);
                    if (online.getInventory().contains(backpack))
                        online.getInventory().remove(backpack);
                    online.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 당신의 GUI가 삭제되었습니다.");
                }
                sender.sendMessage(ChatColor.GREEN +"모든 온라인 플레이어의 GUI를 삭제했습니다.");
            }
            else{
                sender.sendMessage(ChatColor.RED + "명령어 형식: /removeguiall");
            }
        }

        return true;
    }
}