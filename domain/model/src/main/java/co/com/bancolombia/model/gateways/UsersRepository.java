package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.user.User;

import java.util.Optional;

public interface UsersRepository {
    Optional<User> findByEmail (String email);
    User save(User user);
}
