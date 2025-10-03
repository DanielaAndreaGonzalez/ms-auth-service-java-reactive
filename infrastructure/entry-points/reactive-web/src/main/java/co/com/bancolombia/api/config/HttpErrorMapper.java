package co.com.bancolombia.api.config;

import org.springframework.http.HttpStatus;

public class HttpErrorMapper {
    private HttpErrorMapper(){}

    public static HttpStatus statusFrom(Throwable ex){
        String code = (ex.getMessage()==null) ? "" : ex.getMessage();
        return switch (code) {
            case "INVALID_EMAIL_FORMAT" , "WEAK_PASSWORD", "MALFORMED_REQUEST" -> HttpStatus.BAD_REQUEST; //400
            case "EMAIL_ALREADY_EXISTS"   -> HttpStatus.CONFLICT; // 409
            default                       -> HttpStatus.INTERNAL_SERVER_ERROR; //500
        };
    }

    public static String codeFrom(Throwable ex){
        return (ex.getMessage() == null || ex.getMessage().isBlank()) ? "INTERNAL_ERROR" : ex.getMessage();
    }
}
