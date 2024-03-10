package com.wissensalt;

import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity httpSecurity,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      UserDetailsManager userDetailsManager) throws Exception {

    AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(
        AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsManager)
        .passwordEncoder(bCryptPasswordEncoder);

    return authenticationManagerBuilder.build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {

    return new BCryptPasswordEncoder();
  }

  public Authentication getUser() {
    final SecurityContext securityContext = SecurityContextHolder.getContext();

    return securityContext.getAuthentication();
  }

  public void logout() {
    final SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
    securityContextLogoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(),
        null, null);
  }
}
