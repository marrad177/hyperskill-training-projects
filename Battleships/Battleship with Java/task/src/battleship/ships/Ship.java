package battleship.ships;

import battleship.gamefield.Coordinates;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    public List<Coordinates> coordinates;
    protected Coordinates start;
    protected Coordinates tail;
    protected int length;
    public int type; // 5 - aircraft carrier, 4 - battleship, 3 - submarine/cruiser, 2 - destroyer

    protected Ship(Coordinates start, Coordinates tail) {
        this.start = start;
        this.tail = tail;
    }

    public static int getShipLength(Coordinates start, Coordinates tail, int type) {
        int tempLength;
        if (start.getM() == tail.getM() && Coordinates.onGamefield(start) && Coordinates.onGamefield(tail)) {
            tempLength = Math.abs(start.getN() - start.getN()) + 1;
            if(tempLength > type) {
                return -1;
            } else {
                return tempLength;
            }
        } else if (start.getN() == tail.getN() && Coordinates.onGamefield(start) && Coordinates.onGamefield(tail)) {
            tempLength = Math.abs(start.getM() - start.getM()) + 1;
            if(tempLength > type) {
                return -1;
            } else {
                return tempLength;
            }
        } else {
            return -1;
        }
    }

    public static List<Coordinates> buildShipCoordinates(Coordinates start, Coordinates tail) {
        List<Coordinates> coordinatesBuildList = new ArrayList<>(8);
        if (start.getM() == tail.getM() && Coordinates.onGamefield(start) && Coordinates.onGamefield(tail)) {
            if (start.getN() >= tail.getN()) {
                for (int i = start.getN(); i >= tail.getN(); i--) {
                    coordinatesBuildList.add(new Coordinates(start.getM(), i));
                }
            } else {
                for (int i = start.getN(); i <= tail.getN(); i++) {
                    coordinatesBuildList.add(new Coordinates(start.getM(), i));
                }
            }
        } else if (start.getN() == tail.getN() && Coordinates.onGamefield(start) && Coordinates.onGamefield(tail)) {
            if (start.getM() >= tail.getM()) {
                for (int i = start.getM(); i >= tail.getM(); i--) {
                    coordinatesBuildList.add(new Coordinates(i, start.getN()));
                }
            } else {
                for (int i = start.getM(); i <= tail.getM(); i++) {
                    coordinatesBuildList.add(new Coordinates(i, start.getN()));
                }
            }
        }
        return coordinatesBuildList;
    }
}