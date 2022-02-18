package com.gabjuho.junaraplugin;

import com.gabjuho.junaraplugin.commands.JunaraCommand;
import com.gabjuho.junaraplugin.events.JunaraEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable()
    {
        JunaraCommand commands = new JunaraCommand();
        getServer().getPluginManager().registerEvents(new JunaraEvent(),this);
        getCommand("stat").setExecutor(commands);
        getCommand("giveStat").setExecutor(commands);
        getCommand("iniAttack").setExecutor(commands);
        getCommand("iniHealth").setExecutor(commands);
        getCommand("iniDefense").setExecutor(commands);
        getCommand("iniAttackSpeed").setExecutor(commands);
        getCommand("iniMovementSpeed").setExecutor(commands);
        getCommand("tpWindow").setExecutor(commands);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[JunaraPlugin]: Plugin is enabled!");
    }
    @Override
    public void onDisable()
    {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[JunaraPlugin]: Plugin is disabled!");
    }
}
