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
        // Lấy token từ cookie
        String jwt = jwtService.extractTokenFromCookie(request);
        String userName = null;
        if (jwt != null && jwtService.isTokenExpired(jwt)) {
            System.out.println("======================== token is expride ===================");
            clearTokenCookie(response);
            filterChain.doFilter(request, response);
            return;
        }
        if (jwt != null) {
            userName = jwtService.extractUserName(jwt); // Trích xuất tên người dùng từ jwt
        }

        if (jwt == null) {
            // if the authorization header is null then continue to the next filter
            filterChain.doFilter(request, response);
            return;
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Nếu tên người dùng không null và người dùng chưa được xác thực
            // Thì xác thực người dùng
            CustomUserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
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

        System.out.println("========================================================================JWT is expride: " + "da xoa");
    }
}
