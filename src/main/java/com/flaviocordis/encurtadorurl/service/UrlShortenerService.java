package com.flaviocordis.encurtadorurl.service;

import com.flaviocordis.encurtadorurl.dto.ShortenResponse;
import com.flaviocordis.encurtadorurl.model.ShortUrl;
import com.flaviocordis.encurtadorurl.repository.ShortUrlRepository;
import com.flaviocordis.encurtadorurl.util.Base62Encoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final ShortUrlRepository repository;
    private final Base62Encoder encoder;
    
    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @Transactional
    public ShortenResponse createShortUrl(String longUrl){
        // Verifica a possibilidade da URL já ter sido encurtada
        ShortUrl existing = repository.findByFullUrl(longUrl);
        if (existing != null){
            return buildResponse(existing);
        }

        // Cria um novo registro
        ShortUrl shortUrl = ShortUrl.builder()
                .fullUrl(longUrl)
                .acessos(0L)
                .build();

        // Salvando no banco para receber o ID
        shortUrl = repository.save(shortUrl);

        if (shortUrl.getID() == null || shortUrl.getID() <=0 ) {
            throw new RuntimeException("Erro: ID não gerado pelo banco!");
        }

        // Gera o código curto a partir do ID e atualiza o objeto com a chave
        String key = encoder.encode(shortUrl.getID());
        shortUrl.setKey(key);

        // Atualiza o registro com a chave gerada
        shortUrl = repository.saveAndFlush(shortUrl);

        return buildResponse(shortUrl);
    }

    @Transactional
    public String getOriginalUrl(String key) {
        ShortUrl shortUrl = repository.findByKey(key);
        if (shortUrl == null){
            throw new RuntimeException("URL curta não encontrada!");
        }

        shortUrl.setAcessos(shortUrl.getAcessos() + 1);
        repository.save(shortUrl);

        return shortUrl.getFullUrl();
    }

    public ShortenResponse getUrlStatus(String key){
        ShortUrl shortUrl = repository.findByKey(key);
        if (shortUrl == null){
            throw new RuntimeException("URL curta não encontrada!");
        }
        return buildResponse(shortUrl);
    }

    private ShortenResponse buildResponse(ShortUrl shortUrl){
        return ShortenResponse.builder()
                .shortUrl(baseUrl + '/' + shortUrl.getKey())
                .originalUrl(shortUrl.getFullUrl())
                .acessos(shortUrl.getAcessos())
                .build();
    }
}
