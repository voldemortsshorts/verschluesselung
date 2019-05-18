import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.math.BigInteger;

public class Cryptor {

    public static void encrypt (String filePath, PublicKey publicKey, boolean toNewFile) {
        try {
            File sourceFile = new File(filePath); // Zu verschluesselnde Datei wird festgelegt
            File targetFile = new File("encryptedFile.txt"); // Zieldatei: Dateiname wird vorbereitet
            try {
                targetFile.createNewFile(); // Zieldatei wird angelegt
            } catch (IOException e) {
                Konsole.printText("Fehler: Datei fuer verschluesselten Text konnte nicht erstellt werden.");
            }
            FileInputStream fis = new FileInputStream(sourceFile); // InputStream:Eingegebener Dateipfad der zu verschluesselnden Datei wird hinterlegt
            InputStreamReader isr = new InputStreamReader(fis); // Reader wird auf InputStream gesetzt
            BufferedReader br = new BufferedReader(isr); // BufferReader verarbeitet Reader
            FileWriter fw = new FileWriter(targetFile); // Zieldatei wird hinterlegt
            BufferedWriter bw = new BufferedWriter(fw); // Writer wird vorbereitet
            int value = 0;
            while ( (value = br.read()) != -1) { // Geht BufferedReader durch; -1 bedeutet, dass keine Zeichen mehr zu lesen sind
                char c = (char)value; // Wieder rausnehmen, nur Kontrolle
                System.out.println(c); // Wieder rausnehmen, nur Kontrolle
                int basisK = value;
                int expoE = publicKey.getE();
                int modG = publicKey.getG();
                int result = (int) ((Math.pow(basisK, expoE)) % modG);
                if (!toNewFile) {
                    Konsole.printText(Integer.toString(result));
                } else {
                    bw.write(Integer.toString(result));
                    bw.newLine();
                }
            }
            bw.close();
            br.close();
        } catch (FileNotFoundException e) {
            Konsole.printText("Fehler: Unter dem angegebenen Dateipfad konnte keine Datei gefunden werden.");
        } catch (IOException e) {
            Konsole.printText("Fehler: Problem beim Lesen oder Schreiben der Datei");
        }
    }


    public static void decrypt(String filePath, PrivateKey privateKey, boolean toNewFile) {
        try {
            File sourceFile = new File(filePath);
            File targetFile = new File("decryptedFile.txt");
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                Konsole.printText("Datei konnte nicht angelegt werden");
            }
            FileReader fr = new FileReader(sourceFile);
            BufferedReader br = new BufferedReader(fr);
            FileOutputStream fos = new FileOutputStream(targetFile);
            FileWriter fw = new FileWriter(targetFile); // Zieldatei wird hinterlegt
            BufferedWriter bw = new BufferedWriter(fw); // Writer wird vorbereitet
            String line = "";
            while( (line = br.readLine()) !=null )
            {
                // Int viel zu klein fuer exponentielle Berechnungen. Evtl. auch encrypt() anpassen
                BigInteger basisV = new BigInteger(line);
                BigInteger expoD = BigInteger.valueOf(privateKey.getD());
                BigInteger modG = BigInteger.valueOf(privateKey.getG());
                BigInteger resultBIG = basisV.modPow(expoD, modG);

                int result = resultBIG.intValue();

                char newCharacter = (char) result;

                if(!toNewFile) {
                    Konsole.printChar(newCharacter);
                } else {
                    bw.write(newCharacter);
                }
            }
            bw.close();
            br.close();
        } catch (FileNotFoundException e) {
            Konsole.printText("Fehler: Datei nicht gefunden");
        } catch (IOException e) {
            Konsole.printText("Fehler: Problem beim Lesen oder Schreiben der Datei");
        }
    }
}
