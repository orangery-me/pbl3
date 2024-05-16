package com.nhom10.pbl.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nhom10.pbl.security.jwt.JWTService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class HandlingException {
    @Autowired
    private JWTService jwtService;

    @ExceptionHandler(value = io.jsonwebtoken.ExpiredJwtException.class)
    void handelingException(io.jsonwebtoken.ExpiredJwtException exception, @NonNull HttpServletResponse responsen, @NonNull HttpServletRequest request){
        String jwt = jwtService.extractTokenFromCookie(request);

        if (jwt != null && jwtService.isTokenExpired(jwt)) {
            clearTokenCookie(responsen);
        }
        System.out.println("===============================================================================");
    }

    public void clearTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Thời gian sống của cookie là 1 tuần

        response.addCookie(cookie);
        String cookieHeader = String.format("%s; SameSite=Strict", response.getHeader("Set-Cookie"));
        response.setHeader("Set-Cookie", cookieHeader);

        System.out.println(
                "========================================================================JWT is expride: " + "da xoa");
    }
}
