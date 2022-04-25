package me.jann.trademarker;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CartographyInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class CopyEvents implements Listener {

    private final Trademarker main;
    public CopyEvents(Trademarker main){
        this.main = main;
    }

    @EventHandler
    public void craftEvent(CraftItemEvent event) {
        ItemStack item = event.getInventory().getResult();

        if(item == null || item.getAmount()==0) return;

        if (item.getType() != Material.FILLED_MAP) return;

        if (item.getItemMeta().hasLore() && !(item.getItemMeta().getLore().get(0)).contains(event.getWhoClicked().getName()) && !event.getWhoClicked().hasPermission("trademarker.bypass")) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.cant_duplicate")));
        }

    }

    @EventHandler
    public void copyEvent(InventoryClickEvent event){
        if(!event.getInventory().getType().equals(InventoryType.CARTOGRAPHY)) return;

        CartographyInventory inv = (CartographyInventory) event.getInventory();
        ItemStack item = inv.getItem(2);
        if(item == null) return;
        Player player = (Player) event.getWhoClicked();

        if (item.getItemMeta().hasLore() && !(item.getItemMeta().getLore().get(0)).contains(player.getName()) && !player.hasPermission("trademarker.bypass")) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.cant_duplicate")));
        }

    }

}
