package code.climb.cccars.security;

import code.climb.cccars.security.filter.JWTAuthenticationFilter;
import code.climb.cccars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final UserService userService;
    private final JWTAuthenticationFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserService userService, JWTAuthenticationFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/login", "/signup").permitAll();

                    auth.requestMatchers("/user/add-admin").hasAuthority("SUPER_ADMIN");
                    auth.requestMatchers("/user/filter").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/user/**").hasAuthority("ADMIN");

                    auth.requestMatchers(HttpMethod.GET, "/listing/**").permitAll();

                    auth.requestMatchers(HttpMethod.GET, "/car/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/car/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/car/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/car/**").hasAuthority("ADMIN");

                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
