package edu.bothell.multi_ui;

import java.util.Arrays;

public class State {
    public final char[][]   S = new char[][]{
        {' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' '}
    };

    public State(){
        System.out.println("CREATING THE STATE...");
    }


    public void setIt(char it, int x, int y){
        this.S[y][x] = it;
        System.out.println(Arrays.deepToString(S));
    } 

    public char getIt(int x, int y){
        return S[y][x];
    }

    public char getIt(int[] pos){
        return S[pos[1]][pos[0]];
    }

    public char[][] getIt(){
        return S;
    }
    
}
