package com.wissensalt.vaadinloginapp.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.wissensalt.vaadinloginapp.service.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

@Route("admin")
@PageTitle("Admin View")
@RolesAllowed({"ROLE_ADMIN"})
public class AdminView extends VerticalLayout {

  public AdminView(SecurityService securityService) {
    final Authentication authentication = securityService.getUser();
    if (authentication != null) {
      final User principal = (User) authentication.getPrincipal();
      final H2 h2 = new H2("Welcome " + principal);
      add(h2);
    }
    final Button logoutButton = new Button();
    logoutButton.setText("Logout");
    logoutButton.addClickListener(logoutEvent -> securityService.logout());
    add(logoutButton);
    add(new RouterLink("Go to Main View", MainView.class));
  }
}
