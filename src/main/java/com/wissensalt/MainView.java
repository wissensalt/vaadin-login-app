package com.wissensalt;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.userdetails.User;

@Route("")
@PageTitle("Main View")
@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
public class MainView extends VerticalLayout {

  public MainView(SecurityService securityService, AuthenticationContext authenticationContext) {
    final H1 h1 = new H1("This is Secured Main View");
    add(h1);
    authenticationContext.getAuthenticatedUser(User.class).ifPresent(e -> {
      final String principal = e.getUsername();
      final H2 h2 = new H2("Welcome " + principal);
      add(h2);

      final Button logoutButton = new Button();
      logoutButton.setText("Logout");
      logoutButton.addClickListener(logoutEvent -> securityService.logout());
      add(logoutButton);

      boolean isAdmin = e.getAuthorities().stream()
          .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
      if (isAdmin) {
        add(new RouterLink("Go to Admin View", AdminView.class));
      }
    });
  }
}
