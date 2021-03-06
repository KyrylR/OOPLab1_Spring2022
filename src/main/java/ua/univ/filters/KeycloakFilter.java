package ua.univ.filters;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import ua.univ.utils.KeycloakTokenUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@WebFilter("/api/*")
public class KeycloakFilter implements Filter {
    private final List<String> requiredRoles = Arrays.asList("ROLE_ADMIN", "ROLE_MANAGER");

    private void CorsOptions(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS,  DELETE");
        response.addHeader("Access-Control-Allow-Headers", "*");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        this.CorsOptions(response);

        if (request.getMethod().equals("OPTIONS")) {
            return;
        }

        boolean hasRequiredRole = false;
        try {
            AccessToken accessToken = KeycloakTokenUtil.getToken(request, request.getHeader("Authorization"));
            Set<String> roles = KeycloakTokenUtil.getRoles(Objects.requireNonNull(accessToken));
            for (String item : roles) {
                if (requiredRoles.contains(item)) {
                    hasRequiredRole = true;
                    break;
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            response.setStatus(401);
            return;
        }

        if (hasRequiredRole) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.setStatus(403);
        }
    }
}

