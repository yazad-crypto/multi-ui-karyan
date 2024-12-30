package edu.bothell.multi_ui.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Crawler {
    // PROPERTIES ---------------------------------------------------------------------
    private final World w;
    private final List<Location> w2do;
    private Predicate<Location[]>       goal;

    // CONSTRUCTOR --------------------------------------------------------------------
    public Crawler(World w){
        this.w = w;
        this.w2do = new ArrayList<>();
    }
    public Crawler(World w, Predicate goal, Random die){
        this.w = w;
        this.w2do = new ArrayList<>();
        this.goal = goal;

    }

    public void init(Location[] ls){
        for(Location l : ls)
            System.out.println(goal);
    }

}
