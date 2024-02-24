package com.wissensalt;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;

@Route("admin")
@PageTitle("Admin View")
@AnonymousAllowed
@RolesAllowed("ROLE_ADMIN")
public class AdminView extends VerticalLayout {

  public AdminView(AuthenticationContext authenticationContext) {
    System.out.println("Welcome to ADMIN VIEW");
    authenticationContext.getPrincipalName().ifPresent(e -> this.add(new H1("Welcome Admin " + e)));
  }
}
