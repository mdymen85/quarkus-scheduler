package org.init;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.config.Config;

@ApplicationScoped
@AllArgsConstructor
public class ConfigClass {

    private final Config config;

    //Pega as variaveis do arquivo de configuração application.yml
    public String getThreadName() {
        String threadName = config.getValue("application.test.name", String.class);
        return threadName == null ? "Executor-Thread-" : threadName ;
    }

    public Integer getThreads() {
        Integer threads = config.getValue("application.test.threads", Integer.class);
        return threads == null ? 1 : threads;
    }
}
