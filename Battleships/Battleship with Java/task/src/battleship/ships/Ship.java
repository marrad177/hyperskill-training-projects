package battleship.ships;

import battleship.gamefield.Coordinates;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    public List<Coordinates> coordinates;
    protected Coordinates start;
    protected Coordinates tail;
    protected int length;   // 5 - aircraft carrier, 4 - battleship, 3 - submarine/cruiser, 2 - destroyer
    protected String name;

    protected Ship(Coordinates start, Coordinates tail) {
        this.start = start;
        this.tail = tail;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void buildShipCoordinates(Coordinates start, Coordinates tail) {
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
        this.coordinates = coordinatesBuildList;
    }
}