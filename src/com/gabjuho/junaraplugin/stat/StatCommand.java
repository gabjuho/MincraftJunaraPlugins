package com.gabjuho.junaraplugin.stat;

import com.gabjuho.junaraplugin.DataManager;
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

        if (cmd.getName().equalsIgnoreCase("stat")){
            ItemStack item = new ItemStack(Material.GLASS_PANE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN+"스텟");
            meta.setLore(Arrays.asList(ChatColor.WHITE+"자신의 능력치를 확인할 수 있는 명함이다."));
            item.setItemMeta(meta);
            player.getInventory().setItem(9,item);
            sender.sendMessage("스텟창이 세팅되었습니다.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setSP")){
            if(args.length >= 1)
            {
                try
                {
                    int statPoint = Integer.parseInt(args[0]);
                    data.getConfig().set("player." + player.getUniqueId() + ".스텟포인트",statPoint);
                    data.saveConfig();
                    player.sendMessage("스텟포인트가 " + statPoint + "만큼 세팅되었습니다.");
                }
                catch (IllegalArgumentException e){
                    player.sendMessage(ChatColor.DARK_RED + "[System]: 매개변수 값은 정수값을 입력해주세요!");
                }
            }
            else
            {
                player.sendMessage(ChatColor.RED + "[System]: 매개변수를 적어주세요!");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniAttack")){
            data.getConfig().set("player." + player.getUniqueId() + ".공격력포인트",0);
            data.saveConfig();
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);

            player.sendMessage("[System]: 공격력 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniHealth")){
            data.getConfig().set("player." + player.getUniqueId() + ".체력포인트",0);
            data.saveConfig();
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);

            player.sendMessage("[System]: 체력 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniDefense")){
            data.getConfig().set("player." + player.getUniqueId() + ".방어력포인트",0);
            data.saveConfig();
            player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);

            player.sendMessage("[System]: 방어력 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniAttackSpeed")){
            data.getConfig().set("player." + player.getUniqueId() + ".공격속도포인트",0);
            data.saveConfig();
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);

            player.sendMessage("[System]: 공격속도 스텟과 능력이 초기화 되었습니다!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("iniMovementSpeed")){
            data.getConfig().set("player." + player.getUniqueId() + ".이동속도포인트",0);
            data.saveConfig();
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);

            player.sendMessage("[System]: 이동속도 스텟과 능력이 초기화 되었습니다!");
            return true;
        }


        return true;
    }
}
