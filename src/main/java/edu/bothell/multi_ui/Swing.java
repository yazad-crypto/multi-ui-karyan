package edu.bothell.multi_ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public final class Swing extends JFrame implements UI {
    
    private Control c;

    public Swing(){
        init();
    }

    public Swing(Control c){
        this.c = c;
        this.build();
        init();
    }
    
    private void init() {
        super.setSize(new Dimension(200, 600));
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        build();

        super.setVisible(true);
    }
    
    public void build(){
   
        // TODO: Assume I know the depth of the state...
        char[][] s = this.c.getState().getIt();
        for (int y = 0; y < s.length; y++)
            for (int x = 0; x < s[y].length; x++) {
                int[] pos = { x, y };
                super.add( new JTile(this, pos) );
            }

        System.out.println("building....");
        
    }

    public Control getControl(){
        return this.c;
    }
}
