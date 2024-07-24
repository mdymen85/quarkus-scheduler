package org.init;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.init.repository.ObjectRepository;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecutorService {

    private final ConfigClass configClass;
    private final ObjectRepository objectRepository;

    public void execute() throws InterruptedException {

        var iterations = 50;

        ///cria um objeto apenas para ter o calculo de quanto foi a demora do processo
        //eh util para medir os tempos de performance do executor
        StopWatch executorStopWatch = new StopWatch("executorStopWatch");

        //o ExecutorService eh um objeto que se encarrega de executar trabalhos em threads
        //separadas. Por exemplo, neste caso ele pega a quantidade de threads que foram configuradas na classe
        //ConfigClass. Entao ao rodar o metodo ex.submit, vai pegar uma thread do pool de threads disponiveis.
        //Se eu configuro 3 threads, o processo dentro de ex.submit vai ser executado 3 vezes em paralelo
        //e quanto uma dessas threads se libere, um novo processo será executado.
        java.util.concurrent.ExecutorService ex = Executors.newFixedThreadPool(configClass.getThreads(), new ThreadFactory(configClass.getThreadName()));

        //Este é o contador das iterações, serve para segurar o processo do job sem matar essa thread que é
        //principal até que todas as iterações finalizem.
        CountDownLatch countDownLatch = new CountDownLatch(iterations);
        executorStopWatch.start();
        for (int i = 0; i < iterations; i++) {
            int finalI = i;

            ex.submit(() -> {
                this.process(countDownLatch, finalI);
            });
        }

        //Para segurar a thread principal ate que o executor service finalice de processar todos os "iterations"
        countDownLatch.await();

        //Para o relogio de calculo de quanto demorou tudo o processo.
        executorStopWatch.stop();

        log.info("time elapsed: {} seconds",executorStopWatch.getTime(TimeUnit.SECONDS));
    }

    private void process(CountDownLatch countDownLatch, int i) {
        try {
            this.objectRepository.process();
            Thread.sleep(1000); //apenas para teste
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info(Thread.currentThread().getName() + " " + i);
        countDownLatch.countDown();
    }

}
