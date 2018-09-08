package com.github.vspro.framework.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created  on 2018/9/8.
 */

@EnableWebSecurity
//优先级仅次于@EnableAuthorizationServer
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new RawPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //这里的requestMatchers是为了限制只拦截"/login","/oauth/**","/logout"url
            //走UserNamePasswordAuthenticationFilter
            //不然所有请求(包括Oauth2中ResourceServer资源Ulr)都会被UserNamePasswordAuthenticationFilter拦截
            .requestMatchers().antMatchers("/login","/oauth/**","/logout")
            .and()
            .authorizeRequests()
            .antMatchers("/login","/oauth/**","/logout").permitAll()
            .antMatchers("/index").authenticated()
            .antMatchers("/**").authenticated()
            .and()
            .formLogin()
            .passwordParameter("password")
            .usernameParameter("username")
            .loginProcessingUrl("/login")
            .loginPage("/login")
            .defaultSuccessUrl("/index")
            .and()
            .logout();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
    }
}
