package me.jann.trademarker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class TrademarkTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> poscompl = new ArrayList<>();

        if(args.length == 1){
            completions.add("add");
            completions.add("remove");
            if(sender.hasPermission("trademarker.watermark")) completions.add("watermark");
            poscompl.addAll(completions);
            poscompl.removeIf(s -> !s.startsWith(args[0]));
        }

        if(args.length == 2){
            if(args[0].equals("watermark")){
                completions.add("top");
                completions.add("bottom");
            }
            poscompl.addAll(completions);
            poscompl.removeIf(s -> !s.toLowerCase().startsWith(args[1].toLowerCase()));
        }

        if(args.length == 3){
            if(args[0].equals("watermark")){
                completions.add("left");
                completions.add("middle");
                completions.add("right");
            }
            poscompl.addAll(completions);
            poscompl.removeIf(s -> !s.toLowerCase().startsWith(args[2].toLowerCase()));
        }

        if(!poscompl.isEmpty()){
            completions = poscompl;
        }

        return completions;
    }

}
