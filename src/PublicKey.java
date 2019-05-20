public class PublicKey {

  /**
  *erste Stelle des Schluessels; durch Generator ermittelt
  */
    private int e;

    /**
      *zweite Stelle des Schluessels; ergibt ich direkt aus eingegebenen Primzahlen
      */
    private int g;


    /**
     * Konstruktor eines oeffentlichen Schluessels.
     *
     * @param e      erste Stelle des Schluessels.
     * @param g      zweite Stelle des Schluessels.
     */
    public PublicKey(int e, int g) {
        this.e = e;
        this.g = g;
    }

    public int getE() {
        return e;
    }

    public int getG() {
        return g;
    }


}
