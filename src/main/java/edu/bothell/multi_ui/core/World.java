package edu.bothell.multi_ui.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class World extends State {
    // PROPERTIES ----------------------------------------------------------------
    private final char[][] S = super.getIt();
    private Location[][] map; // Map array is y,x
    private List<Location> all = new ArrayList<>();
    
    // CONSTRUCTOR ----------------------------------------------------------------
    public World(){
        super();
        this.map = new Location[S.length][];

        build();
    }

    // METHODS -------------------------------------------------------------------
    public void build() {
        Random die = new Random();
        
        

        

        for (int y = 0; y < S.length; y++) {
            
            map[y] = new Location[S[y].length];
            for (int x = 0; x < map[y].length; x++) {

                // Step 1: Create the location if it doesn't exist
                map[y][x] = (map[y][x] == null)? new Location(x, y, null):map[y][x];

                // Step 2: Loop Directions and add them as adjacent
                for(Directions d: Directions.values()){
                    map[y][x].addAdjacent(getNeighbor(map[y][x], d));
                    
                }
        
                
                
            }
        }

        for(Location[] row: map){
            for(Location l:row) setTerrain(l, die);
        }


        System.out.println();
        System.out.println();
        System.out.println(map[3][3].getAdjacents());
        System.out.println();
        //setTerrain(map[2][3],die);



    }

    private boolean setTerrain(Location l, Random die){
        if(l == null || l.getTerrain() != null) return false;

        l.setTerrain(l.getBestTerrian(die) );
        
        /*
        if(l != null && l.getAdjacents() != null)
            for(Location adj: l.getAdjacent())
                if(adj.getTerrain() == null) 
                    adj.setTerrain(adj.getBestTerrian(die));
         
        */
        return true;
    }






    private Terrain getWeightedRnd(Terrain[] terrains, int[] weights, Random die) {

        int total = 0;
        for(int w:weights) total +=w ;

        if(total == 0){ 
            System.out.println("All terrains exceeded maxConnect. Using fallback terrain.");
            return Terrain.BRICK;
        }

        // Select a random value within the range of total weight
        int rnd = die.nextInt(total);
        int cumulativeWeight = 0;

        for(int i= 0; i <terrains.length; i++){
            cumulativeWeight += weights[i];
            if(rnd < cumulativeWeight) return terrains[i];
        }

        return Terrain.BRICK;
    }

    public Location[][] getMap(){
        return this.map;
    }

    private int calcAdjWeight(int x, int y, Terrain t) {   

        int weight = 0;
        // Iterate through all possible directions
        for (Directions d : Directions.values()) {
            Location nL = getNeighbor(x, y, d);
            if (nL != null) {
                Terrain nT = nL.getTerrain();
                if (nT != null) {
                    // Add the preference weight for the neighbor's terrain
                    weight += t.getPreference(nT);
                }
            }
        }

        return weight;
    }
    
    private void setAdjacents(){
        
        for(Location[] row : map)
            for (Location l : row) 
                for(Directions d: Directions.values()){
                    Location nei = getNeighbor(l,d);

                    if(nei != null) l.addAdjacent(nei);
                    System.out.println("....doing it "+ nei);
                }
    }

    private Location getNeighbor(Location l, Directions d) {
        return getNeighbor(l.getX(), l.getY(), d);
    }
    private Location getNeighbor(int x, int y, Directions d) {
        // Calculate the neighbor coordinates
        int nX = x + d.dX() + ((d.dY() != 0) ? 0 : y % 2);
        int nY = y + d.dY();



        // Wrap vertical (rows)
        nY = (nY + map.length) % map.length;
    
        // Ensure the row exists
        if (map[nY] == null) {
            map[nY] = new Location[S[nY].length]; // Initialize the row if it doesn't exist
        }
    
        // Wrap horizontal (columns within the row)
        nX = (nX + map[nY].length) % map[nY].length;

        // Ensure the location exists
        if (map[nY][nX] == null) {
            map[nY][nX] = new Location(nX, nY, null); // Create a new Location if it doesn't exist
        }
    
        return map[nY][nX];
    }
    

    @Override
    public Object getIt(int[] pos){
        System.out.println("world get's it!");
        return map[pos[1]][pos[0]];
    }

}
