package ua.univ.events;

import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.*;
import ua.univ.DAO.DriverDAO;
import ua.univ.models.Driver;

import javax.ws.rs.core.MultivaluedMap;
import java.sql.SQLException;
import java.util.Objects;

public class RegistrationEventListenerProvider implements EventListenerProvider {
    private final KeycloakSession session;
    private final RealmProvider model;

    public RegistrationEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) throws RuntimeException {

        if (EventType.REGISTER.equals(event.getType())) {
            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel newRegisteredUser = this.session.users().getUserById(event.getUserId(), realm);

            HttpRequest req = session.getContext().getContextObject(HttpRequest.class);
            MultivaluedMap<String, String> formParameters = req.getFormParameters();

            String ourRole = formParameters.get("role").toString();
            DriverDAO driver;

            try {
                driver = new DriverDAO();


                if (Objects.equals(ourRole, "[manager]")) {
                    RoleModel roleModel = realm.getClientById(realm.getClientByClientId("autobase").getId()).getRole("manager");
                    System.out.println("Our Role model: " + roleModel.getName());
                    newRegisteredUser.grantRole(roleModel);
                }

                if (Objects.equals(ourRole, "[driver]")) {
                    RoleModel roleModel = realm.getClientById(realm.getClientByClientId("autobase").getId()).getRole("driver");
                    System.out.println("Our Role model: " + roleModel.getName());
                    newRegisteredUser.grantRole(roleModel);

                    driver.saveDriver(new Driver(-1, newRegisteredUser.getFirstName()));
                }
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.fillInStackTrace().getMessage());
            }

            System.out.println("Hello, am I alive? Am I? (._.) ( l: ) ( .-. ) ( :l ) (._.) -> " + newRegisteredUser.getUsername());
        }

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}
