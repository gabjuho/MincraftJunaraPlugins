package com.gabjuho.junaraplugin.stat;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class StatEvent implements Listener {

    static DataManager dataManager = DataManager.getInstance();
    private final static StatData attack = new Attack();
    private final static StatData health = new MaxHealth();
    private final static StatData defense = new Defense();
    private final static StatData attackSpeed = new AttackSpeed();
    private final static StatData movementSpeed = new MovementSpeed();
    private final static StatData initButton = new InitButton();

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if(!(dataManager.getDataConfig().contains("stat." + player.getUniqueId())))
        {
            dataManager.getDataConfig().set("stat." + player.getUniqueId() + ".스텟포인트",0);
            dataManager.getDataConfig().set("stat." + player.getUniqueId() + ".공격력포인트",0);
            dataManager.getDataConfig().set("stat." + player.getUniqueId() + ".체력포인트",0);
            dataManager.getDataConfig().set("stat." + player.getUniqueId() + ".방어력포인트",0);
            dataManager.getDataConfig().set("stat." + player.getUniqueId() + ".공격속도포인트",0);
            dataManager.getDataConfig().set("stat." + player.getUniqueId() + ".이동속도포인트",0);
            dataManager.saveDataConfig();
        }
        else
        {
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(dataManager.getDataConfig().getInt("stat." + player.getUniqueId() + ".공격력포인트") * attack.perValue + 1);
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(dataManager.getDataConfig().getInt("stat." + player.getUniqueId() + ".체력포인트") * health.perValue + 20);
            player.setHealth(dataManager.getDataConfig().getInt("stat." + player.getUniqueId() + ".체력포인트") * health.perValue + 20);
            player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(dataManager.getDataConfig().getInt("stat." + player.getUniqueId() + ".방어력포인트") * defense.perValue);
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(dataManager.getDataConfig().getInt("stat." + player.getUniqueId() + ".공격속도포인트") * attackSpeed.perValue +4);
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(dataManager.getDataConfig().getInt("stat." + player.getUniqueId() + ".이동속도포인트") * movementSpeed.perValue + 0.1);
        }
    }

    @EventHandler
    public static void onStatPointClick(InventoryClickEvent event) // 스텟 GUI 구성
    {
        if(event.getView().getTitle().equals("스텟")) {
            Player player = (Player)event.getWhoClicked();

            int statPoint = dataManager.getDataConfig().getInt("stat." + player.getUniqueId() + ".스텟포인트");

            if(event.getView().getTitle().equals("스텟") && event.getCurrentItem() != null) {
                event.setCancelled(true);

                if(event.getCurrentItem().getItemMeta().getDisplayName().equals(attack.name)) {
                    attack.clickStat(statPoint,player);
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(health.name)) {
                    health.clickStat(statPoint,player);
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(defense.name)) {
                    defense.clickStat(statPoint,player);
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(attackSpeed.name)) {
                    attackSpeed.clickStat(statPoint,player);
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(movementSpeed.name)) {
                    movementSpeed.clickStat(statPoint,player);
                }
                else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(initButton.name)) {
                    initButton.clickStat(statPoint,player);
                }
            }
        }
    }

    @EventHandler
    public static void onCloseStatWindow(InventoryCloseEvent event)
    {
        if(event.getView().getTitle().equals("스텟"))
        {
            dataManager.saveDataConfig();
        }
    }
}
