package com.url_shortner.project.service;


import com.url_shortner.project.dto.UrlRequestDto;
import com.url_shortner.project.dto.UrlResponseDto;

public interface UrlService {
    UrlResponseDto shortenUrl(UrlRequestDto request);
    String getOriginalUrl(String shortCode);
    void deleteUrl(String shortCode);
}
