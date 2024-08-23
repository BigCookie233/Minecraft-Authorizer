package io.github.bigcookie233.mcauth;

import io.github.bigcookie233.mcauth.config.SecurityConfig;
import io.github.bigcookie233.mcauth.config.VerifierConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{
                AuthenticatorApplication.class,
                SecurityConfig.class,
                VerifierConfig.class
        }, args);
    }

}
