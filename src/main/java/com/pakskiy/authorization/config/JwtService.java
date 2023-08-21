package com.pakskiy.authorization.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pakskiy.authorization.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${app.secret-key}")
    private static String SECRET_KEY;

    @Value("${app.issuer}")
    private static String ISSUER;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        extraClaims.put("email", ((User) userDetails).getEmail());
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(extraClaims)
                .setIssuer(ISSUER)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails, boolean isExternal){
        if(isExternal){
            return !isExternalTokenExpired(token);
        } else {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean extractIsExternal(String token) {
        return extractExternalAllClaims(token).get("iss").asText().equals("accounts.google.com");
    }
    private Date extractExternalExpiration(String token) {
        return new Date(extractExternalAllClaims(token).get(Claims.EXPIRATION).asLong() * 1000);
    }

    public boolean isExternalTokenExpired(String token) {
        return extractExternalExpiration(token).before(new Date());
    }

    public String extractExternalEmail(String token) {
        return extractExternalAllClaims(token).get("email").asText();
    }
    private JsonNode extractExternalAllClaims(String token){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.nullNode();

        String[] parts = token.split("\\.");
        try {
            result = mapper.readTree(Base64.getUrlDecoder().decode(parts[1]));
        }catch (Exception e){
            log.error("ERR_JWT_SERVICE", e);
        }
        return result;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}