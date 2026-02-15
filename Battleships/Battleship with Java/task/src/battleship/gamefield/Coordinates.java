package battleship.gamefield;

import java.util.HashMap;
import java.util.Map;

public class Coordinates {
    private int m;
    private int n;
    Map<String, Integer> dictMtoNumber;

    public Coordinates(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public Coordinates(String inputString) {
        String coordinatesString = inputString.split(" ")[0].split("")[0];
        dictMtoNumber = new HashMap<>();
        dictMtoNumber.put("A",1);
        dictMtoNumber.put("B",2);
        dictMtoNumber.put("C",3);
        dictMtoNumber.put("D",4);
        dictMtoNumber.put("E",5);
        dictMtoNumber.put("F",6);
        dictMtoNumber.put("G",7);
        dictMtoNumber.put("H",8);
        dictMtoNumber.put("I",9);
        dictMtoNumber.put("J",10);
        if(dictMtoNumber.containsKey(coordinatesString.toUpperCase())) {
            this.m = dictMtoNumber.get(coordinatesString.toUpperCase());
        } else {
            throw new IllegalArgumentException();
        }
        this.n = Integer.parseInt(inputString.split(" ")[0].substring(1));
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
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

    public boolean equals(Coordinates equalsCoordinates) {
        if((this.getM() == equalsCoordinates.getM()) && (this.getN() == equalsCoordinates.getN())) {
            return true;
        } else {
            return false;
        }
    }
}
