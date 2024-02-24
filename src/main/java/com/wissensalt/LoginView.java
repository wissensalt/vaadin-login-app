package com.wissensalt;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

@Route("login")
@PageTitle("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

  private final LoginForm loginForm;
  private final transient UserDetailsManager userDetailsManager;
  private final transient AuthenticationManager authenticationManager;

  public LoginView(
      UserDetailsManager userDetailsManager,
      AuthenticationManager authenticationManager
  ) {
    this.userDetailsManager = userDetailsManager;
    this.authenticationManager = authenticationManager;
    this.addClassName(Background.CONTRAST);
    this.setSizeFull();
    setJustifyContentMode(JustifyContentMode.CENTER);
    setAlignItems(Alignment.CENTER);
    LoginI18n i18n = LoginI18n.createDefault();
    LoginI18n.Form i18nForm = i18n.getForm();
    i18nForm.setTitle("Login App with Vaadin");
    i18nForm.setUsername("Username");
    i18nForm.setPassword("Password");
    i18nForm.setSubmit("Submit");
    i18n.setForm(i18nForm);

    LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
    i18nErrorMessage.setTitle("Wrong username/ password");
    i18nErrorMessage.setMessage("Please check your username/ Password.");
    i18n.setErrorMessage(i18nErrorMessage);

    loginForm = new LoginForm();
    loginForm.setI18n(i18n);
    loginForm.addLoginListener(loginEventComponentEventListener());
    add(loginForm);
  }

  private ComponentEventListener<AbstractLogin.LoginEvent> loginEventComponentEventListener() {

    return loginEvent -> {
      final String username = loginEvent.getUsername();
      final String password = loginEvent.getPassword();
      UserDetails userDetails;
      try {
        userDetails = userDetailsManager.loadUserByUsername(username);
      } catch (UsernameNotFoundException usernameNotFoundException) {
        Notification.show("User Not Found");
        loginForm.setError(true);

        return;
      }

      Authentication authentication = null;
      try {
        authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
      } catch (AuthenticationException authenticationException) {
        loginForm.setError(true);
      }

      if (authentication != null) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        VaadinSession.getCurrent().setAttribute("authenticated", userDetails);
        loginForm.getUI().ifPresent(ui -> ui.navigate(""));
      }
    };
  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
      loginForm.setError(true);
    }
  }
}
