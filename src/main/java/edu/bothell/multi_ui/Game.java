package edu.bothell.multi_ui;

public class Game {
    private final int MAX_PLAYERS = 3;
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

    public boolean isValid(int[] pos){
        System.out.println("isVAlid?"+s.getIt(pos)+"|");
        return s.getIt(pos) == ' ';
    }

    public boolean play(int[] pos){
        System.out.println(isValid(pos));
        if(!isValid(pos)) return false;
        this.active = p[turn % p.length];
        this.s.setIt(active.getChar(), pos[0], pos[1]);
        turn++;
        return true;
    }

    public Player getActive() {
        return this.active;
    }

    public State getState() {
        return this.s;
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }


}
