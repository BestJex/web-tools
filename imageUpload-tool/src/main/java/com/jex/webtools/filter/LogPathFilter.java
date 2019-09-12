package com.jex.webtools.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 记录访问日志
 */
@Component
@WebFilter(urlPatterns="/**")
public class LogPathFilter extends OncePerRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(LogPathFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        logger.info(String.format("method: %s, uri: %s, params: %s", method, uri, queryString));
        filterChain.doFilter(request, response);
    }
}
