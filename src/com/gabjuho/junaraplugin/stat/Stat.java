package com.gabjuho.junaraplugin.stat;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Stat {

    public static void open(Player player) {

        StatData attack = new Attack();
        StatData health = new MaxHealth();
        StatData defense = new Defense();
        StatData attackSpeed = new AttackSpeed();
        StatData movementSpeed = new MovementSpeed();
        StatData background = new BackGround();

        ItemStack backgroundGUI = background.makeStatGUI(player);

        int size = DataManager.getInstance().getConfig().getInt("stat.inventory-size");

        Inventory inv = Bukkit.createInventory(null, size, "스텟"); //사이즈 관련 문제

        for(int i=0; i<size ; i++)
        {
            inv.setItem(i,backgroundGUI);
        }

        inv.setItem(attack.place, attack.makeStatGUI(player));
        inv.setItem(health.place, health.makeStatGUI(player));
        inv.setItem(defense.place, defense.makeStatGUI(player));
        inv.setItem(attackSpeed.place, attackSpeed.makeStatGUI(player));
        inv.setItem(movementSpeed.place, movementSpeed.makeStatGUI(player));

        player.openInventory(inv);
    }
}
