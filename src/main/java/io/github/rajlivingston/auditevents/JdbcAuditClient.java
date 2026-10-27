package io.github.rajlivingston.auditevents;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

/** Synchronous JDBC writer. The host application owns the DataSource lifecycle. */
public final class JdbcAuditClient implements AuditClient {
    private static final String INSERT_SQL = """
            INSERT INTO audit_event (user_name, action, target, occurred_at, ip_address, duration_ms)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private final DataSource dataSource;

    public JdbcAuditClient(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "dataSource");
    }

    @Override
    public void record(AuditEvent event) {
        Objects.requireNonNull(event, "event");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, event.user());
            statement.setString(2, event.action());
            statement.setString(3, event.target());
            statement.setTimestamp(4, Timestamp.from(event.occurredAt()));
            statement.setString(5, event.ipAddress());
            if (event.durationMs() == null) statement.setObject(6, null);
            else statement.setLong(6, event.durationMs());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new AuditWriteException("Could not persist activity log entry", e);
        }
    }
}
