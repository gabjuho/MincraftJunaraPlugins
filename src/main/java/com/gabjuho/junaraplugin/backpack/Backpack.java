package com.gabjuho.junaraplugin.backpack;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class Backpack {

    private static final HashMap<UUID,Inventory> backpacks = new HashMap<>();

    public void open(Player player)
    {
        player.openInventory(backpacks.get(player.getUniqueId()));
    }

    public void saveItem(ConfigurationSection section,ItemStack itemStack)
    {
        try{

            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(itemStack);
            os.flush();
            byte[] serializeObject = io.toByteArray();

            String encodedObject = Base64.getEncoder().encodeToString(serializeObject);
            section.set("item",encodedObject);
        }catch(IOException e) {
            System.out.println(e);
        }
    }

    public HashMap<UUID,Inventory> getBackpackHashMap()
    {
        return backpacks;
    }

    public ItemStack loadItem(ConfigurationSection section)
    {
        byte[] serializeObject = Base64.getDecoder().decode(section.getString("item"));

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(serializeObject);
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);

            try {
                return (ItemStack) is.readObject();
            }catch(ClassNotFoundException e)
            {
                System.out.println(e);
            }
        }catch (IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
}