package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCarro;

public class Estacionamento {

	public static void main(String[] args) {
		
		int permissao = 3;
		Semaphore semaforo = new Semaphore(permissao);
		
		for (int idCarro = 0 ; idCarro < 10 ; idCarro++) { // quantidade de carros
			ThreadCarro tCarro = new ThreadCarro(idCarro, semaforo);
			tCarro.start();
		}
	}
}
