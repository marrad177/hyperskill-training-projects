package battleship;

public class Coordinates {
    private int m;
    private int n;

    Coordinates(int m, int n) {
        this.m = m;
        this.n = n;
    }

    Coordinates(String inputString) {
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

    static boolean onGamefield(int m, int n) {
        if(m > 0 && m <= 10 && n > 0 && n <= 10) {
            return true;
        } else {
            return false;
        }
    }

    static boolean onGamefield(Coordinates coordinates) {
        int m = coordinates.getM();
        int n = coordinates.getN();
        if(m > 0 && m <= 10 && n > 0 && n <= 10) {
            return true;
        } else {
            return false;
        }
    }
}
