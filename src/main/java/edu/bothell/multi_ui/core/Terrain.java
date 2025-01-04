package edu.bothell.multi_ui.core;

public enum Terrain {
    PLAIN   (0,2,3,130, '='),
    DESERT  (0,3,3,40, '_'),
    HILLS   (0,4,4,20, '⪮'),
    TREES   (1,4,2,30, '⽊'),
    FOREST  (2,5,1,90,'㭑'),
    MOUNTAIN(2,5,3,30, '⛰'),
    CAVE    (3,5,1,12,'☶'),
    CITY    (3,5,2,2, '㑞'),
    SWAMP   (0,5,2,20, 'w'),
    WATER   (3,5,2,200,'~'),
    BRICK   (2,5,0,1, 'X');

    private int wallsMin;
    private int wallsMax;
    private int maxConnect;
    private int visibility;
    private char symbol;

    private static final int[][] adjacencyPreferences = {
    // PLAIN, DESERT, HILLS, TREES, FOREST, MNTAIN, CAVE, CITY, SWAMP, WATER, BRICK
        {800,   2,    200,  200,    200,    100,    0,      1, 2, 40, 0}, // PLAIN
        {100,   3,    2,    1,      1,      1,      0,      1, 2, 40, 0}, // DESERT
        {200,   2,    300,  400,    1,      300,    1,      1, 2, 40, 0}, // HILLS
        {100,   1,    200,  200,    200,    1,      1,      1, 2, 50, 0}, // TREES
        {100,   1,    1,    200,    400,    2,      2,      1, 1, 60, 0}, // FOREST
        {1,     1,    300,  1,      2,      300,    3,      1, 1, 50, 0}, // MOUNTAIN
        {0,     0,    1,    1,      2,      3,      3,      0, 1, 50, 0}, // CAVE
        {3,     1,    1,    2,      2,      4,      4,      10, 0, 10, 0}, // CITY
        {2,     2,    2,    2,      1,      1,      1,      2, 3, 500, 0}, // SWAMP
        {40,    40,   40,   50,     60,     50,     50,     40, 50, 2200, 0}, // WATER
        {1,     1,    1,    1,      1,      1,      1,      1, 1, 1, 0}  // BRICK
    };

    private Terrain(int wallsMin, int wallsMax, int visibility, int maxConnect, char symbol){
        this.wallsMin = wallsMin;
        this.wallsMax = wallsMax;
        this.maxConnect = maxConnect;
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
