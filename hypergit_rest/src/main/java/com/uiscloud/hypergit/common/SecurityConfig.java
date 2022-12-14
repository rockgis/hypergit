package com.uiscloud.hypergit.common;

import com.uiscloud.hypergit.member.service.MemberService;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private MemberService memberService;

    protected SecurityConfig(MemberService memberService) {
        this.memberService =memberService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception
//    {
////        PathRequest.toStaticResources().atCommonLocations()
//        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
//        web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .antMatchers(getResources()).permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/admin/**").authenticated()
                    //.antMatchers("/").authenticated()
                .and() // 로그인 설정
                    .formLogin()
                    .loginPage("/admin/login")
                    .defaultSuccessUrl("/admin")
                    .permitAll()
                .and() // 로그아웃 설정
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/admin")
                    .invalidateHttpSession(true)
                .and()
                    .csrf()
                    .ignoringAntMatchers("/admin/*post")
                    .ignoringAntMatchers("/admin/*del")
                    .ignoringAntMatchers("/admin/post")
                    .ignoringAntMatchers("/admin/post")
                    .ignoringAntMatchers("/post")
                .and()
                    .oauth2Login().loginPage("/login")
                .and()
                // 403 예외처리 핸들링
                    .exceptionHandling().accessDeniedPage("/user/denied");

        http.authorizeRequests()
                .antMatchers("/login","/","/login/oauth2/code/wso2").permitAll()
                .antMatchers("/help","/api/**").permitAll()
                .anyRequest().authenticated()
                //.antMatchers("/user/**").authenticated()
                .and()
                .oauth2Login().loginPage("/login");

//              .and().csrf().disable();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    private String[] getResources() {
        return  Arrays.stream(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .toArray(String[]::new);
    }
}

