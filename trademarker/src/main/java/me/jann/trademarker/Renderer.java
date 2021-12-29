package me.jann.trademarker;

import org.bukkit.entity.Player;
import org.bukkit.map.*;

public class Renderer extends MapRenderer {

    private boolean done;
    private String name;
    private String posx;
    private String posy;

    public Renderer(String name, String posx, String posy){
        done = false;
        this.name = name;
        this.posx = posx;
        this.posy = posy;
    }

    @Override
    public void render(MapView view, MapCanvas canvas, Player player) {
        if (done) return;


        int width = MinecraftFont.Font.getWidth(name);
        int height = MinecraftFont.Font.getHeight();
        int x;
        int y;

        switch(posy){
            case "bottom":
                y = 128-height-1;
                break;
            default:
                y = 1;
        }

        switch(posx){
            case "left":
                x = 1;
                break;
            case "right":
                x = 128-width-1;
                break;
            default:
                x = (128-width)/2;
                break;
        }

        canvas.drawText(x,y, MinecraftFont.Font,name);
        done = true;
    }

}
