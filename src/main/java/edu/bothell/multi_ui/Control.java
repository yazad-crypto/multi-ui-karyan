package edu.bothell.multi_ui;

import org.springframework.stereotype.Service;

@Service
public class Control {

    private final UI    ui;
    private final Game   g = new Game(this);
    
    public Control(){
        this.ui = null; //new Swing(this);
        System.out.println("CONTROL ACTIVE");
    }

    public State getState(){
        return this.g.getState();
    }

    public boolean update(int[] pos) {
        System.out.println("MAKE UPDATE! " + pos[0] + "|" + pos[1]);
        return g.play(pos);
    }

    public Player getActive() {
        return g.getActive();
    }

    public int getMaxPlayers() {
        return g.getMaxPlayers();
    }

}
