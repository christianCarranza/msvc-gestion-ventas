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
				.requestMatchers("/public/**").hasRole("USER")
                .requestMatchers("/venta/**").hasRole("VENDEDOR")
                .requestMatchers("/almacen/**").hasRole("ALMACENERO")
                .requestMatchers("/private/**").hasRole("ADMIN")
				.requestMatchers("/shared/**").hasAnyRole("ADMIN", "USER", "VENDEDOR", "ALMACENERO")
                .requestMatchers("/**").hasRole("ADMIN")
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

}
