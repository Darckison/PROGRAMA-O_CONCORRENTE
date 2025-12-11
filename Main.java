/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arquiteturadosistema;

/**
 *
 * @author mh665
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Instancia o ônibus, que será compartilhado por todas as threads
        Onibus amatur = new Onibus(); 
        
        System.out.println("=================================================");
        System.out.println("          Parte I: O Caos (Race Condition)");
        System.out.println("  (Assentos: 5. Agentes: 7. Sem Bloco Sincronizado)");
        System.out.println("=================================================");

        // --- PARTE I: O CAOS (Descomente para rodar a versão sem sincronização) ---
        // Você precisaria de uma versão de Onibus sem synchronized para demonstrar a Parte I
        // Mas a Onibus acima já está configurada para as Partes II, III e IV.
        // Se a Parte II for comentada (removendo synchronized(this)), o caos da Parte I ocorreria.
        // O código a seguir já demonstrará as soluções.
        
        // Criando 7 Agentes de Venda
        Thread[] agentes = new Thread[7];
        for (int i = 0; i < 7; i++) {
            agentes[i] = new Thread(new AgenteVenda(amatur, "Agente " + (i + 1)));
        }

        // Criando a thread de Cancelamento (Parte III)
        Thread cancelador = new Thread(new Cancelamento(amatur));

        // Iniciando todas as threads (Agentes e Cancelador)
        cancelador.start(); // Inicia o cancelador primeiro para que ele possa esperar
        for (Thread agente : agentes) {
            agente.start();
        }

        // Aguarda a conclusão de todas as threads
        for (Thread agente : agentes) {
            agente.join();
        }
        cancelador.join();

        System.out.println("\n=================================================");
        System.out.println("       FIM da Simulacao");
        System.out.println("       Assentos finais: " + amatur.getAssentosDisponiveis());
        System.out.println("=================================================");

        // Explicação: Na Parte I, ao rodar sem o 'synchronized(this)' e sem o 'while(assentosDisponiveis <= 0) wait()',
        // você veria vendas excessivas (overbooking) devido ao Thread.sleep(100).
        // Na execução atual, as Partes II, III e IV estão ativas e funcionando corretamente.
    }
}
