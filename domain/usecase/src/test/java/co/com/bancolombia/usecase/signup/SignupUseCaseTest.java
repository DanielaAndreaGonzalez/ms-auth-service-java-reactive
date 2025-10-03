package co.com.bancolombia.usecase.signup;

import co.com.bancolombia.model.gateways.UsersRepository;
import co.com.bancolombia.model.shared.Password;
import co.com.bancolombia.model.user.Email;
import co.com.bancolombia.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

public class SignupUseCaseTest {

    private UsersRepository users;
    private SignupUseCase useCase;

    @BeforeEach
    void setup() {
        users = new InMemoryUsers();
        useCase = new SignupUseCase(users);
    }

    @Test
    void signup_feliz() {
        assertDoesNotThrow(() -> useCase.execute("kevin@example.com", "supersegura"));
        assertTrue(users.findByEmail("kevin@example.com").isPresent());
    }

    static class InMemoryUsers implements UsersRepository {
        private final List<User> data = new ArrayList<>();
        @Override public Optional<User> findByEmail(String email) {
            return data.stream().filter(u -> u.email().value().equalsIgnoreCase(email)).findFirst();
        }

        @Override
        public User save(User user) {
            data.add(user); return user;}
        }

    @Test
    void signup_email_duplicado() {
        users.save(new User(new Email("kevin@example.com"), new Password("supersegura")));
        var ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("kevin@example.com", "otroPass123"));
        assertEquals("EMAIL_ALREADY_EXISTS", ex.getMessage());
    }

    @Test
    void signup_email_invalido() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("kevin@bad", "supersegura"));
        assertEquals("INVALID_EMAIL_FORMAT", ex.getMessage());
    }

    @Test
    void signup_password_debil() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("kevin@example.com", "1234567"));
        assertEquals("WEAK_PASSWORD", ex.getMessage());
    }



}


