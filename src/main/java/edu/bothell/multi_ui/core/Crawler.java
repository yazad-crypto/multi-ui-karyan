package edu.bothell.multi_ui.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class Crawler {
    // PROPERTIES ---------------------------------------------------------------------
    private final World w;
    private final Stack<Location> stack;
    private final Stack<Location> progress;
    private int startX;
    private int startY;

    // CONSTRUCTOR --------------------------------------------------------------------
    public Crawler(World w){
        this.w = w;
        this.stack = new Stack<>();
        this.progress = new Stack<>();
    }

    // STATIC UTILITIES ---------------------------------------------------------------
    public static Wall[] buildWalls(Location l, Random die){
        ArrayList<Directions> edges = new ArrayList<>(Arrays.asList(Directions.values()));
        ArrayList<Directions> openings = new ArrayList<>();
        for(Directions d : edges) if(!l.hasWall(d)) openings.add(d);
        
        //Add minimum walls
        int min = (l.getTerrain() != null)? l.getTerrain().getWallsMin() : 3;
        for(int i = 0; i < min - l.getWallCount(); i++ ){
            int roll = die.nextInt(openings.size());
            l.addWall( openings.get(roll) );
            openings.remove(roll);
        }

        //Roll for maximum walls
        int range = (int) Math.pow(2, openings.size() );
        int roll = die.nextInt(range);
        
        for(int i = 0; i < openings.size(); i++){
            if ((roll & (1 << i)) != 0) l.addWall(openings.get(i));
        }
      
        l.markWalled();
        return l.getWalls(); 
    }
    
    public static Terrain bestFit(Location l, Random die){

        // Step 0: If the location already has a terrain, return it
        if(l.getTerrain() != null || l.getAdjacents() == null) return l.getTerrain();

        // Step 1: create dictionary of preferences so as to remove by name
        HashMap<Terrain, Integer> changingPrefrences = new HashMap<>();
        for (Terrain terrain : Terrain.values()) {
            changingPrefrences.put(terrain, 0); // Add each Terrain with a default value of 0
        }

        // Step 2: Iterate through all Neighbords to sum preferences
        for( Location nei: l.getAdjacents() ) 
            addOddsAndCheckConnections(changingPrefrences, nei);
        
        // Step 3: Role the die and get the terrain
        return useOddsAndRollDie(changingPrefrences, die, l);
    }

    private static boolean addOddsAndCheckConnections(HashMap<Terrain, Integer> oddsTable, Location neighbor ){
        Terrain t = neighbor.getTerrain();
        if(t == null) return false;

        // Check max connect information and edit table as needed
        int alreadyConnected = getConnected(neighbor, null).size();
        if( alreadyConnected >= t.getMaxConnect() ){
            oddsTable.remove(t);
            return false;
        }
        
        // or if terrain is possible, loop remaining terrains to get odds
        int[] nPrefs = t.getPreference();
        
        List<Terrain> ts = new ArrayList<>(oddsTable.keySet());
        for( Terrain eachPossible : ts){
            oddsTable.put(
                eachPossible, 
                oddsTable.getOrDefault(eachPossible, 0) 
                + nPrefs[eachPossible.ordinal()]
            );
        }

        return true;
    }

    private static Terrain useOddsAndRollDie(HashMap<Terrain, Integer> oddsTable, Random die, Location l){
        // sum the table
        System.out.println(l);
        int totalWeight = oddsTable.values().stream().mapToInt(Integer::intValue).sum();
     
        if(totalWeight<1)return Terrain.WATER;

        // Roll the die
        int roll = die.nextInt(totalWeight);
        for (Map.Entry<Terrain, Integer> entry : oddsTable.entrySet()) {
            roll -= entry.getValue();
            if (roll < 0)   return entry.getKey();
            
        }
        System.out.println();
        System.out.println("brick for roll:" + roll);
        System.out.println(oddsTable);
        System.out.println(totalWeight);
        System.out.println();
        return Terrain.BRICK;
    }


    public static ArrayList<Location> getConnected(Location l, ArrayList<Location> ls){
        if(ls == null)  ls = new ArrayList<>();
        if(ls.contains(l)) return ls;

        ls.add(l);
        Terrain t = l.getTerrain();
        
        for(Location nei: l.getAdjacents()){ 
            if(nei.getTerrain() != null && nei.getTerrain().equals(t)){
                getConnected(nei, ls);
            } 
        }
        return ls;

    }

    // METHODS -----------------------------------------------------------------------

    public void treverse(
        int startX,
        int startY,
        Random die,
        BiPredicate<Location, Random> filter,
        BiConsumer <Location, Random> action
    ){
        treverse(w.getLocation(startX, startY), die, filter, action);
    }

    public void treverse(
        Location start,
        Random die,
        BiPredicate <Location, Random> filter,
        BiConsumer <Location, Random> action
    ){
        if(start == null) throw new IllegalStateException("Starting location not found at (" + startX + ", " + startY + ").");

        stack.push(start);

        System.out.println("start Treverse: " + start);
        if( filter.test(start, die) ) action.accept(start, die);
        while(!stack.isEmpty()){
            Location l = stack.pop();
            for ( Location nei : l.getAdjacents() ) {
                if( filter.test(nei, die) ){
                    action.accept(nei, die);
                    stack.push(nei);
                }
            }
        }
    }

}
