package com.alexmau.shorturlaliasservice.service;

import com.alexmau.shorturlaliasservice.dao.ShortUrlAliasDao;
import com.alexmau.shorturlaliasservice.dto.ShortUrlAlias;
import com.alexmau.shorturlaliasservice.entity.ShortUrlAliasEntity;
import com.alexmau.shorturlaliasservice.exception.BadRequestException;
import com.alexmau.shorturlaliasservice.exception.NotFoundEntityException;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Transactional
@Slf4j
@Service
public class ShortUrlAliasService {

    @Autowired
    private ShortUrlAliasDao shortUrlDao;

    public ShortUrlAliasEntity getUrl(String alias) {
        Optional<ShortUrlAliasEntity> shortUrlAlias = shortUrlDao.findByAlias(alias);
        if (shortUrlAlias.isPresent()) {
            return shortUrlAlias.get();
        }
        final String message = "Entity with alias: " + alias + " has not been found";
        throw new NotFoundEntityException(message);
    }

    public ShortUrlAliasEntity createShortUrl(ShortUrlAlias shortUrl) {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (urlValidator.isValid(shortUrl.getUrl())) {
            Optional<ShortUrlAliasEntity> entity = shortUrlDao.findByUrl(shortUrl.getUrl());
            if (entity.isPresent()) {
                return entity.get();
            } else {
                String alias = Hashing.murmur3_32().hashString(shortUrl.getUrl(), StandardCharsets.UTF_8).toString();
                ShortUrlAliasEntity result = new ShortUrlAliasEntity(alias, shortUrl.getUrl());
                return shortUrlDao.save(result);
            }
        }
        final String message = "URL is not correct " + shortUrl.getUrl();
        throw new BadRequestException(message);
    }
}
