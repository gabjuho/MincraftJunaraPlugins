package com.gabjuho.junaraplugin.backpack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BackpackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Backpack backpack = new Backpack();

        if (!(sender instanceof Player)) {
            sender.sendMessage("오직 플레이어만 명령어를 사용할 수 있습니다.");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("backpack")) {
            if (args.length == 0)
                backpack.open(player);
            else
                sender.sendMessage(ChatColor.RED + "명령어 형식: /backpack");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("backpackclear")) {
            if (args.length >= 1 && args.length < 3) {
                Player argPlayer = Bukkit.getPlayerExact(args[0]);
                if (argPlayer == null) {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                    return false;
                }
                Inventory inv = backpack.getBackpackHashMap().get(argPlayer.getUniqueId());
                if (args.length == 1) {
                    inv.clear();
                    player.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 가방을 클리어하였습니다.");
                    argPlayer.sendMessage(ChatColor.GREEN + player.getName() + "님에 의해 당신의 가방이 클리어되었습니다.");
                } else {
                    try {
                        int slot = Integer.parseInt(args[1]);
                        inv.clear(slot);
                        player.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 가방의 " + slot + "번째 슬롯을 클리어하였습니다.");
                        argPlayer.sendMessage(ChatColor.GREEN + player.getName() + "님에 의해 당신의 가방의 " + slot + "번째 슬롯이 클리어되었습니다.");
                    }catch(IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.RED + "명령어 형식: /backpackclear <플레이어 이름>");
                        sender.sendMessage(ChatColor.RED + "혹은 명령어 형식: /backpackclear <플레이어 이름> <슬롯번호>");
                    }
                }
                backpack.getBackpackHashMap().put(argPlayer.getUniqueId(), inv);
            } else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /backpackclear <플레이어 이름>");
                sender.sendMessage(ChatColor.RED + "혹은 명령어 형식: /backpackclear <플레이어 이름> <슬롯번호>");
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("backpackclearall")) {
            if (args.length == 0) {
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    Inventory inv = backpack.getBackpackHashMap().get(online.getUniqueId());
                    inv.clear();
                    online.sendMessage(ChatColor.GREEN + player.getName() + "님에 의해 당신의 가방이 클리어되었습니다.");
                }
                player.sendMessage(ChatColor.GREEN + "모든 온라인 플레이어의 가방을 클리어하였습니다.");
            }
            else if(args.length == 1) {
                try {
                    int slot = Integer.parseInt(args[0]);
                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        Inventory inv = backpack.getBackpackHashMap().get(online.getUniqueId());
                        inv.clear(slot); //parseInt에 int 값 안넣고 다른 이상한 string 값 넣었을 때, 예외처리 해줘야하나 (안했으면, 안해준거 전부 처리해줘야함.)
                        online.sendMessage(ChatColor.GREEN + player.getName() + "님에 의해 당신의 가방의 " + slot + "번째 슬롯이 클리어되었습니다.");
                    }
                    player.sendMessage(ChatColor.GREEN + "모든 온라인 플레이어의 가방의 " + slot + "번째 슬롯을 클리어하였습니다.");
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.RED + "명령어 형식: /backpackclearall");
                    sender.sendMessage(ChatColor.RED + "혹은 명령어 형식: /backpackclearall <슬롯번호>");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /backpackclearall");
                sender.sendMessage(ChatColor.RED + "혹은 명령어 형식: /backpackclearall <슬롯번호>");
            }
        }
        return true;
    }
}
