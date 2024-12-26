package edu.bothell.multi_ui.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class World extends State {
    // PROPERTIES ----------------------------------------------------------------
    private final char[][] S = super.getIt();
    private Location[][] map; // Map array is y,x
    
    // CONSTRUCTOR ----------------------------------------------------------------
    public World(){
        super();
        this.map = new Location[S.length][];

        build();
    }

    // METHODS -------------------------------------------------------------------
    public void build() {
        Random die = new Random();
        int[][] connectionCounts = new int[Terrain.values().length][1];
    
        for (int y = 0; y < S.length; y++) {
            map[y] = new Location[S[y].length];
    
            for (int x = 0; x < map[y].length; x++) {
                // Step 1: Create the location
                map[y][x] = new Location(x, y, Terrain.CAVE);
    
                // Step 2: Apply walls
                //applyWalls(x, y, die);
    
                // Step 3: Apply terrain based on walls
                //Terrain terrain = getTerrainMatch(x, y, connectionCounts, die);
                //map[y][x].setTerrain(terrain);
            }
        }
    
        setAdjacents(); // Finalize adjacency relationships
    }

    private void applyWalls(int x, int y, Random die) {
        Location current = map[y][x];

        // Assume the starting terrain is CITY
        Terrain terrain = Terrain.CITY;

        // Randomly select the number of walls within the range for CITY
        int wallsCount = terrain.getWallsMin() + die.nextInt(terrain.getWallsMax() - terrain.getWallsMin() + 1);

        // Randomly apply walls until the desired count is reached
        List<Directions> directions = new ArrayList<>(Arrays.asList(Directions.values()));
        Collections.shuffle(directions, die); // Shuffle directions for randomness

        for (int i = 0; i < wallsCount; i++) {
            Directions dir = directions.get(i);
            current.setWall(dir, true);

            // Align walls with the adjacent location
            Location neighbor = getNeighbor(x, y, dir);
            if (neighbor != null) {
                neighbor.setWall(dir.opposite(), true);
            }
        }
    }    

    private Terrain getTerrainMatch(int x, int y, int[][] counts, Random die){
        Terrain[] terrains = Terrain.values();
        int[] weights = new int[terrains.length];

        // Calculate weights based on adjacency and maxConnect
        for (int i = 0; i < terrains.length; i++) {
            Terrain t = terrains[i];

            weights[i] = calcAdjWeight(x, y, t);

            // Exclude terrains that exceed maxConnect
            if (counts[t.ordinal()][0] >= t.getMaxConnect())  weights[i] = 0;
        }

        Terrain it = getWeightedRnd(terrains, weights, die);
        // Increment the connection count for the selected terrain
        counts[it.ordinal()][0]++;

        System.out.printf("Weights for (%d, %d): %s%n", x, y, Arrays.toString(weights));
        return it;
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
        
        for(int y =0; y < map.length; y++){
            Location[] row = map[y];
            for (int x = 0; x < row.length; x++) {
                Location l = row[x];
                for(Directions d: Directions.values()){
                    Location nei = getNeighbor(x,y,d);

                    if(nei != null) l.addAdjacent(nei);
                }
            }
        }
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
