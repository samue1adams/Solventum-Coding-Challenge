package com.example.solventum_challenge.controller;

import com.example.solventum_challenge.service.UrlUtilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UrlUtilityController.class)
@TestPropertySource(properties = "concurrent-requests.max=0")
public class ConcurrentRequestFilterTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlUtilityService urlUtilityService;


    @Test
    void filterThrows_returnsTooManyRequests_StatusWhenConcurrentLimit_IsSurpassed() throws Exception {
        String requestBody = "{\"url\": \"https://verylongurl.com/waytoolong\"}";

        mockMvc.perform(post("/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isTooManyRequests())
                .andExpect(content().string("{\"error\": \"Too many concurrent requests. Try again later.\"}"));
    }

}
