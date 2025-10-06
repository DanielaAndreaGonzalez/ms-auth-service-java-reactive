package co.com.bancolombia.model.user.gateway;

import co.com.bancolombia.model.user.model.User;

import java.util.Optional;

public interface UsersRepository {
    Optional<User> findByEmail (String email);
    User save(User user);
}
