package ut03.ej13;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Barra {

    //Atributos
    private int maxPlatos;
    private Queue<String> colaPlatos;

    //Constructor
    public Barra(int maxPlatos) {
        this.maxPlatos = maxPlatos;
        this.colaPlatos = new LinkedList<>();
    }

    //Metodos
    public synchronized void ponerPlato(String plato) throws InterruptedException {
        while (colaPlatos.size() == maxPlatos) {
            wait();
        }
        colaPlatos.add(plato);
        notifyAll();
    }

    public synchronized String tomarPlato() throws InterruptedException {
        //Si barra vacia, camarero espera
        //Si hay platos, toma y avisa a cocineros
        //notifyAll
        while(colaPlatos.isEmpty()){
            wait();
        }
        String plato = colaPlatos.remove();
        notifyAll();
        return plato;
    }

    public boolean isEmpty() {
        return colaPlatos.isEmpty();
    }

    public synchronized List<String> getPlatos(){
        return new ArrayList<>(colaPlatos); //Copia de la lista
    }

}
