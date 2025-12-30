package com.url_shortner.project.controller;


import com.url_shortner.project.dto.UrlRequestDto;
import com.url_shortner.project.dto.UrlResponseDto;
import com.url_shortner.project.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDto> shortenUrl(@Valid @RequestBody UrlRequestDto request) {
        UrlResponseDto response = urlService.shortenUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/redirect")
    public RedirectView redirect(@RequestParam("code") String code) {
        String originalUrl = urlService.getOriginalUrl(code);
        return new RedirectView(originalUrl);
    }

    @DeleteMapping("/shorten/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode) {
        urlService.deleteUrl(shortCode);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }

}
