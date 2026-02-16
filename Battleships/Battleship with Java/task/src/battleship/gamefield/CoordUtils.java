package battleship.gamefield;

import java.util.HashMap;
import java.util.Map;

public class CoordUtils {
    public static int translateLetterToNumber(String letter) {
        Map<String, Integer> dictMtoNumber = new HashMap<>();
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
        if(dictMtoNumber.containsKey(letter.toUpperCase())) {
            return dictMtoNumber.get(letter.toUpperCase());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Coordinates extractShotCoordinates(String inputString) {
        Coordinates coordinates;
        try {
            int m = translateLetterToNumber(inputString.split("")[0]);
            int n = Integer.parseInt(inputString.substring(1));
            coordinates = new Coordinates(m, n);
        } catch (IllegalArgumentException iae) {
            coordinates = new Coordinates(-1, -1);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            coordinates = new Coordinates(-1, -1);
        }
        return coordinates;
    }

    public static Coordinates[] extractShipPlacementCoordinates(String inputString) {
        Coordinates[] startTail = new Coordinates[2];
        try {
            int mStart = translateLetterToNumber(inputString.split(" ")[0].substring(0, 1));
            int nStart = Integer.parseInt(inputString.split(" ")[0].substring(1));
            startTail[0] = new Coordinates(mStart, nStart);
            int mTail = translateLetterToNumber(inputString.split(" ")[1].split("")[0]);
            int nTail = Integer.parseInt(inputString.split(" ")[1].substring(1));
            startTail[1] = new Coordinates(mTail, nTail);
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println(aioobe.getMessage());
        }
        return startTail;
    }
}
