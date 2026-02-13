package battleship.ships;

import battleship.gamefield.Coordinates;

public class Battleship extends Ship {
    public Battleship(Coordinates start, Coordinates tail) {
        super(start, tail);
        this.type = 4;
        this.coordinates = Ship.buildShipCoordinates(start, tail);
        this.length = Ship.getShipLength(start, tail, type);
    }
}
