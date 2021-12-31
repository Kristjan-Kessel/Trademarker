package me.jann.trademarker;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.ArrayList;

public class TrademarkCommand implements CommandExecutor {

    main main;
    public TrademarkCommand(main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command.");
            return true;
        }

        Player p = (Player)sender;
        if (!p.getInventory().getItemInMainHand().getType().equals(Material.FILLED_MAP)){
            p.sendMessage(ChatColor.RED + "You need to be holding a map to use this command");
            return true;
        }

        if(!p.hasPermission("trademarker.use")){
            p.sendMessage(ChatColor.RED+"You don't have permission to use this command");
            return true;
        }

        if(args.length < 1) return true;

        ItemStack item = p.getInventory().getItemInMainHand();
        MapMeta meta = (MapMeta) item.getItemMeta();
        ArrayList<String> lore;

        switch(args[0]) {
            case "remove":
                if (meta.hasLore()) {
                    if (!(meta.getLore().get(0)).contains(p.getName()) && !p.hasPermission("trademarker.removeother")) {
                        p.sendMessage(ChatColor.RED + "You can't remove this trademark.");
                    } else {
                        lore = new ArrayList();
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.sendMessage(ChatColor.GREEN + "Trademark was removed.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "This map hasn't been trademarked.");
                }
                break;
            case "add":
                if (!meta.hasLore()) {
                    lore = new ArrayList();
                    lore.add(ChatColor.RED + "By " + p.getName());
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                } else {
                    p.sendMessage(ChatColor.RED + "This map has already been trademarked.");
                }
                break;
            case "watermark":
                if(!p.hasPermission("trademarker.watermark")){
                    p.sendMessage(ChatColor.RED+"You don't have permission to use this command.");
                    return true;
                }

                if(!(meta.hasLore() && meta.getLore().get(0).contains(p.getName())) && !p.hasPermission("trademarker.watermarkother")){
                    p.sendMessage(ChatColor.RED+"You can only watermark your own trademarked maps!");
                    break;
                }

                MapView view = meta.getMapView();
                String posx = "";
                String posy = "";

                if(args.length>1){
                    posy = args[1];
                    if(args.length>2){
                        posx = args[2];
                    }
                }

                view.addRenderer(new WatermarkRenderer(p.getName(),posx,posy));

                break;
            default:
                p.sendMessage(ChatColor.RED + "Use a valid sub-command: add, remove");
        }

        return true;
    }

}
