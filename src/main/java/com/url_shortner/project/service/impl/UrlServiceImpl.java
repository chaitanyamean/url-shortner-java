package com.url_shortner.project.service.impl;

import com.url_shortner.project.dto.UrlRequestDto;
import com.url_shortner.project.dto.UrlResponseDto;
import com.url_shortner.project.entity.UrlEntity;
import com.url_shortner.project.repository.UrlRepository;
import com.url_shortner.project.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final SecureRandom random = new SecureRandom();

    @Value("${app.base-url:http://localhost:8080/}")
    private String baseUrl;

    @Override
    @Transactional
    public UrlResponseDto shortenUrl(UrlRequestDto request) {

        Optional<UrlEntity> existingUrl = urlRepository.findByOriginalUrl(request.getUrl());

        if(existingUrl.isPresent()) {
            log.debug("URL Already found");
            UrlEntity entity = existingUrl.get();
            return UrlResponseDto.builder()
                    .originalUrl(entity.getOriginalUrl())
                    .shortCode(entity.getShortCode())
                    // Reconstruct the full URL (or store it in DB if preferred)
                    .shortUrl(baseUrl + "redirect?code=" + entity.getShortCode())
                    .build();
        }



        String code = generateUniqueCode();

        UrlEntity entity = UrlEntity.builder()
                .originalUrl(request.getUrl())
                .shortCode(code)
                .build();

        UrlEntity savedEntity = urlRepository.save(entity);

        return UrlResponseDto.builder()
                .originalUrl(savedEntity.getOriginalUrl())
                .shortCode(savedEntity.getShortCode())
                .shortUrl(baseUrl + "redirect?code=" + savedEntity.getShortCode())
                .build();

    }

    @Override
    @Transactional(readOnly = true)
    public String getOriginalUrl(String shortCode) {
        log.debug("Fetching URL for code: {}", shortCode);
        return urlRepository.findByShortCode(shortCode)
                .map(UrlEntity::getOriginalUrl)
                .orElseThrow(() -> new RuntimeException("URL not found for code: " + shortCode));
        // In a real app, throw a custom exception like ResourceNotFoundException
    }

    @Override
    @Transactional
    public void deleteUrl(String shortCode) {
        log.debug("Deleting URL with code: {}", shortCode);
        UrlEntity entity = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        urlRepository.delete(entity);
    }


    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomString(6);
        } while (urlRepository.findByShortCode(code).isPresent());
        return code;
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }








}
