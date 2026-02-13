package battleship.ships;

import battleship.gamefield.Coordinates;

public class Destroyer extends Ship {
    public Destroyer(Coordinates start, Coordinates tail) {
        super(start, tail);
        buildShipCoordinates(start, tail);
        this.name = "Destroyer";
        this.length = 2;
    }
}