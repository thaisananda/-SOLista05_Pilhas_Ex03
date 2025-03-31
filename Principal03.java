package Lista05 ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

import Controller.Atleta;

public class Principal03 {
    
	public static void main(String[] args) {
        final int TOTAL_ATLETAS = 25;
        Semaphore semaforoTiro = new Semaphore(5);
        ArrayList<Atleta> atletas = new ArrayList<>();

        // Cria e inicia as threads
        for (int i = 1; i <= TOTAL_ATLETAS; i++) {
            Atleta a = new Atleta(i, semaforoTiro);
            atletas.add(a);
            a.start();
        }

        // Espera todos terminarem
        for (Atleta a : atletas) {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Ordenar por pontuação total
        Collections.sort(atletas, Comparator.comparingInt(Atleta::getPontuacaoTotal).reversed());

        // Exibe ranking final
        System.out.printf("%-8s %-12s %-12s %-15s %-12s\n", "Posição", "Atleta", "Tempo(s)", "Pontos Tiro", "Total");
        int posicao = 1;
        for (Atleta a : atletas) {
            System.out.printf("%-8d %-12d %-12.2f %-15d %-12d\n",
                    posicao++, a.getIdAtleta(), a.getTempoTotal() / 1000, a.getPontosTiro(), a.getPontuacaoTotal());
        }
    }
}
