package com.alexmau.shorturlaliasservice.service;

import com.alexmau.shorturlaliasservice.TestUtil;
import com.alexmau.shorturlaliasservice.dao.ShortUrlAliasDao;
import com.alexmau.shorturlaliasservice.entity.ShortUrlAliasEntity;
import com.alexmau.shorturlaliasservice.exception.BadRequestException;
import com.alexmau.shorturlaliasservice.exception.NotFoundEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ShortUrlServiceTest {

    @Autowired
    private ShortUrlAliasService shortUrlAliasService;

    @Autowired
    private ShortUrlAliasDao shortUrlAliasDao;

    @BeforeEach
    void createTestData() throws Exception {
        shortUrlAliasDao.save(TestUtil.TEST_ENTITY_1);
    }

    @Test
    void shouldCreateShortUrl() throws Exception {
        ShortUrlAliasEntity result = shortUrlAliasService.createShortUrl(TestUtil.TEST_REQUEST_2);
        Optional<ShortUrlAliasEntity> entityFromDb = shortUrlAliasDao.findById(result.getId());
        assertNotNull(result.getAlias());
        assertNotNull(result.getUrl());
        assertTrue(entityFromDb.isPresent());
        assertEquals(entityFromDb.get(), result);
    }

    @Test
    void shouldThrowBadRequestException() throws Exception {
        assertThrows(BadRequestException.class, () -> shortUrlAliasService
                .createShortUrl(TestUtil.WRONG_TEST_REQUEST_1));
    }

    @Test
    void shouldFindUrl() throws Exception {
        final ShortUrlAliasEntity result = shortUrlAliasService.getUrl(TestUtil.TEST_ALIAS_1);
        assertEquals(TestUtil.TEST_ENTITY_1, result);
    }

    @Test
    void shouldNotFindUrl() throws Exception {
        assertThrows(NotFoundEntityException.class, () -> shortUrlAliasService
                .getUrl(TestUtil.WRONG_TEST_ALIAS_1));
    }
}
