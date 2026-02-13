package battleship;

import battleship.gamefield.Coordinates;
import battleship.gamefield.Gamefield;
import battleship.ships.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Gamefield gamefield = new Gamefield();
        gamefield.printGamefield();

        // gameplay
        try(Scanner scanner = new Scanner(System.in)){
            // Input format: MN MN
            // M: A-J
            // N: 0-10
            String[][] allExpectedShips = {{"Aircraft Carrier", "5"}, {"Battleship", "4"}, {"Submarine", "3"},
                                {"Cruiser", "3"}, {"Destroyer", "2"}};
            for(String[] expectedShip : allExpectedShips) {
                System.out.printf("Enter the coordinates of the %s (%s cells):\n", expectedShip[0], expectedShip[1]);
                String shipInput = scanner.nextLine();
                Coordinates startShip = extractCoordinatesFromInput(shipInput, "start");
                Coordinates tailShip = extractCoordinatesFromInput(shipInput, "tail");

                while(startShip.distance(tailShip) != Integer.parseInt(expectedShip[1])) {
                    System.out.printf("Error! Wrong length of the %s! Try again:\n", expectedShip[0]);
                    String newInput = scanner.nextLine();
                    startShip = extractCoordinatesFromInput(newInput, "start");
                    tailShip = extractCoordinatesFromInput(newInput, "tail");
                }

                Ship ship = switch (expectedShip[0]) {
                    case "Aircraft Carrier" -> new AircraftCarrier(startShip, tailShip);
                    case "Battleship" -> new Battleship(startShip, tailShip);
                    case "Submarine" -> new Submarine(startShip, tailShip);
                    case "Cruiser" -> new Cruiser(startShip, tailShip);
                    case "Destroyer" -> new Destroyer(startShip, tailShip);
                    default -> null;
                };

                while(!gamefield.placeShip(ship.coordinates)) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    String newInput = scanner.nextLine();
                    startShip = extractCoordinatesFromInput(newInput, "start");
                    tailShip = extractCoordinatesFromInput(newInput, "tail");
                    ship.buildShipCoordinates(startShip, tailShip);
                }


                gamefield.printGamefield();
            }
        }
    }

    static Coordinates extractCoordinatesFromInput(String inputString, String coordType) {
        Coordinates coordinates = switch (coordType) {
            case "start" -> new Coordinates(inputString.split(" ")[0]);
            case "tail" -> new Coordinates(inputString.split(" ")[1]);
            default -> new Coordinates(0,0);
        };
        return coordinates;
    }
}
