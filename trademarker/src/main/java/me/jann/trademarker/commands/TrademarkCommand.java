package me.jann.trademarker.commands;

import me.jann.trademarker.Trademarker;
import me.jann.trademarker.WatermarkRenderer;
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

    private final Trademarker main;
    public TrademarkCommand(Trademarker main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command.");
            return true;
        }

        Player player = (Player)sender;
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.FILLED_MAP)){
            player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.not_holding_map")));
            return true;
        }

        if(!player.hasPermission("trademarker.use")){
            player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.no_perms")));
            return true;
        }

        if(args.length < 1) return true;

        ItemStack item = player.getInventory().getItemInMainHand();
        MapMeta meta = (MapMeta) item.getItemMeta();
        ArrayList<String> lore;

        switch(args[0]) {
            case "remove":
                if (!meta.hasLore()){
                    player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.remove_no_trademark")));
                    break;
                }

                if ( !(meta.getLore().get(0)).contains(player.getName()) && !player.hasPermission("trademarker.removeother") ) {
                    player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.cant_remove")));
                    break;
                }

                lore = new ArrayList<>();
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.trademark_removed")));
                break;
            case "add":
                if (!meta.hasLore()) {
                    player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.cant_trademark")));
                    break;
                }

                lore = new ArrayList<>();
                String trademark = main.getConfig().getString("lang.trademark_format");
                trademark = trademark.replace("%player%",player.getName());
                trademark = Trademarker.colorCode(trademark);
                lore.add(trademark);
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.trademark_added")));
                break;
            case "watermark":
                if(!player.hasPermission("trademarker.watermark")){
                    player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.no_perms")));
                    return true;
                }

                if(!(meta.getLore().get(0).contains(player.getName())) && !player.hasPermission("trademarker.watermarkother")){
                    player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.watermark_others_trademarked")));
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

                view.addRenderer(new WatermarkRenderer(player.getName(),posx,posy));

                player.sendMessage(Trademarker.colorCode(main.getConfig().getString("lang.watermark_added")));

                break;
        }

        return true;
    }

}
