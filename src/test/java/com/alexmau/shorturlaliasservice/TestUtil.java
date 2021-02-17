package com.alexmau.shorturlaliasservice;


import com.alexmau.shorturlaliasservice.dto.ShortUrlAlias;
import com.alexmau.shorturlaliasservice.entity.ShortUrlAliasEntity;

public final class TestUtil {

    private TestUtil() {

    }

    public static final String TEST_URL_1 = "https://www.youtube.com";
    public static final String TEST_URL_2 = "https://www.instagram.com";
    public static final String TEST_URL_3 = "https://www.airbnb.com";

    public static final String TEST_ALIAS_1 = "test";

    public static final String WRONG_TEST_ALIAS_1 = "test1";

    public static final String NOT_EXIST_TEST_URL_1 = "https://www.facebook.com";

    public static final String WRONG_TEST_URL_1 = "hs://www.youtube.com";

    public static  final ShortUrlAliasEntity TEST_ENTITY_1 = new ShortUrlAliasEntity(1L, TEST_ALIAS_1, TEST_URL_1);

    public static final ShortUrlAlias TEST_REQUEST_1 = new ShortUrlAlias(null, TEST_URL_1);

    public static final ShortUrlAlias TEST_REQUEST_2 = new ShortUrlAlias(null, TEST_URL_3);

    public static final ShortUrlAlias WRONG_TEST_REQUEST_1 = new ShortUrlAlias(null, WRONG_TEST_URL_1);
}
