package edu.bothell.multi_ui.core;

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
    public void build(){
        Terrain[][] terrains = generateTerrainMap();
        
        for(int y = 0; y < S.length; y++){
            map[y] = new Location[ S[y].length ];
            for(int x=0; x < map[y].length; x++)
                map[y][x] = new Location(x,y, Terrain.CITY);
        }

        setAdjacents();
    }

    private Terrain[][] generateTerrainMap(){
        Terrain[][] m = new Terrain[3][3];

        return m;
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

    private Location getNeighbor(int x, int y, Directions d){
        // Calculate the neighbor coordinates
        int nX = x + d.dX() + ((d.dY() != 0) ? 0 : y % 2);
        int nY = y + d.dY();
        
        // Wrap vertical (rows)
        nY = (nY + map.length) % map.length;

        // Wrap horizontal (columns within the row)
        nX = (nX + map[nY].length) % map[nY].length;
        
        return map[nY][nX];
    }

    @Override
    public Object getIt(int[] pos){
        System.out.println("world get's it!");
        return map[pos[1]][pos[0]];
    }

}
