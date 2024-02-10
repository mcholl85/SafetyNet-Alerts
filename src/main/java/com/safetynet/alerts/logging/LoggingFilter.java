package com.safetynet.alerts.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Log4j2
@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(wrappedRequest, wrappedResponse);

        String method = wrappedRequest.getMethod();
        String uri = wrappedRequest.getRequestURI();
        String requestBody = wrappedRequest.getContentAsString().replaceAll("[\n ]", "");
        String requestQuery = wrappedRequest.getQueryString();
        int status = wrappedResponse.getStatus();
        String responseBody = new String(wrappedResponse.getContentAsByteArray());

        log.info(String.format("Request - Method : %s - URI : %s - Query : %s - Body : %s", method, uri, requestQuery, requestBody));
        log.info(String.format("Response - Status : %s - Response : %s", status, responseBody));

        wrappedResponse.copyBodyToResponse();
    }
}
