package com.esenciayaroma.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Cifra la contraseña de la administradora.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Crea la cuenta administradora en memoria.
    @Bean
    public UserDetailsService userDetailsService(
            Environment environment,
            PasswordEncoder passwordEncoder) {

        String usuario = environment.getProperty("APP_ADMIN_USERNAME");
        String contrasena = environment.getProperty("APP_ADMIN_PASSWORD");

        if (usuario == null || usuario.isBlank()
                || contrasena == null || contrasena.length() < 12) {
            throw new IllegalStateException(
                    "Configura APP_ADMIN_USERNAME y APP_ADMIN_PASSWORD " +
                            "(contraseña de al menos 12 caracteres)."
            );
        }

        UserDetails administradora = User.withUsername(usuario)
                .password(passwordEncoder.encode(contrasena))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(administradora);
    }

    // Configura el inicio y cierre de sesión.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .usernameParameter("usuario")
                        .passwordParameter("contrasena")
                        .defaultSuccessUrl("/panel", true)
                        .failureUrl("/?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
