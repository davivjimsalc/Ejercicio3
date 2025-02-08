package com.project.controlaccesoparquemonitores;

public class Atraccion {
    private final String nombre; // nombre de la atraccion
    private boolean ocupada = false; // indica si la atraccion esta en uso

    // constructor que asigna el nombre de la atraccion
    public Atraccion(String nombre) {
        this.nombre = nombre;
    }

    // metodo sincronizado para que un visitante acceda
    public void acceder(Visitante visitante) {
        synchronized (this) {
            while (ocupada) {
                try {
                    wait(); // espera si la atraccion esta ocupada
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            ocupada = true; // marca la atraccion como ocupada
        }

        System.out.println("visitante " + visitante.getId() + " accediendo a la atraccion " + nombre);

        // simula el tiempo que el visitante pasa en la atraccion
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("visitante " + visitante.getId() + " ha salido de la atraccion " + nombre);

        synchronized (this) {
            ocupada = false; // libera la atraccion
            notify(); // notifica a otros visitantes que pueden acceder
        }
    }
}
