package com.alexmau.shorturlaliasservice.dao;

import com.alexmau.shorturlaliasservice.entity.ShortUrlAliasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlAliasDao extends JpaRepository<ShortUrlAliasEntity, Long> {

    Optional<ShortUrlAliasEntity> findByAlias(String alias);

    Optional<ShortUrlAliasEntity> findByUrl(String url);
}
