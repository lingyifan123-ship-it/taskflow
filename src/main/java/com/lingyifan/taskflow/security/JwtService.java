package com.lingyifan.taskflow.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.time.*; import java.util.Date;
@Service
public class JwtService {
    private final SecretKey key; private final long ttl;
    public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.ttl-seconds:7200}") long ttl){this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.ttl=ttl;}
    public String create(String username,String role){Instant now=Instant.now();return Jwts.builder().subject(username).claim("role",role).issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(ttl))).signWith(key).compact();}
    public Claims parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
}
