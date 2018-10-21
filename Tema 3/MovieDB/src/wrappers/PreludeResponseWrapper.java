package wrappers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PreludeResponseWrapper extends HttpServletResponseWrapper {
    private CharArrayWriter output;
    private List<String> lstWelcomeForeignLanguage;

    public PreludeResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new CharArrayWriter();

        lstWelcomeForeignLanguage = new ArrayList<>();
        lstWelcomeForeignLanguage.add("Bine ati venit!");
        lstWelcomeForeignLanguage.add("Welcome!");
        lstWelcomeForeignLanguage.add("Bienvenue!");
        lstWelcomeForeignLanguage.add("Willkommen!");
        lstWelcomeForeignLanguage.add("Welkom!");
        lstWelcomeForeignLanguage.add("Bienvenido!");
    }

    @Override
    public PrintWriter getWriter() throws IOException {

        Random random = new Random();
        output.write("<p style=\"color:green;\"> <i><b>" + lstWelcomeForeignLanguage.get(random.nextInt(lstWelcomeForeignLanguage.size())) + "</b></i></p>");
        return new PrintWriter(output);
    }

    @Override
    public String toString() {
        return output.toString();
    }
}
