package edu.bothell.multi_ui.core;

public enum Directions {
    NE(0, -1),      // North-East
    E(1, 0),     // East
    SE(0, 1),    // South-East
    SW(-1, 1),      // South-West
    W(-1, 0),       // West
    NW(-1, -1);        // North-West

    private final int dx;
    private final int dy;

    Directions(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dX() {
        return dx;
    }

    public int dY() {
        return dy;
    }

    /**
     * Get the opposite direction.
     */
    public Directions opposite() {
        switch (this) {
            case NE: return SW;
            case E:  return W;
            case SE: return NW;
            case SW: return NE;
            case W:  return E;
            case NW: return SE;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
