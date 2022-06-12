package com.gabjuho.junaraplugin.commands;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.backpack.Backpack;
import com.gabjuho.junaraplugin.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuCommand implements CommandExecutor {

    FileConfiguration config = DataManager.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("오직 플레이어만 명령어를 사용할 수 있습니다.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setGUI")) {
            if (args.length == 1) {
                Player argPlayer = Bukkit.getPlayerExact(args[0]);
                if (argPlayer == null) {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                    return false;
                }

                ItemStack stat, backpack, background;
                stat = Util.makeItem(config.getString("stat.name"), config.getString("stat.item"), config.getInt("stat.custom-model-data"), config.getString("stat.description"));
                backpack = Util.makeItem(config.getString("backpack.name"), config.getString("backpack.item"), config.getInt("backpack.custom-model-data"), config.getString("backpack.description")); //makeItem에서 meta를 못받아올 때 예외 처리하기
                background = Util.makeItem(" ", config.getString("prohibit-item.item"), config.getInt("prohibit-item.custom-model-data"));

                if (config.getBoolean("prohibit-inv")) {
                    for (int i = 9; i <= 35; i++)
                        argPlayer.getInventory().setItem(i, background);
                }

                if (argPlayer.getInventory().contains(stat))
                    argPlayer.getInventory().remove(stat);

                if (config.getBoolean("set-gui-setting.stat"))
                    argPlayer.getInventory().setItem(config.getInt("stat.inventory-placing"), stat);

                if (argPlayer.getInventory().contains(backpack))
                    argPlayer.getInventory().remove(backpack);

                if (config.getBoolean("set-gui-setting.backpack"))
                    argPlayer.getInventory().setItem(config.getInt("backpack.inventory-placing"), backpack); //makeItem에서 meta를 못받아올 때 예외 처리하기

                sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 GUI가 세팅되었습니다.");
                argPlayer.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 GUI가 세팅되었습니다.");
            } else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /setgui <플레이어 이름>");
            }
        }

        if (cmd.getName().equalsIgnoreCase("setGUIAll")) {
            if (args.length == 0) {
                ItemStack stat, backpack, background;

                stat = Util.makeItem(config.getString("stat.name"), config.getString("stat.item"), config.getInt("stat.custom-model-data"), config.getString("stat.description"));
                backpack = Util.makeItem(config.getString("backpack.name"), config.getString("backpack.item"), config.getInt("backpack.custom-model-data"), config.getString("backpack.description")); //makeItem에서 meta를 못받아올 때 예외 처리하기
                background = Util.makeItem(" ", config.getString("prohibit-item.item"), config.getInt("prohibit-item.custom-model-data"));

                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.getInventory().contains(backpack))
                        online.getInventory().remove(backpack);
                    if (online.getInventory().contains(stat))
                        online.getInventory().remove(stat);
                    if (config.getBoolean("prohibit-inv")) {
                        for (int i = 9; i <= 35; i++)
                            online.getInventory().setItem(i, background);
                    }
                    if (config.getBoolean("set-gui-setting.backpack"))
                        online.getInventory().setItem(config.getInt("backpack.inventory-placing"), backpack);
                    if (config.getBoolean("set-gui-setting.stat"))
                        online.getInventory().setItem(config.getInt("stat.inventory-placing"), stat);
                    online.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 GUI가 세팅되었습니다.");
                }
                sender.sendMessage(ChatColor.GREEN + "GUI가 모든 온라인 플레이어에게 세팅되었습니다.");
            } else {
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
                ItemStack stat, backpack, background;
                stat = Util.makeItem(config.getString("stat.name"), config.getString("stat.item"), config.getInt("stat.custom-model-data"), config.getString("stat.description"));
                backpack = Util.makeItem(config.getString("backpack.name"), config.getString("backpack.item"), config.getInt("backpack.custom-model-data"), config.getString("backpack.description")); //makeItem에서 meta를 못받아올 때 예외 처리하기
                background = Util.makeItem(" ", config.getString("prohibit-item.item"), config.getInt("prohibit-item.custom-model-data"));

                if (argPlayer.getInventory().contains(stat))
                    argPlayer.getInventory().remove(stat);
                if (argPlayer.getInventory().contains(backpack))
                    argPlayer.getInventory().remove(backpack);
                if (argPlayer.getInventory().contains(background))
                    argPlayer.getInventory().remove(background);

                sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 GUI를 삭제했습니다.");
                argPlayer.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 당신의 GUI가 삭제되었습니다.");
            } else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /removegui <플레이어 이름>");
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("removeGUIAll")) {
            if (args.length == 0) {
                ItemStack stat, backpack, background;
                stat = Util.makeItem(config.getString("stat.name"), config.getString("stat.item"), config.getInt("stat.custom-model-data"), config.getString("stat.description"));
                backpack = Util.makeItem(config.getString("backpack.name"), config.getString("backpack.item"), config.getInt("backpack.custom-model-data"), config.getString("backpack.description")); //makeItem에서 meta를 못받아올 때 예외 처리하기
                background = Util.makeItem(" ", config.getString("prohibit-item.item"), config.getInt("prohibit-item.custom-model-data"));

                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.getInventory().contains(stat))
                        online.getInventory().remove(stat);
                    if (online.getInventory().contains(backpack))
                        online.getInventory().remove(backpack);
                    if (online.getInventory().contains(background))
                        online.getInventory().remove(background);
                    online.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 당신의 GUI가 삭제되었습니다.");
                }
                sender.sendMessage(ChatColor.GREEN + "모든 온라인 플레이어의 GUI를 삭제했습니다.");
            } else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /removeguiall");
            }
        }

        if (cmd.getName().equalsIgnoreCase("invclear")) {
            if (args.length >= 1 && args.length <= 2) {
                Player argPlayer = Bukkit.getPlayerExact(args[0]);
                if (argPlayer == null) {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                    return false;
                }
                Inventory inv = argPlayer.getInventory();
                if (args.length == 1) {
                    inv.clear();
                    sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 인벤토리를 클리어하였습니다.");
                    argPlayer.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 당신의 인벤토리가 클리어되었습니다.");
                } else {
                    try {
                        int slot = Integer.parseInt(args[1]);
                        inv.clear(slot);
                        sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 인벤토리의 " + slot + "번째 슬롯을 클리어하였습니다.");
                        argPlayer.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 당신의 인벤토리의 " + slot + "번째 슬롯이 클리어되었습니다.");
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.RED + "명령어 형식: /invclear <플레이어 이름>");
                        sender.sendMessage(ChatColor.RED + "혹은 명령어 형식: /invclear <플레이어 이름> <슬롯번호>");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /invclear <플레이어 이름>");
                sender.sendMessage(ChatColor.RED + "혹은 명령어 형식: /invclear <플레이어 이름> <슬롯번호>");
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("invclearall")) {
            if (args.length == 0) {
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    Inventory inv = online.getInventory();
                    inv.clear();
                    online.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 당신의 인벤토리가 클리어되었습니다.");
                }
                sender.sendMessage(ChatColor.GREEN + "모든 온라인 플레이어의 인벤토리를 클리어하였습니다.");
            } else if (args.length == 1) {
                try {
                    int slot = Integer.parseInt(args[0]);
                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        Inventory inv = online.getInventory();
                        inv.clear(slot);
                        online.sendMessage(ChatColor.GREEN + sender.getName() + "님에 의해 당신의 인벤토리의 " + slot + "번째 슬롯이 클리어되었습니다.");
                    }
                    sender.sendMessage(ChatColor.GREEN + "모든 온라인 플레이어의 인벤토리의 " + slot + "번째 슬롯을 클리어하였습니다.");

                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.RED + "명령어 형식: /invclearall");
                    sender.sendMessage(ChatColor.RED + "혹은 명령어 형식: /invclearall <슬롯번호>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /invclearall");
                sender.sendMessage(ChatColor.RED + "혹은 명령어 형식: /invclearall <슬롯번호>");
            }
            return true;
        }

        return true;
    }
}