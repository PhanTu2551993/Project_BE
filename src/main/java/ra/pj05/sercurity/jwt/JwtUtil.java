package ra.pj05.sercurity.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ra.pj05.sercurity.principal.UserDetailsCustom;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt_secret}")
    private String SECRET_KEY;
    @Value("${jwt_expiration}")
    private Long EXPRIED_ACCESS;
    public String generateToken(UserDetailsCustom userDetailsCustom) {

        return Jwts.builder()
                .setSubject(userDetailsCustom.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPRIED_ACCESS))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException ex) {
            log.error("Invalid JWT token {}", ex.getMessage());
        } catch (SignatureException ex) {
            log.error("Signature exception {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Malformed URL exception {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty {}", ex.getMessage());
        }
        return false;
    }

    public String GetUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
