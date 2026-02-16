package battleship;

import battleship.gamefield.Battle;
import battleship.gamefield.Gamefield;

public class Main {

    public static void main(String[] args) {
        Gamefield gamefieldPlayer1 = new Gamefield();
        Gamefield gamefieldPlayer2 = new Gamefield();
        Battle battle = new Battle(gamefieldPlayer1, gamefieldPlayer2);
        battle.fillGamefieldsWithShips();
        int nextPlayer = battle.takeAShot(1);
        while(!battle.battleWon()) {
            nextPlayer = battle.takeAShot(nextPlayer);
        }
    }
}

