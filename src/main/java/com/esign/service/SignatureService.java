package com.esign.service;

import com.esign.model.Signature;
import com.esign.model.SignatureUsage;
import com.esign.repository.SignatureRepository;
import com.esign.repository.SignatureUsageRepository;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SignatureService {
    private final SignatureRepository signatureRepository;
    private final SignatureUsageRepository usageRepository;
    private final Clock clock;

    public SignatureService(SignatureRepository signatureRepository,
                            SignatureUsageRepository usageRepository,
                            Clock clock) {
        this.signatureRepository = signatureRepository;
        this.usageRepository = usageRepository;
        this.clock = clock;
    }

    public Signature registerSignature(String ownerId,
                                       String label,
                                       byte[] content,
                                       Map<String, String> attributes) {
        Instant now = Instant.now(clock);
        Signature signature = new Signature(UUID.randomUUID(), ownerId, label, content, now, now, attributes);
        return signatureRepository.save(signature);
    }

    public Optional<Signature> loadSignature(UUID signatureId) {
        return signatureRepository.findById(signatureId);
    }

    public List<Signature> listSignatures(String ownerId) {
        return signatureRepository.findByOwnerId(ownerId);
    }

    public Signature updateSignature(UUID signatureId, byte[] content, Map<String, String> attributes) {
        Signature signature = signatureRepository.findById(signatureId)
                .orElseThrow(() -> new IllegalArgumentException("Signature not found: " + signatureId));
        signature.updateContent(content, attributes, Instant.now(clock));
        return signatureRepository.save(signature);
    }

    public SignatureUsage recordUsage(UUID signatureId,
                                      String documentId,
                                      String formFieldId,
                                      String channel,
                                      String requestIp) {
        SignatureUsage usage = new SignatureUsage(
                UUID.randomUUID(),
                signatureId,
                documentId,
                formFieldId,
                channel,
                requestIp,
                Instant.now(clock)
        );
        return usageRepository.record(usage);
    }

    public List<SignatureUsage> listUsageBySignature(UUID signatureId) {
        return usageRepository.findBySignatureId(signatureId);
    }

    public List<SignatureUsage> listUsageByDocument(String documentId) {
        return usageRepository.findByDocumentId(documentId);
    }
}
