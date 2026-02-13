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
            // Input format MN
            // M: A-J
            // N: 0-10
            System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
            String aircraftCarrierInput = scanner.nextLine();
            Coordinates startAircraftCarrier = extractCoordinatesFromInput(aircraftCarrierInput, "start");
            Coordinates tailAircraftCarrier = extractCoordinatesFromInput(aircraftCarrierInput, "tail");
            Ship aircraftCarrier = new AircraftCarrier(startAircraftCarrier, tailAircraftCarrier);
            gamefield.placeShip(aircraftCarrier.coordinates);
            gamefield.printGamefield();

            System.out.println("Enter the coordinates of the Battleship (4 cells):");
            String battleshipInput = scanner.nextLine();
            Coordinates startBattleship = extractCoordinatesFromInput(battleshipInput, "start");
            Coordinates tailBattleship = extractCoordinatesFromInput(battleshipInput, "tail");
            Ship battleship = new Battleship(startBattleship, tailBattleship);
            while(startBattleship.distance(tailBattleship) != battleship.type) {
                System.out.println("Error! Wrong length of the Battleship! Try again:");
                String fixBattleshipInput = scanner.nextLine();
                startBattleship = extractCoordinatesFromInput(fixBattleshipInput, "start");
                tailBattleship = extractCoordinatesFromInput(fixBattleshipInput, "tail");
                battleship = new Battleship(startBattleship, tailBattleship);
            }
            while(!gamefield.placeShip(battleship.coordinates)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                String fixBattleshipInput = scanner.nextLine();
                startBattleship = extractCoordinatesFromInput(fixBattleshipInput, "start");
                tailBattleship = extractCoordinatesFromInput(fixBattleshipInput, "tail");
                battleship = new Battleship(startBattleship, tailBattleship);
            }
            gamefield.printGamefield();

            System.out.println("Enter the coordinates of the Submarine (3 cells):");
            String submarineInput = scanner.nextLine();
            Coordinates startSubmarine = extractCoordinatesFromInput(submarineInput, "start");
            Coordinates tailSubmarine = extractCoordinatesFromInput(submarineInput, "tail");
            Ship submarine = new Submarine(startSubmarine, tailSubmarine);
            while(startBattleship.distance(tailBattleship) != battleship.type) {
                System.out.println("Error! Wrong length of the Submarine! Try again:");
                String fixSubmarineInput = scanner.nextLine();
                startSubmarine = extractCoordinatesFromInput(fixSubmarineInput, "start");
                tailSubmarine = extractCoordinatesFromInput(fixSubmarineInput, "tail");
                submarine = new Submarine(startSubmarine, tailSubmarine);
            }
            while(!gamefield.placeShip(submarine.coordinates)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                String fixSubmarineInput = scanner.nextLine();
                startSubmarine = extractCoordinatesFromInput(fixSubmarineInput, "start");
                tailSubmarine = extractCoordinatesFromInput(fixSubmarineInput, "tail");
                submarine = new Submarine(startSubmarine, tailSubmarine);
            }
            gamefield.printGamefield();

            System.out.println("Enter the coordinates of the Cruiser (3 cells):");
            String cruiserInput = scanner.nextLine();
            Coordinates startCruiser = extractCoordinatesFromInput(cruiserInput, "start");
            Coordinates tailCruiser = extractCoordinatesFromInput(cruiserInput, "tail");
            Ship cruiser = new Cruiser(startCruiser, tailCruiser);
            while(startCruiser.distance(tailCruiser) != cruiser.type) {
                System.out.println("Error! Wrong length of the Cruiser! Try again:");
                String fixCruiserInput = scanner.nextLine();
                startCruiser = extractCoordinatesFromInput(fixCruiserInput, "start");
                tailCruiser = extractCoordinatesFromInput(fixCruiserInput, "tail");
                cruiser = new Cruiser(startCruiser, tailCruiser);
            }
            while(!gamefield.placeShip(cruiser.coordinates)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                String fixCruiserInput = scanner.nextLine();
                startCruiser = extractCoordinatesFromInput(fixCruiserInput, "start");
                tailCruiser = extractCoordinatesFromInput(fixCruiserInput, "tail");
                cruiser = new Cruiser(startCruiser, tailCruiser);
            }
            gamefield.printGamefield();

            System.out.println("Enter the coordinates of the Destroyer (2 cells):");
            String destroyerInput = scanner.nextLine();
            Coordinates startDestroyer = extractCoordinatesFromInput(destroyerInput, "start");
            Coordinates tailDestroyer = extractCoordinatesFromInput(destroyerInput, "tail");
            Ship destroyer = new Destroyer(startDestroyer, tailDestroyer);
            while(startCruiser.distance(tailCruiser) != cruiser.type) {
                System.out.println("Error! Wrong length of the Destroyer! Try again:");
                String fixDestroyerInput = scanner.nextLine();
                startDestroyer = extractCoordinatesFromInput(fixDestroyerInput, "start");
                tailDestroyer = extractCoordinatesFromInput(fixDestroyerInput, "tail");
                destroyer = new Destroyer(startDestroyer, tailDestroyer);
            }
            while(!gamefield.placeShip(destroyer.coordinates)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                String fixDestroyerInput = scanner.nextLine();
                startDestroyer = extractCoordinatesFromInput(fixDestroyerInput, "start");
                tailDestroyer = extractCoordinatesFromInput(fixDestroyerInput, "tail");
                destroyer = new Destroyer(startDestroyer, tailDestroyer);
            }
            gamefield.printGamefield();
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
