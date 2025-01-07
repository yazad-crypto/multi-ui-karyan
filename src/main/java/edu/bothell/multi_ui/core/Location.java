package edu.bothell.multi_ui.core;

import java.util.ArrayList;
import java.util.List;
import edu.bothell.multi_ui.ui.swing.Locatable;

public class Location {
    // PROPERTIES ----------------------------------------------------------------
    public static final int EDGES = Directions.values().length; // Walls: {NE, E, SE, SW, W, NW}
    private final Location[] adjacents = new Location[EDGES]; // Adjacent locations
    private final Wall[] walls = new Wall[EDGES]; 
    private final List<Thing> occupants = new ArrayList<>(); // Objects in this location
    private boolean isWalled = false;
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
        if( hasWall(d) || getAdjacent(d).hasWall( d.opposite() ) ) return;
        walls[d.ordinal()] = new Wall();
        getAdjacent( d ).shareWall(d, walls[d.ordinal()]);
        
        if(getWallCount() == EDGES-1) this.isWalled = true;
    }
    
    private void shareWall(Directions d, Wall w){
        walls[d.opposite().ordinal()] = w;
        if(getWallCount() == EDGES-1) this.isWalled = true;
    }

    public boolean isWalled(){
        return isWalled;
    }

    public void markWalled(){
        this.isWalled = true;
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
        if (loc != null && adjacents[d.ordinal()] == null) {
            adjacents[d.ordinal()] = loc;
            walls[d.ordinal()] = new Wall();
            loc.shareWall(d, walls[d.ordinal()]);
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
        if(t != null) return ""+t;
        return "L{" + x +
                "," + y +
              // "," + Arrays.toString(walls) +
              //  "," + t +
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

    public String getTerrainString(){
        return (t == null)? " ": t.name();
    }



}
