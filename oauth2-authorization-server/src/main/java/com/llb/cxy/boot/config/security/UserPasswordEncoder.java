package com.llb.cxy.boot.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * description: UserPasswordEncoder <br>
 * date: 2019/11/22 10:02 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class UserPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}