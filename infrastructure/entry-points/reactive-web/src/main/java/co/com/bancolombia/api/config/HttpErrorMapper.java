package co.com.bancolombia.api.config;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class HttpErrorMapper {
    private HttpErrorMapper(){}

    public static HttpStatus statusFrom(Throwable ex){
        String code = (ex.getMessage()==null) ? "" : ex.getMessage();
        return switch (code) {
            case "INVALID_EMAIL_FORMAT" , "WEAK_PASSWORD", "MALFORMED_REQUEST" -> HttpStatus.BAD_REQUEST; //400
            case "EMAIL_ALREADY_EXISTS"   -> HttpStatus.CONFLICT; // 409
            case "USER_NOT_FOUND"      -> HttpStatus.NOT_FOUND;     // 404
            case "INVALID_CREDENTIALS" -> HttpStatus.UNAUTHORIZED;
            default                       -> HttpStatus.INTERNAL_SERVER_ERROR; //500
        };
    }

    public static String codeFrom(Throwable ex){
        return (ex.getMessage() == null || ex.getMessage().isBlank()) ? "INTERNAL_ERROR" : ex.getMessage();
    }

    public static String messageFrom(String code){
        return switch (code) {
            case "INVALID_EMAIL_FORMAT" -> "Formato de email inválido";
            case "WEAK_PASSWORD" -> "La contraseña debe tener al menos 8 caracteres";
            case "EMAIL_ALREADY_EXISTS" -> "Email ya registrado";
            case "MALFORMED_REQUEST" -> "Solicitud malformada";
            case "USER_NOT_FOUND" -> "Usuario no encontrado";
            case "INVALID_CREDENTIALS" -> "Credenciales inválidas";
            case "INVALID_XREQUEST_ID" -> "X-Request-ID inválido";
            default -> "Error interno del servidor";
        };
    }

    public static Map<String, Object> errorResponseFrom(Throwable ex, String messageId, String xRequestId){
        String code = codeFrom(ex);
        String message = messageFrom(code);
        Map<String, Object> correlation = Map.of(
            "x_request_id", xRequestId != null ? xRequestId : ""
        );
        if (messageId != null && !messageId.isBlank()) {
            correlation = Map.of(
                "message_id", messageId,
                "x_request_id", xRequestId != null ? xRequestId : ""
            );
        }
        return Map.of(
            "error", Map.of(
                "code", code,
                "message", message,
                "details", Map.of(),
                "correlation", correlation
            )
        );
    }
}
