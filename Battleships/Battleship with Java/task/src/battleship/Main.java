package battleship;

import battleship.gamefield.CoordUtils;
import battleship.gamefield.Coordinates;
import battleship.gamefield.Gamefield;
import battleship.ships.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Gamefield gamefield = new Gamefield();
        gamefield.printGamefield();
        List<Ship> shipList = new ArrayList<>(6);

        // Input format: MN MN
        // M: A-J
        // N: 0-10
        String[][] allExpectedShips = {{"Aircraft Carrier", "5"}, {"Battleship", "4"}, {"Submarine", "3"},
                {"Cruiser", "3"}, {"Destroyer", "2"}};
        for (String[] expectedShip : allExpectedShips) {
            String inputShip =
                gamefield.promptInput("Enter the coordinates of the " + expectedShip[0] + " (" + expectedShip[1] + " cells):");
            while(!CoordUtils.validateInput(inputShip, 2)) {
                inputShip = gamefield.promptInput("Input Error! \"MN MN\" expected. Try again:");
            }
            Coordinates[] startTailShip = CoordUtils.extractShipPlacementCoordinates(inputShip);

            while (startTailShip[0].distance(startTailShip[1]) != Integer.parseInt(expectedShip[1]) ||
                    !Gamefield.onGamefield(startTailShip[0]) || !Gamefield.onGamefield(startTailShip[1])) {
                String newInput = new String();
                if (!Gamefield.onGamefield(startTailShip[0]) || !Gamefield.onGamefield(startTailShip[1])) {
                    newInput = gamefield.promptInput("Error! Coordinates not on battlefield! Try again:");
                } else if (startTailShip[0].distance(startTailShip[1]) != Integer.parseInt(expectedShip[1])) {
                    newInput =
                            gamefield.promptInput("Error! Wrong length of the " + expectedShip[0] + "! Try again:");
                }
                startTailShip = CoordUtils.extractShipPlacementCoordinates(newInput);
            }

            Ship ship = switch (expectedShip[0]) {
                case "Aircraft Carrier" -> new AircraftCarrier(startTailShip[0], startTailShip[1]);
                case "Battleship" -> new Battleship(startTailShip[0], startTailShip[1]);
                case "Submarine" -> new Submarine(startTailShip[0], startTailShip[1]);
                case "Cruiser" -> new Cruiser(startTailShip[0], startTailShip[1]);
                case "Destroyer" -> new Destroyer(startTailShip[0], startTailShip[1]);
                default -> null;
            };

            while (!gamefield.placeShip(ship.coordinates)) {
                String newInput = gamefield.promptInput("Error! You placed it too close to another one. Try again:");
                startTailShip = CoordUtils.extractShipPlacementCoordinates(newInput);
                ship.buildShipCoordinates(startTailShip[0], startTailShip[1]);
            }
            shipList.add(ship);
            System.out.println();
            gamefield.printGamefield();
        }

        System.out.println("The game starts!");
        while (!shipList.isEmpty()) {
            gamefield.printMaskedGamefield();
            String shotInput = gamefield.promptInput("Take a shot!");
            while(!CoordUtils.validateInput(shotInput, 2)) {
                shotInput = gamefield.promptInput("Input Error! \"MN MN\" expected. Try again:");
            }
            Coordinates shot = CoordUtils.extractShotCoordinates(shotInput);
            while (!Gamefield.onGamefield(shot)) {
                String shotFixInput =
                        gamefield.promptInput("Error! You entered the wrong coordinates! Try again:");
                shot = CoordUtils.extractShotCoordinates(shotFixInput);
            }
            StringBuilder hitMsg = new StringBuilder(50);
            if (gamefield.shoot(shot)) {
                Ship deleteShip = null;
                for (Ship ship : shipList) {
                    Coordinates deleteCoordinates = null;
                    for (Coordinates coordinate : ship.getCoordinates()) {
                        if (coordinate.equals(shot)) {
                            deleteCoordinates = coordinate;
                            break;
                        }
                    }
                    if (deleteCoordinates != null) {
                        ship.getCoordinates().remove(deleteCoordinates);
                    }
                    if (ship.getCoordinates().isEmpty()) {
                        deleteShip = ship;
                        break;
                    } else {
                        hitMsg.append("You hit a ship!");
                        break;
                    }
                }
                if (deleteShip != null) {
                    shipList.remove(deleteShip);
                    if (shipList.isEmpty()) {
                        gamefield.printMaskedGamefield();
                        hitMsg.append("You sank the last ship. You won. Congratulations!");
                    } else {
                        hitMsg.append("You sank a ship!");
                    }
                }
            } else {
                hitMsg.append("You missed!");
            }
            gamefield.printMessage(hitMsg.toString());
        }
    }
}

