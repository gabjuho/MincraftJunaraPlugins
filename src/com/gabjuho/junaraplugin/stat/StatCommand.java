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

        if(!(sender instanceof Player))
        {
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
                else
                {
                    sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "명령어 형식: setstat <플레이어 이름>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setSP")){
            if(args.length == 2)
            {
                try
                {
                    Player argPlayer = Bukkit.getPlayerExact(args[0]);
                    if(argPlayer != null) {
                        int statPoint = Integer.parseInt(args[1]);
                        data.getDataConfig().set("stat." + argPlayer.getUniqueId() + ".스텟포인트", statPoint);
                        data.saveDataConfig();
                        sender.sendMessage(ChatColor.GREEN + argPlayer.getName() + "님에게 " + statPoint + "만큼 스텟포인트를 세팅하였습니다.");
                        argPlayer.sendMessage(ChatColor.GREEN + player.getName() + "님에 의해 당신의 스텟포인트가 " + statPoint + "만큼 세팅되었습니다.");
                    }
                    else
                    {
                        sender.sendMessage(ChatColor.RED + "해당 플레이어의 이름을 찾을 수 없습니다.");
                    }
                }
                catch (IllegalArgumentException e){
                    sender.sendMessage(ChatColor.RED + "명령어 형식: setsp <플레이어 이름> <정수 sp값>");
                }
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "명령어 형식: setsp <플레이어 이름> <정수 sp값>");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniAttack")){
            data.getDataConfig().set("stat." + player.getUniqueId() + ".공격력포인트",0);
            data.saveDataConfig();
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);

            player.sendMessage("[System]: 공격력 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniHealth")){
            data.getDataConfig().set("stat." + player.getUniqueId() + ".체력포인트",0);
            data.saveDataConfig();
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);

            player.sendMessage("[System]: 체력 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniDefense")){
            data.getDataConfig().set("stat." + player.getUniqueId() + ".방어력포인트",0);
            data.saveDataConfig();
            player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);

            player.sendMessage("[System]: 방어력 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniAttackSpeed")){
            data.getDataConfig().set("stat." + player.getUniqueId() + ".공격속도포인트",0);
            data.saveDataConfig();
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);

            player.sendMessage("[System]: 공격속도 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniMovementSpeed")){
            data.getDataConfig().set("stat." + player.getUniqueId() + ".이동속도포인트",0);
            data.saveDataConfig();
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);

            player.sendMessage("[System]: 이동속도 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("stat")){
            Stat.open(player);
        }


        return true;
    }
}
