package edu.bothell.multi_ui.core;

import java.util.ArrayList;
import java.util.Random;

public class Wall extends Thing {
    public Wall(){
        super(2);
    }

    public static void build(Location l, Random die){
        if(getWallMax(l) == 0) return;
        
        Wall[] walls = l.getWalls(); // get existing walls

        ArrayList<Directions> ds = new ArrayList<>();
        for(int i = 0; i < walls.length; i++){
            if(walls[i] == null) ds.add( Directions.values()[i]);
        }
        
    }
    
    public static int getWallMin(Location l){
        if(l.getTerrain() == null) return 0;
        return Math.min(l.getTerrain().getWallsMin() - getWallCount(l),0);
    }

    public static int getWallMax(Location l){
        if(l.getTerrain() == null) return Location.EDGES - 1;
        return Math.min(l.getTerrain().getWallsMax() - getWallCount(l), Location.EDGES - 1 );
    }

    public static int getWallCount(Location l){
        int count = 0;
        for(Wall w:l.getWalls() ) if(w != null) count++;
        return count;
    }
}
