/* DESCRIÇÃO DO PROBLEMA
 * 10 carros estão próximos de um estacionamento (1 a 2 km)
 * Cada carro roda a uma velocidade média de 100 m/s
 * O estacionamento possui apenas 3 vagas
 * Os 3 primeiros a chegar estacionam na vaga e permanecem entre 1 a 3s
 * Se o estacionamento estiver cheio e não ter vaga, os próximos aguardam
 * em uma fila pela ordem de chegada
 * Quando um carrp sai, o próximo da fila entra e estaciona
   É importante saber a ordem de chegada e de saída */

package controller;

import java.util.concurrent.Semaphore;

public class ThreadCarro extends Thread {

	private int idCarro;
	private static int posChegada; // static já inicia em pos = 0
	private static int posSaida; // static já inicia em pos = 0

	private Semaphore semaforo;

	public ThreadCarro(int idCarro, Semaphore semaforo) {
		this.idCarro = idCarro;
		this.semaforo = semaforo;
	}

	// Executa o que estiver no método run()
	@Override
	public void run() {
		carroAndando(); // Paralelizavel (sem restrições)

		// INÍCIO DA SEÇÃO CRÍTICA - SEMÁFORO ACQUIRE
		try {
			semaforo.acquire();
			carroEstacionado(); // Tem apenas 3 vagas
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally { // o bloco roda independente do try ou catch ser executado
			semaforo.release(); // Libera o semaforo
		}
		// FIM DA SEÇÃO CRÍTICA - SEMÁFORO RELEASE

		carroSaindo(); // Paralelizavel (sem restrições)
	}

	private void carroAndando() {
		// A distância dos carros está entre 1000 a 2000 metros
		// Portanto gera uma distância aleatória entre 0 e 1000 e soma com 1000
		int distanciaFinal = (int) ((Math.random() * 1001) + 1000);
		System.out.println("Distância para o carro #"+idCarro+" chegar ao estacionamento: "+distanciaFinal+ "m");
		int distanciaPercorrida = 0;
		int deslocamento = 100;
		int tempo = 500; // sleep que vai simular o deslocamento

		while (distanciaPercorrida < distanciaFinal) {
			distanciaPercorrida += deslocamento;

			try {
				sleep(tempo); // simulação da velocidade, percorre e para
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Carro #" + idCarro + " percorreu " + distanciaPercorrida + " m");
		}
		posChegada++;
		System.err.println("Carro #" + idCarro + " foi o " + posChegada + " a chegar");
	}

	private void carroEstacionado() {
		System.err.println("Carro #" + idCarro + " estacionou");
		// tempo parado de 1 a 3 segundos
		int tempoParado = (int) ((Math.random() * 1001) + 2000);

		try {
			sleep(tempoParado);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void carroSaindo() {
		posSaida++;
		System.err.println("Carro #" + idCarro + " foi o " + posSaida + " a sair");
	}

}
