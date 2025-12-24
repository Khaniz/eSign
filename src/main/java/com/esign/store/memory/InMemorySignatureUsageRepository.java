package com.esign.store.memory;

import com.esign.model.SignatureUsage;
import com.esign.repository.SignatureUsageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemorySignatureUsageRepository implements SignatureUsageRepository {
    private final ConcurrentMap<UUID, SignatureUsage> store = new ConcurrentHashMap<>();

    @Override
    public SignatureUsage record(SignatureUsage usage) {
        store.put(usage.getId(), usage);
        return usage;
    }

    @Override
    public List<SignatureUsage> findBySignatureId(UUID signatureId) {
        List<SignatureUsage> results = new ArrayList<>();
        for (SignatureUsage usage : store.values()) {
            if (usage.getSignatureId().equals(signatureId)) {
                results.add(usage);
            }
        }
        return results;
    }

    @Override
    public List<SignatureUsage> findByDocumentId(String documentId) {
        List<SignatureUsage> results = new ArrayList<>();
        for (SignatureUsage usage : store.values()) {
            if (usage.getDocumentId().equals(documentId)) {
                results.add(usage);
            }
        }
        return results;
    }
}
