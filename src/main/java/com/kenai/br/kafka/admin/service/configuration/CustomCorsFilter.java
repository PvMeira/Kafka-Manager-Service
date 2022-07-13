package com.kenai.br.kafka.admin.service.configuration;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kenai.br.kafka.admin.service.controller.DefaultController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setHeader(  "Access-Control-Allow-Origin"
                           , "http://localhost:3000");
        response.setHeader(  "Access-Control-Allow-Credentials"
                           , "true");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setHeader(  "Access-Control-Allow-Methods"
                               , "POST, GET, DELETE, PUT, OPTIONS");
            response.setHeader(  "Access-Control-Allow-Headers"
                               , "Authorization, Content-Type, Accept, " + DefaultController.HEADER_NAME_CLUSTER_ID + ", " + DefaultController.HEADER_NAME_CONF_NAME);
            response.setHeader(  "Access-Control-Max-Age"
                               , "3600");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }
    }



}