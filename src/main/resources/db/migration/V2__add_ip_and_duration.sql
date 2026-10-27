ALTER TABLE audit_event
    ADD COLUMN ip_address INET,
    ADD COLUMN duration_ms BIGINT;

ALTER TABLE audit_event
    ADD CONSTRAINT chk_audit_event_duration_nonnegative
    CHECK (duration_ms IS NULL OR duration_ms >= 0);
