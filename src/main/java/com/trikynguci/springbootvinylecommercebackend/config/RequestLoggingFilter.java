package com.trikynguci.springbootvinylecommercebackend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Request logging filter that adds correlation ID and logs HTTP requests/responses.
 * Adds trace information to MDC (Mapped Diagnostic Context) for structured logging.
 */
@Slf4j
@Component
public class RequestLoggingFilter implements Filter {

    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String REQUEST_ID_MDC_KEY = "requestId";
    private static final String USER_ID_MDC_KEY = "userId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Generate or extract request ID
        String requestId = httpRequest.getHeader(REQUEST_ID_HEADER);
        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }

        // Add to MDC for structured logging
        MDC.put(REQUEST_ID_MDC_KEY, requestId);
        
        // Add request ID to response header
        httpResponse.setHeader(REQUEST_ID_HEADER, requestId);

        long startTime = System.currentTimeMillis();
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        String queryString = httpRequest.getQueryString();
        String fullUrl = queryString != null ? uri + "?" + queryString : uri;

        try {
            log.info("Incoming request: {} {}", method, fullUrl);
            
            chain.doFilter(request, response);
            
            long duration = System.currentTimeMillis() - startTime;
            int status = httpResponse.getStatus();
            
            if (status >= 500) {
                log.error("Request completed: {} {} - Status: {} - Duration: {}ms", 
                    method, fullUrl, status, duration);
            } else if (status >= 400) {
                log.warn("Request completed: {} {} - Status: {} - Duration: {}ms", 
                    method, fullUrl, status, duration);
            } else {
                log.info("Request completed: {} {} - Status: {} - Duration: {}ms", 
                    method, fullUrl, status, duration);
            }
            
        } catch (Exception ex) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("Request failed: {} {} - Duration: {}ms - Error: {}", 
                method, fullUrl, duration, ex.getMessage(), ex);
            throw ex;
        } finally {
            // Clean up MDC
            MDC.remove(REQUEST_ID_MDC_KEY);
            MDC.remove(USER_ID_MDC_KEY);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("RequestLoggingFilter initialized");
    }

    @Override
    public void destroy() {
        log.info("RequestLoggingFilter destroyed");
    }
}
