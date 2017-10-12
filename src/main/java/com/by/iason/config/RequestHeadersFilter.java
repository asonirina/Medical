package com.by.iason.config;

import com.by.iason.model.entity.Node;
import com.by.iason.service.MedChainManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
        try {
            getInfo(httpServletRequest);
        } catch (Exception ex) {
            throw new ServletException();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void getInfo(HttpServletRequest request) throws Exception {
        MedChainManager manager = new MedChainManager(BlockChain.defaultNode());
        try {
            String address = request.getHeader("Address");

            Node node = manager.getNode(address);
            manager.kill();

            SecurityContextHolder.setContext(new SimpleContext(address, node));
        } finally {
            manager.kill();
        }
    }
}
