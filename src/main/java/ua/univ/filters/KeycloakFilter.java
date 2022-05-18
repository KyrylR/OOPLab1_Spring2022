package ua.univ.filters;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import ua.univ.utils.KeycloakTokenUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@WebFilter("/api/drivers/*")
public class KeycloakFilter implements Filter {
    private final List<String> requiredRoles = Arrays.asList("ROLE_ADMIN", "ROLE_MANAGER");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean hasRequiredRole = false;

        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            try {
                AccessToken accessToken = KeycloakTokenUtil.getToken(request, request.getHeader("Authorization"));
                Set<String> roles = KeycloakTokenUtil.getRoles(accessToken);
                for (String item: roles) {
                    if (requiredRoles.contains(item)) {
                        hasRequiredRole = true;
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
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

    @Override
    public void destroy() {

    }
}
