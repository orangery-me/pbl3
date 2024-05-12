package com.nhom10.pbl.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestInterceptor implements HandlerInterceptor {
    // Request is intercepted by this method before reaching the Controller
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // * Business logic just when the request is received and intercepted by this
        // interceptor before reaching the controller
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("1 - preHandle() : Before sending request to the Controller");
            System.out.println("Method Type: " + request.getMethod());
            System.out.println("Request URL: " + request.getRequestURI());
        }
        // * If the Exception is caught, this method will return false
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
