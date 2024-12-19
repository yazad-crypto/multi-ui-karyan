package edu.bothell.multi_ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JTile extends JButton implements ActionListener, Placeable {
    
    private JFrame  b;
    private Control c;
    
    private int[]   pos;
    private char    it;


    public JTile(Swing b, int[] pos){
        this.b      = b;
        this.c      = b.getControl();
        this.pos    = pos;
        System.out.println("hi");
        init();
    }

    private void init(){
        this.addActionListener(this);
        b.add(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        c.update( pos,"x" );
    }

    @Override
    public int[] getPos() {
        return this.pos;
    }

    @Override
    public char getIt(){
        return this.it;
    }

    @Override
    public void setIt(char x){
        this.it = x;
        super.setText(""+ x);
    }
    
}
