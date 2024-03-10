package com.wissensalt;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

@Route("")
@PageTitle("Main View")
@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
public class MainView extends VerticalLayout {

  private final SecurityService securityService;
  public MainView(SecurityService securityService) {
    this.securityService = securityService;
    final H1 h1 = new H1("This is Secured Main View");
    add(h1);
    final Authentication authentication = securityService.getUser();
    if (authentication != null) {
      final User principal = (User) authentication.getPrincipal();
      final H2 h2 = new H2("Welcome " + principal);
      add(h2);

      final Button logoutButton = new Button();
      logoutButton.setText("Logout");
      logoutButton.addClickListener(logoutEvent -> securityService.logout());
      add(logoutButton);

      boolean isAdmin = authentication.getAuthorities().stream()
          .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
      if (isAdmin) {
        add(new RouterLink("Go to Admin View", AdminView.class));
      }
    }
  }
}
