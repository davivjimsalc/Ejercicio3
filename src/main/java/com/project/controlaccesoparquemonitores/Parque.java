package com.project.controlaccesoparquemonitores;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Parque {
    private final Puerta[] puertas; // lista de puertas del parque
    private int codigoAcceso = 1; // codigo de acceso unico para cada visitante

    // constructor que crea las puertas del parque
    public Parque(int numPuertas) {
        puertas = new Puerta[numPuertas];
        for (int i = 0; i < numPuertas; i++) {
            puertas[i] = new Puerta(i + 1); // asigna un numero a cada puerta
        }
    }

    // inicia la simulacion con 10 visitantes
    public void iniciarSimulacion() {
        ExecutorService executor = Executors.newFixedThreadPool(10); // crea 10 hilos para los visitantes

        for (int i = 0; i < 10; i++) {
            final int visitanteId = i + 1;
            executor.execute(() -> {
                Visitante visitante = new Visitante(visitanteId, obtenerCodigoAcceso()); // crea visitante con id y codigo de acceso
                accederAlParque(visitante);
            });
        }

        executor.shutdown(); // cierra el ejecutor cuando termine
    }

    // metodo sincronizado para generar un codigo de acceso unico
    private int obtenerCodigoAcceso() {
        synchronized (this) {
            return codigoAcceso++;
        }
    }

    // intenta acceder al parque a traves de las puertas disponibles
    private void accederAlParque(Visitante visitante) {
        System.out.println("visitante " + visitante.getId() + " con codigo " + visitante.getCodigoAcceso() + " intentando acceder al parque.");
        for (Puerta puerta : puertas) {
            if (puerta.acceder(visitante)) {
                return; // si logra entrar, termina el metodo
            }
        }
        System.out.println("visitante " + visitante.getId() + " esta esperando en la cola."); // si no hay puertas disponibles
    }
}
