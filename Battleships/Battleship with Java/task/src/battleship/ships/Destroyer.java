package battleship.ships;

import battleship.gamefield.Coordinates;

public class Destroyer extends Ship {
    public Destroyer(Coordinates start, Coordinates tail) {
        super(start, tail);
        this.type = 2;
        this.coordinates = Ship.buildShipCoordinates(start, tail);
        this.length = Ship.getShipLength(start, tail, type);
    }
}