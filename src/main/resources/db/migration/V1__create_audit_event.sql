CREATE TABLE audit_event (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_name   VARCHAR(255) NOT NULL,
    action      VARCHAR(120) NOT NULL,
    target      VARCHAR(500) NOT NULL DEFAULT '',
    occurred_at TIMESTAMPTZ NOT NULL,
    ip_address  INET,
    duration_ms BIGINT CHECK (duration_ms IS NULL OR duration_ms >= 0)
);

CREATE INDEX idx_audit_event_occurred_at ON audit_event (occurred_at DESC);
CREATE INDEX idx_audit_event_user_time ON audit_event (user_name, occurred_at DESC);
CREATE INDEX idx_audit_event_action_time ON audit_event (action, occurred_at DESC);
