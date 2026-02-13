package battleship.gamefield;

import java.util.*;

public class Gamefield {
    // 10x10 Feld mit je einer Zeile bzw. Spalte für Metadaten
    String[][] gameField;
    List<Coordinates> occupiedFields;

    public Gamefield() {
        gameField = new String[11][11];
        gameField[0][0] = " ";   // field in upper left corner
        // table metadata assignment
        char aChar = 'a';
        for(int i = 1; i <= 10; i++) {
            gameField[0][i] = String.valueOf(i);
            gameField[i][0] = String.valueOf(aChar).toUpperCase();
            aChar++;
        }
        for (int i = 1; i < gameField.length; i++) {
            for (int j = 1; j < gameField.length; j++) {
                gameField[i][j] = "~";
            }
        }
        this.occupiedFields = new ArrayList<>();
    }

    public boolean placeShip(List<Coordinates> coordinates) {
        Deque<Coordinates> coordsDeque = new ArrayDeque<>();
        for(Coordinates coordinate : coordinates) {
            if(neighbourFree(coordinate.getM(), coordinate.getN())) {
                gameField[coordinate.getM()][coordinate.getN()] = "o";
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
            this.occupiedFields.addAll(coordsDeque);
            return true;
        } else {
            return false;
        }
    }

    public boolean neighbourFree(int m, int n) {
        for (Coordinates coord : occupiedFields) {
            if((coord.getM() == m-1 && (coord.getN() == n-1 || coord.getN() == n || coord.getN() == n+1)) ||
                    (coord.getM() == m && (coord.getN() == n-1 || coord.getN() == n || coord.getN() == n+1)) ||
                    (coord.getM() == m+1 && (coord.getN() == n-1 || coord.getN() == n || coord.getN() == n+1))
                ) {
                System.out.println(coord.getM() + " " + coord.getN() + " " + m + " " + n);
                return false;
            }
        }
        return true;
    }

    public void printGamefield() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++)
                System.out.print(gameField[i][j] + " ");
            System.out.println();
        }
    }
}
