package co.com.bancolombia.model.user;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Email {
    private static final Pattern RFC_5322 =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    private final String value;

    /*
    Valida null, vacío y formato.
    Si falla, lanza IllegalArgumentException con el código de error del dominio
    (INVALID_EMAIL_FORMAT) → luego el HttpErrorMapper lo traduce a 400.
     */
    public Email(String value) {
        String v = (value == null) ? null : value.trim();  // <-- trim ANTES de validar
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException("INVALID_EMAIL_FORMAT");
        }
        if (!RFC_5322.matcher(v).matches()) {
            System.out.println("DEBUG Email regex FAIL: [" + v + "] len=" + v.length());
            throw new IllegalArgumentException("INVALID_EMAIL_FORMAT");
        }
        this.value = v;
    }

    public String value() {return value; }

    @Override
    public boolean equals(Object o){
        if (this == o ) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email other = (Email) o;
        return value.equalsIgnoreCase(other.value);
    }

    @Override public int hashCode(){return Objects.hash(value.toLowerCase());}
    @Override public String toString(){return "Email{+++}"; }
}
