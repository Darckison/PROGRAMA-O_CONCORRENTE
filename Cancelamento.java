/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arquiteturadosistema;

/**
 *
 * @author mh665
 */
public class Cancelamento implements Runnable {
    private final Onibus onibus;

    public Cancelamento(Onibus onibus) {
        this.onibus = onibus;
    }

    @Override
    public void run() {
        try {
            // Espera 3 segundos para simular que o cancelamento ocorre após um tempo
            Thread.sleep(3000); 
            onibus.cancelarPassagem("Cancelador Automatico");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
