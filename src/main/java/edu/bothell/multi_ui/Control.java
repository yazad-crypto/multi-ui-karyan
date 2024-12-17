package edu.bothell.multi_ui;

public class Control {

    private final UI    ui;
    private final Game   g = new Game(this);
    
    public Control(){
        this.ui = new Swing(this);
        //this.g  = new Game(this);
        System.out.println("CONTROL ACTIVE");
    }

    public State getState(){
        return this.g.getState();
    }

    public void update() {
        System.out.println("MAKE UPDATE!");
        g.play();
    }

    public Player getActive() {
        return g.getActive();
    }

}
