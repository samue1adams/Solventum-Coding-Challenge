package com.example.solventum_challenge.filter;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.Semaphore;

@Component
public class ConcurrentRequestsFilter extends OncePerRequestFilter {

    @Value("${concurrent-requests.max}")
    private int maxConcurrentRequests;

    private Semaphore semaphore;

    @PostConstruct
    public void init() {
        this.semaphore = new Semaphore(maxConcurrentRequests);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean acquired = semaphore.tryAcquire();
        if (!acquired) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Too many concurrent requests. Try again later.\"}");
        } else {
            try {
                filterChain.doFilter(request, response);
            } finally {
                semaphore.release();
            }
        }
    }
}
