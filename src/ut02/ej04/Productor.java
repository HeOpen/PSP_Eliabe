package ut02.ej04;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Productor {

    public static String OUTPUT = "mensajes.txt";
    public static long SLEEP_TIME = 1000;

    static void main() {
        for(int i = 1; i <= 20; i++) {
               try(BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT, true))){

                //Escribe fecha y flush
                bw.write(LocalDateTime.now().toString()+ " ");
                bw.flush();
                //Duerme
                Thread.sleep(SLEEP_TIME);

                bw.write("Iteracion: "+i);
                bw.newLine();
                bw.flush();

          //Estaria bien añadir mas de una exception en un mismo catch?
          //Al parecer, si
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
               //Despues de la vuelta 20, escribe FIN
               try(BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT, true))){
                   bw.write("FIN");
                   bw.newLine();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }

            System.out.println("Fin del programa");

        }

}
}