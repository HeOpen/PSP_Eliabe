package ut03.ej13;

import static ut03.ej13.Main.faker;

public class Camarero extends Thread{

    private String nombre;
    private Barra barraCompartida;
    private long tiempoPreparoPlato;

    public Camarero(String nombre, Barra barraCompartida, long tiempoPreparoPlato) {
        this.nombre = nombre;
        this.barraCompartida = barraCompartida;
        this.tiempoPreparoPlato = tiempoPreparoPlato;
    }

    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                System.out.printf("El camarero %s va a buscar un plato \n", this.nombre);
                String plato = barraCompartida.tomarPlato();

                System.out.printf("El camarero %s ha servido: %s \n", this.nombre, plato);
                Thread.sleep(tiempoPreparoPlato);
            }
        } catch (InterruptedException e) {
            System.out.printf("El camarero %s fue interrumpido...\n", this.nombre);
        }
    }




}
