package co.com.bancolombia.sessions.memory;

import co.com.bancolombia.model.gateways.SessionsRepository;
import co.com.bancolombia.model.session.Session;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemorySessionsRepository implements SessionsRepository {
    private final Map<UUID, Session> store = new ConcurrentHashMap<>();

    @Override
    public Session save(Session session) {
        store.put(session.sessionId(), session);
        return null;
    }
}
