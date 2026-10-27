package io.github.rajlivingston.auditevents;

import java.time.Instant;
import java.util.Objects;

/** The six activity-log values displayed by the application. */
public record AuditEvent(
        String user,
        String action,
        String target,
        Instant occurredAt,
        String ipAddress,
        Long durationMs) {

    public AuditEvent {
        user = requireText(user, "user");
        action = requireText(action, "action");
        target = Objects.requireNonNullElse(target, "");
        occurredAt = Objects.requireNonNullElseGet(occurredAt, Instant::now);
        if (durationMs != null && durationMs < 0) {
            throw new IllegalArgumentException("durationMs must not be negative");
        }
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
