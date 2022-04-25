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

    Trademarker main = Trademarker.getPlugin(Trademarker.class);

    @EventHandler
    public void craftEvent(CraftItemEvent e) {
        ItemStack item = e.getInventory().getResult();

        if(item == null || item.getAmount()==0) return;

        if (item.getType().equals(Material.FILLED_MAP)){
            if (item.getItemMeta().hasLore() && !(item.getItemMeta().getLore().get(0)).contains(e.getWhoClicked().getName()) && !e.getWhoClicked().hasPermission("trademarker.bypass")) {
                e.setCancelled(true);
                e.getWhoClicked().sendMessage(main.colorcode(main.getConfig().getString("lang.cant_duplicate")));
            }
        }else if(item.getType().equals(Material.WRITTEN_BOOK)){
            BookMeta meta = (BookMeta) item.getItemMeta();
            if(meta.hasAuthor() && meta.getAuthor().equals(e.getWhoClicked().getName()) && !e.getWhoClicked().hasPermission("trademarker.bypass") && main.getConfig().getBoolean("protect_books")){
                e.setCancelled(true);
                e.getWhoClicked().sendMessage(main.colorcode(main.getConfig().getString("lang.cant_duplicate")));
            }
        }
    }

    @EventHandler
    public void copyEvent(InventoryClickEvent e){
        if(!e.getInventory().getType().equals(InventoryType.CARTOGRAPHY)) return;

        CartographyInventory inv = (CartographyInventory) e.getInventory();
        ItemStack item = inv.getItem(2);
        if(item == null) return;
        Player p = (Player) e.getWhoClicked();

        if (item.getItemMeta().hasLore() && !(item.getItemMeta().getLore().get(0)).contains(p.getName()) && !p.hasPermission("trademarker.bypass")) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(main.colorcode(main.getConfig().getString("lang.cant_duplicate")));
        }

    }

}
