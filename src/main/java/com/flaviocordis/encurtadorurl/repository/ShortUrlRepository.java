package com.flaviocordis.encurtadorurl.repository;

import com.flaviocordis.encurtadorurl.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    ShortUrl findByKey(String key); //Data JPA entende o findBy e soma com o campo depois
    ShortUrl findByFullUrl(String fullUrl);
    boolean existsByKey(String key);

}
