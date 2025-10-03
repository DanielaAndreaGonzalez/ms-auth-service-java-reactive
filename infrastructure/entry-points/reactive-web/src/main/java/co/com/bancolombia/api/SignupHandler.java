package co.com.bancolombia.api;

import co.com.bancolombia.usecase.singup.SignupUseCase;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Component

public class SignupHandler {

    private final SignupUseCase useCase;



    public SignupHandler(SignupUseCase useCase) {
        this.useCase = useCase;
    }


    public Mono<ServerResponse> signup(ServerRequest serverRequest) {
        String xreq = serverRequest.headers().firstHeader("x-request-id");
        final String reqId = (xreq == null || xreq.isBlank()) ? UUID.randomUUID().toString() : xreq;
        if (xreq == null || xreq.isBlank()) xreq = UUID.randomUUID().toString();

        return serverRequest.bodyToMono(SignupRequest.class)
                .flatMap(body -> {
                    System.out.println("DEBUG signup email=" + body.email + " password=" + body.password);
                   return  Mono.fromRunnable(() ->
                                    useCase.execute(body.email, body.password));

                })
                .then(ServerResponse.created(URI.create("/signup"))
                        .header("x-request-id", reqId)
                        .build())
                .onErrorResume(ex -> {
                    var status = co.com.bancolombia.api.config.HttpErrorMapper.statusFrom(ex);
                    var code = co.com.bancolombia.api.config.HttpErrorMapper.codeFrom(ex);
                    return ServerResponse.status(status)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("x-request-id" , reqId)
                            .bodyValue(java.util.Map.of("code", code));
                        }

                );
    }

    public static final class SignupRequest {
        public String email;
        public String password;
    }

}
