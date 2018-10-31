package Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectSender {

    static public void showInputError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.getSession().setAttribute("error", message);
        response.sendRedirect("/JavaServerPages/input.jsp");
    }
}
