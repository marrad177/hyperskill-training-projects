package battleship.ships;

import battleship.gamefield.Coordinates;

public class Battleship extends Ship {
    public Battleship(Coordinates start, Coordinates tail) {
        super(start, tail);
        buildShipCoordinates(start, tail);
        this.name = "Battleship";
        this.length = 4;
    }
}
