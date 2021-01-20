package com.llb.cxy.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * description: Password <br>
 * date: 2020/2/26 9:24 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@SpringBootTest
public class Password {
    @Test
    void contextLoads() {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String encode = bc.encode("999");

        System.out.println(encode);;
        System.out.println(bc.matches("999", encode));

    }

    public static void main(String[] args) {
        new BCryptPasswordEncoder().encode("123");
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String encode = bc.encode("spring");

        System.out.println(encode);;
        System.out.println(bc.matches("spring",
                "$2a$10$lEyJwkP.Ws6qYM8IcpNyC.mr1N7E8WD4QWbmchAPHMhCDxAEN59te"));

        System.out.println("==="+new BCryptPasswordEncoder().encode("111111"));

        System.out.println(bc.matches("1111",
                "$2a$10$fyKNHs2cATlenEPqGxcdtOuVeXT03eNNZPSSL3fOyz6yj6hAilFFC"));
    }

    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
        encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

}
