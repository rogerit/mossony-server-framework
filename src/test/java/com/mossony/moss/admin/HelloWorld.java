package com.mossony.moss.admin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Collections;

public class HelloWorld {


    @Test
    public void jwt() {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123456");
            String token = JWT.create()
                    .withIssuer("moss")
                    .withSubject("roger")
                    .sign(algorithm);
            JWTVerifier me = JWT.require(algorithm).build();
            DecodedJWT verify = me.verify(token);
            String signature = verify.getSignature();
            System.out.println(token);

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }
}
