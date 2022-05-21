package com.gabjuho.junaraplugin.backpack;

import com.gabjuho.junaraplugin.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
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
        Inventory inv = Bukkit.createInventory(null, dataManager.getConfig().getInt("backpack.inventory-size"),"가방"); // owner를 player에서 null로 변경

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
    public void onAttemptPickUp(PlayerAttemptPickupItemEvent event)
    {
        if (!dataManager.getConfig().getBoolean("use-automatic-pick-up"))
            return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack(); //먹은 아이템
        ItemStack temp; //먹은 아이템 복사할 임시 저장소
        Inventory inv = backpack.getBackpackHashMap().get(player.getUniqueId());

        if (inv == null) {
            player.sendMessage("인벤토리가 존재하지 않습니다.");
            return;
        }


        event.setCancelled(true);
        event.getItem().remove();

        if (inv.firstEmpty() == -1) {
            for (ItemStack invItem : inv.getContents()) {
                if (invItem == null)
                    continue;
                if (invItem.getType() != item.getType() && invItem.getAmount() >= invItem.getMaxStackSize() && !(invItem.getItemMeta().equals(item.getItemMeta())))
                    continue;
                int canStore = invItem.getMaxStackSize() - invItem.getAmount(); // canStore은 최대 아이템을 먹을 수 있는 개수
                temp = item.clone();
                temp.setAmount(canStore);
                if (item.getAmount() > canStore) {//먹을 수 있는 개수가 먹은 아이템 개수 보다 적을 때
                    inv.addItem(temp);
                    item.setAmount(item.getAmount() - canStore);
                } else {
                    inv.addItem(item);
                    item.setAmount(0);
                    break;
                }
            }
            if (item.getAmount() > 0)
                player.getWorld().dropItem(player.getLocation().add(0, 1, 0), item);
        } else
            inv.addItem(item);

        backpack.getBackpackHashMap().put(player.getUniqueId(), inv);
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event)
    {
        Player player = (Player) event.getPlayer();
        ItemStack item = player.getItemOnCursor();

        if(event.getView().getTitle().equals("가방")) {
            if(item.getType() == Material.AIR)
                return;
            player.setItemOnCursor(null);
            if(backpack.getBackpackHashMap().get(player.getUniqueId()).firstEmpty() != -1)
                backpack.getBackpackHashMap().get(player.getUniqueId()).addItem(item);
            else //자꾸 오류 뜸 105번째 줄 조건식에 문제가 있나 아니면 아래 getLocation add에 문제가 잇나 cannot drop air라네요. -> 해결
                player.getWorld().dropItem(player.getLocation().add(0,1,0),item);
        }
    }
}
