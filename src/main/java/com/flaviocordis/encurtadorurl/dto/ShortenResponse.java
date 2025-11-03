package com.flaviocordis.encurtadorurl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenResponse {
    private String shortUrl;
    private String originalUrl;
    private Long acessos;
}
