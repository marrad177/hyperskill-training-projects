package battleship.ships;

import battleship.gamefield.Coordinates;

public class Submarine extends Ship {
    public Submarine(Coordinates start, Coordinates tail) {
        super(start, tail);
        this.type = 3;
        this.coordinates = Ship.buildShipCoordinates(start, tail);
        this.length = Ship.getShipLength(start, tail, type);
    }
}