package edu.bothell.multi_ui;

public class Game {
    private Player[] p;
    private Control  c;
    private State    s;
    private int      turn;
    private Player   active;

    public Game(Control c){
        this.c = c;
        this.turn = 0;
        
        this.p = new Player[]{ new Player() };
        this.s = new State();
    }

    public void play(){
        System.out.println(active + ": start turn " + turn );
        this.active = p[turn++ % p.length];
    }

    public Player getActive() {
        return this.active;
    }

    public State getState() {
        return this.s;
    }


}
