package com.nhom10.pbl.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.nhom10.pbl.security.service.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTService {
    private static final String SECRET_KEY = "892BB8100E1162FA0C0FD35A628A2225590D1B935E4C2F8D307B2833F760DDAD";

    // generate a jwt token for the user
    public String generateToken(CustomUserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, CustomUserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60*60*2))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // validate the jwt token
    public boolean validateToken(String jwt, CustomUserDetails userDetails) {
        final String userName = extractUserName(jwt);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    public boolean isTokenExpired(String jwt) {
        return extractClaim(jwt, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    // extract the username from the jwt
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // signing key is the secret key used to create the signature of the jwt
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); // Creates a new SecretKey instance for use with HMAC-SHA algorithms based
                                             // on the specified key byte array.
    }

    public String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String extractUserNameFromTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return extractUserName(cookie.getValue());
                }
            }
        }
        return null;
    }
    // public void createTokenCookie(String token, HttpServletResponse response) {
    //     Cookie cookie = new Cookie("auth_token", token);
    //     cookie.setHttpOnly(true);
    //     cookie.setSecure(true);
    //     cookie.setPath("/");
    //     cookie.setMaxAge(60*60); // Thời gian sống của cookie là 1 tuần

    //     response.addCookie(cookie);
    //     String cookieHeader = String.format("%s; SameSite=Strict", response.getHeader("Set-Cookie"));
    //     response.setHeader("Set-Cookie", cookieHeader);
    // }
}
