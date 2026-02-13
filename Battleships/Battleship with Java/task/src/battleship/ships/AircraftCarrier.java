package battleship.ships;

import battleship.gamefield.Coordinates;

public class AircraftCarrier extends Ship{
    public AircraftCarrier(Coordinates start, Coordinates tail) {
        super(start, tail);
        this.type = 5;
        this.coordinates = Ship.buildShipCoordinates(start, tail);
        this.length = Ship.getShipLength(start, tail, type);
    }
}
