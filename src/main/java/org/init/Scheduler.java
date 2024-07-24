package org.init;

import io.quarkus.scheduler.Scheduled;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Scheduler {

    private final ExecutorService executorTestsService;

    private static Integer COUNTER = 0;

    //o job que roda a cada momento configurado
    @Scheduled(every="10s")
    void job() throws InterruptedException {

        executorTestsService.execute();

        log.info("Counter {}", COUNTER++);

    }

}
