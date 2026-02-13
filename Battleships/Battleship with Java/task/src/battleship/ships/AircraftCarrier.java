package battleship.ships;

import battleship.gamefield.Coordinates;

public class AircraftCarrier extends Ship{
    public AircraftCarrier(Coordinates start, Coordinates tail) {
        super(start, tail);
        buildShipCoordinates(start, tail);
        this.name = "Aircraft Carrier";
        this.length = 5;
    }
}
