package co.com.bancolombia.usecase.signup;

import co.com.bancolombia.model.user.gateway.UsersRepository;
import co.com.bancolombia.model.user.value.Password;
import co.com.bancolombia.model.shared.common.crq.Command;
import co.com.bancolombia.model.shared.common.crq.ContextData;
import co.com.bancolombia.model.shared.common.crq.XrequestId;
import co.com.bancolombia.model.user.value.Email;
import co.com.bancolombia.model.user.model.User;
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
        var payload = new SignupPayLoad("kevin@example.com", "supersegura");
        var context = new ContextData("msg123", XrequestId.generate());
        var cmd = new Command<>(payload, context);
        assertDoesNotThrow(() -> useCase.execute(cmd));
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
        var payload = new SignupPayLoad("kevin@example.com", "otroPass123");
        var context = new ContextData("msg123", XrequestId.generate());
        var cmd = new Command<>(payload, context);
        var ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(cmd));
        assertEquals("EMAIL_ALREADY_EXISTS", ex.getMessage());
    }

    @Test
    void signup_email_invalido() {
        var payload = new SignupPayLoad("kevin@bad", "supersegura");
        var context = new ContextData("msg123", XrequestId.generate());
        var cmd = new Command<>(payload, context);
        var ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(cmd));
        assertEquals("INVALID_EMAIL_FORMAT", ex.getMessage());
    }

    @Test
    void signup_password_debil() {
        var payload = new SignupPayLoad("kevin@example.com", "1234567");
        var context = new ContextData("msg123", XrequestId.generate());
        var cmd = new Command<>(payload, context);
        var ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(cmd));
        assertEquals("WEAK_PASSWORD", ex.getMessage());
    }



}


