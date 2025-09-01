package cz.ondra.gamehub.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@TestConfiguration
public class TestAuditingConfig {

    public static final UUID TEST_AUDITOR = UUID.randomUUID();

    @Bean
    @Primary
    public AuditorAware<UUID> testAuditProvider() {
        return () -> Optional.of(TEST_AUDITOR);
    }
}
