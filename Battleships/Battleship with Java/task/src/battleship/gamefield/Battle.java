package battleship.gamefield;

import battleship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Battle {
    Scanner scanner;
    Gamefield gamefieldPlayer1;
    Gamefield gamefieldPlayer2;
    Gamefield emptyGamefield;
    List<Ship> shipListPlayer1;
    List<Ship> shipListPlayer2;

    public Battle(Gamefield gamefieldPlayer1, Gamefield gamefieldPlayer2) {
        this.gamefieldPlayer1 = gamefieldPlayer1;
        this.gamefieldPlayer2 = gamefieldPlayer2;
        this.emptyGamefield = new Gamefield();
        this.shipListPlayer1 = new ArrayList<>(6);
        this.shipListPlayer2 = new ArrayList<>(6);
        this.scanner = new Scanner(System.in);
    }

    public String promptInput(String message) {
        System.out.println(message);
        String inputString = scanner.nextLine();
        return inputString;
    }

    public void printMessage(String msg) {
        System.out.println(msg);
    }

    public void fillGamefieldsWithShips() {
        printMessage("Player 1 place your ships on the game field");
        gamefieldPlayer1.printGamefield();
        String[][] allExpectedShips = {{"Aircraft Carrier", "5"}, {"Battleship", "4"}, {"Submarine", "3"},
                {"Cruiser", "3"}, {"Destroyer", "2"}};
        for (String[] expectedShip : allExpectedShips) {
            String shipInput =
                    promptInput("Enter the coordinates of the " + expectedShip[0] + " (" + expectedShip[1] + " cells):");
            Pattern shipInputPattern = Pattern.compile("\\b[a-jA-J][1-9][^1-9]?\\b\\s+\\b[a-jA-J][1-9][^1-9]?\\b");
            Matcher shipInputMatcher = shipInputPattern.matcher(shipInput);
            while (!shipInputMatcher.matches()) {
                shipInput = promptInput("Input Error! \"MN MN\" expected. Try again:");
                shipInputMatcher = shipInputPattern.matcher(shipInput);
            }
            Coordinates[] startTailShip = CoordUtils.extractShipPlacementCoordinates(shipInput);

            while (startTailShip[0].distance(startTailShip[1]) != Integer.parseInt(expectedShip[1]) ||
                    !Gamefield.onGamefield(startTailShip[0]) || !Gamefield.onGamefield(startTailShip[1])) {
                String newInput = new String();
                if (!Gamefield.onGamefield(startTailShip[0]) || !Gamefield.onGamefield(startTailShip[1])) {
                    newInput = promptInput("Error! Coordinates not on battlefield! Try again:");
                } else if (startTailShip[0].distance(startTailShip[1]) != Integer.parseInt(expectedShip[1])) {
                    newInput =
                            promptInput("Error! Wrong length of the " + expectedShip[0] + "! Try again:");
                }
                startTailShip = CoordUtils.extractShipPlacementCoordinates(newInput);
            }

            Ship ship = new Ship(expectedShip[0], expectedShip[1], startTailShip[0], startTailShip[1]);

            while (!gamefieldPlayer1.placeShip(ship.getCoordinates())) {
                String newInput = promptInput("Error! You placed it too close to another one. Try again:");
                startTailShip = CoordUtils.extractShipPlacementCoordinates(newInput);
                ship.buildShipCoordinates(startTailShip[0], startTailShip[1]);
            }
            shipListPlayer1.add(ship);
            System.out.println();
            gamefieldPlayer1.printGamefield();

        }

//        promptInput("Press Enter and pass the move to another player\n...");
//
//        printMessage("Player 2 place your ships on the game field");
//        gamefieldPlayer2.printGamefield();
//        for (String[] expectedShip : allExpectedShips) {
//            String shipInput =
//                    promptInput("Enter the coordinates of the " + expectedShip[0] + " (" + expectedShip[1] + " cells):");
//            Pattern shipInputPattern = Pattern.compile("\\b[a-jA-J][1-9][^1-9]?\\b\\s+\\b[a-jA-J][1-9][^1-9]?\\b");
//            Matcher shipInputMatcher = shipInputPattern.matcher(shipInput);
//            while (!shipInputMatcher.matches()) {
//                shipInput = promptInput("Input Error! \"MN MN\" expected. Try again:");
//                shipInputMatcher = shipInputPattern.matcher(shipInput);
//            }
//            Coordinates[] startTailShip = CoordUtils.extractShipPlacementCoordinates(shipInput);
//
//            while (startTailShip[0].distance(startTailShip[1]) != Integer.parseInt(expectedShip[1]) ||
//                    !Gamefield.onGamefield(startTailShip[0]) || !Gamefield.onGamefield(startTailShip[1])) {
//                String newInput = new String();
//                if (!Gamefield.onGamefield(startTailShip[0]) || !Gamefield.onGamefield(startTailShip[1])) {
//                    newInput = promptInput("Error! Coordinates not on battlefield! Try again:");
//                } else if (startTailShip[0].distance(startTailShip[1]) != Integer.parseInt(expectedShip[1])) {
//                    newInput =
//                            promptInput("Error! Wrong length of the " + expectedShip[0] + "! Try again:");
//                }
//                startTailShip = CoordUtils.extractShipPlacementCoordinates(newInput);
//            }
//
//            Ship ship = new Ship(expectedShip[0], expectedShip[1], startTailShip[0], startTailShip[1]);
//
//            while (!gamefieldPlayer2.placeShip(ship.getCoordinates())) {
//                String newInput = promptInput("Error! You placed it too close to another one. Try again:");
//                startTailShip = CoordUtils.extractShipPlacementCoordinates(newInput);
//                ship.buildShipCoordinates(startTailShip[0], startTailShip[1]);
//            }
//            shipListPlayer2.add(ship);
//            System.out.println();
//            gamefieldPlayer2.printGamefield();
//        }

        printMessage("The game starts!");
    }

    public int takeAShot(int player) {
        Gamefield enemyGamefield;
        Gamefield ownGamefield;
        List<Ship> enemyShipList;
        int nextPlayer;

        switch (player) {
            case 1:
                enemyGamefield = emptyGamefield;
                enemyShipList = shipListPlayer2;
                ownGamefield = gamefieldPlayer1;
                nextPlayer = 2;
                break;
            case 2:
                enemyGamefield = emptyGamefield;
                enemyShipList = shipListPlayer1;
                ownGamefield = gamefieldPlayer2;
                nextPlayer = 1;
                break;
            default:
                enemyGamefield = null;
                enemyShipList = null;
                nextPlayer = 0;
                ownGamefield = null;
                System.out.println("Unknown Player.");
        }

        enemyGamefield.printMaskedGamefield();
        System.out.println("---------------------");
        ownGamefield.printMaskedGamefield();
        String shotInput = promptInput("Player " + player + ", it's your turn:");
        Pattern shotInputPattern = Pattern.compile("\\b[a-jA-J][1-9]0?\\b");
        Matcher shotInputMatcher = shotInputPattern.matcher(shotInput);
        while (!shotInputMatcher.matches()) {
            shotInput = promptInput("Input Error! \"MN\" expected. Try again:");
            shotInputMatcher = shotInputPattern.matcher(shotInput);
        }
        Coordinates shot = CoordUtils.extractShotCoordinates(shotInput);
        while (!Gamefield.onGamefield(shot)) {
            String shotFixInput =
                    promptInput("Error! You entered the wrong coordinates! Try again:");
            shot = CoordUtils.extractShotCoordinates(shotFixInput);
        }
        String hitMsg = new String();
        if (enemyGamefield.shoot(shot)) {
            Ship deleteShip = null;
            for (Ship ship : enemyShipList) {
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
                enemyShipList.remove(deleteShip);
                if (enemyShipList.isEmpty()) {
                    enemyGamefield.printMaskedGamefield();
                    hitMsg = "You sank the last ship. You won. Congratulations!";
                    scanner.close();

                } else {
                    hitMsg = "You sank a ship!";
                }
            }
        } else {
            hitMsg = "You missed!";
        }
        printMessage(hitMsg.toString());
        printMessage("Press Enter and pass the move to another player");
        promptInput("...");
        return nextPlayer;
    }

    public boolean battleWon() {
        if(shipListPlayer1.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
