package battleship.ships;

import battleship.gamefield.Coordinates;

public class Submarine extends Ship {
    public Submarine(Coordinates start, Coordinates tail) {
        super(start, tail);
        buildShipCoordinates(start, tail);
        this.name = "Submarine";
        this.length = 3;
    }
}