package battleship.gamefield;

public enum NCoord {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10),
    DEFAULT(-1);


    private int number;

    NCoord(int number) {
        this.number = number;
    }

//    static public String getNCoordString(int number) {
//        switch (number) {
//            case 1 : return "A";
//            case 2 : return "B";
//            case 3 : return "C";
//            case 4 : return "D";
//            case 5 : return "E";
//            case 6 : return "F";
//            case 7 : return "G";
//            case 8 : return "H";
//            case 9 : return "I";
//            case 10 : return "J";
//            default: return null;
//        }
//    }

    public int getNumber() {
        return number;
    }
}
