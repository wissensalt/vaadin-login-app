package com.wissensalt;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;

@Route("admin")
@PageTitle("Admin View")
@RolesAllowed({"ROLE_ADMIN", "ADMIN"})
public class AdminView extends VerticalLayout {

  private final SecurityService securityService;

  public AdminView(AuthenticationContext authenticationContext, SecurityService securityService) {
    this.securityService = securityService;
    System.out.println("Welcome to ADMIN VIEW");
    authenticationContext.getPrincipalName().ifPresent(e -> this.add(new H1("Welcome Admin " + e)));
    add(new RouterLink("Go to Main View", MainView.class));
  }
}
