package ut03.ej13;

import static ut03.ej13.Main.faker;

public class Cocinero extends Thread {
    //Atributos
    private String nombre;
    private Barra barraCompartida;
    private long tiempoPreparoPlato;

    //Constructor
    public Cocinero(String nombre, Barra barraCompartida, long tiempoPreparoPlato) {
        this.nombre = nombre;
        this.barraCompartida = barraCompartida;
        this.tiempoPreparoPlato = tiempoPreparoPlato;
    }

    //Metodos
    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                String plato = cocinaPlato();

                barraCompartida.ponerPlato(plato);

                Thread.sleep(tiempoPreparoPlato);
            }
        } catch (InterruptedException e) {
            System.out.printf("El cocinero %s ha sido interrumpido y termina. \n", nombre);
        }
    }

    public String cocinaPlato(){
        String plato = faker.food().sushi();
        System.out.printf("El cocinero %s ha cocinado %s\n", nombre, plato);
        return plato;
    }

}