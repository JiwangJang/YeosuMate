package com.yeosu_mate.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.yeosu_mate.backend.filter.SessionCheckFilter;
import com.yeosu_mate.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class AuthConfig {
    private final SessionCheckFilter sessionCheckFilter;
    private final UserRepository userRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // JSON으로 데이터를 주고받으므로 referer 확인을 통해 csrf 방어를 구성
        httpSecurity.csrf(csrf -> csrf.disable());

        // Autentication required path setting
        /**
         * 나의 경우 JSON으로 데이터를 주고받기 때문에 프론트에서 우선적으로 OPTIONS메서드로 Preflight요청을 날리기 때문에 이를
         * 허용해줘야함
         */
        httpSecurity
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.OPTIONS, "/**/*")
                                .permitAll()
                                .requestMatchers("/auth/register", "/auth/login", "/error")
                                .permitAll()
                                .anyRequest().authenticated());

        // session configuration, 각 상황에서 던져지는 에러 핸들링
        httpSecurity.sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true));

        // setting securityContextRepository
        httpSecurity.securityContext(context -> context.securityContextRepository(securityContextRepository()));

        // CORS header setting
        httpSecurity.cors(cors -> cors.configurationSource(configurationSource()));

        httpSecurity.addFilter(sessionCheckFilter);

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userRepository);
        provider.setPasswordEncoder(getPasswordEncoder());

        return provider;
    }

    @Bean
    UrlBasedCorsConfigurationSource configurationSource() {
        // CorsConfiguration Object Setting(AllowOriginsHeader Setting)
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type"));

        // Path that applied configuration setting
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}