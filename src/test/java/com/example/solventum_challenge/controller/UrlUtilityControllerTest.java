package com.example.solventum_challenge.controller;

import com.example.solventum_challenge.exeption.UrlNotFoundException;
import com.example.solventum_challenge.model.OriginalUrlResponse;
import com.example.solventum_challenge.model.ShortenedUrlResponse;
import com.example.solventum_challenge.model.UrlRequest;
import com.example.solventum_challenge.service.UrlUtilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.solventum_challenge.util.Constants.DECODE_ERROR_MESSAGE;
import static com.example.solventum_challenge.util.Constants.SHORT_BASE_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(UrlUtilityController.class)

class UrlUtilityControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlUtilityService urlUtilityService;


    @Test
    void encodeUrl_returns200_ok() throws Exception {
        when(urlUtilityService.encodeUrl(UrlRequest.builder().url("https://verylongurl.com/waytoolong").build()))
                .thenReturn(ShortenedUrlResponse.builder().shortenedUrl(SHORT_BASE_URL + "6b44d1").build());

        String requestBody = "{\"url\": \"https://verylongurl.com/waytoolong\"}";

        mockMvc.perform(post("/encode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"shortenedUrl\":\"http://short.est/6b44d1\"}"));
    }


    @Test
    void decodeUrl_returns200_ok() throws Exception {
        when(urlUtilityService.decodeUrl(UrlRequest.builder().url(SHORT_BASE_URL + "6b44d1").build()))
                .thenReturn(OriginalUrlResponse.builder().originalUrl("https://verylongurl.com/waytoolong").build());

        String requestBody = "{\"url\": \"http://short.est/6b44d1\"}";

        mockMvc.perform(post("/decode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"originalUrl\":\"https://verylongurl.com/waytoolong\"}"));
    }

    @Test
    void decodeUrl_returns404_notFound() throws Exception {
        when(urlUtilityService.decodeUrl(UrlRequest.builder().url(SHORT_BASE_URL + "6b44d1").build()))
                .thenThrow(new UrlNotFoundException(DECODE_ERROR_MESSAGE + "http://short.est/6b44d1" ));

        String requestBody = "{\"url\": \"http://short.est/6b44d1\"}";

        mockMvc.perform(post("/decode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"error\":\"Unable to decode shortened url: http://short.est/6b44d1\"}"));
    }

    @Test
    void decodeUrl_returns400_badRequest() throws Exception {
        String requestBody = "{\"url\": \"\"}";

        mockMvc.perform(post("/decode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"error\":\"Url must be present in request body.\"}"));

        verify(urlUtilityService, never()).decodeUrl(any());
    }

    @Test
    void encodeUrl_returns400_badRequest() throws Exception {
        String requestBody = "{\"url\": \"\"}";

        mockMvc.perform(post("/encode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"error\":\"Url must be present in request body.\"}"));

        verify(urlUtilityService, never()).encodeUrl(any());
    }





}