package com.wissensalt.vaadinloginapp.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route("/session-expired")
public class SessionExpiredView extends VerticalLayout {

  public SessionExpiredView() {
    add(new H1("Your session is Expired"));
    add(new RouterLink("Login", LoginView.class));
  }
}
