package co.com.bancolombia.model.shared.common.crq;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public final class XrequestId {
    private static final Pattern ALLOWED = Pattern.compile("^[A-Za-z0-9\\-_.:]{8,100}$"); // seguro para logs/headers

    private final String value;

    public XrequestId(String raw) {
        String v = (raw == null) ? null : raw.trim();
        if (v == null || v.isBlank() || !ALLOWED.matcher(v).matches()) {
            throw new IllegalArgumentException("INVALID_XREQUEST_ID");
        }
        this.value = v;
    }

    /** Genera un x-request-id válido (UUID). */
    public static XrequestId generate() {
        return new XrequestId(UUID.randomUUID().toString());
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XrequestId other)) return false;
        return value.equals(other.value);
    }
    @Override
    public int hashCode() { return Objects.hash(value); }
    @Override
    public String toString() { return value; }
}
