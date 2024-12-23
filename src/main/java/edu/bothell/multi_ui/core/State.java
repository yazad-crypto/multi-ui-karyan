package edu.bothell.multi_ui.core;

import java.util.Arrays;

public class State implements Statable {
    public final char[][]   S = new char[][]{
        {' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' '},
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

    public char[][] getIt(){
        return S;
    }

    @Override
    public Object getIt(int[] pos){
        return S[pos[1]][pos[0]];
    }

    @Override
    public boolean isOpen(int[] pos){
        return S[pos[1]][pos[0]] == ' ';
    }

    
    
}