package co.com.bancolombia.model.user.retrieveuser.gateway;

import co.com.bancolombia.model.shared.common.crq.ContextData;
import co.com.bancolombia.model.shared.common.crq.Query;
import co.com.bancolombia.model.user.model.User;

import java.util.Optional;

public interface UsersReader {
    Optional<User> findByEmail(Query<String, ContextData> query);
}
