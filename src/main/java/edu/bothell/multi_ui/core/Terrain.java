package edu.bothell.multi_ui.core;

public enum Terrain {
    PLAIN   (0,2,3,9, '='),
    DESERT  (0,3,3,8, '_'),
    HILLS   (0,4,4,6, '⪮'),
    TREES   (1,4,2,4, '⽊'),
    FOREST  (2,5,1,12,'㭑'),
    MOUNTAIN(2,5,3,3, '⛰'),
    CAVE    (3,5,1,6,'☶'),
    CITY    (3,5,2,2, '㑞'),
    SWAMP   (0,5,2,7, 'w'),
    WATER   (3,5,2,2,'~'),
    BRICK   (0,0,0,1, 'X');

    private int wallsMin;
    private int wallsMax;
    private int maxConnect;
    private int visibility;
    private char symbol;

    private static final int[][] adjacencyPreferences = {
    // PLAIN, DESERT, HILLS, TREES, FOREST, MOUNTAIN, CAVE, CITY, SWAMP, WATER, BRICK
        {500,   2,  200, 1,     1, 1, 0, 1, 2, 4, 0}, // PLAIN
        {2,     3,  2,   1,     1, 1, 0, 1, 2, 4, 0}, // DESERT
        {200,   2,  300, 500,   1, 300, 1, 1, 2, 4, 0}, // HILLS
        {1,     1,  500, 500, 200, 1, 1, 1, 2, 5, 0}, // TREES
        {1,     1,  1,   200, 400, 2, 2, 1, 1, 6, 0}, // FOREST
        {1,     1,  300, 1,     2, 300, 3, 1, 1, 5, 0}, // MOUNTAIN
        {0,     0,  1,   1,     2, 3, 3, 0, 1, 5, 0}, // CAVE
        {3,     1,  1,   2,     2, 4, 4, 10, 0, 10, 0}, // CITY
        {2,     2,  2,   2, 1, 1, 1, 2, 3, 5, 0}, // SWAMP
        {4,     4,  4,   5, 6, 5, 5, 4, 5, 1100, 0}, // WATER
        {0,     0,  0,   0, 0, 0, 0, 0, 0, 0, 0}  // BRICK
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

    public int[] getPreference(){
        return adjacencyPreferences[this.ordinal()];
    }
    
    public int getPreference(Terrain neighborTerrain) {
        int i = neighborTerrain.ordinal();
        return adjacencyPreferences[this.ordinal()][i];
    }
}
