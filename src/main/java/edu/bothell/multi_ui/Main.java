package edu.bothell.multi_ui;

import edu.bothell.multi_ui.core.Control;
import edu.bothell.multi_ui.core.Player;
import edu.bothell.multi_ui.core.World;

public class Main {
    public static void main(String[] args){
        
        Player p  = new Player('x');
        Control c = new Control(p);

        System.out.println("MAIN RAN WITHOUT ERRORS!");
    }
}
