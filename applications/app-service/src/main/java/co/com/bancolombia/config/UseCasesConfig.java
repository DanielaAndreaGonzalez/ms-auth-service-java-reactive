package co.com.bancolombia.config;

import co.com.bancolombia.model.gateways.SessionsRepository;
import co.com.bancolombia.model.user.gateway.UsersRepository;
import co.com.bancolombia.usecase.signin.SigninUseCase;
import co.com.bancolombia.usecase.signup.SignupUseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.bancolombia.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

        public SignupUseCase signupUseCase(UsersRepository usersRepository){
                return new SignupUseCase(usersRepository);
        }

        public SigninUseCase signipUseCase(UsersRepository usersRepository, SessionsRepository sessionsRepository){
                return new SigninUseCase(usersRepository, sessionsRepository);
        }

}
