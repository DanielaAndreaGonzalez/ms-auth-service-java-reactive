package co.com.bancolombia.model.session;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Session {
    private final UUID sessionId;
    private final UUID userId;
    private final Instant createdAt;

    private Session(UUID sessionId, UUID userId, Instant createdAt){
        this.sessionId = Objects.requireNonNull(sessionId, "MALFORMED_REQUEST");
        this.userId = Objects.requireNonNull(userId, "MALFORMED_REQUEST");
        this.createdAt = Objects.requireNonNull(createdAt, "MALFORMED_REQUEST");
    }

    public static Session createForUser(UUID userId){
        return new Session(UUID.randomUUID(), userId, Instant.now());
    }

    public UUID sessionId() {return sessionId;}
    public UUID userId() {return userId;}
    public Instant createdAt() {return createdAt;}

}
