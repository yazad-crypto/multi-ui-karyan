package edu.bothell.multi_ui.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class Crawler {
    // PROPERTIES ---------------------------------------------------------------------
    private final World w;
    private final Stack<Location> stack;
    private int startX;
    private int startY;

    // CONSTRUCTOR --------------------------------------------------------------------
    public Crawler(World w){
        this.w = w;
        this.stack = new Stack<>();
    }

    // STATIC UTILITIES ---------------------------------------------------------------
    public static Terrain bestFit(Location l, Random die){
        // Step 0: If the location already has a terrain, return it
        // We are getting the terrain FOR location L!!!!
        if(l.getTerrain() != null) return l.getTerrain();

        // Step 1: Collect placeholders for neighbor terrains prefs
        int[] totalPrefs = new int[Terrain.values().length];
        boolean hasNeiTerrain = false;      // book-keeping

        // Step 3: Iterate through all Neighbords to sum preferences
        for(Location nei: l.getAdjacents()){
            Terrain t = nei.getTerrain();   // Fetch terrain of the neighbor
            if(t == null) continue;         // Skip this if this neighbor location isn't set
            else hasNeiTerrain = true;      // If it is, don't worry about this check again.
            
            int[] nPrefs = t.getPreference();
            for(int i=0; i<totalPrefs.length; i++){
                // TODO: Need to add check for max connected.
                totalPrefs[i] += nPrefs[i];
            } 
        }

        // Step 4: (sideeffect) If there are no locations exit poorly
        if (!hasNeiTerrain)  
            throw new IllegalStateException("No adjacent locations have defined terrains.");
        
        // Step 5: Calculate total weight
        int totalWeight = 0;
        for (int weight : totalPrefs) totalWeight += weight;
        
        // Step 6: Load the dice and roll!
        int roll = die.nextInt(totalWeight);
        Terrain[] terrains = Terrain.values();
        for (int i = 0; i < terrains.length; i++) {
            roll -= totalPrefs[i];
            if (roll < 0)               return terrains[i];
        }

        return Terrain.BRICK;
    }

    // METHODS -----------------------------------------------------------------------
    public void walls(int startX, int startY, Random die){
        Location start = w.getLocation(startX, startY);
        if (start == null) {
            throw new IllegalStateException("Starting location not found at (" + startX + ", " + startY + ").");
        }


        stack.push(start);

        while( !stack.isEmpty() ){
            Location l = stack.pop();
            for(Location nei : l.getAdjacents() ){
                if(nei.getTerrain() == null){
         
                    stack.push(nei);
                }
            }
        }
    }

    public void terrain(int startX, int startY, Terrain startT, Random die ){
        Location start = w.getLocation(startX, startY); 
        if (start == null) {
            throw new IllegalStateException("Starting location not found at (" + startX + ", " + startY + ").");
        }

        // Set the initial terrain
        start.setTerrain(startT);
        stack.push(start);

        while( !stack.isEmpty() ){
            Location l = stack.pop();
            for(Location nei : l.getAdjacents() ){
                System.out.println(nei);
                
                if(nei.getTerrain() == null){
                    Terrain neiTerrain = bestFit(nei,die);
                    nei.setTerrain(neiTerrain);
                    stack.push(nei);
                }/* */
            }
        }        
    }

}
