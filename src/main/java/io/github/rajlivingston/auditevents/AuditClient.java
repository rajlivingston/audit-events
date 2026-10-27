package io.github.rajlivingston.auditevents;

/** Writes activity log entries to the configured PostgreSQL database. */
@FunctionalInterface
public interface AuditClient {
    void record(AuditEvent event);
}
