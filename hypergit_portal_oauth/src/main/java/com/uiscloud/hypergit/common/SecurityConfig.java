package com.uiscloud.hypergit.common;

import com.uiscloud.hypergit.member.service.MemberService;
import com.uiscloud.hypergit.common.config.auth.CustomOAuth2UserService;
import com.uiscloud.hypergit.user.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//    private final CustomOAuth2UserService customOAuth2UserService;

    private MemberService memberService;

    protected SecurityConfig(MemberService memberService, CustomOAuth2UserService customOAuth2UserService) {
        this.memberService =memberService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(getResources()).permitAll()
                .antMatchers("/help","/api/**").permitAll()
                //.antMatchers("/admin/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                //.antMatchers("/").authenticated()
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                .logoutSuccessUrl("/admin")
                .invalidateHttpSession(true)
                .and()
                .csrf()
                .ignoringAntMatchers("/admin/*post")
                .ignoringAntMatchers("/admin/*del")
                .ignoringAntMatchers("/admin/*search")
                .ignoringAntMatchers("/admin/*/edit")
                .ignoringAntMatchers("/admin/post")
                //.ignoringAntMatchers("/post")
                .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/admin/denied");


//        http.authorizeRequests()
//                .antMatchers("/login","/","/login/oauth2/code/wso2").permitAll()
//                .antMatchers("/help","/api/**").permitAll()
//                .anyRequest().authenticated()
//                //.antMatchers("/user/**").authenticated()
//                .and()
//                .csrf()
//                .ignoringAntMatchers("/admin/*post")
//                .ignoringAntMatchers("/admin/*del")
//                .ignoringAntMatchers("/admin/*search")
//                .ignoringAntMatchers("/admin/*/edit")
//                .ignoringAntMatchers("/admin/post")
//                //.ignoringAntMatchers("/post")
//                .and()
//                // 403 예외처리 핸들링
//                .exceptionHandling().accessDeniedPage("/admin/denied")
//                .and() // 로그아웃 설정
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/")
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint() // 로그인 이후 사용자 정보를 가져올 때 설정
//                .userService(customOAuth2UserService);

//        http
//                .csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login","/","/login/oauth2/code/wso2").permitAll()
//                .antMatchers("/help","/api/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/")
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint()
//                .userService(this.customOAuth2UserService);

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

