import java.io.File;
import java.sql.SQLOutput;
import java.util.Scanner;


public class Konsole {

    public Konsole() { // Leerer Konstruktor, hat keine Variablen, nur Methoden
    }


    // Allererste Nutzerabfrage: Welche Funktion soll ausgefuehrt werden?
    public static void inputSelection() {

        printText("Welche Funktion soll ausgefuehrt werden?\n" +
                "1) Die Schluesselpaargenerierung (KeyGenerator)\n" +
                "2) Die Verschluesselungsfunktion (encrypt)\n" +
                "3) Die Entschluesselungsfunktion (decrypt)");
        int function = Konsole.getNumber(1, 3); // Wert von int function (1, 2 oder 3) legt fest, welche Funktion im naechsten Schritt ausgefuehrt wird

        switch (function) {
            case 1: startKeyGen(); break;
            case 2: startEncrypt(); break;
            case 3: startDecrypt(); break;
        }
    }


    // Printet uebergebenen String in der Konsole
    public static void printText(String text) {
        System.out.println(text);
    }


    // Printet variable Anahl von Eingabeobjekten in der Konsole
    public static void printFormatText(String text, Object... args) {
        System.out.printf(text + "\n", args);
    }


    // Printet chars in der Konsole
    public static void printChar(char charecter) {
        System.out.println(charecter);
    }



    // Scannt naechste Textzeile in der Konsole
    public static String getText() {
        Scanner textScanner = new Scanner(System.in);
        return textScanner.nextLine();
    }



    /* getNumber-Funktion: Scannt eine Nutzereingabe in der Konsole.
    Diese kann entweder in eine bestimmten, vorher festgelegten Intervall liegenm,
    oder es kann eine beliebige Zahl gewaehlt werden, wenn kein Intervall ausgewaehlt wurde
     */
    public static int getNumber() { // Falls kein min und max festgelegt wurden, wird in der Folge einfach jede Zahl akzeptiert
        return getNumber(0, 0, null, null);
    }


    // Vorbereitung fuer Aufruf mit Intervall von [min-max]
    public static int getNumber(int min, int max) {
        return getNumber(min, max, null, null);
    }


    // Aufruf mit Intervall von [min-max]
    public static int getNumber(int min, int max, String noNumberError, String outOfRangeError) {

        Scanner intScanner = new Scanner(System.in); // Stellt Int-Scanner bereit
        boolean inputAccepted = false; // Fuer den Anfang auf falsch gesetzt
        int nextInt = 0; // nextInt wird mit 0 initialisiert und spaeter ueberschrieben

        while (!inputAccepted) { // Am Anfang auf jeden Fall erfuellt und danach solange, bis Eingabe korrekt war
            try {
                nextInt = Integer.parseInt(intScanner.nextLine()); // Wandelt Eingabe aus einem String in einen Integer um
                if (max == 0) { // Falls kein min und max festgelegt wurden, wird einfach jede Zahl akzeptiert
                    inputAccepted = true;
                } else {
                    if (nextInt < min || nextInt > max) { // Falls Eingabe nicht in Intervall [min-max] liegt
                        if (outOfRangeError != null && !outOfRangeError.isEmpty()) {
                            printFormatText(outOfRangeError, min, max);
                        } else {
                            printFormatText("Fehler :Die Eingegebene Zahl muss groesser als %d und kleiner als %d sein.", min, max);
                        }
                    } else {
                        inputAccepted = true;
                    }
                }
            } catch (NumberFormatException noNumberException) { // Faengt alls ab, was keine Zahl ist
                if (noNumberError != null && !noNumberError.isEmpty()) {
                    printText(noNumberError);
                } else {
                    printText("Fehler: Die Eingabe muss ein Integer sein.");
                }
            }
        }
        return nextInt;
    }

    public static String getFilePath() {
        return getFilePath("Fehler: Der eingegebene Pfad ist ungueltig.");
    }


    public static String getFilePath (String noPathError) {
        String filePath = getText();
        while (!new File(filePath).isFile()) {
            Konsole.printText(noPathError);
            filePath = Konsole.getText();
        }
        return filePath;
    }


    public static void startKeyGen() {

        Konsole.printText("Schluesselpaargenerierung gestartet");
        Konsole.printText("Bitte geben Sie die erste Primzahl p ein:");
        int p = Konsole.getNumber();
        while (!isPrime(p)) {
            Konsole.printText("Fehler: Die eingegebene Zahl muss eine Primzahl sein.");
            p = Konsole.getNumber();
        }
        Konsole.printText("Bitte geben Sie die zweite Primzahl q ein:");
        int q = Konsole.getNumber();
        while (!isPrime(q)) {
            Konsole.printText("Fehler: Die eingegebene Zahl muss eine Primzahl sein.");
            q = Konsole.getNumber();
        }

        Generator gen = new Generator(p, q);
        // gen.startKeyPairGen(); Jetzt direkt in den Konstruktor gepackt
    }


    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= num/2; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }


    public static void startEncrypt () {
        Konsole.printText("Verschluesselungsprozess gestartet");
        Konsole.printText("Bitte geben Sie den Dateipfad zu der Datei ein, die Sie verschluesseln moechten:");

        String filePath = Konsole.getFilePath();

        Konsole.printText("Wie soll der verschluesselte Text bereitgestellt werden?\n" +
                "1) In einer neuen Datei\n" + // diese wird im Ordner, in dem das Programm laeuft, erzeugt
                "2) In der Konsole");

        int dataOutputSel = Konsole.getNumber(1, 2);
        boolean toNewFile = false;
        if (dataOutputSel == 1) {
            toNewFile = true;
        }

        Konsole.printText("Geben Sie jetzt bitte die Werte Ihres Oeffentlichen Schluessels ein.\n" +
                "e: ");
        int e = Konsole.getNumber();

        Konsole.printText("g: ");
        int g = Konsole.getNumber();

        PublicKey publicKey = new PublicKey(e, g);

        Cryptor.encrypt(filePath, publicKey, toNewFile);
    }


    public static void startDecrypt () {
        Konsole.printText("Entschluesselungsprozess gestartet");
        Konsole.printText("Bitte geben Sie den Dateipfad zu der Datei ein, die Sie entschluesseln moechten:");

        String filePath = Konsole.getFilePath();

        Konsole.printText("Wie soll der entschluesselte Text bereitgestellt werden?\n" +
                "1) In einer neuen Datei\n" + // diese wird im Ordner, in dem das Programm laeuft, erzeugt
                "2) In der Konsole");

        int dataOutputSel = Konsole.getNumber(1, 2);
        boolean toNewFile = false;
        if (dataOutputSel == 1) {
            toNewFile = true;
        }

        Konsole.printText("Geben Sie jetzt bitte die Werte Ihres Oeffentlichen Schluessels ein.\n" +
                "d: ");
        int d = Konsole.getNumber();

        Konsole.printText("g: ");
        int g = Konsole.getNumber();

        PrivateKey privateKey= new PrivateKey(d, g);

        Cryptor.decrypt(filePath, privateKey, toNewFile);
    }
}
