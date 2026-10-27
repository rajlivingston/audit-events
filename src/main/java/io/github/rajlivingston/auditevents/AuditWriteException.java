package io.github.rajlivingston.auditevents;

/** Unchecked exception raised when an activity log entry cannot be written. */
public final class AuditWriteException extends RuntimeException {
    public AuditWriteException(String message, Throwable cause) { super(message, cause); }
}
