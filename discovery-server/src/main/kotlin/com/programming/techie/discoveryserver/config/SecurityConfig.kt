package com.programming.techie.discoveryserver.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    
    @Value("\${eureka.username}")
    lateinit var username: String
    
    @Value("\${eureka.password}")
    lateinit var password: String
    
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(username).password(password).authorities("USER")
    }
    
    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable()
                .authorizeRequests { it.anyRequest().authenticated() }
                .httpBasic()
    }
    
}