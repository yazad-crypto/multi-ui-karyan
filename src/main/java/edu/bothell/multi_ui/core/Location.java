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
    private final Location[] adjacents = new Location[EDGES]; // Adjacent locations
    private final Wall[] walls = new Wall[EDGES]; 
    private final List<Thing> occupants = new ArrayList<>(); // Objects in this location
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
    public void addWall (Directions d) {
        if(walls[d.ordinal()] == null && getAdjacent(d) != null)
            walls[d.ordinal()] = shareWall(d,getAdjacent(d));
    }
    
    private Wall shareWall(Directions d, Location l){
        Wall w = new Wall();
        w.setLocations( new Location[]{this,l} );
        if(l != null) l.addWall(d.opposite());
        return w;
    }

    public int getWallCount(){
        int count = 0;
        for(Wall w: walls) if(w!=null) count++;
        return count;
    }

    public Wall[] getWalls() {
        return walls.clone();
    }

    public boolean hasWall(Directions dir) {
        return walls[dir.ordinal()] != null;
    }


    public int getAdjCount(){
        int count = 0;
        for(Location l:adjacents) if(l != null) count++;
        return count;
    }
    public Location getAdjacent(Directions d) {
        return this.adjacents[d.ordinal()];
    }
    // Adjacent Locations
    public Location[] getAdjacents() {
        return this.adjacents; // TODO: DOES NOT Return a copy ??
    }

    public void addAdjacent(Location loc, Directions d) {
        System.out.println("addAdjacent");
        System.out.println(loc);
        if (loc != null && adjacents[d.ordinal()] == null) {
            adjacents[d.ordinal()] = loc;
        }
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
        if(adjacents.length > 1 && t != null) return "" + t.getSymbol();
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
