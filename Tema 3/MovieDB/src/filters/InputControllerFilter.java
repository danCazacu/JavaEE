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
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filter = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String name = servletRequest.getParameter("name");
        String description = servletRequest.getParameter("description");
        String requestType = servletRequest.getParameter("operationType");
        String genre = servletRequest.getParameter("genre");

        if (requestType == null) {
            RedirectSender.showInputError((HttpServletRequest)servletRequest,(HttpServletResponse) servletResponse,"Please select type of operation");
        }
        else if(!requestType.toLowerCase().equals("get")) {
            if (name == null || name.trim().equals("")) {
                RedirectSender.showInputError((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, "Invalid name");
            } else if (description == null || description.trim().equals("")) {
                RedirectSender.showInputError((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, "Invalid description");
            } else if (genre == null || genre.trim().equals("")) {
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
                filterChain.doFilter(wrapper, servletResponse);
            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }else {//for get only
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

}
