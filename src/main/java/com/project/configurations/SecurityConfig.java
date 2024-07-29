package com.project.configurations;

import com.project.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.FilterRegistration;
import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()  // Thêm cấu hình CORS vào đây
                .csrf().disable()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(
                        String.format("%s/auth/login", apiPrefix)
                )
                .permitAll()
                .antMatchers(GET,
                        String.format("%s/categories**", apiPrefix)).hasAnyRole("USER", "ADMIN")
                .antMatchers(POST,
                        String.format("%s/categories/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(PUT,
                        String.format("%s/categories/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(DELETE,
                        String.format("%s/categories/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(GET,
                        String.format("%s/products**", apiPrefix)).hasAnyRole("USER", "ADMIN")
                .antMatchers(POST,
                        String.format("%s/products/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(PUT,
                        String.format("%s/products/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(DELETE,
                        String.format("%s/products/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(POST,
                        String.format("%s/orders/**", apiPrefix)).hasAnyRole("USER")
                .antMatchers(GET,
                        String.format("%s/orders/**", apiPrefix)).hasAnyRole("USER", "ADMIN")
                .antMatchers(PUT,
                        String.format("%s/orders/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(DELETE,
                        String.format("%s/orders/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(POST,
                        String.format("%s/order_details/**", apiPrefix)).hasAnyRole("USER")
                .antMatchers(GET,
                        String.format("%s/order_details/**", apiPrefix)).hasAnyRole("USER", "ADMIN")
                .antMatchers(PUT,
                        String.format("%s/order_details/**", apiPrefix)).hasRole("ADMIN")
                .antMatchers(DELETE,
                        String.format("%s/order_details/**", apiPrefix)).hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Bean
    public FilterRegistrationBean corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()
        ));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(-102);
        return bean;
    }
}
