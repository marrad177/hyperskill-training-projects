package battleship.gamefield;

import java.util.*;

public class Gamefield {
    // 10x10 Feld mit je einer Zeile bzw. Spalte für Metadaten
    String[][] gameField;
    Set<Coordinates> occupiedFields;
    int countOccupiedFields;

    public Gamefield() {
        gameField = new String[11][11];
        fillGamefield();
        this.occupiedFields = new HashSet<>();
        this.countOccupiedFields = 0;
    }

    public boolean placeShip(List<Coordinates> coordinates) {
        Deque<Coordinates> coordsDeque = new ArrayDeque<>();
        for(Coordinates coordinate : coordinates) {
            if(neighbourFree(coordinate.getM(), coordinate.getN())) {
                gameField[coordinate.getM()][coordinate.getN()] = "O";
                coordsDeque.push(coordinate);
            } else {
                for(Coordinates undoCoordinate : coordsDeque) {
                    gameField[undoCoordinate.getM()][undoCoordinate.getN()] = "~";
                    coordsDeque.pop();
                }
                return false;
            }
        }
        if(!coordsDeque.isEmpty()) {
            occupiedFields.addAll(coordsDeque);
            countOccupiedFields += coordsDeque.size();
            return true;
        } else {
            return false;
        }
    }

    public int getShipPartsLeft() {
        return countOccupiedFields;
    }

    public boolean neighbourFree(int m, int n) {
        for (Coordinates coord : occupiedFields) {
            if((coord.getM() == m-1 && (coord.getN() == n-1 || coord.getN() == n || coord.getN() == n+1)) ||
                    (coord.getM() == m && (coord.getN() == n-1 || coord.getN() == n || coord.getN() == n+1)) ||
                    (coord.getM() == m+1 && (coord.getN() == n-1 || coord.getN() == n || coord.getN() == n+1))
                ) {
                return false;
            }
        }
        return true;
    }

    public boolean shoot(Coordinates coordinates) {
        if(gameField[coordinates.getM()][coordinates.getN()] == "O" ||
                gameField[coordinates.getM()][coordinates.getN()] == "X") {
            gameField[coordinates.getM()][coordinates.getN()] = "X";
            countOccupiedFields--;
            return true;
        } else {
            gameField[coordinates.getM()][coordinates.getN()] = "M";
            return false;
        }
    }

    public static boolean onGamefield(int m, int n) {
        if(m > 0 && m <= 10 && n > 0 && n <= 10) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean onGamefield(Coordinates coordinates) {
        int m = coordinates.getM();
        int n = coordinates.getN();
        if(m > 0 && m <= 10 && n > 0 && n <= 10) {
            return true;
        } else {
            return false;
        }
    }

    public void fillGamefield() {
        char aChar = 'a';
        for(int i = 1; i <= 10; i++) {
            gameField[0][i] = String.valueOf(i);
            gameField[i][0] = String.valueOf(aChar).toUpperCase();
            aChar++;
        }
        gameField[0][0] = " ";   // field in upper left corner
        for (int i = 1; i < gameField.length; i++) {
            for (int j = 1; j < gameField.length; j++) {
                gameField[i][j] = "~";
            }
        }
    }

    public void printGamefield() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++)
                System.out.print(gameField[i][j] + " ");
            System.out.println();
        }
    }

    public void printMaskedGamefield() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                String temp = gameField[i][j];
                if("O".equals(temp)) {
                    temp = "~";
                }
                System.out.print(temp + " ");
            }
            System.out.println();
        }
    }
}
