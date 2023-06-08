package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns =  {"/user/*", "/login.jsp", "/register.jsp"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        if ((session.getAttribute("usuario") == null || session.getAttribute("logado") != "true") 
            && !req.getRequestURI().endsWith("login.jsp") && !req.getRequestURI().endsWith("register.jsp") && !req.getRequestURI().endsWith("index.jsp")) {
                resp.sendRedirect("login.jsp");
        }
        else if((session.getAttribute("usuario") != null && session.getAttribute("logado") == "true")
            && (req.getRequestURI().endsWith("login.jsp") || req.getRequestURI().endsWith("register.jsp"))) {
                resp.sendRedirect(req.getContextPath()+"/user");
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
