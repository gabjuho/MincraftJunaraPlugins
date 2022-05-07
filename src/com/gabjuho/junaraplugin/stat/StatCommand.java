package com.gabjuho.junaraplugin.stat;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StatCommand implements CommandExecutor {

    static DataManager data = DataManager.getInstance(); // 데이터매니저 싱글톤 객체로 불러옴

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("오직 플레이어만 명령어를 사용할 수 있습니다.");
            return true;
        }
        Player player = (Player)sender;

        if (cmd.getName().equalsIgnoreCase("setStat")){
            if(args.length == 1) {
                Player argPlayer = Bukkit.getPlayerExact(args[0]);
                if(argPlayer != null) {
                    ItemStack item = new ItemStack(Material.valueOf(data.getConfig().getString("stat.item")));
                    ItemMeta meta = item.getItemMeta();

                    if (meta != null) {
                        meta.setDisplayName(Util.format(data.getConfig().getString("stat.name")));
                        meta.setLore(Arrays.asList(Util.format(data.getConfig().getString("stat.description"))));
                        meta.setCustomModelData(data.getConfig().getInt("stat.custom-model-data"));
                        item.setItemMeta(meta);

                        if (argPlayer.getInventory().contains(item))
                            argPlayer.getInventory().remove(item);
                        argPlayer.getInventory().setItem(data.getConfig().getInt("stat.inventory-placing"), item);

                        sender.sendMessage("스텟창이 세팅되었습니다.");
                        argPlayer.sendMessage(ChatColor.GREEN + player.getName() + "님에 의해 스텟창이 세팅되었습니다.");
                    } else {
                        sender.sendMessage("스텟 GUI의 아이템 정보를 가져올 수 없습니다.");
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /setstat <플레이어 이름>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setSP")){
            if(args.length == 2) {
                try {
                    Player argPlayer = Bukkit.getPlayerExact(args[0]);
                    if(argPlayer != null) {
                        int statPoint = Integer.parseInt(args[1]);
                        data.getDataConfig().set("stat." + argPlayer.getUniqueId() + ".스텟포인트", statPoint);
                        data.saveDataConfig();
                        sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님에게 " + statPoint + "만큼 스텟포인트를 세팅하였습니다.");
                        argPlayer.sendMessage(ChatColor.GREEN + player.getName() + "님에 의해 당신의 스텟포인트가 " + statPoint + "만큼 세팅되었습니다.");
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                    }
                }
                catch (IllegalArgumentException e){
                    sender.sendMessage(ChatColor.RED + "명령어 형식: /setsp <플레이어 이름> <정수 sp값>");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /setsp <플레이어 이름> <정수 sp값>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("stat")){
            if(args.length == 0)
                Stat.open(player);
            else
                sender.sendMessage(ChatColor.RED + "명령어 형식: /stat");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniStat")){
            if(args.length == 1) {
                Player argPlayer = Bukkit.getPlayerExact(args[0]);
                if (argPlayer != null) {
                    iniStat(argPlayer,player);
                    sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님의 스텟을 초기화하였습니다.");
                }
                else {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "명령어 형식: /inistat <플레이어 이름>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniStatAll")) {
            if(args.length == 0) {
                for (Player argPlayer : Bukkit.getServer().getOnlinePlayers())
                    iniStat(argPlayer, player);
                sender.sendMessage(ChatColor.GREEN + "모든 온라인 플레이어들의 스텟을 초기화하였습니다.");
            }
            else{
                sender.sendMessage(ChatColor.RED + "명령어 형식: /inistatall");
            }
        }
        if (cmd.getName().equalsIgnoreCase("setStatAll")) {
            if(args.length == 0) {
                ItemStack stat = new ItemStack(Material.valueOf(DataManager.getInstance().getConfig().getString("stat.item")));
                ItemMeta sMeta = stat.getItemMeta();
                if (sMeta != null) {
                    sMeta.setDisplayName(Util.format(DataManager.getInstance().getConfig().getString("stat.name")));
                    sMeta.setLore(Arrays.asList(Util.format(DataManager.getInstance().getConfig().getString("stat.description"))));
                    sMeta.setCustomModelData(DataManager.getInstance().getConfig().getInt("stat.custom-model-data"));
                    stat.setItemMeta(sMeta);
                } else {
                    sender.sendMessage("스텟 GUI의 아이템 정보를 가져올 수 없습니다.");
                    return false;
                }
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online.getInventory().contains(stat))
                        online.getInventory().remove(stat);
                    online.getInventory().setItem(DataManager.getInstance().getConfig().getInt("stat.inventory-placing"), stat);
                }
                sender.sendMessage(ChatColor.GREEN + "스텟 GUI가 모든 온라인 유저에게 세팅되었습니다.");
            }
            else{
                sender.sendMessage(ChatColor.RED + "명령어 형식: /setstatall");
            }
        }
        return true;
    }

    private void iniStat(Player argPlayer, Player sender) // argPlayer는 초기화 대상, sender는 커맨드 사용자
    {
        int sp = 0;

        sp += data.getDataConfig().getInt("stat." + argPlayer.getUniqueId() + ".공격력포인트");
        data.getDataConfig().set("stat." + argPlayer.getUniqueId() + ".공격력포인트", 0);
        argPlayer.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);

        sp += data.getDataConfig().getInt("stat." + argPlayer.getUniqueId() + ".체력포인트");
        data.getDataConfig().set("stat." + argPlayer.getUniqueId() + ".체력포인트", 0);
        argPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);

        sp += data.getDataConfig().getInt("stat." + argPlayer.getUniqueId() + ".방어력포인트");
        data.getDataConfig().set("stat." + argPlayer.getUniqueId() + ".방어력포인트", 0);
        argPlayer.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);

        sp += data.getDataConfig().getInt("stat." + argPlayer.getUniqueId() + ".공격속도포인트");
        data.getDataConfig().set("stat." + argPlayer.getUniqueId() + ".공격속도포인트", 0);
        argPlayer.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);

        sp += data.getDataConfig().getInt("stat." + argPlayer.getUniqueId() + ".이동속도포인트");
        data.getDataConfig().set("stat." + argPlayer.getUniqueId() + ".이동속도포인트", 0);
        argPlayer.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);

        sp += data.getDataConfig().getInt("stat." + argPlayer.getUniqueId() + ".스텟포인트");

        data.getDataConfig().set("stat." + argPlayer.getUniqueId() + ".스텟포인트", sp);

        DataManager.getInstance().saveDataConfig();
        argPlayer.sendMessage(ChatColor.GREEN + sender.getName() + "에 의해 당신의 스텟이 모두 초기화되었습니다.");
    }
}
