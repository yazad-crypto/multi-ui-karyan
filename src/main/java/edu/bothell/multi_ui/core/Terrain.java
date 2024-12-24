package edu.bothell.multi_ui.core;

public enum Terrain {
    PLAIN(0,2,3,5),
    DESERT(0,3,3,4),
    HILLS(0,4,4,3),
    TREES(1,4,2,3),
    FOREST(2,5,1,6),
    MOUNTAIN(2,5,3,3),
    CAVE(3,5,1,5),
    CITY(3,5,2,2),
    SWAMP(0,5,2,2);

    private int wallsMin;
    private int wallsMax;
    private int maxConnect;
    private int visibility;

    private static final int[][] adjacencyPreferences = {
        // PLAIN, DESERT, HILLS, TREES, FOREST, MOUNTAIN, CAVE, CITY, SWAMP, WATER
        {9, 5, 5, 2, 1, 1, 0, 2, 3, 2}, // PLAIN
        {5, 9, 4, 1, 1, 1, 0, 1, 3, 1}, // DESERT
        {5, 4, 9, 3, 2, 2, 1, 2, 4, 2}, // HILLS
        {2, 1, 3, 9, 6, 2, 1, 1, 3, 2}, // TREES
        {1, 0, 2, 6, 9, 3, 2, 1, 2, 2}, // FOREST
        {1, 1, 2, 2, 3, 9, 5, 2, 2, 1}, // MOUNTAIN
        {0, 0, 1, 1, 2, 5, 9, 1, 1, 4}, // CAVE
        {2, 1, 2, 1, 1, 2, 1, 9, 3, 5}, // CITY
        {3, 3, 4, 3, 2, 2, 1, 3, 9, 5}, // SWAMP
        {3, 3, 4, 3, 2, 2, 1, 3, 9, 9}  // WATER
    };

    private Terrain(int wallsMin, int wallsMax, int visibility, int maxConnect){
        this.wallsMin = wallsMin;
        this.wallsMax = wallsMax;
        this.visibility = visibility;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getWallsMax() {
        return wallsMax;
    }
    
    public int getWallsMin() {
        return wallsMin;
    }
}
