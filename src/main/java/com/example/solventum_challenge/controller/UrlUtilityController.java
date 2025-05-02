package com.example.solventum_challenge.controller;

import com.example.solventum_challenge.model.OriginalUrlResponse;
import com.example.solventum_challenge.model.UrlRequest;
import com.example.solventum_challenge.model.ShortenedUrlResponse;
import com.example.solventum_challenge.service.UrlUtilityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class UrlUtilityController {

    UrlUtilityService urlUtilityService;

    public UrlUtilityController(UrlUtilityService urlUtilityService) {
        this.urlUtilityService = urlUtilityService;
    }

    @PostMapping("encode")
    public ShortenedUrlResponse shortenUrl(@Valid @RequestBody UrlRequest request){
        return urlUtilityService.encodeUrl(request);
    }

    @PostMapping("decode")
    public OriginalUrlResponse retrieveOriginalUrl(@Valid @RequestBody UrlRequest request){
        return urlUtilityService.decodeUrl(request);
    }

}
