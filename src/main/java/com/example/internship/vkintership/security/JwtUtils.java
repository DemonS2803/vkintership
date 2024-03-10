package com.example.internship.vkintership.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.internship.vkintership.entities.User;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Calendar;


@Component
@Slf4j
public class JwtUtils {

    @Autowired
    private Environment environment;

    public String generateJwtToken(User user, Integer expirationTime) {
        Long userId = user.getId();
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationTime);
        // log.info("secret: " + environment.getRequiredProperty("jwt.secret") + " expired: " + expirationTime);
        return JWT.create()
                .withSubject(user.getLogin())
                .withClaim("userId", userId.toString())
                .withIssuer(environment.getRequiredProperty("jwt.issuer"))
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }


    // проверяет, не истек ли токен
    public Boolean validateJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        try {
            JWT.require(algorithm)
                    .withIssuer(environment.getRequiredProperty("jwt.issuer"))
                    .acceptExpiresAt(Integer.parseInt(environment.getRequiredProperty("jwt.time.accept")))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            log.error("Exception {}", e.getMessage());
            // e.printStackTrace(new PrintWriter(stringWriter));
            log.error("Exception {}", stringWriter);
            return false;
        }
        return true;
    }

    public Long getUserIdFromJwtToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(environment.getRequiredProperty("jwt.secret"));
        return JWT.require(algorithm).build().verify(token).getClaim("userId").asLong();
    }

    public String getUserLoginFromToken(String token) {
        return JWT.decode(token).getSubject();
    }


}
