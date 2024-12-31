package edu.bothell.multi_ui.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.bothell.multi_ui.ui.swing.Locatable;

public class Location {
    // PROPERTIES ----------------------------------------------------------------
    public static final int EDGES = Directions.values().length; // Walls: {NE, E, SE, SW, W, NW}
    private final Wall[] walls = new Wall[EDGES]; 
    private final List<Thing> occupants = new ArrayList<>(); // Objects in this location
    private final List<Location> adjacents = new ArrayList<>(); // Adjacent locations
    private Terrain t;
    private Locatable uiElem; // Link to GUI element (e.g., Swing or Web)

    private final int x, y; // Hex grid coordinates
   // private Game game; // Reference to the parent game

    // CONSTRUCTOR --------------------------------------------------------------
    public Location(int x, int y, Terrain t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }

    // METHODS ------------------------------------------------------------------

    

    public void setTerrain(Terrain t) {
        this.t = t;
    }

    public Terrain getTerrain() {
        return t;
    }    

    // Walls
    public void setWalls(boolean[] walls) {
        //System.arraycopy(walls, 0, this.walls, 0, Math.min(walls.length, this.walls.length));
    }
    
    public Wall[] getWalls() {
        return walls.clone();
    }

    public boolean hasWall(Directions dir) {
        return walls[dir.ordinal()] != null;
    }

    public void setWall(Directions dir, boolean hasWall) {
       // walls[dir.ordinal()] = hasWall;
    }

    // Adjacent Locations
    public List<Location> getAdjacents() {
        System.out.println("am I about to null!!!!! " + adjacents);
        return new ArrayList<>(adjacents); // Return a copy to prevent external modification
    }

    public void addAdjacent(Location loc) {

        if (loc != null && !adjacents.contains(loc)) {
            System.out.println("add? " + loc + "  " + adjacents);
            adjacents.add(loc);
        }
        else{
            System.out.println();
            System.out.println();
            System.out.println("--------------addA");   
            System.out.println("already");
        }
    }

    public void removeAdjacent(Location loc) {
        adjacents.remove(loc);
    }

    // Occupants
    public List<Thing> getOccupants() {
        return new ArrayList<>(occupants); // Return a copy to prevent external modification
    }

    public void addOccupant(Thing occupant) {
        if (occupant != null && !occupants.contains(occupant)) {
            occupants.add(occupant);
        }
    }

    public void removeOccupant(Thing occupant) {
        occupants.remove(occupant);
    }

    public boolean hasOccupantOfType(Class<? extends Thing> type) {
        return occupants.stream().anyMatch(type::isInstance);
    }

    // GUI Link
    public Locatable getUiElem() {
        return uiElem;
    }

    public void setUiElem(Locatable uiElem) {
        this.uiElem = uiElem;
    }

    // Position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Utility Methods
    @Override
    public String toString() {
        if(adjacents.size() > 1 && t != null) return "" + t.getSymbol();
        return "L{" + x +
                "," + y +
                '}';
    }

    public List<Location> getMatchTerrain(List<Location> visited){
        if (this.getTerrain() == null) return visited;
        if (visited == null) visited = new ArrayList<>();
        

        // Add this location to the visited list
        if   (!visited.contains(this)) visited.add(this);
        else return visited; // Avoid infinite loops       
    
        // Explore adjacent locations
        for (Location adj : this.adjacents) {
            if (adj.getTerrain() != null 
                    && adj.getTerrain().equals(this.getTerrain()) 
                    && !visited.contains(adj)) {
                adj.getMatchTerrain(visited); // Recursively collect matches
            }
        }
    
        return visited;
    }   


    public Terrain getBestTerrian(Random die) {

        // Initialize the hashmap for terrain odds
        Map<Terrain, Integer> weights = new HashMap<>();

        // Step 2: Update weights based on adjacent terrains
        for (Location adj : this.adjacents) {
            Terrain at = adj.getTerrain();
            if (at != null) {
                for (Terrain t : Terrain.values()) {
                    int preference = t.getPreference(at);
                    weights.merge(t, preference, Integer::sum);
                }
            }
            else
                System.out.print(" [ "+ adj +" ] ");
        }

        // Step 5: Fallback case for debugging
        if (weights.isEmpty()) {

            return Terrain.CITY;
        }

        // Step 3: Remove over-connected terrains
        if (getTerrain() != null && getTerrain().getMaxConnect() <= getMatchTerrain(null).size()) {
            weights.remove(getTerrain());
        }
        
 

        

        int totalWeight = weights.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println(totalWeight);
        if(totalWeight < 1) return Terrain.CITY;
        int roll = die.nextInt(totalWeight);

        System.out.println("Total weight: " + totalWeight + ", Random roll: " + roll );
        
        for (Map.Entry<Terrain, Integer> entry : weights.entrySet()) {
            roll -= entry.getValue();
            if (roll < 0) {
                System.out.println("Selected terrain: " + entry.getKey());
                return entry.getKey();
            }
        }
        return Terrain.BRICK;
    }

    public String getTerrainString(){
        return (t == null)? " ": t.name();
    }
}
