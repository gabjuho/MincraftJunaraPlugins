package com.gabjuho.junaraplugin.backpack;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class BackpackEvent implements Listener {

    static DataManager dataManager = DataManager.getInstance();
    static Backpack backpack = new Backpack();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,"가방"); // owner를 player에서 null로 변경

        if(dataManager.getDataConfig().contains("backpacks."+player.getUniqueId()))
        {
            for(String item:dataManager.getDataConfig().getConfigurationSection("backpacks." + player.getUniqueId()).getKeys(false))
            {
                inv.addItem(backpack.loadItem(Objects.requireNonNull(dataManager.getDataConfig().getConfigurationSection("backpacks." + player.getUniqueId() + "." + item))));
            }
        }
        backpack.getBackpackHashMap().put(player.getUniqueId(),inv);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if(!dataManager.getDataConfig().contains("backpacks."+player.getUniqueId()))
        {
            dataManager.getDataConfig().createSection("backpacks."+player.getUniqueId());
        }

        char c = 'a';

        if(dataManager.getDataConfig().contains("backpacks."+player.getUniqueId()))
        {
            for (String item : dataManager.getDataConfig().getConfigurationSection("backpacks." + player.getUniqueId()).getKeys(false))
            {
                dataManager.getDataConfig().set("backpacks."+player.getUniqueId()+"."+item,null); //인벤토리가 비어있을 때, config저장시 uuid섹션 사라지는 버그 수정
            }
        }

        for(ItemStack itemStack: backpack.getBackpackHashMap().get(player.getUniqueId())) // 서버 리로드 시 해쉬맵이 초기화되서, get에서 null이 반환됨
        {
            if(itemStack != null)
            {
                backpack.saveItem(DataManager.getInstance().getDataConfig().createSection("backpacks."+player.getUniqueId() + "." + c++),itemStack);
                //아이템을 넣는 건 저장이 되는데, 빼는 건 저장이 안됨. -> 해결됨
            }
        }

        dataManager.saveDataConfig();
    }

    @EventHandler
    public static void onGetItem(EntityPickupItemEvent event)
    {
        Player player = (Player) event.getEntity();
        ItemStack item = event.getItem().getItemStack();
        Inventory inv = backpack.getBackpackHashMap().get(player.getUniqueId());

        if(inv != null)
        {
            event.setCancelled(true);
            if(inv.firstEmpty() != -1) {
                event.getItem().remove();
                inv.addItem(item);
                backpack.getBackpackHashMap().put(player.getUniqueId(), inv);
            }
//            else
//            {
//                player.sendMessage(ChatColor.RED + "인벤토리가 꽉 찼습니다."); // 이 문구가 아이템 주을 때 마다 생성되서 task 이용해서 해볼 예정 (퀘스트 텍스트에도 영향을 끼칠 수 있으므로)
//            }
        }
        else
        {
            player.sendMessage("인벤토리가 존재하지 않습니다.");
        }
    }
}
