package edu.bothell.multi_ui;

public class Main {
    public static void main(String[] args){

        Player p  = new Player('x');
        Control c = new Control(p);

        System.out.println("MAIN RAN WITHOUT ERRORS!");
    }
}
