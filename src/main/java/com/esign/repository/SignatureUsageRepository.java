package com.esign.repository;

import com.esign.model.SignatureUsage;

import java.util.List;
import java.util.UUID;

public interface SignatureUsageRepository {
    SignatureUsage record(SignatureUsage usage);

    List<SignatureUsage> findBySignatureId(UUID signatureId);

    List<SignatureUsage> findByDocumentId(String documentId);
}
