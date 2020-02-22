package com.IvanBlade.security.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /*引入日志*/
    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * 认证管理器
     * @param auth
     * throws Exception
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }

    /**
     * 资源权限配置
     * @param http
     * throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login/page")
                .loginProcessingUrl("/login/process")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                ;
    }

}
