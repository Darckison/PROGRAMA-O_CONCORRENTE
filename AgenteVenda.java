/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arquiteturadosistema;

/**
 *
 * @author mh665
 */
public class AgenteVenda implements Runnable {
    private final Onibus onibus;
    private final String nome;

    public AgenteVenda(Onibus onibus, String nome) {
        this.onibus = onibus;
        this.nome = nome;
    }

    @Override
    public void run() {
        // Todos os agentes tentarão reservar um assento
        onibus.reservarAssento(nome);
    }
}
