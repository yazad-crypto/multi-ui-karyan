package edu.bothell.multi_ui.core;

public interface Statable {
    
    Object getIt(int[] pos); // x is pop[0] // y is pos[1]

    default boolean isOpen(int[] pos) {
        Object item = getIt(pos);
        if(item instanceof Character) return (char) item == ' ';
        return true;
    }
}
