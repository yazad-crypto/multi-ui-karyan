package edu.bothell.multi_ui.core;

public class Thing {
    private final int size;
    private final Location[] ls;
    private int durrability;
    
    public Thing(){
        this.size = 1;
        this.ls = new Location[size];
        this.durrability = 1;
    }

    public Thing(int size){
        this.size = size;
        this.ls = new Location[size];
        this.durrability = 1;
    }

    public void setLocations(Location[] newLs){
        for(int i = 0; i < ls.length; i++)
            ls[i] = newLs[i]; 
    }

    public Location[] getLocations(){
        return this.ls;
    }

    public Location getLocation(){
        return ls[0];
    }

    public int getSize() {
        return size;
    }

    public int getDurrability() {
        return durrability;
    }

    public void setDurrability(int durrability) {
        this.durrability = durrability;
    }

}
