package Controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Atleta extends Thread {
    private static int colocacao = 0;
    private static final Object lock = new Object();

    private int id;
    private double tempoTotal;
    private int pontosTiro;
    private int pontosFinal;
    private Semaphore semaforoTiro;

    public Atleta(int id, Semaphore semaforoTiro) {
        this.id = id;
        this.semaforoTiro = semaforoTiro;
    }

    @Override
    public void run() {
        try {
            Random rand = new Random();

            // Corrida - 3km
            double velCorrida = 20 + rand.nextDouble() * 5; // 20 a 25 m/s
            double tempoCorrida = (3000 / velCorrida) * 1000;
            Thread.sleep((int) tempoCorrida);

            // Espera para atirar
            semaforoTiro.acquire();

            for (int i = 0; i < 3; i++) {
                int tempoTiro = 500 + rand.nextInt(2501); // 500 a 3000 ms
                Thread.sleep(tempoTiro);
                pontosTiro += rand.nextInt(11); // 0 a 10
            }

            semaforoTiro.release();

            // Ciclismo - 5km
            double velBike = 30 + rand.nextDouble() * 10; // 30 a 40 m/s
            double tempoBike = (5000 / velBike) * 1000;
            Thread.sleep((int) tempoBike);

            tempoTotal = tempoCorrida + tempoBike; // soma final

            synchronized (lock) {
                colocacao++;
                pontosFinal = 260 - (colocacao * 10); // 250, 240, 230, ..., 10
                if (pontosFinal < 10) pontosFinal = 10; // mínimo
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getIdAtleta() {
        return id;
    }

    public double getTempoTotal() {
        return tempoTotal;
    }

    public int getPontosTiro() {
        return pontosTiro;
    }

    public int getPontosFinal() {
        return pontosFinal;
    }

    public int getPontuacaoTotal() {
        return pontosFinal + pontosTiro;
    }
}
