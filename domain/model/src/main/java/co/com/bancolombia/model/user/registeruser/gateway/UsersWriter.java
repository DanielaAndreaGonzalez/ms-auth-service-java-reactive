package co.com.bancolombia.model.user.registeruser.gateway;

import co.com.bancolombia.model.shared.common.crq.Command;
import co.com.bancolombia.model.shared.common.crq.ContextData;
import co.com.bancolombia.model.user.model.User;

public interface UsersWriter {
    User save(Command<User, ContextData> command);
}
