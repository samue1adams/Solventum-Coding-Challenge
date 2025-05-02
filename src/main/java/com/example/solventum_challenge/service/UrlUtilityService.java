package com.example.solventum_challenge.service;

import com.example.solventum_challenge.exeption.UrlNotFoundException;
import com.example.solventum_challenge.model.OriginalUrlResponse;
import com.example.solventum_challenge.model.ShortenedUrlResponse;
import com.example.solventum_challenge.model.UrlRequest;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.solventum_challenge.util.Constants.DECODE_ERROR_MESSAGE;
import static com.example.solventum_challenge.util.Constants.SHORT_BASE_URL;



@Service
public class UrlUtilityService {


    private final Map<String, String> originalUrls = new ConcurrentHashMap<>();

    public ShortenedUrlResponse encodeUrl(UrlRequest request) {

        String encodedUrl = Hashing.sha256().hashString(request.getUrl(), StandardCharsets.UTF_8).toString().substring(0, 6);
        String shortenedUrl = SHORT_BASE_URL + encodedUrl;
        originalUrls.put(shortenedUrl, request.getUrl());

        return ShortenedUrlResponse.builder()
                .shortenedUrl(shortenedUrl)
                .build();
    }

    public OriginalUrlResponse decodeUrl(UrlRequest request) {
        String originalUrl = originalUrls.get(request.getUrl());
        if(originalUrl != null && !originalUrl.isEmpty()){
            return OriginalUrlResponse.builder().originalUrl(originalUrls.get(request.getUrl())).build();
        }
        else {
            throw new UrlNotFoundException(DECODE_ERROR_MESSAGE + request.getUrl());
        }
    }
}
