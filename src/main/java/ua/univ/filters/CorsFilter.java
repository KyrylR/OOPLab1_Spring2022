package ua.univ.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.addHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS,  DELETE");
        httpResponse.addHeader("Access-Control-Allow-Headers", "*");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
