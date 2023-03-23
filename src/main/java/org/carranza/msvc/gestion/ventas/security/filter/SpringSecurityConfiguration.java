package org.carranza.msvc.gestion.ventas.security.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.security.configuration.JWTHTTPConfigurer;
import org.carranza.msvc.gestion.ventas.security.utils.JWTUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfiguration {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JWTUtils jWTUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(new JWTAuthEntryPoint());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/auth/**").permitAll());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/libre/**").permitAll());

        http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/public/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/venta/**").hasAnyRole("ADMIN", "VENDEDOR")
                .requestMatchers("/almacen/**").hasAnyRole("ADMIN", "ALMACENERO")
                .requestMatchers("/private/**").hasRole("ADMIN")
				.requestMatchers("/shared/**").hasAnyRole("ADMIN", "USER", "VENDEDOR", "ALMACENERO")
//                .requestMatchers("/**").hasRole("ADMIN")
				.anyRequest()
				.authenticated());


        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.apply(new JWTHTTPConfigurer(jWTUtils));
        return http.build();
    }

    @Bean
    public JWTAuthorizationFilter jWTAuthorizationFilter() {
        return new JWTAuthorizationFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        log.info("Load authentication provider...");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
//                        .allowedOrigins("http://localhost:4200", "http://localhost:8086")
                        .allowedMethods("*");
//                        .allowedMethods("GET", "POST", "PUT", /*"DELETE"*/"HEAD", "OPTIONS")
//                        .allowedHeaders("Access-Control-Allow-Headers", "Authorization",
//                                "Access-Control-Allow-Origin", "Cache-Control", "Content-Type")
//                        .exposedHeaders("Access-Control-Allow-Headers", "Authorization",
//                                "Access-Control-Allow-Origin", "Cache-Control", "Content-Type");
            }
        };
    }

}
