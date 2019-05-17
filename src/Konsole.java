import java.util.Scanner;

public class Konsole {

    public Konsole() {
    } // Leerer Konstruktor, hat keine Variablen, nur Methoden

    // Allererste Nutzerabfrage: Welche Funktion soll ausgefuehrt werden?
    public static void inputSelection() {

        printText("Welche Funktion soll ausgefuehrt werden?\n" +
                "1) Die Schluesselpaargenerierung (KeyGenerator)\n" +
                "2) Die Verschluesselungsfunktion (encrypt)\n" +
                "3) Die Entschluesselungsfunktion (decrypt)");
        int function = Konsole.getNumber(1, 3); // Wert von int function (1, 2 oder 3) legt fest, welche Funktion im naechsten Schritt ausgefuehrt wird
    }


    // Printet uebergebenen String in der Konsole
    public static void printText(String text) {
        System.out.println(text);
    }


    // Printet variable Anahl von Eingabeobjekten
    public static void printFormatText(String text, Object... args) {
        System.out.printf(text + "\n", args);
    }


    // Scannt naechste Textzeile in der Konsole
    public static String scanText() {
        Scanner textScanner = new Scanner(System.in);
        return textScanner.nextLine();
    }


    //
    public static int getNumber() { // Falls kein min und max festgelegt wurden, wird in der Folge einfach jede Zahl akzeptiert
        return getNumber(0, 0, null, null);
    }


    //
    public static int getNumber(int min, int max) {
        return getNumber(min, max, null, null);
    }


    //
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
                        if (outOfRangeError != null && !outOfRangeError.isEmpty()) { // Fall ueberfluessig?
                            printFormatText(outOfRangeError, min, max);
                        } else {
                            printFormatText("Die Eingegebene Zahl muss groesser als %d und kleiner als %d sein.", min, max);
                        }
                    } else {
                        inputAccepted = true;
                    }
                }
            } catch (NumberFormatException noNumberException) { // Faengt alls ab, was keine Zahl ist
                if (noNumberError != null && !noNumberError.isEmpty()) { // Fall ueberfluessig?
                    printText(noNumberError);
                } else {
                    printText("Die Eingabe muss ein Integer sein.");
                }
            }
        }
        return nextInt;
    }
}



/*
        public static long getNumber(long min, long max) {
            return getNumber(min, max, null, null);
        }

        public static long getNumber ( long min, long max, String noNumberError, String outOfRangeError){

            Scanner intScanner = new Scanner(System.in);
            boolean inputAccepted = false;
            long nextLong = 0;
            while (!inputAccepted)
                try {
                    nextLong = Long.parseLong(intScanner.nextLine());
                    if (max == 0) {
                        inputAccepted = true;
                    } else {
                        if (nextLong < min || nextLong > max) {
                            if (outOfRangeError != null && !outOfRangeError.isEmpty()) {
                                sendFormatText(outOfRangeError, min, max);
                            } else {
                                sendFormatText("Fehler! Die Zahl muss groesser als %d und kleiner als %d. sein.", min, max);
                            }
                        } else {
                            inputAccepted = true;
                        }
                    }
                } catch (NumberFormatException noNumberException) {
                    if (noNumberError != null && !noNumberError.isEmpty()) {
                        printText(noNumberError);
                    } else {
                        printText("Input has to be an integer number.");
                    }
                }
            return nextLong;
        }
    }



        public static void sendChar(char character) {

            System.out.print(character);
        }

        public static void sendFormatText(String text, Object... args) {

            System.out.printf(text+"\n", args);
        }

 */





