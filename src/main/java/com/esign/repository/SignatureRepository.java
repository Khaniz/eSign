package com.esign.repository;

import com.esign.model.Signature;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SignatureRepository {
    Signature save(Signature signature);

    Optional<Signature> findById(UUID id);

    List<Signature> findByOwnerId(String ownerId);
}
