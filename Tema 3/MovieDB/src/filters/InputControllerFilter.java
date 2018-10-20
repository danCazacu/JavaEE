package filters;

import Util.RedirectSender;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebFilter(urlPatterns = "/InputController",
        initParams = @WebInitParam(name = "genre",value = "Unknown"))
public class InputControllerFilter implements Filter {
    private FilterConfig filter = null;
    private String name;
    private String description;
    private String requestType;
    private String genre;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filter = filterConfig;

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        name = servletRequest.getParameter("name");
        description = servletRequest.getParameter("description");
        requestType = servletRequest.getParameter("operationType");
        genre = servletRequest.getParameter("genre");

        if (requestType == null) {
            RedirectSender.showInputError((HttpServletRequest)servletRequest,(HttpServletResponse) servletResponse,"Please select type of operation");
        }
        else if(requestType.toLowerCase().equals("create") || requestType.toLowerCase().equals("update")) {
            ServletRequest response = validateParameters(servletRequest, servletResponse);
            filterChain.doFilter(response, servletResponse);
        }else {//for get no filtering is needed
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
    private ServletRequest validateParameters(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        if (name == null || name.trim().equals("")) {
            RedirectSender.showInputError((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, "Invalid name");
            return servletRequest;
        }
        if (description == null || description.trim().equals("")) {
            RedirectSender.showInputError((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, "Invalid description");
            return servletRequest;
        }
        if (genre == null || genre.trim().equals("")) {
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper((HttpServletRequest) servletRequest) {
                @Override
                public String getParameter(String name) {
                    String param = super.getParameter(name);
                    if (name.equals("genre")) {
                        return filter.getInitParameter("genre");
                    }
                    return param;
                }
            };
            return wrapper;
        }
        return servletRequest;
    }
    @Override
    public void destroy() {

    }

}
