package net.raissa.students.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final String[] SWAGGER_WHITELIST = new String[] { "/auth/login/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**" };

    private static final String[] SWAGGER_WHITELIST_H2 = new String[] { "/**/*.html", "/**/*.{png,jpg,jpeg,svg.ico}", "/**/*.css", "/**/*.js" };

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return new InMemoryUserDetailsManager(new UserDetails[] { User.withUsername("user1").password(passwordEncoder.encode("1234")).authorities(new String[] { "USER" }).build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).authorities(new String[] { "USER", "ADMIN" }).build() });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return (PasswordEncoder)new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return (SecurityFilterChain)httpSecurity.headers(headers -> headers.frameOptions().disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(ar -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)ar.requestMatchers(SWAGGER_WHITELIST)).permitAll())
                .authorizeHttpRequests(ar -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)ar.anyRequest()).authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        return (JwtEncoder)new NimbusJwtEncoder((JWKSource)new ImmutableSecret(this.secretKey.getBytes()));
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.secretKey.getBytes(), "RSA");
        return (JwtDecoder) NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return (AuthenticationManager)new ProviderManager(new AuthenticationProvider[] { (AuthenticationProvider)daoAuthenticationProvider });
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return (CorsConfigurationSource)source;
    }
}
