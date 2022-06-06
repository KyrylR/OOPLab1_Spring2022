package ua.univ.events;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class RegistrationEventListenerProviderFactory implements EventListenerProviderFactory {
    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new RegistrationEventListenerProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
        // Do nothing
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // Do nothing
    }

    @Override
    public void close() {
        // Do nothing
    }

    @Override
    public String getId() {
        return "registration_event_listener";
    }
}
