package co.com.bancolombia.usecase.signin;

import co.com.bancolombia.model.gateways.SessionsRepository;
import co.com.bancolombia.model.user.gateway.UsersRepository;
import co.com.bancolombia.model.session.Session;
import co.com.bancolombia.model.user.value.Password;
import co.com.bancolombia.model.user.value.Email;
import co.com.bancolombia.model.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import static org.junit.jupiter.api.Assertions.*;

public class SigninUseCaseTest {

    private UsersRepository users;
    private SessionsRepository sessions;
    private SigninUseCase useCase;

    @BeforeEach
    void setup() {
        users = new InMemoryUsers();
        sessions = new InMemorySessions();
        useCase = new SigninUseCase(users, sessions);
    }

    @Test
    void signin_feliz() {
        var u = new User(new Email("kevin@example.com"), new Password("supersegura"));
        users.save(u);
        String sessionId = useCase.execute("kevin@example.com", "supersegura");
        assertNotNull(sessionId);
        assertFalse(sessionId.isBlank());
    }

    @Test
    void signin_usuario_no_existe() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("noexiste@example.com", "x"));
        assertEquals("USER_NOT_FOUND", ex.getMessage());
    }

    @Test
    void signin_credenciales_invalidas() {
        users.save(new User(new Email("kevin@example.com"), new Password("supersegura")));
        var ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("kevin@example.com", "mala"));
        assertEquals("INVALID_CREDENTIALS", ex.getMessage());
    }



    static class InMemoryUsers implements UsersRepository {
        private final List<User> data = new ArrayList<>();
        @Override public Optional<User> findByEmail(String email) {
            return data.stream().filter(u -> u.email().value().equalsIgnoreCase(email)).findFirst();
        }

        @Override
        public User save(User user) {
            data.add(user); return user;
        }


    }

    static class InMemorySessions implements SessionsRepository {
        private final Map<UUID, Session> store = new ConcurrentHashMap<>();
        @Override public Session save(Session session) { store.put(session.sessionId(), session); return session; }
    }

}
