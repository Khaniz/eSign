package com.esign.model;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Signature {
    private final UUID id;
    private final String ownerId;
    private final String label;
    private byte[] content;
    private Instant createdAt;
    private Instant updatedAt;
    private Map<String, String> attributes;

    public Signature(UUID id,
                     String ownerId,
                     String label,
                     byte[] content,
                     Instant createdAt,
                     Instant updatedAt,
                     Map<String, String> attributes) {
        this.id = Objects.requireNonNull(id, "id");
        this.ownerId = Objects.requireNonNull(ownerId, "ownerId");
        this.label = Objects.requireNonNull(label, "label");
        setContent(content);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt");
        setAttributes(attributes);
    }

    public UUID getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getLabel() {
        return label;
    }

    public byte[] getContent() {
        return content.clone();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public void updateContent(byte[] newContent, Map<String, String> newAttributes, Instant updatedAt) {
        setContent(newContent);
        setAttributes(newAttributes);
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt");
    }

    private void setContent(byte[] content) {
        Objects.requireNonNull(content, "content");
        this.content = content.clone();
    }

    private void setAttributes(Map<String, String> attributes) {
        if (attributes == null) {
            this.attributes = new HashMap<>();
        } else {
            this.attributes = new HashMap<>(attributes);
        }
    }
}
