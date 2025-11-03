package com.flaviocordis.encurtadorurl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenRequest {

    @NotBlank(message = "URL não pode estar vazia!")
    @Pattern(regexp = "^https?://.*", message = "URL deve começar com http:// ou https://")
    private String url;
}
