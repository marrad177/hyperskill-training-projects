package battleship;

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
            Coordinates startAircraftCarrier = new Coordinates(aircraftCarrierInput.split(" ")[0]);
            Coordinates tailAircraftCarrier = new Coordinates(aircraftCarrierInput.split(" ")[1]);
            Ship aircraftCarrier = new AircraftCarrier(startAircraftCarrier, tailAircraftCarrier);
            while(!gamefield.placeShip(aircraftCarrier.coordinates)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
            gamefield.printGamefield();

            System.out.println("Enter the coordinates of the Battleship (4 cells):");
            String battleshipInput = scanner.nextLine();
            Coordinates startBattleship = new Coordinates(battleshipInput.split(" ")[0]);
            Coordinates tailBattleship = new Coordinates(battleshipInput.split(" ")[1]);
            Ship battleship = new Battleship(startBattleship, tailBattleship);
            gamefield.placeShip(battleship.coordinates);
            gamefield.printGamefield();

            System.out.println("Enter the coordinates of the Submarine (3 cells):");
            String submarineInput = scanner.nextLine();
            Coordinates startSubmarine = new Coordinates(submarineInput.split(" ")[0]);
            Coordinates tailSubmarine = new Coordinates(submarineInput.split(" ")[1]);
            Ship submarine = new Submarine(startSubmarine, tailSubmarine);
            gamefield.placeShip(submarine.coordinates);
            gamefield.printGamefield();

            System.out.println("Enter the coordinates of the Cruiser (3 cells):");
            String cruiserInput = scanner.nextLine();
            Coordinates startCruiser = new Coordinates(cruiserInput.split(" ")[0]);
            Coordinates tailCruiser = new Coordinates(cruiserInput.split(" ")[1]);
            Ship cruiser = new Cruiser(startCruiser, tailCruiser);
            gamefield.placeShip(cruiser.coordinates);
            gamefield.printGamefield();

            System.out.println("Enter the coordinates of the Destroyer (2 cells):");
            String destroyerInput = scanner.nextLine();
            Coordinates startDestroyer = new Coordinates(destroyerInput.split(" ")[0]);
            Coordinates tailDestroyer = new Coordinates(destroyerInput.split(" ")[1]);
            Ship destroyer = new Destroyer(startDestroyer, tailDestroyer);
            gamefield.placeShip(destroyer.coordinates);
            gamefield.printGamefield();
        }
    }
}
