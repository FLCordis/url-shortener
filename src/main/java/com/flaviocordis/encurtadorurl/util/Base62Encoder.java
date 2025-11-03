package com.flaviocordis.encurtadorurl.util;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String encode(Long id) {
        // Validação: se ID é null ou <= 0, trata como erro
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID deve ser maior que 0");
        }

        StringBuilder sb = new StringBuilder();
        long num = id;

        // Faz a conversão com segurança
        while (num > 0) {
            sb.append(BASE62.charAt((int)(num % 62)));
            num = num / 62; // divisão inteira
        }

        return sb.reverse().toString();
    }

    public Long decode(String encoded) {
        if (encoded == null || encoded.isEmpty()) {
            throw new IllegalArgumentException("Encoded string não pode ser vazia");
        }

        long id = 0;
        for (char c : encoded.toCharArray()) {
            int index = BASE62.indexOf(c);
            if (index == -1) {
                throw new IllegalArgumentException("Caractere inválido: " + c);
            }
            id = id * 62 + index;
        }

        return id;
    }
}