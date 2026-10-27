# Audit Events

Framework-neutral Java 17 library that stores the six activity-log values shown in the UI: user, action, target, time, IP address, and duration. The application supplies a JDBC `DataSource`; this library does not require Spring Boot.

## Database setup

Apply `src/main/resources/db/migration/V1__create_audit_event.sql` with the application's migration tool. PostgreSQL adds an internal identity key for each row; the API does not require the caller to provide it.

## Use from a Java backend

```java
import io.github.rajlivingston.auditevents.AuditClient;
import io.github.rajlivingston.auditevents.AuditEvent;
import io.github.rajlivingston.auditevents.JdbcAuditClient;

AuditClient audit = new JdbcAuditClient(applicationDataSource);

audit.record(new AuditEvent(
    "Anup Kannan",
    "Report download",
    "Quarterly Report.pdf",
    null,                 // defaults to the current UTC instant
    clientIpAddress,      // determine this in the backend
    1250L                 // elapsed milliseconds; null if not applicable
));
```

The web frontend sends its request to the application's HTTP API. The backend should determine the authenticated user's display name and client IP, perform the requested operation, measure duration if needed, and then call `record(...)`. Do not trust a user name or IP address supplied directly by the browser. Each action is a string, so the library can store any action name without an enum update. Database write failures throw `AuditWriteException`.

## Build

```shell
mvn package
```

The resulting jar is `target/auditevents-0.1.0-SNAPSHOT.jar`. Update the version, project URL, and publishing metadata before releasing it.
