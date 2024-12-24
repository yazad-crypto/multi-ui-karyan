package edu.bothell.multi_ui.core;

import java.util.ArrayList;
import java.util.List;

import edu.bothell.multi_ui.ui.swing.Locatable;

public class Location {
    // PROPERTIES ----------------------------------------------------------------
    private final boolean[] walls = new boolean[6]; // Walls: {NE, E, SE, SW, W, NW}
    private final List<Thing> occupants = new ArrayList<>(); // Objects in this location
    private final List<Location> adjacents = new ArrayList<>(); // Adjacent locations
    private final Terrain t;
    private Locatable uiElem; // Link to GUI element (e.g., Swing or Web)

    private final int x, y; // Hex grid coordinates
    private Game game; // Reference to the parent game

    // CONSTRUCTOR --------------------------------------------------------------
    public Location(int x, int y, Terrain t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }

    // METHODS ------------------------------------------------------------------

    public Terrain getTerrain() {
        return t;
    }

    // Walls
    public boolean hasWall(Directions dir) {
        return walls[dir.ordinal()];
    }

    public void setWall(Directions dir, boolean hasWall) {
        walls[dir.ordinal()] = hasWall;
    }

    // Adjacent Locations
    public List<Location> getAdjacent() {
        return new ArrayList<>(adjacents); // Return a copy to prevent external modification
    }

    public void addAdjacent(Location loc) {
        if (!adjacents.contains(loc)) {
            adjacents.add(loc);
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
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", walls=" + wallsToString() +
                ", occupants=" + occupants +
                '}';
    }

    private String wallsToString() {
        StringBuilder sb = new StringBuilder();
        Directions[] directions = Directions.values();
        for (int i = 0; i < walls.length; i++) {
            if (walls[i]) sb.append(directions[i]).append(" ");
        }
        return sb.toString().trim();
    }
}
