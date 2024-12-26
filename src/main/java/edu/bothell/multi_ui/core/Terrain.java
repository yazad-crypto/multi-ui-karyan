package edu.bothell.multi_ui.core;

public enum Terrain {
    PLAIN   (0,2,3,9, '='),
    DESERT  (0,3,3,8, '_'),
    HILLS   (0,4,4,6, '⪮'),
    TREES   (1,4,2,4, '⽊'),
    FOREST  (2,5,1,12,'㭑'),
    MOUNTAIN(2,5,3,3, '⛰'),
    CAVE    (3,5,1,36,'☶'),
    CITY    (3,5,2,3, '㑞'),
    SWAMP   (0,5,2,7, 'w'),
    WATER   (3,5,2,36,'~'),
    BRICK   (0,0,0,1, 'X');

    private int wallsMin;
    private int wallsMax;
    private int maxConnect;
    private int visibility;
    private char symbol;

    private static final int[][] adjacencyPreferences = {
        // PLAIN, DESERT, HILLS, TREES, FOREST, MOUNTAIN, CAVE, CITY, SWAMP, WATER, BRICK
        {9, 5, 5, 2, 1, 1, 0, 2, 3, 2}, // PLAIN
        {5, 9, 4, 1, 1, 1, 0, 1, 3, 1}, // DESERT
        {5, 4, 9, 3, 2, 2, 1, 2, 4, 2}, // HILLS
        {2, 1, 3, 9, 6, 2, 1, 1, 3, 2}, // TREES
        {1, 0, 2, 6, 9, 3, 2, 1, 2, 2}, // FOREST
        {1, 1, 2, 2, 3, 9, 5, 2, 2, 1}, // MOUNTAIN
        {0, 0, 1, 1, 2, 5, 9, 1, 1, 4}, // CAVE
        {2, 1, 2, 1, 1, 2, 1, 9, 3, 5}, // CITY
        {3, 3, 4, 3, 2, 2, 1, 3, 9, 5}, // SWAMP
        {3, 3, 4, 3, 2, 2, 1, 3, 9, 9}, // WATER
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}  // BRICK
    };

    private Terrain(int wallsMin, int wallsMax, int visibility, int maxConnect, char symbol){
        this.wallsMin = wallsMin;
        this.wallsMax = wallsMax;
        this.visibility = visibility;
        this.symbol = symbol;
    }

    public int getMaxConnect(){
        return this.maxConnect;
    }

    public char getSymbol(){
        return this.symbol;
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

    public int getPreference(Terrain neighborTerrain) {
        int i = neighborTerrain.ordinal();
        return adjacencyPreferences[this.ordinal()][i];
    }
}
