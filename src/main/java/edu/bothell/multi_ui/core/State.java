package edu.bothell.multi_ui.core;

public class State implements Statable<int[]> {
    //public final char[][]   S = new char[4][4];   
    public final char[][]   S = {{' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' '}};

    public State(){
        System.out.println("CREATING THE STATE...");
        for(char[] row: S){
            for(int x = 0; x < row.length; x++){
                row[x] = ' ';
            }
        }
    }


    public void setIt(char it, int x, int y){
        this.S[y][x] = it;
        for(int i = 0; i < S.length; i++){
            for (int o = 0; o < S[i].length; o++) {
                System.out.print("[" + S[i][o] + "]");
            }
            System.out.println();
        }
        //System.out.println(Arrays.deepToString(S));
    } 

    public char getIt(int x, int y){
        //System.out.println("hello gang");
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

    @Override
    public int[] getAdj(int x, int y, Directions d) {
        return new int[]{x + d.dX(), y + d.dY() };
    }
    
}

