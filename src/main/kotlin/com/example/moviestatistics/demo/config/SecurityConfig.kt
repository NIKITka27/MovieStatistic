package com.example.moviestatistics.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(private var userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder(12)

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/resources/**").permitAll()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/image/**").permitAll()
            .antMatchers("/registration").permitAll()
            .antMatchers("/review").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .defaultSuccessUrl("/review", true)
            .loginPage("/login").permitAll()
            .and()
            .logout()
            .permitAll()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true);

    }
}
