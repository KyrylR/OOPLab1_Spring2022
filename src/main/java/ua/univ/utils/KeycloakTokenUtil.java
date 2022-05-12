package ua.univ.utils;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;

public class KeycloakTokenUtil {
    public static final String REALM = "autobase";

    public static AccessToken getToken(HttpServletRequest httpServletRequest){
        Principal principal = httpServletRequest.getUserPrincipal();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
        return keycloakPrincipal.getKeycloakSecurityContext().getToken();
    }

    public static String getPreferredUsername(HttpServletRequest httpServletRequest){
        Principal principal = httpServletRequest.getUserPrincipal();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
        AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
        return accessToken.getPreferredUsername();
    }

    public static Set<String> getRoles(HttpServletRequest httpServletRequest){
        Principal principal = httpServletRequest.getUserPrincipal();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
        return keycloakPrincipal.getKeycloakSecurityContext()
                .getToken().getResourceAccess(REALM).getRoles();
    }
}
