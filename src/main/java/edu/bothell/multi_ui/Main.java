package edu.bothell.multi_ui;

import edu.bothell.multi_ui.core.Control;
import edu.bothell.multi_ui.core.Player;

public class Main {
    public static void main(String[] args){
        
        Player p  = new Player('x');
        new Control(p);

        System.out.println("MAIN RAN WITHOUT ERRORS!");
    }
}
