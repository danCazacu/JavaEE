package wrappers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PreludeResponseWrapper extends HttpServletResponseWrapper {
    private CharArrayWriter output;
    public PreludeResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new CharArrayWriter();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        output.write("prelude");
        return new PrintWriter(output);
    }

    @Override
    public String toString() {
        return output.toString();
    }
}
