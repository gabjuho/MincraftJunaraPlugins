package com.gabjuho.junaraplugin;

import com.gabjuho.junaraplugin.backpack.BackpackCommand;
import com.gabjuho.junaraplugin.backpack.BackpackEvent;
import com.gabjuho.junaraplugin.commands.JunaraCommand;
import com.gabjuho.junaraplugin.events.JunaraEvent;
import com.gabjuho.junaraplugin.backpack.Backpack;
import com.gabjuho.junaraplugin.stat.StatCommand;
import com.gabjuho.junaraplugin.stat.StatEvent;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin {

    Backpack backpack = new Backpack();

    @Override
    public void onEnable()
    {
        registerEvent();
        registerCommand();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[JunaraPlugin]: Plugin is enabled!");
    }
    @Override
    public void onDisable()
    {
        for(Map.Entry<UUID,Inventory> entry:backpack.getBackpacks().entrySet())
        {
            if(!DataManager.getInstance().getConfig().contains("backpacks."+entry.getKey()))
            {
                DataManager.getInstance().getConfig().createSection("backpacks."+entry.getKey());
            }

            char c = 'a';

            for(ItemStack itemStack: entry.getValue())
            {
                if(itemStack != null)
                {
                    backpack.saveItem(DataManager.getInstance().getConfig().createSection("backpacks."+entry.getKey() + "." + c++),itemStack);
                }
            }
            DataManager.getInstance().saveConfig();
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
        getCommand("tpWindow").setExecutor(mainCommands);

        //스텟 관련 커멘드
        getCommand("stat").setExecutor(statCommands);
        getCommand("setSP").setExecutor(statCommands);
        getCommand("iniAttack").setExecutor(statCommands);
        getCommand("iniHealth").setExecutor(statCommands);
        getCommand("iniDefense").setExecutor(statCommands);
        getCommand("iniAttackSpeed").setExecutor(statCommands);
        getCommand("iniMovementSpeed").setExecutor(statCommands);
        
        //가방 관련 커멘드
        getCommand("backpack").setExecutor(backpackCommand);
    }
}