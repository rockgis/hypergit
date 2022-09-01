package com.goodmit.hypergit.common;

import com.goodmit.hypergit.member.service.MemberService;
import com.goodmit.hypergit.user.service.CustomOAuth2UserService;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private MemberService memberService;

    private final CustomOAuth2UserService customOAuth2UserService;

    protected SecurityConfig(MemberService memberService, CustomOAuth2UserService customOAuth2UserService) {
        this.memberService =memberService;
        this.customOAuth2UserService = customOAuth2UserService;
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


        http.authorizeRequests()
                .antMatchers("/login","/","/login/oauth2/code/wso2").permitAll()
                .antMatchers("/help","/api/**").permitAll()
                //.anyRequest().authenticated()
                .antMatchers("/user/**").authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
               // .userInfoEndpoint() // 로그인 이후 사용자 정보를 가져올 때 설정
                //.userService(customOAuth2UserService)
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

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

