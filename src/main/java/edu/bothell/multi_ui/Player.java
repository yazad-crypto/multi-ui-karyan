package edu.bothell.multi_ui;

public class Player {

    private char   chr = 'x';
    private String sId = "";

    public Player(){
    }

    public Player(char chr){
        this.chr = chr;
    }

    public char getChar(){
        return this.chr;
    }

    public void setSId(String s){
        this.sId = s;
    }

    public String getSId(){
        return this.sId;
    }
}
