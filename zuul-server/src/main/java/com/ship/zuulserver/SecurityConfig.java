package com.ship.zuulserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admiral").password("admiral").roles("ADMIRAL")
            .and()
            .withUser("viceAdmiral").password("viceAdmiral").roles("VICE_ADMIRAL")
            .and()
            .withUser("captain").password("captain").roles("CAPTAIN")
            .and()
            .withUser("commander").password("commander").roles("COMMANDER")
            .and()
            .withUser("lieutenant").password("lieutenant").roles("LIEUTENANT")
            .and()
            .withUser("ensign").password("ensign").roles("ENSIGN")
            .and()
            .withUser("crewman").password("crewman").roles("CREWMAN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and().httpBasic();
    }
}