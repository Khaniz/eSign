package com.esign.web;

import com.esign.model.Signature;
import com.esign.model.SignatureUsage;
import com.esign.service.SignatureService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/signatures")
public class SignatureController {
    private final SignatureService signatureService;

    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @PostMapping
    public ResponseEntity<SignatureResponse> register(@RequestBody SignatureRequest request) {
        Signature signature = signatureService.registerSignature(
                request.getOwnerId(),
                request.getLabel(),
                decodeBase64(request.getContentBase64()),
                request.getAttributes()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(SignatureResponse.from(signature));
    }

    @GetMapping("/{signatureId}")
    public ResponseEntity<SignatureResponse> load(@PathVariable UUID signatureId) {
        return signatureService.loadSignature(signatureId)
                .map(SignatureResponse::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<SignatureResponse> list(@RequestParam("ownerId") String ownerId) {
        return signatureService.listSignatures(ownerId).stream()
                .map(SignatureResponse::from)
                .toList();
    }

    @PutMapping("/{signatureId}")
    public ResponseEntity<SignatureResponse> update(@PathVariable UUID signatureId,
                                                    @RequestBody SignatureUpdateRequest request) {
        Signature signature = signatureService.updateSignature(
                signatureId,
                decodeBase64(request.getContentBase64()),
                request.getAttributes()
        );
        return ResponseEntity.ok(SignatureResponse.from(signature));
    }

    @PostMapping("/{signatureId}/usage")
    public ResponseEntity<SignatureUsageResponse> recordUsage(@PathVariable UUID signatureId,
                                                              @RequestBody SignatureUsageRequest request) {
        SignatureUsage usage = signatureService.recordUsage(
                signatureId,
                request.getDocumentId(),
                request.getFormFieldId(),
                request.getChannel(),
                request.getRequestIp()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(SignatureUsageResponse.from(usage));
    }

    @GetMapping("/{signatureId}/usage")
    public List<SignatureUsageResponse> listUsage(@PathVariable UUID signatureId) {
        return signatureService.listUsageBySignature(signatureId).stream()
                .map(SignatureUsageResponse::from)
                .toList();
    }

    @GetMapping("/usage")
    public List<SignatureUsageResponse> listUsageByDocument(@RequestParam("documentId") String documentId) {
        return signatureService.listUsageByDocument(documentId).stream()
                .map(SignatureUsageResponse::from)
                .toList();
    }

    private byte[] decodeBase64(String payload) {
        return Base64.getDecoder().decode(payload);
    }

    public static class SignatureRequest {
        private String ownerId;
        private String label;
        private String contentBase64;
        private Map<String, String> attributes;

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getContentBase64() {
            return contentBase64;
        }

        public void setContentBase64(String contentBase64) {
            this.contentBase64 = contentBase64;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

    public static class SignatureUpdateRequest {
        private String contentBase64;
        private Map<String, String> attributes;

        public String getContentBase64() {
            return contentBase64;
        }

        public void setContentBase64(String contentBase64) {
            this.contentBase64 = contentBase64;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

    public static class SignatureUsageRequest {
        private String documentId;
        private String formFieldId;
        private String channel;
        private String requestIp;

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public String getFormFieldId() {
            return formFieldId;
        }

        public void setFormFieldId(String formFieldId) {
            this.formFieldId = formFieldId;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getRequestIp() {
            return requestIp;
        }

        public void setRequestIp(String requestIp) {
            this.requestIp = requestIp;
        }
    }

    public static class SignatureResponse {
        private UUID id;
        private String ownerId;
        private String label;
        private String contentBase64;
        private String createdAt;
        private String updatedAt;
        private Map<String, String> attributes;

        public static SignatureResponse from(Signature signature) {
            SignatureResponse response = new SignatureResponse();
            response.id = signature.getId();
            response.ownerId = signature.getOwnerId();
            response.label = signature.getLabel();
            response.contentBase64 = Base64.getEncoder().encodeToString(signature.getContent());
            response.createdAt = signature.getCreatedAt().toString();
            response.updatedAt = signature.getUpdatedAt().toString();
            response.attributes = signature.getAttributes();
            return response;
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

        public String getContentBase64() {
            return contentBase64;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }
    }

    public static class SignatureUsageResponse {
        private UUID id;
        private UUID signatureId;
        private String documentId;
        private String formFieldId;
        private String channel;
        private String requestIp;
        private String usedAt;

        public static SignatureUsageResponse from(SignatureUsage usage) {
            SignatureUsageResponse response = new SignatureUsageResponse();
            response.id = usage.getId();
            response.signatureId = usage.getSignatureId();
            response.documentId = usage.getDocumentId();
            response.formFieldId = usage.getFormFieldId();
            response.channel = usage.getChannel();
            response.requestIp = usage.getRequestIp();
            response.usedAt = usage.getUsedAt().toString();
            return response;
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

        public String getUsedAt() {
            return usedAt;
        }
    }
}
