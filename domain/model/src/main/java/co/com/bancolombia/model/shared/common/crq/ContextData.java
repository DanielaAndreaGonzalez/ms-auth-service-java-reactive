package co.com.bancolombia.model.shared.common.crq;

import java.util.Objects;

public final class ContextData {
    private final String messageId;
    private final XrequestId xrequestId;

    public ContextData(String messageId, XrequestId xrequestId) {
        this.messageId  = Objects.requireNonNull(messageId, "MALFORMED_REQUEST");
        this.xrequestId = Objects.requireNonNull(xrequestId, "MALFORMED_REQUEST");
    }

    public String messageId()      { return messageId; }
    public XrequestId xrequestId() { return xrequestId; }

}
