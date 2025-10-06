package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.session.Session;

public interface SessionsRepository {
    Session save(Session session);
}
