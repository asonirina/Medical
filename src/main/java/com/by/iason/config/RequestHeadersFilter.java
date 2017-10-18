package com.by.iason.config;

import com.by.iason.crypto.MessageEncoder;
import com.by.iason.crypto.PKManager;
import com.by.iason.exception.NodeNotFoundException;
import com.by.iason.exception.ReadPKException;
import com.by.iason.model.entity.Node;
import com.by.iason.model.response.ErrorResponse;
import com.by.iason.service.MedChainManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import multichain.command.MultichainException;
import org.apache.http.entity.ContentType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PrivateKey;

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
        } catch (ReadPKException ex) {
            send403(httpServletResponse);
            return;
        } catch (NodeNotFoundException | MultichainException e) {
            throw new ServletException(e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void getInfo(HttpServletRequest request) throws NodeNotFoundException, IOException, MultichainException, ReadPKException {
        MedChainManager manager = new MedChainManager(BlockChain.defaultNode());
        try {
            String address = request.getHeader("Address");
            String password = request.getHeader("Password");

            Node node = manager.getNode(address);
            PrivateKey privateKey = readPrivateKey(manager, address, password);
            manager.kill();

            SecurityContextHolder.setContext(new SimpleContext(address, node, privateKey));
        } finally {
            manager.kill();
        }
    }

    private PrivateKey readPrivateKey(MedChainManager manager, String address, String password) throws ReadPKException {
        if (password == null) {
            return null;
        }
        try {
            return new PKManager().readPrivateKey(new MessageEncoder(password).decodeByte(new String(manager.getPrivateKey(address))));
        } catch (Exception ex) {
            throw new ReadPKException();
        }
    }

    private void send403(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType(ContentType.APPLICATION_JSON.toString());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(403);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), new ErrorResponse("Access Denied", 403));

        httpServletResponse.flushBuffer();
    }
}
