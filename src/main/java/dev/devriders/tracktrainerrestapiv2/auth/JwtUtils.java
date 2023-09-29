package dev.devriders.tracktrainerrestapiv2.auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {
    private final byte[] secretKey;
    private final long expiration;
    public JwtUtils(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.expiration}") long expiration) {
        this.secretKey = secret.getBytes(StandardCharsets.UTF_8);
        this.expiration = expiration;
    }
    public String generateToken(String subject) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expirationDate = Date.from(now.plus(expiration, ChronoUnit.MINUTES));
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String extractSubject(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new JwtExpirationException("El token ha expirado", e);
        } catch (MalformedJwtException e) {
            throw new JwtMalformedException("El token es inválido o está mal formado", e);
        } catch (SignatureException e) {
            throw new JwtSignatureException("La firma del token es inválida", e);
        } catch (JwtException e) {
            throw new JwtProcessingException("Error al procesar el token", e);
        }
    }
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtExpirationException("El token ha expirado", e);
        } catch (MalformedJwtException e) {
            throw new JwtMalformedException("El token es inválido o está mal formado", e);
        } catch (SignatureException e) {
            throw new JwtSignatureException("La firma del token es inválida", e);
        } catch (JwtException e) {
            throw new JwtProcessingException("Error al procesar el token", e);
        }
    }
    public String extractUsername(String token) {
        String subject = extractSubject(token);
        // Aquí puedes realizar cualquier transformación o validación adicional en el subject si es necesario
        return subject;
    }
}
class JwtExpirationException extends RuntimeException {
    public JwtExpirationException(String message, Throwable cause) {
        super(message, cause);
    }
}
class JwtSignatureException extends RuntimeException {
    public JwtSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
}
class JwtMalformedException extends RuntimeException {
    public JwtMalformedException(String message, Throwable cause) {
        super(message, cause);
    }
}
class JwtProcessingException extends RuntimeException {
    public JwtProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
