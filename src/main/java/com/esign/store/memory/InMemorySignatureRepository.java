package com.esign.store.memory;

import com.esign.model.Signature;
import com.esign.repository.SignatureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemorySignatureRepository implements SignatureRepository {
    private final ConcurrentMap<UUID, Signature> store = new ConcurrentHashMap<>();

    @Override
    public Signature save(Signature signature) {
        store.put(signature.getId(), signature);
        return signature;
    }

    @Override
    public Optional<Signature> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Signature> findByOwnerId(String ownerId) {
        List<Signature> results = new ArrayList<>();
        for (Signature signature : store.values()) {
            if (signature.getOwnerId().equals(ownerId)) {
                results.add(signature);
            }
        }
        return results;
    }
}
