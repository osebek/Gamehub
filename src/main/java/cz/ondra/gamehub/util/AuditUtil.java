package cz.ondra.gamehub.util;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

public class AuditUtil {

    private static AuditorAware<UUID> auditorAware = new AuditorAwareImpl();

    public static Optional<UUID> getCurrentAuditor() {
        return auditorAware.getCurrentAuditor();
    }
}
