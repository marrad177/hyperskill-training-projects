package battleship.gamefield;

import java.util.HashMap;
import java.util.Map;

public class Coordinates {
    private int m;
    private int n;
    Map<String, Integer> dictMtoNumber;

    public Coordinates(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int distance(Coordinates distantCoordinates) {
        if (distantCoordinates != null && this.getM() == distantCoordinates.getM()) {
            return Math.abs(this.getN() - distantCoordinates.getN()) + 1;
        } else if (distantCoordinates != null && this.getN() == distantCoordinates.getN()) {
            return Math.abs(this.getM() - distantCoordinates.getM()) + 1;
        } else {
            return 0;
        }
    }

    public boolean equals(Coordinates equalsCoordinates) {
        if((this.getM() == equalsCoordinates.getM()) && (this.getN() == equalsCoordinates.getN())) {
            return true;
        } else {
            return false;
        }
    }
}
