package poder;

public enum TipoPoder {


    NINGUNO("sprites/ship1.png", "sprites/shot1.png"),
    DOBLEDISPARO("sprites/ship2.png", "sprites/shot2.png"),
    MASDANNO("sprites/ship3.png", "sprites/shot3.png"),
    DISPARORAPIDO("sprites/ship4.png", "sprites/shot4.png");

    private final String nave, bala;

    TipoPoder(String nave, String bala) {
        this.nave = nave;
        this.bala = bala;
    }

    public String getNave() {
        return nave;
    }

    public String getBala() {
        return bala;
    }
}
