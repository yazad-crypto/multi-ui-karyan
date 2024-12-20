package edu.bothell.multi_ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JTile extends JButton implements ActionListener, Placeable {
    
    private final JFrame  b;
    private final Control c;
    
    private final int[]   pos;
    private char          it;


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
        char bt = c.update( pos,"" );
        if(bt != ' ') super.setText("" + bt);
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
