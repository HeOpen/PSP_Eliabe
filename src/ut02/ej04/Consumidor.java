package ut02.ej04;

import java.io.*;

public class Consumidor {

    public static String INPUT = "mensajes_ut02_04.txt";
    public static long SLEEP_TIME = 3000;

    static void main() {

    boolean continuar = true; // Flag

        while (continuar) {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT))) {

            String currentLine;
            String lastLine = "";

            // Logic to find the LAST line
            while ((currentLine = br.readLine()) != null) {
                lastLine = currentLine;
            }

            // Check if last line is FIN
            if ("FIN".equalsIgnoreCase(lastLine)) {
                continuar = false; // Set flag to false
                System.out.println("Received FIN. Stopping.");
            } else if (!lastLine.isEmpty()) {
                // Show line and sleep [cite: 1081]
                System.out.println("Leido: " + lastLine);
                Thread.sleep(SLEEP_TIME);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found yet, waiting...");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
}