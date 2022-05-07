package com.gabjuho.junaraplugin;

import com.gabjuho.junaraplugin.backpack.BackpackCommand;
import com.gabjuho.junaraplugin.backpack.BackpackEvent;
import com.gabjuho.junaraplugin.commands.JunaraCommand;
import com.gabjuho.junaraplugin.events.JunaraEvent;
import com.gabjuho.junaraplugin.backpack.Backpack;
import com.gabjuho.junaraplugin.stat.StatCommand;
import com.gabjuho.junaraplugin.stat.StatEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Main extends JavaPlugin {

    Backpack backpack = new Backpack();

    @Override
    public void onEnable()
    {
        registerEvent();
        registerCommand();
        DataManager dataManager = DataManager.getInstance();
        if(dataManager.getDataConfig().contains("backpacks")) {
            for (String uuid : dataManager.getDataConfig().getConfigurationSection("backpacks").getKeys(false)) {
                if (dataManager.getDataConfig().contains("backpacks." + uuid)) {
                    Inventory inv = Bukkit.createInventory(null, DataManager.getInstance().getConfig().getInt("backpack.inventory-size"), "가방");
                    for (String item : dataManager.getDataConfig().getConfigurationSection("backpacks." + uuid).getKeys(false)) {
                        inv.addItem(backpack.loadItem(Objects.requireNonNull(dataManager.getDataConfig().getConfigurationSection("backpacks." + uuid + "." + item))));
                    }
                    backpack.getBackpackHashMap().put(UUID.fromString(uuid), inv);
                }
            }
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[JunaraPlugin]: Plugin is enabled!");
    }
    @Override
    public void onDisable()
    {
        DataManager dataManager = DataManager.getInstance();

        for(Map.Entry<UUID,Inventory> entry:backpack.getBackpackHashMap().entrySet())
        {
            if(!DataManager.getInstance().getDataConfig().contains("backpacks."+entry.getKey()))
            {
                DataManager.getInstance().getDataConfig().createSection("backpacks."+entry.getKey());
            }

            char c = 'a';

            if(dataManager.getDataConfig().contains("backpacks."+entry.getKey()))
            {
                for (String item : dataManager.getDataConfig().getConfigurationSection("backpacks." + entry.getKey()).getKeys(false))
                {
                    dataManager.getDataConfig().set("backpacks."+entry.getKey()+"."+item,null); //인벤토리가 비어있을 때, config저장시 uuid섹션 사라지는 버그 수정
                }
            }

            for(ItemStack itemStack: entry.getValue())
            {
                if(itemStack != null)
                {
                    backpack.saveItem(DataManager.getInstance().getDataConfig().createSection("backpacks."+entry.getKey() + "." + c++),itemStack);
                }
            }
            DataManager.getInstance().saveDataConfig();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[JunaraPlugin]: Plugin is disabled!");
    }

    void registerEvent()
    {
        getServer().getPluginManager().registerEvents(new JunaraEvent(),this);
        getServer().getPluginManager().registerEvents(new StatEvent(),this);
        getServer().getPluginManager().registerEvents(new BackpackEvent(),this);
    }

    void registerCommand() //추가할 커맨드
    {
        JunaraCommand mainCommands = new JunaraCommand();
        StatCommand statCommands = new StatCommand();
        BackpackCommand backpackCommand = new BackpackCommand();

        //Main 커멘드
        getCommand("setGUIAll").setExecutor(mainCommands);
        getCommand("removeGUI").setExecutor(mainCommands);

        //스텟 관련 커멘드
        getCommand("setStat").setExecutor(statCommands);
        getCommand("setSP").setExecutor(statCommands);
        getCommand("stat").setExecutor(statCommands);
        getCommand("iniStat").setExecutor(statCommands);
        getCommand("iniStatAll").setExecutor(statCommands);
        getCommand("setStatAll").setExecutor(statCommands);
        
        //가방 관련 커멘드
        getCommand("setBackpack").setExecutor(backpackCommand);
        getCommand("backpack").setExecutor(backpackCommand);
        getCommand("setBackpackAll").setExecutor(backpackCommand);
    }
}