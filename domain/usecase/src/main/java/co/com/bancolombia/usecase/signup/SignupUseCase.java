package co.com.bancolombia.usecase.signup;

import co.com.bancolombia.model.gateways.UsersRepository;
import co.com.bancolombia.model.shared.Password;
import co.com.bancolombia.model.shared.common.crq.Command;
import co.com.bancolombia.model.shared.common.crq.ContextData;
import co.com.bancolombia.model.user.Email;
import co.com.bancolombia.model.user.User;

public class SignupUseCase {

    private final UsersRepository users;

    public SignupUseCase(UsersRepository users){
        this.users = users;
    }

    public void execute(Command<SignupPayLoad, ContextData> cmd){
        var emailRaw    = cmd.payload().email();
        var passwordRaw = cmd.payload().password();

        Email email = new Email(emailRaw);
        Password password = new Password(passwordRaw);


        users.findByEmail(email.value())
                .ifPresent(u -> {throw new IllegalArgumentException("EMAIL_ALREADY_EXISTS");});

        users.save(new User(email, password));
    }
}
