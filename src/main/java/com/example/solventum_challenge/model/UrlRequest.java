package com.example.solventum_challenge.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest {

        @NotBlank(message = "Url must be present in request body.")
        public String url;

}
