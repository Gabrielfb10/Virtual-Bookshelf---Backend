package com.acirio.virtual_bookshelf.config;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Registro manual do H2 Console — necessário no Spring Boot 4.x,
    // pois a autoconfiguration pode não ativar quando o datasource é configurado via env vars
    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2ConsoleServlet() {
        ServletRegistrationBean<JakartaWebServlet> registration =
                new ServletRegistrationBean<>(new JakartaWebServlet());
        registration.addUrlMappings("/h2-console/*");
        registration.addInitParameter("webAllowOthers", "false");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita o salvamento do estado da sessão
            .csrf(csrf -> csrf.disable())
            // Faz com que o estado da sessão não seja guardado
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Define os tipos de erros que serão disparados em casos de acesso negado por autenticação ou por falta de permissao
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                    .accessDeniedHandler((request, response, accessDeniedException) -> response.setStatus(HttpStatus.FORBIDDEN.value()) )
            )

            // Permite que iframes sejam carregados na mesma origem (necessário para o H2)
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))

            // Libera o acesso às URLs de autenticação (login e registro) sem precisar de login, mas exige login para o resto
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/auth/**").permitAll()
                .anyRequest().authenticated()
            )

            // Adiciona a nossa verificação para ser feita antes da padrao do spring
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

            // Mantém o formulário de login para o restante da API
            .formLogin(form -> form.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

