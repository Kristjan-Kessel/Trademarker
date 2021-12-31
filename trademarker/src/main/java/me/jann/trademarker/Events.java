package me.jann.trademarker;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CartographyInventory;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    @EventHandler
    public void craftEvent(CraftItemEvent e) {
        if (!e.getInventory().getResult().getType().equals(Material.FILLED_MAP)) return;

        ItemStack item = e.getInventory().getResult();

        if (item.getItemMeta().hasLore() && !(item.getItemMeta().getLore().get(0)).contains(e.getWhoClicked().getName()) && !e.getWhoClicked().hasPermission("trademarker.bypass")) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.RED + "You can't duplicate this map");
        }

    }

    @EventHandler
    public void copyEvent(InventoryClickEvent e){
        if(!e.getInventory().getType().equals(InventoryType.CARTOGRAPHY)) return;

        CartographyInventory inv = (CartographyInventory) e.getInventory();
        ItemStack item = inv.getItem(2);
        Player p = (Player) e.getWhoClicked();

        if (item.getItemMeta().hasLore() && !(item.getItemMeta().getLore().get(0)).contains(p.getName()) && !p.hasPermission("trademarker.bypass")) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.RED + "You can't duplicate this map");
        }

    }

}
