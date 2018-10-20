package filters;

import wrappers.PreludeResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebFilter(urlPatterns = "/JavaServerPages/*")
public class PreludeCodaFilter implements Filter {

    private FilterConfig filter = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filter = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PreludeResponseWrapper wrapper = new PreludeResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(servletRequest,wrapper);

        String content = wrapper.toString();
        StringWriter sw = new StringWriter();
        sw.write(content);
        sw.write("Thank you coda!");

        PrintWriter out = servletResponse.getWriter();
        out.write(sw.toString());
    }

    @Override
    public void destroy() {

    }
}
