package sn.neldamoney.neldam.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class WebSecurityConfigRedefine extends WebSecurityConfig {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Noncompliant
    }
}
