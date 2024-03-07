package com.example.demo.customs;

import com.example.demo.response.ResponseAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandle implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        ResponseAPI responseAPI = new ResponseAPI(false,"Access denied for the request",null);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseAPI);
        response.getWriter().write(json);
    }
}
