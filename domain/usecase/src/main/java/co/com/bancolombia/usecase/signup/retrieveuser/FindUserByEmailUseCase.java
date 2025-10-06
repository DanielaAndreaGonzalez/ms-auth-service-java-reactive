package co.com.bancolombia.usecase.signup.retrieveuser;

import co.com.bancolombia.model.user.gateway.UsersRepository;
import co.com.bancolombia.model.shared.common.crq.ContextData;
import co.com.bancolombia.model.shared.common.crq.Query;
import co.com.bancolombia.model.user.model.User;

public class FindUserByEmailUseCase {
    private final UsersRepository users;

    public FindUserByEmailUseCase(UsersRepository users) { this.users = users; }

    public java.util.Optional<User> execute(Query<FindUserByEmailPayload, ContextData> qry) {

        var emailRaw = qry.payload().email();
        var ctx = qry.context(); //
        return users.findByEmail(emailRaw);
    }
}
