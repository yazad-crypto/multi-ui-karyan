package edu.bothell.multi_ui.ui;


public class Transit {
    public char[][] s;
    public String[][] t;
    public char     a;
    public Transit(char[][] state, char active){
        this.s = state;
        this.a = active;
    }
    public Transit(char[][] state, char active, String[][] terrain){
        this.s = state;
        this.a = active;
        this.t = terrain;
    }
}
 