package cz.ondra.gamehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

import cz.ondra.gamehub.util.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing
public class PersitenceConfig {

    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
