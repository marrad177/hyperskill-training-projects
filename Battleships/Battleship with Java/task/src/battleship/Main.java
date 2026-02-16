package battleship;

import battleship.gamefield.CoordUtils;
import battleship.gamefield.Coordinates;
import battleship.gamefield.Gamefield;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String shipInput =
                gamefield.promptInput("Enter the coordinates of the " + expectedShip[0] + " (" + expectedShip[1] + " cells):");
            Pattern shipInputPattern = Pattern.compile("\\b[a-jA-J][1-9][^1-9]?\\b\\s+\\b[a-jA-J][1-9][^1-9]?\\b");
            Matcher shipInputMatcher = shipInputPattern.matcher(shipInput);
            while(!shipInputMatcher.matches()) {
                shipInput = gamefield.promptInput("Input Error! \"MN MN\" expected. Try again:");
                shipInputMatcher = shipInputPattern.matcher(shipInput);
            }
            Coordinates[] startTailShip = CoordUtils.extractShipPlacementCoordinates(shipInput);

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

            Ship ship = new Ship(expectedShip[0], expectedShip[1], startTailShip[0], startTailShip[1]);

            while (!gamefield.placeShip(ship.getCoordinates())) {
                String newInput = gamefield.promptInput("Error! You placed it too close to another one. Try again:");
                startTailShip = CoordUtils.extractShipPlacementCoordinates(newInput);
                ship.buildShipCoordinates(startTailShip[0], startTailShip[1]);
            }
            shipList.add(ship);
            System.out.println();
            gamefield.printGamefield();
        }

        gamefield.printMessage("The game starts!");
        while (!shipList.isEmpty()) {
            gamefield.printMaskedGamefield();
            String shotInput = gamefield.promptInput("Take a shot!");
            Pattern shotInputPattern = Pattern.compile("\\b[a-jA-J][1-9]0?\\b");
            Matcher shotInputMatcher = shotInputPattern.matcher(shotInput);
            while(!shotInputMatcher.matches()) {
                shotInput = gamefield.promptInput("Input Error! \"MN\" expected. Try again:");
                shotInputMatcher = shotInputPattern.matcher(shotInput);
            }
            Coordinates shot = CoordUtils.extractShotCoordinates(shotInput);
            while (!Gamefield.onGamefield(shot)) {
                String shotFixInput =
                        gamefield.promptInput("Error! You entered the wrong coordinates! Try again:");
                shot = CoordUtils.extractShotCoordinates(shotFixInput);
            }
            String hitMsg = new String();
            if (gamefield.shoot(shot)) {
                Ship deleteShip = null;
                for (Ship ship : shipList) {
                    Coordinates deleteCoordinates = null;
                     for (Coordinates coordinate : ship.getCoordinates()) {
                         if (coordinate.equals(shot)) {
                            deleteCoordinates = coordinate;
                        }
                    }
                    if (deleteCoordinates != null) {
                        ship.removeCoordinates(deleteCoordinates);
                    }
                    if (ship.getCoordinates().isEmpty()) {
                        deleteShip = ship;
                    } else {
                        hitMsg = "You hit a ship!";
                    }
                }
                if (deleteShip != null) {
                    shipList.remove(deleteShip);
                    if (shipList.isEmpty()) {
                        gamefield.printMaskedGamefield();
                        hitMsg = "You sank the last ship. You won. Congratulations!";
                    } else {
                        hitMsg = "You sank a ship!";
                    }
                }
            } else {
                hitMsg = "You missed!";
            }
            gamefield.printMessage(hitMsg.toString());
        }
    }
}

