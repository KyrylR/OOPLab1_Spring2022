package ua.univ.utils;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;

@Slf4j
public class KeycloakTokenUtil {
    public static final String RESOURCE = "spring-boot-client";

    private KeycloakTokenUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static AccessToken getToken(HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        KeycloakPrincipal<?> keycloakPrincipal = (KeycloakPrincipal<?>) principal;
        return keycloakPrincipal.getKeycloakSecurityContext().getToken();
    }

    public static AccessToken getToken(HttpServletRequest req, String tokenStr) throws VerificationException {
        String method = req.getMethod();
        if (method.equals("GET") || method.equals("POST") ||
                method.equals("DELETE") || method.equals("PATCH") || method.equals("PUT")) {
            try {
                tokenStr = tokenStr.replace("Bearer ", "");
                return TokenVerifier.create(tokenStr, AccessToken.class).getToken();
            } catch (VerificationException e) {
                String message = "Bad token provided!";
                log.error(message);
                throw new VerificationException(message);
            }
        }
        return null;
    }

    public static String getPreferredUsername(AccessToken token) {
        return token.getPreferredUsername();
    }

    public static Set<String> getRoles(AccessToken token) {
        return token.getResourceAccess(RESOURCE).getRoles();
    }
}
