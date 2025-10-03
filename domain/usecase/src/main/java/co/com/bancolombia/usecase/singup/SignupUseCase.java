package co.com.bancolombia.usecase.singup;

import co.com.bancolombia.model.gateways.UsersRepository;
import co.com.bancolombia.model.shared.Password;
import co.com.bancolombia.model.user.Email;
import co.com.bancolombia.model.user.User;
import lombok.val;

public class SignupUseCase {

    private final UsersRepository users;

    public SignupUseCase(UsersRepository users){
        this.users = users;
    }

    public void execute(String emailRaw, String passwordRaw){
        Email email = new Email(emailRaw);
        Password password = new Password(passwordRaw);

        users.findByEmail(email.value())
                .ifPresent(u -> {throw new IllegalArgumentException("EMAIL_ALREADY_EXISTS");});

        users.save(new User(email, password));

    }
}
