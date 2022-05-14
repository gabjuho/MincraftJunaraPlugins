package com.gabjuho.junaraplugin.stat;

import com.gabjuho.junaraplugin.DataManager;
import com.gabjuho.junaraplugin.utils.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

abstract public class StatData {
    final FileConfiguration config = DataManager.getInstance().getConfig();
    final FileConfiguration dataConfig = DataManager.getInstance().getDataConfig();
    protected ConfigurationSection section;
    protected Material item;
    protected int customModelData;
    protected int maxLevel;
    protected int place;
    protected double perValue;
    protected String name;

    public void Init()
    {
        this.item = Material.valueOf(section.getString("stat-item"));
        this.customModelData = section.getInt("stat-item-custommodeldata");
        this.maxLevel = section.getInt("max-level");
        this.perValue = section.getDouble("value-per-stat");
        this.name = section.getString("stat-name");
        this.place = section.getInt("inventory-placing");
    }
    public ItemStack makeStatGUI(Player player) {
        ItemStack itemStack = new ItemStack(this.item);

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null)
            return new ItemStack(Material.AIR);

        meta.setCustomModelData(this.customModelData);
        meta.setDisplayName(this.name);
        meta.setLore(makeLore(player));
        itemStack.setItemMeta(meta);

        return itemStack;
    }
    abstract public void clickStat(int statPoint, Player player);
    abstract public List<String> makeLore(Player player);
}

class Attack extends StatData
{
    Attack()
    {
        this.section = config.getConfigurationSection("attack-damage");
        Init();
    }

    @Override
    public List<String> makeLore(Player player)
    {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+"현재 "+ name +"스텟: " + dataConfig.get("stat." + player.getUniqueId() + ".공격력포인트"));
        lore.add("스텟당 " + name + "이(가) "+ perValue +" 증가합니다.");
        lore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataConfig.get("stat." + player.getUniqueId() + ".스텟포인트"));
        return lore;
    }
    @Override
    public void clickStat(int statPoint, Player player)
    {
        if(statPoint > 0)
        {
            int point = DataManager.getInstance().getDataConfig().getInt("stat." + player.getUniqueId() + ".공격력포인트");
            if(maxLevel > point) {
                point++;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".공격력포인트", point);
                statPoint--;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".스텟포인트", statPoint);
                Stat.open(player);
                player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(point * perValue + 2);
            }
            else
                player.sendMessage(ChatColor.RED + "[System]: 해당 능력치가 최고 레벨에 도달했습니다.");
        }
        else
        {
            player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
        }
    }
}
class MaxHealth extends StatData
{
    MaxHealth()
    {
        this.section = config.getConfigurationSection("max-health");
        Init();
    }
    @Override
    public List<String> makeLore(Player player)
    {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+"현재 "+ name +"스텟: " + dataConfig.get("stat." + player.getUniqueId() + ".체력포인트"));
        lore.add("스텟당 " + name + "이(가) "+ perValue +" 증가합니다.");
        lore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataConfig.get("stat." + player.getUniqueId() + ".스텟포인트"));
        return lore;
    }
    @Override
    public void clickStat(int statPoint, Player player)
    {
        if(statPoint > 0)
        {
            int point = DataManager.getInstance().getDataConfig().getInt("stat." + player.getUniqueId() + ".체력포인트");
            if(maxLevel > point) {
                point++;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".체력포인트", point);
                statPoint--;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".스텟포인트", statPoint);
                Stat.open(player);
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 + point * perValue);
            }
            else
                player.sendMessage(ChatColor.RED + "[System]: 해당 능력치가 최고 레벨에 도달했습니다.");
        }
        else
        {
            player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
        }
    }
}
class Defense extends StatData
{
    Defense()
    {
        this.section = config.getConfigurationSection("defense");
        Init();
    }
    @Override
    public List<String> makeLore(Player player)
    {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+"현재 "+ name +"스텟: " + dataConfig.get("stat." + player.getUniqueId() + ".방어력포인트"));
        lore.add("스텟당 " + name + "이(가) "+ perValue +" 증가합니다.");
        lore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataConfig.get("stat." + player.getUniqueId() + ".스텟포인트"));
        return lore;
    }
    @Override
    public void clickStat(int statPoint, Player player)
    {
        if(statPoint > 0)
        {
            int point = DataManager.getInstance().getDataConfig().getInt("stat." + player.getUniqueId() + ".방어력포인트");
            if(maxLevel > point) {
                point++;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".방어력포인트", point);
                statPoint--;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".스텟포인트", statPoint);
                Stat.open(player);
                player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(point * perValue);
            }
            else
                player.sendMessage(ChatColor.RED + "[System]: 해당 능력치가 최고 레벨에 도달했습니다.");
        }
        else
        {
            player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
        }
    }
}
class AttackSpeed extends StatData
{
    AttackSpeed()
    {
        this.section = config.getConfigurationSection("attack-speed");
        Init();
    }
    @Override
    public List<String> makeLore(Player player)
    {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+"현재 "+ name +"스텟: " + dataConfig.get("stat." + player.getUniqueId() + ".공격속도포인트"));
        lore.add("스텟당 " + name + "이(가) "+ perValue +" 증가합니다.");
        lore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataConfig.get("stat." + player.getUniqueId() + ".스텟포인트"));
        return lore;
    }
    @Override
    public void clickStat(int statPoint, Player player)
    {
        if(statPoint > 0)
        {
            int point = DataManager.getInstance().getDataConfig().getInt("stat." + player.getUniqueId() + ".공격속도포인트");
            if(maxLevel > point) {
                point++;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".공격속도포인트", point);
                statPoint--;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".스텟포인트", statPoint);
                Stat.open(player);
                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4 + point * perValue);
            }
            else
                player.sendMessage(ChatColor.RED + "[System]: 해당 능력치가 최고 레벨에 도달했습니다.");
        }
        else
        {
            player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
        }
    }
}
class MovementSpeed extends StatData
{
    MovementSpeed()
    {
        this.section = config.getConfigurationSection("movement-speed");
        Init();
    }
    @Override
    public List<String> makeLore(Player player)
    {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+"현재 "+ name +"스텟: " + dataConfig.get("stat." + player.getUniqueId() + ".이동속도포인트"));
        lore.add("스텟당 " + name + "이(가) "+ perValue +" 증가합니다.");
        lore.add(ChatColor.WHITE + "가진 스텟포인트: " + dataConfig.get("stat." + player.getUniqueId() + ".스텟포인트"));
        return lore;
    }
    @Override
    public void clickStat(int statPoint, Player player)
    {
        if(statPoint > 0)
        {
            int point = DataManager.getInstance().getDataConfig().getInt("stat." + player.getUniqueId() + ".이동속도포인트");
            if(maxLevel > point) {
                point++;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".이동속도포인트", point);
                statPoint--;
                DataManager.getInstance().getDataConfig().set("stat." + player.getUniqueId() + ".스텟포인트", statPoint);
                Stat.open(player);
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1 + point * perValue);
            }
            else
                player.sendMessage(ChatColor.RED + "[System]: 해당 능력치가 최고 레벨에 도달했습니다.");
        }
        else
        {
            player.sendMessage(ChatColor.RED + "[System]: 스텟포인트가 부족합니다.");
        }
    }
}
class BackGround extends StatData
{
    BackGround()
    {
        this.section = config.getConfigurationSection("background");

        if(this.section != null) {
            this.item = Material.valueOf(section.getString("stat-item"));
            this.customModelData = section.getInt("stat-item-custommodeldata");
        }
    }
    public ItemStack makeStatGUI(Player player)
    {
        ItemStack itemStack = new ItemStack(item);

        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null) {
            meta.setCustomModelData(customModelData);
            meta.setDisplayName(" ");
            itemStack.setItemMeta(meta);
        }
        else
            return null;

        return itemStack;
    }
    @Override
    public List<String> makeLore(Player player)
    {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED+"");
        return lore;
    }
    @Override
    public void clickStat(int statPoint, Player player) {}
}

class InitButton extends StatData
{
    private String description;
    InitButton()
    {
        this.section = config.getConfigurationSection("init-button");

        if(this.section != null) {
            this.item = Material.valueOf(section.getString("stat-item"));
            this.customModelData = section.getInt("stat-item-custommodeldata");
            this.name = section.getString("stat-name");
            this.place = section.getInt("inventory-placing");
            this.description = section.getString("description");
        }
    }
    public ItemStack makeStatGUI(Player player)
    {
        ItemStack itemStack = new ItemStack(item);

        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null) {
            meta.setCustomModelData(customModelData);
            meta.setDisplayName(Util.format(name));
            meta.setLore(makeLore(player));

            itemStack.setItemMeta(meta);
        }
        else
            return null;

        return itemStack;
    }
    @Override
    public List<String> makeLore(Player player)
    {
        List<String> lore = new ArrayList<>();
        lore.add(Util.format(description));
        return lore;
    }
    @Override
    public void clickStat(int statPoint, Player player) {
        int sp = 0;

        sp += dataConfig.getInt("stat." + player.getUniqueId() + ".공격력포인트");
        dataConfig.set("stat." + player.getUniqueId() + ".공격력포인트",0);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1);

        sp+= dataConfig.getInt("stat." + player.getUniqueId() + ".체력포인트");
        dataConfig.set("stat." + player.getUniqueId() + ".체력포인트",0);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);

        sp+= dataConfig.getInt("stat." + player.getUniqueId() + ".방어력포인트");
        dataConfig.set("stat." + player.getUniqueId() + ".방어력포인트",0);
        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(0);

        sp+= dataConfig.getInt("stat." + player.getUniqueId() + ".공격속도포인트");
        dataConfig.set("stat." + player.getUniqueId() + ".공격속도포인트",0);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);

        sp+= dataConfig.getInt("stat." + player.getUniqueId() + ".이동속도포인트");
        dataConfig.set("stat." + player.getUniqueId() + ".이동속도포인트",0);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);

        dataConfig.set("stat." + player.getUniqueId() + ".스텟포인트",sp+statPoint);

        DataManager.getInstance().saveDataConfig();
        Stat.open(player);
        player.sendMessage(ChatColor.GREEN + "[System]: 스텟이 모두 초기화되었습니다.");
    }
}

