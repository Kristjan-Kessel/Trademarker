package me.jann.trademarker;

import me.jann.trademarker.commands.ReloadCommand;
import me.jann.trademarker.commands.TrademarkCommand;
import me.jann.trademarker.commands.TrademarkTab;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Trademarker extends JavaPlugin implements Listener {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    public static String colorCode(String message) {
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void onEnable() {

        getConfig().addDefault("lang.cant_remove","&cYou can't remove this trademark.");
        getConfig().addDefault("lang.trademark_removed","&aTrademark removed.");
        getConfig().addDefault("lang.trademark_added","&aTrademark added.");
        getConfig().addDefault("lang.not_holding_map","&cYou need to be holding a map to use this command.");
        getConfig().addDefault("lang.cant_duplicate","&cYou can't duplicate this.");
        getConfig().addDefault("lang.cant_trademark","&cThis map has already been trademarked.");
        getConfig().addDefault("lang.remove_no_trademark","&cThis map hasn't been trademarked.");
        getConfig().addDefault("lang.watermark_others_trademark","&cYou can only watermark your own trademarked maps!");
        getConfig().addDefault("lang.watermark_added","&aWatermark added.");
        getConfig().addDefault("lang.no_perms","&cYou don't have permission to use this command.");
        getConfig().addDefault("lang.trademark_format","&cBy %player%");
        getConfig().options().copyDefaults(true);
        saveConfig();


        reloadConfig();

        this.getServer().getPluginManager().registerEvents(new CopyEvents(this), this);
        getCommand("trademark").setExecutor(new TrademarkCommand(this));
        getCommand("trademark").setTabCompleter(new TrademarkTab());
        getCommand("trademarkreload").setExecutor(new ReloadCommand(this));
    }

}