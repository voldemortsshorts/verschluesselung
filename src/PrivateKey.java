public class PrivateKey {

    private int d;
    private int g;

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
