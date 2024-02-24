package com.wissensalt;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        auth -> {
          auth.requestMatchers(new AntPathRequestMatcher("/public/**")).permitAll();
          auth.requestMatchers(new AntPathRequestMatcher("/admin")).hasAnyRole("ADMIN");
        });

    super.configure(http);
    setLoginView(http, LoginView.class);
  }

  @Bean
  public UserDetailsManager userDetailsManager(BCryptPasswordEncoder bCryptPasswordEncoder) {
    final String password = bCryptPasswordEncoder.encode("password");
    final UserDetails userAdmin = User.withUsername("admin").password(password)
        .roles("ADMIN").build();
    final UserDetails userRegular = User.withUsername("user").password(password)
        .roles("USER").build();

    return new InMemoryUserDetailsManager(userAdmin, userRegular);
  }
}
