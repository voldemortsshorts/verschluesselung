public class Generator {
    private int g;
    private int h;
    private int e;
    private int d;
    private PublicKey pubKey;
    private PrivateKey privKey;


    public Generator(int p, int q) {
        g = p * q;
        h = (p - 1) * (q - 1); // h = phi

        startKeyPairGen();

    }

/*aus der Aufgabenstellung geht nicht so richtig hervor, ob wir einfach die kleinste passende zahl
oder irgendeine passende zahl ermitteln sollen. hab jetzt eine zufällige passende zahl genutzt,
aber kann das auch noch ändern, weil die Zahlen teilweise ja sehr groß werden*/

    public void generiereOeff() { //ermittelt e, d.h. eine zahl, die keinen gemeinsamen Teiler mit h hat

        e = 1; //e auf 1 setzen

        while (e == 1) {
            int zufall = (int) (Math.random() * 10) + 1; //Zufallszahl zwischen 1 und 10 erstellen
            boolean teiltBeide = false;
            int i = 2;
            while (!teiltBeide && i <= zufall) { //beginnend bei 2, alle potentiellen Teiler testen  // es gibt auch direkt gcd()
                if (h % i == 0 && zufall % i == 0) {
                    teiltBeide = true; /*sobald ein gemeinsamer Teiler gefunden ist, hört innere Schleife auf, e bleibt dann 1
        und eine neue Zufallszahl wird generiert*/

                }
                i++;
            }
      /*erst wenn fest steht, dass keine zahl zwischen 2 und der zufallszahl gemeinsamer teiler ist,
      wird die zufallszahl e zugewiesen:*/
            if (!teiltBeide) {
                e = zufall;
            }

        }

        pubKey = new PublicKey(e, g);

        System.out.println("Oeffentlicher Schluessel wurde erstellt. {e: " + e + ", g: " + g+ "}"); //zur Überprüfung

    }


    public void generierePriv() { //ermittle d, also eine Zahl, so dass h d*e mit Rest 1 teilt

        d = 1; //d auf 1 setzen
        while (d == 1) {
            int zufall = (int) (Math.random() * 40000000) + 1; //ermittle Zufallszahl zwischen 1 und 1000
            int rest = zufall * e % h;
    /*wenn h Zufallszahl*e mit Rest 1 teilt, wird Zufallszahl d zugewiesen,
    ansonsten bleibt d = 1 und neue Zufallszahl wird generiert:*/
            if (rest == 1) {
                d = zufall;
            }

        }
        privKey = new PrivateKey(d, g);

        System.out.println("Privater Schluessel wurde erstellt. {d: " + d + ", g: " + g + "}"); //zur Überprüfung
    }

    public void startKeyPairGen() {
        generiereOeff();
        generierePriv();
    }


}
