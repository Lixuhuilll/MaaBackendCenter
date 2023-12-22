package plus.maa.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.command.annotation.CommandScan;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author AnselYuki
 */
@Slf4j
@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableMethodSecurity
@CommandScan("plus.maa.backend.command")
public class MainApplication {

    private static final AtomicBoolean RUN = new AtomicBoolean(true);

    public static void main(String[] args) {
        while (RUN.compareAndSet(true, false)) {

            SpringApplication.run(MainApplication.class, args);
            if (RUN.get()) {
                log.info("Restarting...");
            }
        }
    }

    public static void restart() {
        if (RUN.compareAndSet(false, true)) {
            // Close Spring Shell And Spring Context
            throw new ExitRequest();
        }
    }
}
