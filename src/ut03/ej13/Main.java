package ut03.ej13;

/*
Queremos simular el funcionamiento de un restaurante usando el modelo productor–
consumidor en Java.
• Los cocineros serán productores: preparan platos y los colocan en una barra.
• Los camareros serán consumidores: toman platos de la barra y los sirven.
• La barra será un buffer con capacidad limitada, compartido por todos los hilos.
Requisitos
• Clase Barra (buffer compartido)
o Con capacidad máxima, que recibirá por constructor. Ejemplo: 5 platos.
o Implementar usando una cola que almacene objetos de tipo String.
o Métodos
▪ public synchronized void colocarPlato(String plato)
• Si la barra está llena, el cocinero espera.
• Si no está llena, coloca el plato, y avisa a camareros.
▪ public synchronized String tomarPlato()
• Si la barra está vacía, el camarero espera
• Si hay platos, coge el plato, y avisa a los cocineros.
▪ public synchronized List<String> getPreparados()
• Devuelve una COPIA de la cola disponible.
• Clase Cocinero
o Extiende Thread o implementa Runnable.
o Recibe por constructor un nombre para identificarlo, la barra compartida, y el
tiempo que tarda en preparar cada plato.
o El método run se ejecuta indefinidamente, será interrumpido cuando el
programa vaya a terminar. Cuando el hilo se interrumpa, termina
inmediatamente, aunque tenga platos a medias.
o Genera platos aleatorios cada cierto tiempo. Para esto:
▪ Los platos aleatorios se pueden generar con DataFaker, que tiene un
provider para comidas.
▪ Los platos que genera los añade a la barra.
▪ Una vez añadido el plato, se duerme un tiempo (el tiempo de
preparación de platos que se recibe en constructor).
▪ Muestra una traza cada vez que prepara un plato, justo antes de
añadirlo a la barra, del tipo “El cocinero <nombre> ha cocinado
<plato> y va a colocarlo en la barra.”.
• Clase Camarero
o Extiende Thread o implementa Runnable.
o Recibe por constructor un nombre para identificarlo, la barra compartida, y
cada cuanto tiempo pasa por la barra para coger un plato que debe servir.
o El método run se ejecuta indefinidamente, será interrumpido cuando el
programa vaya a terminar. Cuando el hilo se interrumpa, termina
inmediatamente, aunque aún haya platos en la barra.
o Una vez servidor el plato, se duerme un tiempo (el tiempo recibido por
constructor).
o Muestra una traza antes de retirar un plato, justo antes de retirarlo de la
barra, del tipo “El camarero <nombre> va a buscar un plato de la barra.”.
• Programa principal
o Crea una barra
o Crea e inicia varios cocineros
o Crea e inicia varios camareros
o Espera un tiempo determinado (unos cuantos segundos). Mientras espera,
cada medio segundo mostrará el contenido de la barra. Obtener el contenido
de la barra usará el método getPreparados.
o Una vez pasado el tiempo, parará a los cocineros, usando interrupt(), y
esperará a que acaben.
o Esperará a que los camareros hayan servido todos los platos que quedaran en
la barra. Mientras espera, cada medio segundo mostrará el contenido de la
barra, para ver si se va vaciando.
o Cuando la barra esté vacía, parará a los camareros usando interrupt(), y
esperará a que terminen.
o Cuando los camareros también hayan terminado, el programa terminará.
 */

import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final Faker faker = new Faker();

    public static final long TIEMPO_SIMULACION = 10000;
    public static final long TIEMPO_MONITOREO = 500;
    private static final long TIEMPO_ESPERA_COCINEROS = 500;
    private static final long TIEMPO_ESPERA_CAMAREROS = 1000;
    private static final long TIEMPO_COCINADO_TOTAL = 5_000;

    public static final int NUM_COCINEROS = 10;
    public static final int NUM_CAMAREROS = 5;

    public static final int MAX_PLATOS_BARRA = 7;


    public static void main(String[] args) throws InterruptedException {

        Barra barraCompartida = new Barra(MAX_PLATOS_BARRA);

        List<Cocinero> cocineros = new ArrayList<>();
        List<Camarero> camareros = new ArrayList<>();

        // crear y lanzar cocineros
        for (int i = 0; i < NUM_COCINEROS; i++) {
            Cocinero c = new Cocinero("Cocinero " + i, barraCompartida, TIEMPO_ESPERA_COCINEROS);
            c.start();
            cocineros.add(c);
        }

        // crear y lanzar camareros
        for (int i = 0; i < NUM_CAMAREROS; i++) {
            Camarero c = new Camarero("Camarero " + i, barraCompartida, TIEMPO_ESPERA_CAMAREROS);
            c.start();
            camareros.add(c);
        }

        long tiempoTranscurrido = 0;
        long momentoInicio = System.currentTimeMillis();

        while(tiempoTranscurrido < TIEMPO_COCINADO_TOTAL){
            System.out.printf("Contenido de la barra: %s\n", barraCompartida.getPlatos());
            Thread.sleep(TIEMPO_MONITOREO);
            tiempoTranscurrido = System.currentTimeMillis() - momentoInicio;
        }

        //Para a los cocineros
        for(Cocinero c : cocineros){
            c.interrupt();
        }
        for(Cocinero c : cocineros){
            c.join();
        }

        // Esperar a que la barra se vacie
        while(!barraCompartida.isEmpty()){
            System.out.printf("Vaciando barra... quedan: %s \n", barraCompartida.getPlatos());
            Thread.sleep(TIEMPO_MONITOREO);
        }

        for(Camarero c : camareros){
            c.interrupt();
        }
        for(Camarero c : camareros){
            c.join();
        }

        System.out.println("Y cada uno se fue a su casa a descansar (: ");


    }
}
