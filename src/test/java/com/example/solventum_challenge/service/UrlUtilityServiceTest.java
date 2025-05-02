package com.example.solventum_challenge.service;

import com.example.solventum_challenge.exeption.UrlNotFoundException;
import com.example.solventum_challenge.model.OriginalUrlResponse;
import com.example.solventum_challenge.model.ShortenedUrlResponse;
import com.example.solventum_challenge.model.UrlRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.solventum_challenge.util.Constants.SHORT_BASE_URL;
import static org.junit.jupiter.api.Assertions.*;


class UrlUtilityServiceTest {

    UrlUtilityService urlUtilityService;

    @BeforeEach
    void setUp(){
        urlUtilityService = new UrlUtilityService();
    }


    @Test
    void encodeUrl_returnsShortenedUrlResponse(){
        UrlRequest request = UrlRequest.builder().url("https://verylongurl.com/waytoolong").build();
        ShortenedUrlResponse expected = ShortenedUrlResponse.builder().shortenedUrl(SHORT_BASE_URL + "6b44d1").build();

        ShortenedUrlResponse actual = urlUtilityService.encodeUrl(request);
        assertEquals(expected, actual);

    }

    @Test
    void decodeUrl_returnsOriginalUrlResponse(){
        UrlRequest encodeRequest = UrlRequest.builder().url("https://verylongurl.com/waytoolong").build();
        urlUtilityService.encodeUrl(encodeRequest);

        OriginalUrlResponse expected = OriginalUrlResponse.builder().originalUrl("https://verylongurl.com/waytoolong").build();

        UrlRequest decodeRequest = UrlRequest.builder().url(SHORT_BASE_URL + "6b44d1").build();
        OriginalUrlResponse actual = urlUtilityService.decodeUrl(decodeRequest);

        assertEquals(expected, actual);

    }

    @Test
    void decodeUrl_throwsUrlNotFoundException(){

        UrlRequest decodeRequest = UrlRequest.builder().url(SHORT_BASE_URL + "12345").build();

        assertThrows(UrlNotFoundException.class, () -> urlUtilityService.decodeUrl(decodeRequest));
    }


}