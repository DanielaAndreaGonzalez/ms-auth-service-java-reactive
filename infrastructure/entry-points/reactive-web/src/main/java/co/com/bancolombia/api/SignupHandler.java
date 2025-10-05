package co.com.bancolombia.api;

import co.com.bancolombia.model.shared.common.crq.Command;
import co.com.bancolombia.model.shared.common.crq.ContextData;
import co.com.bancolombia.model.shared.common.crq.XrequestId;
import co.com.bancolombia.usecase.signup.SignupPayLoad;
import co.com.bancolombia.usecase.signup.SignupUseCase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;
import co.com.bancolombia.api.dto.SignupRequest;

@Component

public class SignupHandler {

    private final SignupUseCase useCase;



    public SignupHandler(SignupUseCase useCase) {
        this.useCase = useCase;
    }


    public Mono<ServerResponse> signup(ServerRequest serverRequest) {

        String x = serverRequest.headers().firstHeader("x-request-id");
        String m = serverRequest.headers().firstHeader("message-id");

        final String messageId = (m == null || m.isBlank()) ? UUID.randomUUID().toString() : m;
        final String rawXreq   = (x == null || x.isBlank()) ? messageId : x;

        final ContextData ctx  = new ContextData(messageId, new XrequestId(rawXreq));


        return serverRequest.bodyToMono(SignupRequest.class)
                .flatMap(body -> {
                    System.out.println("DEBUG signup email=" + body.email + " password=" + body.password);
                    return Mono.fromRunnable(() -> {
                        var payload = new SignupPayLoad(body.email, body.password);
                        var cmd     = new Command<SignupPayLoad, ContextData>(payload, ctx);
                        useCase.execute(cmd);
                    });

                })
                .then(ServerResponse.created(URI.create("/signup"))
                        .header("x-request-id", ctx.xrequestId().value())
                        .build())
                .onErrorResume(ex -> {
                    var status = co.com.bancolombia.api.config.HttpErrorMapper.statusFrom(ex);
                    var errorResponse = co.com.bancolombia.api.config.HttpErrorMapper.errorResponseFrom(
                        ex, ctx.messageId(), ctx.xrequestId().value());
                    return ServerResponse.status(status)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("x-request-id" ,ctx.xrequestId().value())
                            .bodyValue(errorResponse);
                        }

                );
    }


}
