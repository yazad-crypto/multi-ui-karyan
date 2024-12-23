package edu.bothell.multi_ui.core;

import org.springframework.stereotype.Service;

import edu.bothell.multi_ui.ui.UI;
import edu.bothell.multi_ui.ui.swing.Swing;

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
        return g.play(pos, sId);
    }

    public Player getActive() {
        return g.getActive();
    }

    public Player addPlayer(char c, String sId){
        return g.addPlayer(c, sId);
    }

    public char[] getPlayersChar(){
        return g.getPlayersChar();
    }

    public int getPlayerCount(){
        return g.getPlayerCount();
    }

    public int getMaxPlayers() {
        return g.getMaxPlayers();
    }
    
    public int getTurn(){
        return g.getTurn();
    }

}
