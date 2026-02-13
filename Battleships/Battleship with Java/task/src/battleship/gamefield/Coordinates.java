package battleship.gamefield;

public class Coordinates {
    private int m;
    private int n;

    public Coordinates(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public Coordinates(String inputString) {
        String startStringM = inputString.split(" ")[0].split("")[0];
        this.m = NCoord.valueOf(startStringM).getNumber();
        this.n = Integer.parseInt(inputString.split(" ")[0].substring(1));
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int distance(Coordinates newCoordinates) {
        if (this.getM() == newCoordinates.getM()) {
            return Math.abs(this.getN() - newCoordinates.getN()) + 1;
        } else if (this.getN() == newCoordinates.getN()) {
            return Math.abs(this.getM() - newCoordinates.getM()) + 1;
        } else {
            return 0;
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
}
