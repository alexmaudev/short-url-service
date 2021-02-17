package com.alexmau.shorturlaliasservice.mapper;

import com.alexmau.shorturlaliasservice.dto.ShortUrlAlias;
import com.alexmau.shorturlaliasservice.entity.ShortUrlAliasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",   unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShortUrlAliasMapper {

    ShortUrlAlias mapEntityToDto(ShortUrlAliasEntity shortUrlAliasEntity);

    ShortUrlAliasEntity mapDtoToEntity(ShortUrlAlias shortUrlAlias);
}
