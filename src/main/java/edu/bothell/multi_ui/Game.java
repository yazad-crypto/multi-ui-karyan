package edu.bothell.multi_ui;

import java.util.ArrayList;


public class Game {
    private final int            MAX_PLAYERS = 3;
    private ArrayList<Player>    p;
    private Control              c;
    private State                s;
    private int                  turn;
    private Player               active;

    public Game(Control c){
        this.c = c;
        this.turn = 0;
        this.s = new State();
        this.p = new ArrayList<Player>();
    }
    
    public void addPlayer(Player p){
        this.p.add(p);
        if(this.active == null) active = p;
    }

    public Player addPlayer(char c, String sId){
        Player p = new Player(c);
        p.setSId(sId);
        this.p.add( p );
        if(this.active == null) active = p;

        return p;
    }

    public Player addPlayer(){
        Player p = new Player();
        this.p.add( p );
        return p;
    }

    public boolean isValid(int[] pos, String sId){
        System.out.println("isVAlid?"+s.getIt(pos)+"|");
        return s.getIt(pos) == ' ' && active.getSId().equals(sId);
    }

    public boolean play(int[] pos, String sId){
        if(!isValid(pos, sId)) return false;
        turn++;
        this.s.setIt(active.getChar(), pos[0], pos[1]);
        this.active = p.get( turn % p.size() );
        

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

    public int getPlayerCount() {
        return p.size();
    }

    public ArrayList<Player> getPlayers(){
        return this.p;
    }

    public int getTurn(){
        return this.turn;
    }


}
