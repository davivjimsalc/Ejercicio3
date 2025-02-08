package com.project.controlaccesoparquemonitores;

public class Puerta {
    private final int id; // identificador de la puerta
    private boolean ocupada = false; // indica si la puerta esta en uso

    // constructor que asigna el id de la puerta
    public Puerta(int id) {
        this.id = id;
    }

    // metodo sincronizado para que un visitante intente acceder
    public boolean acceder(Visitante visitante) {
        synchronized (this) {
            while (ocupada) {
                try {
                    wait(); // espera si la puerta esta ocupada
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            ocupada = true; // marca la puerta como ocupada
        }

        System.out.println("visitante " + visitante.getId() + " accediendo a traves de la puerta " + id);

        // simula el tiempo que tarda en acceder
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("visitante " + visitante.getId() + " ha accedido a la puerta " + id);

        synchronized (this) {
            ocupada = false; // libera la puerta
            notify(); // notifica a otros visitantes que pueden acceder
        }

        return true;
    }
}
