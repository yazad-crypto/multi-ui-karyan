package edu.bothell.multi_ui;

import org.springframework.stereotype.Service;

@Service
public class Control {

    private final UI    ui;
    private final Game   g = new Game(this);
    
     
    public Control(){
        this.ui = null;
        System.out.println("CONTROL ACTIVE: for web");
    }

    public Control(Player p){
        this.ui = new Swing(this);
        this.g.addPlayer(p);
        System.out.println("CONTROL ACTIVE: for SWING");
    }


    public void launch(){
    
    }

    public State getState(){
        return this.g.getState();
    }

    public char update(int[] pos, String sId) {
        System.out.println("MAKE UPDATE! " + pos[0] + "|" + pos[1]);
        return g.play(pos, sId);
    }

    public Player getActive() {
        return g.getActive();
    }

    public Player addPlayer(char c, String sId){
        return g.addPlayer(c, sId);
    }

    public Player addPlayer(){
        return g.addPlayer();
    } 

    public int getPlayerCount(){
        return g.getPlayerCount();
    }

    public int getMaxPlayers() {
        return g.getMaxPlayers();
    }
    public UI getUI(){
        return this.ui;
    }
    public int getTurn(){
        return g.getTurn();
    }

}
