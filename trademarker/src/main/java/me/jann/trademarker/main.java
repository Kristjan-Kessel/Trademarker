package me.jann.trademarker;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin implements Listener {

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        getCommand("trademark").setExecutor(new TrademarkCommand(this));
        getCommand("trademark").setTabCompleter(new TrademarkTab());
    }

}