package com.flaviocordis.encurtadorurl.controller;

import com.flaviocordis.encurtadorurl.dto.ShortenRequest;
import com.flaviocordis.encurtadorurl.dto.ShortenResponse;
import com.flaviocordis.encurtadorurl.exception.ErrorResponse;
import com.flaviocordis.encurtadorurl.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@RestController()
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService service;

    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(
            @Validated
            @RequestBody
            ShortenRequest request){

        ShortenResponse response = service.createShortUrl(request.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{key}")
    public RedirectView redirect(@PathVariable String key){
        String originalUrl = String.valueOf(service.getOriginalUrl(key));
        return new RedirectView(originalUrl);
    }

    @GetMapping("/api/status/{key}")
    public ResponseEntity<ShortenResponse> getStats(@PathVariable String key){
        ShortenResponse stats = service.getUrlStatus(key);
        return ResponseEntity.ok(stats);
    }
}
