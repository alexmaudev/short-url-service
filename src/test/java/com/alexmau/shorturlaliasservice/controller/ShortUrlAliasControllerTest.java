package com.alexmau.shorturlaliasservice.controller;

import com.alexmau.shorturlaliasservice.TestUtil;
import com.alexmau.shorturlaliasservice.exception.BadRequestException;
import com.alexmau.shorturlaliasservice.exception.NotFoundEntityException;
import com.alexmau.shorturlaliasservice.service.ShortUrlAliasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ShortUrlAliasControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ShortUrlAliasService shortUrlAliasService;

    @InjectMocks
    private ShortUrlAliasController shortUrlAliasController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void get() throws Exception {
        Mockito.when(shortUrlAliasService.getUrl(TestUtil.TEST_ALIAS_1)).thenReturn(TestUtil.TEST_ENTITY_1);

        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/shorturl/{alias}",  TestUtil.TEST_ALIAS_1);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isMovedPermanently());
    }

    @Test
    void getNotFound() throws Exception {
        Mockito.when(shortUrlAliasService.getUrl(TestUtil.WRONG_TEST_ALIAS_1))
                .thenThrow(new NotFoundEntityException("Entity with alias: " +
                        TestUtil.WRONG_TEST_ALIAS_1 + " has not been found"));

        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/shorturl/{alias}",  TestUtil.WRONG_TEST_ALIAS_1);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.messages[0].message")
                        .value("Entity with alias: " +
                                TestUtil.WRONG_TEST_ALIAS_1 + " has not been found"))
                .andExpect(jsonPath("$.messages[0].code")
                        .value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void post() throws Exception {
        Mockito.when(shortUrlAliasService.createShortUrl(TestUtil.TEST_REQUEST_1))
                .thenReturn(TestUtil.TEST_ENTITY_1);

        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/shorturl")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "\t\"url\": \"" + TestUtil.TEST_URL_1 + "\"\n" +
                                "}");

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data.alias").value(TestUtil.TEST_ALIAS_1))
                .andExpect(jsonPath("$.data.url").value(TestUtil.TEST_URL_1));
    }

    @Test
    void postBadRequest() throws Exception {
        Mockito.when(shortUrlAliasService.createShortUrl(TestUtil.WRONG_TEST_REQUEST_1))
                .thenThrow(new BadRequestException("URL is not correct " + TestUtil.WRONG_TEST_URL_1));

        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/shorturl")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "\t\"url\": \"" + TestUtil.WRONG_TEST_URL_1 + "\"\n" +
                                "}");

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.messages[0].message")
                        .value("URL is not correct " + TestUtil.WRONG_TEST_URL_1))
                .andExpect(jsonPath("$.messages[0].code")
                        .value(HttpStatus.BAD_REQUEST.value()));
    }
}
