package com.esign.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class SignatureUsage {
    private final UUID id;
    private final UUID signatureId;
    private final String documentId;
    private final String formFieldId;
    private final String channel;
    private final String requestIp;
    private final Instant usedAt;

    public SignatureUsage(UUID id,
                          UUID signatureId,
                          String documentId,
                          String formFieldId,
                          String channel,
                          String requestIp,
                          Instant usedAt) {
        this.id = Objects.requireNonNull(id, "id");
        this.signatureId = Objects.requireNonNull(signatureId, "signatureId");
        this.documentId = Objects.requireNonNull(documentId, "documentId");
        this.formFieldId = Objects.requireNonNull(formFieldId, "formFieldId");
        this.channel = Objects.requireNonNull(channel, "channel");
        this.requestIp = Objects.requireNonNull(requestIp, "requestIp");
        this.usedAt = Objects.requireNonNull(usedAt, "usedAt");
    }

    public UUID getId() {
        return id;
    }

    public UUID getSignatureId() {
        return signatureId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getFormFieldId() {
        return formFieldId;
    }

    public String getChannel() {
        return channel;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public Instant getUsedAt() {
        return usedAt;
    }
}
