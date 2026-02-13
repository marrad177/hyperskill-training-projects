package battleship;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Gamefield {
    // 10x10 Feld mit je einer Zeile bzw. Spalte für Metadaten
    String[][] gameField;

    Gamefield() {
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
    }

    public boolean placeShip(List<Coordinates> coordinates) {
        Deque<Coordinates> undoDeque = new ArrayDeque<>();
        for(Coordinates coordinate : coordinates) {
            if(neighbourFree(coordinate.getM(), coordinate.getN())) {
                gameField[coordinate.getM()][coordinate.getN()] = "o";
                undoDeque.push(coordinate);
            } else {
                for(Coordinates undoCoordinate : undoDeque) {
                    gameField[undoCoordinate.getM()][undoCoordinate.getN()] = "~";
                }
                return false;
            }
        }
        return true;
    }

    public boolean neighbourFree(int m, int n) {
        if (gameField[m-1][n-1] == "o" || gameField[m-1][n] == "o" || gameField[m-1][n+1] == "o" ||
                gameField[m][n-1] == "o" || gameField[m][n] == "o" || gameField[m][n+1] == "o" ||
                gameField[m+1][n-1] == "o"|| gameField[m+1][n] == "o" || gameField[m+1][n+1] == "o") {
            return false;
        } else {
            return true;
        }
    }

    public void printGamefield() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++)
                System.out.print(gameField[i][j] + " ");
            System.out.println();
        }
    }
}
