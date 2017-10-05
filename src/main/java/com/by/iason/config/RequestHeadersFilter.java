package com.by.iason.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by iason
 * on 10/5/2017.
 */

@Component
public class RequestHeadersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        getInfo(httpServletRequest);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void getInfo(HttpServletRequest request) {
        String address = request.getHeader("Address");
        String node = request.getHeader("Node");

        SecurityContextHolder.setContext(new SimpleContext(address, node));
    }
}
