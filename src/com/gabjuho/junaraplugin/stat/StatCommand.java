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

        if (cmd.getName().equalsIgnoreCase("setStat")){
            ItemStack item = new ItemStack(Material.valueOf(data.getConfig().getString("stat.item")));
            ItemMeta meta = item.getItemMeta();

            if(meta != null) {
                meta.setDisplayName(ChatColor.GREEN + data.getConfig().getString("stat.name"));
                meta.setLore(Arrays.asList(ChatColor.WHITE + data.getConfig().getString("stat.description")));
                meta.setCustomModelData(data.getConfig().getInt("stat.custom-model-data"));
                item.setItemMeta(meta);

                if(player.getInventory().contains(item))
                    player.getInventory().remove(item);
                player.getInventory().setItem(data.getConfig().getInt("stat.inventory-placing"), item);
                sender.sendMessage("스텟창이 세팅되었습니다.");
            }
            else {
                sender.sendMessage("스텟 GUI의 아이템 정보를 가져올 수 없습니다.");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setSP")){
            if(args.length >= 1)
            {
                try
                {
                    int statPoint = Integer.parseInt(args[0]);
                    data.getDataConfig().set("stat." + player.getUniqueId() + ".스텟포인트",statPoint);
                    data.saveDataConfig();
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


        return true;
    }
}
