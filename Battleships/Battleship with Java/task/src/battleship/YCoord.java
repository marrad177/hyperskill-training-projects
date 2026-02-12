package battleship;

public enum YCoord {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10);


    private int number;

    YCoord(int number) {
        this.number = number;
    }

    public YCoord getYCoordString(int number) {
        switch (number) {
            case 1 : return this.A;
            case 2 : return this.B;
            case 3 : return this.C;
            case 4 : return this.D;
            case 5 : return this.E;
            case 6 : return this.F;
            case 7 : return this.G;
            case 8 : return this.H;
            case 9 : return this.I;
            case 10 : return this.J;
            default: return null;
        }
    }

    public int getNumber() {
        return number;
    }
}
