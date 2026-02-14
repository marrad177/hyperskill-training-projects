package battleship;

import battleship.gamefield.Coordinates;
import battleship.gamefield.Gamefield;
import battleship.ships.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Gamefield gamefield = new Gamefield();
        gamefield.printGamefield();
        List<Ship> shipList = new ArrayList<>(6);

        // gameplay
        try(Scanner scanner = new Scanner(System.in)){
            // Input format: MN MN
            // M: A-J
            // N: 0-10
            String[][] allExpectedShips = {{"Aircraft Carrier", "5"}, {"Battleship", "4"}, {"Submarine", "3"},
                                {"Cruiser", "3"}, {"Destroyer", "2"}};
            for(String[] expectedShip : allExpectedShips) {
                System.out.printf("\nEnter the coordinates of the %s (%s cells):\n\n", expectedShip[0], expectedShip[1]);
                String shipInput = scanner.nextLine();
                Coordinates startShip = extractCoordinatesFromInput(shipInput, "start");
                Coordinates tailShip = extractCoordinatesFromInput(shipInput, "tail");

                while(startShip.distance(tailShip) != Integer.parseInt(expectedShip[1]) ||
                        !Gamefield.onGamefield(startShip) || !Gamefield.onGamefield(tailShip)) {

                    if(startShip.distance(tailShip) != Integer.parseInt(expectedShip[1])) {
                        System.out.printf("\nError! Wrong length of the %s! Try again:\n", expectedShip[0]);
                        String newInput = scanner.nextLine();
                        startShip = extractCoordinatesFromInput(newInput, "start");
                        tailShip = extractCoordinatesFromInput(newInput, "tail");
                    }

                    if(!Gamefield.onGamefield(startShip) || !Gamefield.onGamefield(tailShip)) {
                        System.out.println("\nError! Coordinates not on battlefield! Try again:\n");
                        String outOfBounceFixInput = scanner.nextLine();
                        startShip = extractCoordinatesFromInput(outOfBounceFixInput, "start");
                        tailShip = extractCoordinatesFromInput(outOfBounceFixInput, "tail");
                    }
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
                    System.out.println("\nError! You placed it too close to another one. Try again:\n");
                    String newInput = scanner.nextLine();
                    startShip = extractCoordinatesFromInput(newInput, "start");
                    tailShip = extractCoordinatesFromInput(newInput, "tail");
                    ship.buildShipCoordinates(startShip, tailShip);
                }
                shipList.add(ship);
                System.out.println();
                gamefield.printGamefield();
            }

            System.out.println("\nThe game starts!\n");
            while(gamefield.getShipPartsLeft() > 0) {
                gamefield.printMaskedGamefield();
                System.out.println("\nTake a shot!\n");
                String shotInput = scanner.nextLine();
                Coordinates shot = extractCoordinatesFromInput(shotInput, "start");
                while(!Gamefield.onGamefield(shot)) {
                    System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
                    String shotFixInput = scanner.nextLine();
                    shot = extractCoordinatesFromInput(shotFixInput, "start");
                }
                if(gamefield.shoot(shot)) {
                    Ship deleteShip = null;
                    for(Ship ship : shipList) {
                        Coordinates deleteCoordinates = null;
                        for(Coordinates coordinate : ship.getCoordinates()) {
                            if(coordinate.getM() == shot.getM() && coordinate.getN() == shot.getN()) {
                                deleteCoordinates = coordinate;
                                break;
                            }
                        }
                        if(deleteCoordinates != null) {
                            ship.takeAHit(deleteCoordinates);
                        }

                        if(ship.getCoordinates().size() == 0) {
                            System.out.println("You sank a ship!");
                            deleteShip = ship;
                            break;
                        } else {
                            System.out.println("\nYou hit a ship!\n");
                        }
                    }
                    if(deleteShip != null) {
                        shipList.remove(deleteShip);
                    }
                } else {
                    System.out.println("\nYou missed!\n");
                }
            }
            System.out.println("\nYou sank the last ship. You won. Congratulations!");
            gamefield.printGamefield();
        }
    }

    static Coordinates extractCoordinatesFromInput(String inputString, String coordType) {
        Coordinates coordinates;
        try {
            coordinates = switch (coordType) {
                case "start" -> new Coordinates(inputString.split(" ")[0]);
                case "tail" -> new Coordinates(inputString.split(" ")[1]);
                default -> new Coordinates(0,0);
            };
        } catch (IllegalArgumentException iae) {
            coordinates = new Coordinates(-1, -1);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            coordinates = new Coordinates(-1, -1);
        }

        return coordinates;
    }
}
