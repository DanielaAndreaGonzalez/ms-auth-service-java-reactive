package co.com.bancolombia.usecase.singin;

import co.com.bancolombia.model.gateways.SessionsRepository;
import co.com.bancolombia.model.gateways.UsersRepository;
import co.com.bancolombia.model.session.Session;

public class SigninUseCase {
    private final UsersRepository users;
    private final SessionsRepository sessions;

    public SigninUseCase(UsersRepository users, SessionsRepository sessions){
        this.users = users;
        this.sessions = sessions;
    }

    public String execute(String emailRaw, String passwordRaw){
        var user = users.findByEmail(emailRaw)
                .orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));
        if(!user.password().value().equals(passwordRaw)){
            throw new IllegalArgumentException("INVALID_CREDENTIALS");
        }
        var session = Session.createForUser(user.id());
        sessions.save(session);
        return session.sessionId().toString();
    }
    
}
