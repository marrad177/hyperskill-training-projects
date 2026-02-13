package battleship.ships;

import battleship.gamefield.Coordinates;

public class Cruiser extends Ship {
    public Cruiser(Coordinates start, Coordinates tail) {
        super(start, tail);
        buildShipCoordinates(start, tail);
        this.name = "Cruiser";
        this.length = 3;
    }
}
