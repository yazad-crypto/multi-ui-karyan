package edu.bothell.multi_ui.core;

public interface Statable<T> {
    
    Object getIt(int[] pos); // x is pop[0] // y is pos[1]

    abstract T getAdj(int x, int y, Directions d);

    default boolean isOpen(int[] pos) {
        Object item = getIt(pos);
        if(item instanceof Character) return (char) item == ' ';
        return true;
    }
}
