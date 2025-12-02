package Projeto.Concessionaria.NathanLucas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Administrador admin = administradorRepository.findByEmail(email);
            if (admin != null) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(admin.getEmail())
                        .password(admin.getSenha())
                        .roles("ADMIN")
                        .build();
            }

            return usuarioDAO.findByEmail(email)
                    .map(u -> org.springframework.security.core.userdetails.User.builder()
                            .username(u.getEmail())
                            .password(u.getSenha())
                            .roles(u.getRole())
                            .build()
                    )
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/Fotos/**", "/js/**", "/fotos_perfil/**").permitAll()
                .requestMatchers("/register", "/longi").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/loja", "/perfil", "/atualizarFoto").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/longi")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("senha")
                .defaultSuccessUrl("/loja", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/longi?logout")
                .permitAll()
            );

        return http.build();
    }
}
