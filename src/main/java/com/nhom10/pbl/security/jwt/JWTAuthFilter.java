package com.nhom10.pbl.security.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.nhom10.pbl.security.service.CustomUserDetails;
import com.nhom10.pbl.security.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = null;
        String userName = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("accessToken")) {
                    jwt = cookie.getValue();
                }
            }
        }

        if (jwt == null) {
            // if the authorization header is null then continue to the next filter
            filterChain.doFilter(request, response);
            return;
        }

        userName = jwtService.extractUserName(jwt);// extract the username from the jwt
        if (userName != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            // if the username is not null and the user is not authenticated
            // then authenticate the user
            CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterChain.doFilter(request, response);
    }

    public void clearTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
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