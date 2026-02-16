package battleship;

import battleship.gamefield.Coordinates;
import battleship.gamefield.Gamefield;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private List<Coordinates> coordinates;
    private Coordinates start;
    private Coordinates tail;
    private int length;   // 5 - aircraft carrier, 4 - battleship, 3 - submarine/cruiser, 2 - destroyer
    private String name;

    protected Ship(String name, String length, Coordinates start, Coordinates tail) {
        this.name = name;
        this.length = Integer.parseInt(length);
        this.start = start;
        this.tail = tail;
        this.buildShipCoordinates(start, tail);
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

    public void removeCoordinates(Coordinates coordinates) {
        this.coordinates.removeIf(coords -> coords.equals(coordinates));
    }

    public void buildShipCoordinates(Coordinates start, Coordinates tail) {
        List<Coordinates> coordinatesBuildList = new ArrayList<>(8);
        if (start.getM() == tail.getM() && Gamefield.onGamefield(start) && Gamefield.onGamefield(tail)) {
            if (start.getN() >= tail.getN()) {
                for (int i = start.getN(); i >= tail.getN(); i--) {
                    coordinatesBuildList.add(new Coordinates(start.getM(), i));
                }
            } else {
                for (int i = start.getN(); i <= tail.getN(); i++) {
                    coordinatesBuildList.add(new Coordinates(start.getM(), i));
                }
            }
        } else if (start.getN() == tail.getN() && Gamefield.onGamefield(start) && Gamefield.onGamefield(tail)) {
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