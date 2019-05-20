public class PrivateKey {

/**
*erste Stelle des Schluessels; durch Generator ermittelt
*/
    private int d;

  /**
    *zweite Stelle des Schluessels; ergibt ich direkt aus eingegebenen Primzahlen
    */
    private int g;

    /**
     * Konstruktor eines privaten Schluessels.
     *
     * @param d      erste Stelle des Schluessels.
     * @param g      zweite Stelle des Schluessels.
     */
    public PrivateKey(int d, int g) {
        this.d = d;
        this.g = g;
    }

    public int getD() {
        return d;
    }

    public int getG() {
        return g;
    }

}
