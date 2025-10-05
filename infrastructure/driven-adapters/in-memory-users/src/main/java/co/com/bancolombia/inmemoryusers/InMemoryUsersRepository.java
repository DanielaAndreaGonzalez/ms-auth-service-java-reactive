package co.com.bancolombia.inmemoryusers;

import co.com.bancolombia.model.user.gateway.UsersRepository;
import co.com.bancolombia.model.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryUsersRepository implements UsersRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public Optional<User> findByEmail(String email) {
        if ( email == null ) return Optional.empty();
        return users.stream()
                .filter( u -> u.email().value().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }
}
