/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arquiteturadosistema;

/**
 *
 * @author mh665
 */
import java.util.concurrent.Semaphore;
public class Onibus {
    // Parte I: Assentos iniciais
    private int assentosDisponiveis = 5;
    
    // Parte IV: Semáforo para controlar conexões simultâneas
    private final Semaphore limiteConexao = new Semaphore(3); // Apenas 3 agentes podem acessar simultaneamente

    /**
     * Parte I & II: Simula a tentativa de reserva de assento.
     * Esta versão usa bloco synchronized (Parte II) e lógica wait/notify (Parte III).
     */
    public void reservarAssento(String agente) {
        // Parte IV: Adquire permissão para entrar na "seção crítica" de conexão (não de assento)
        try {
            System.out.println("🚦 " + agente + " esta aguardando permissao para CONEXAO. (Permissoes restantes: " + limiteConexao.availablePermits() + ")");
            limiteConexao.acquire();
            System.out.println("🟢 " + agente + " adquiriu permissao de CONEXAO. Tentando reservar assento.");

            // Bloco Sincronizado (synchronized(this)): Garante que APENAS uma thread
            // por vez execute o código DENTRO deste bloco. (Parte II)
            synchronized (this) {
                // Parte III: Lógica de espera (wait) se o ônibus estiver lotado
                while (assentosDisponiveis <= 0) {
                    try {
                        System.out.println("🛑 " + agente + ": Onibus lotado. Aguardando cancelamento (wait()).");
                        wait(); // Libera o lock e espera ser notificado (notify/notifyAll)
                        System.out.println("🔔 " + agente + ": Fui notificado! Tentando reservar novamente.");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // *** Seção Crítica da Parte I (Race Condition) ***
                if (assentosDisponiveis > 0) {
                    try {
                        // Simula a latência de rede/sistema (Parte I)
                        Thread.sleep(100); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                    assentosDisponiveis--;
                    System.out.println("✅ " + agente + " reservou! Assentos restantes: " + assentosDisponiveis);
                }
            } // Fim do Bloco Synchronized

        } catch (InterruptedException e) {
            System.out.println("❌ " + agente + " foi interrompido.");
            Thread.currentThread().interrupt();
        } finally {
            // Parte IV: Libera a permissão de conexão
            limiteConexao.release();
            System.out.println("🔓 " + agente + " liberou permissao de CONEXAO. (Permissoes restantes: " + limiteConexao.availablePermits() + ")");
        }
    }

    /**
     * Parte III: Simula o cancelamento de uma passagem.
     */
    public void cancelarPassagem(String agente) {
        // wait() e notify() DEVEM estar em um bloco sincronizado
        synchronized (this) {
            assentosDisponiveis++;
            System.out.println("\n📣 " + agente + " CANCELOU uma passagem. Assentos restantes: " + assentosDisponiveis);
            // Acorda TODAS as threads que estavam em espera (wait())
            notifyAll(); 
            System.out.println("📢 Notificando todos os agentes em espera...");
        }
    }

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }
}