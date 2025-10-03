package co.com.bancolombia.api;

import co.com.bancolombia.api.config.HttpErrorMapper;
import co.com.bancolombia.usecase.singin.SigninUseCase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Component
public class SigninHandler {
    private final SigninUseCase useCase;

    public SigninHandler(SigninUseCase signinUseCase){this.useCase = signinUseCase;}

    public Mono<ServerResponse> signin(ServerRequest serverRequest) {
        String xreq = serverRequest.headers().firstHeader("x-request-id");
        final String reqId = (xreq == null || xreq.isBlank()) ? UUID.randomUUID().toString() : xreq;


        if (xreq == null || xreq.isBlank()) xreq = UUID.randomUUID().toString();

       return serverRequest.bodyToMono(SigninRequest.class)
               .flatMap(body -> Mono.fromCallable(() -> useCase.execute(body.email,body.password)))
               .flatMap(sessionId ->
                       ServerResponse.ok()
                               .contentType(MediaType.APPLICATION_JSON)
                               .header("x-request-id", reqId)
                               .bodyValue(Map.of("sessionId", sessionId))
               )
               .onErrorResume(ex ->
                       ServerResponse.status(HttpErrorMapper.statusFrom(ex))
                               .contentType(MediaType.APPLICATION_JSON)
                               .header("x-request-id" , reqId)
                               .bodyValue(Map.of("code", HttpErrorMapper.codeFrom(ex)))
               );
    }

    public static final class SigninRequest {
        public String email;
        public String password;
    }

}
