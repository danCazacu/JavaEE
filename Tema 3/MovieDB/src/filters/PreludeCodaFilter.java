package filters;

import wrappers.PreludeResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

@WebFilter(urlPatterns = "/JavaServerPages/*")
public class PreludeCodaFilter implements Filter {

    List<String> lstQuotes = new ArrayList<>();
    private FilterConfig filter = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filter = filterConfig;

        lstQuotes.add("\"Don't cry because it's over, smile because it happened.\" ― Dr. Seuss ");
        lstQuotes.add("\"Two things are infinite: the universe and human stupidity; and I'm not sure about the universe.\" ― Albert Einstein");
        lstQuotes.add("\"You only live once, but if you do it right, once is enough.\" ― Mae West");
        lstQuotes.add("\"It is better to be hated for what you are than to be loved for what you are not.\"― Andre Gide, Autumn Leaves");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        PreludeResponseWrapper wrapper = new PreludeResponseWrapper((HttpServletResponse) servletResponse);

        filterChain.doFilter(servletRequest,wrapper);

        String content = wrapper.toString();
        StringWriter sw = new StringWriter();
        sw.write(content);

        Random random = new Random();
        sw.write("<p style=\"color:blue;\"> <i><b>" + lstQuotes.get(random.nextInt(4)) + "</b></i></p>");

        PrintWriter out = servletResponse.getWriter();
        out.write(sw.toString());
    }

    @Override
    public void destroy() {

    }
}
