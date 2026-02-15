package battleship.gamefield;

public class CoordUtils {
    public static Coordinates extractShotCoordinates(String inputString) {
        Coordinates coordinates;
        try {
            coordinates = new Coordinates(inputString.split(" ")[0]);
        } catch (IllegalArgumentException iae) {
            coordinates = new Coordinates(-1, -1);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            coordinates = new Coordinates(-1, -1);
        }
        return coordinates;
    }

    public static Coordinates[] extractShipPlacementCoordinates(String inputString) {
        Coordinates[] startTail = new Coordinates[2];
        try {
            startTail[0] = new Coordinates(inputString.split(" ")[0]);
            startTail[1] = new Coordinates(inputString.split(" ")[1]);
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println(aioobe.getMessage());
        }
        return startTail;
    }
}
