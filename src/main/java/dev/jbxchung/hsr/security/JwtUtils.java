package dev.jbxchung.hsr.security;

import dev.jbxchung.hsr.service.UserDetailsImpl;
import dev.jbxchung.hsr.util.HostUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${hsr.auth.jwt.secret.file:}")
    private String jwtSecretFile;

    @Value("${hsr.auth.jwt.secret.override:}")
    private String jwtSecret;

    @Value("${hsr.auth.jwt.expiration}")
    private int jwtExpirationMs;

    @PostConstruct
    public void init() {
        // local should override jwt secret, otherwise get from file
        if (!HostUtils.isLocalhost()) {
            try {
                jwtSecret = Files.readString(Paths.get(jwtSecretFile));
            } catch (IOException e) {
                logger.error("Failed to get jwt secret from configured file path: {}", jwtSecretFile, e);
            }
        }
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) key()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
//            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            Jwts.parser().verifyWith((SecretKey) key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
