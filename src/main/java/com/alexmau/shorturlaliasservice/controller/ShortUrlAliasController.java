package com.alexmau.shorturlaliasservice.controller;

import com.alexmau.shorturlaliasservice.dto.DataResponse;
import com.alexmau.shorturlaliasservice.dto.ShortUrlAlias;
import com.alexmau.shorturlaliasservice.dto.StatusType;
import com.alexmau.shorturlaliasservice.entity.ShortUrlAliasEntity;
import com.alexmau.shorturlaliasservice.exception.BadRequestException;
import com.alexmau.shorturlaliasservice.exception.NotFoundEntityException;
import com.alexmau.shorturlaliasservice.mapper.ShortUrlAliasMapper;
import com.alexmau.shorturlaliasservice.service.ShortUrlAliasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@RequestMapping("/shorturl")
@RestController
@Slf4j
public class ShortUrlAliasController {

    @Autowired
    private ShortUrlAliasService shortUrlAliasService;

    @Autowired
    private ShortUrlAliasMapper shortUrlAliasMapper;

    @GetMapping(value = "/{alias}")
    public ResponseEntity<DataResponse<ShortUrlAlias>> getShortUrl(@PathVariable String alias) throws URISyntaxException {
        try {
            ShortUrlAliasEntity shortUrlAlias = shortUrlAliasService.getUrl(alias);
            URI uri = new URI(shortUrlAlias.getUrl());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
        } catch (NotFoundEntityException e){
            final DataResponse<ShortUrlAlias> response = new DataResponse<>();
            response.setStatus(StatusType.WARNING);
            log.warn(e.getMessage());
            response.addMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<DataResponse<ShortUrlAlias>> create(@Valid @RequestBody ShortUrlAlias request) {
        try {
            ShortUrlAlias result = shortUrlAliasMapper.mapEntityToDto(shortUrlAliasService.createShortUrl(request));
            final DataResponse<ShortUrlAlias> response = new DataResponse<>();
            response.setData(result);
            response.setStatus(StatusType.SUCCESSFUL);
            final String message = "URL is created " + request.getUrl();
            log.info(message);
            response.addMessage(HttpStatus.OK.value(), message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadRequestException e) {
            final DataResponse<ShortUrlAlias> response = new DataResponse<>();
            response.setStatus(StatusType.WARNING);
            log.error(e.getMessage());
            response.addMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
