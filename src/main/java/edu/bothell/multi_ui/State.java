package edu.bothell.multi_ui;

public class State {
    public final char[][]   S = new char[][]{
        {' ',' ',' ',' '},
        {' ',' ',' ',' '},
        {' ',' ',' ',' '},
        {' ',' ',' ',' '}
    };

    public State(){
        System.out.println("CREATING THE STATE...");
    }


    public void setIt(char it, int x, int y){
        this.S[y][x] = it;
    } 

    public char[][] getIt(){
        return S;
    }
    
}
