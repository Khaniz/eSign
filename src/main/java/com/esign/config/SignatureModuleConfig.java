package com.esign.config;

import com.esign.repository.SignatureRepository;
import com.esign.repository.SignatureUsageRepository;
import com.esign.service.SignatureService;
import com.esign.store.memory.InMemorySignatureRepository;
import com.esign.store.memory.InMemorySignatureUsageRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class SignatureModuleConfig {
    @Bean
    public SignatureRepository signatureRepository() {
        return new InMemorySignatureRepository();
    }

    @Bean
    public SignatureUsageRepository signatureUsageRepository() {
        return new InMemorySignatureUsageRepository();
    }

    @Bean
    public SignatureService signatureService(SignatureRepository signatureRepository,
                                             SignatureUsageRepository signatureUsageRepository,
                                             Clock clock) {
        return new SignatureService(signatureRepository, signatureUsageRepository, clock);
    }

    @Bean
    public Clock signatureClock() {
        return Clock.systemUTC();
    }
}
