package controllers;

import Util.PropertiesManager;
import Util.RedirectSender;
import com.sun.istack.internal.NotNull;
import internal.MovieDetails;
import internal.PageState;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Main controller of the application
 * It receives data from input.jsp.
 * Processes received that and acts accordingly.
 *  1. Forward request to result.jsp on correct input
 *  2. Forwards request to input.jsp with error message on incorrect input
 */
@WebServlet(name = "InputController" , urlPatterns = "/InputController")
public class InputController extends HttpServlet {
    static Map<String, PageState> pageStateMap = new HashMap<>();
    PropertiesManager propertiesManager;

    @Override
    public void init() throws ServletException {
        propertiesManager = PropertiesManager.getPropertiesManagerInstance();
    }


    /**
     *
     * Sets cookie and process the request depending on type of operations
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("captcha")!=null) {
            if(verifyCAPTCHA(request, response)) {
                setCookie(request, response);
                String requestType = request.getParameter("operationType");
                switch (requestType.toLowerCase()) {
                    case "create":
                        createRecord(request, response);
                        break;
                    case "get":
                        showAllRecords(request, response);
                        break;
                    case "update":
                        updateRecord(request, response);
                        break;
                }
            }
        }else{
            RedirectSender.showInputError(request,response,"Invalid CAPTCHA");
        }

    }

    /**
     * Constructs a movie from request data and calls PropertiesManager.updateMovie
     * @throws ServletException
     * @throws IOException
     */
    private void updateRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String genre = request.getParameter("genre");
        MovieDetails movieDetails = new MovieDetails(name, description, genre);
        if (propertiesManager.updateMovie(movieDetails))
            showAllRecords(request, response);
        else
            RedirectSender.showInputError(request, response, "Movie name does not exist");

    }
    /**
     * Constructs a movie from request data and calls PropertiesManager.addNewMovie
     * @throws ServletException
     * @throws IOException
     */
    private void createRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String genre = request.getParameter("genre");
        MovieDetails movieDetails = new MovieDetails(name, description, genre);
        if (propertiesManager.addNewMovie(movieDetails)) {
            showAllRecords(request, response);
        } else {
            RedirectSender.showInputError(request, response, "Movie name already exists! Try update.");
        }


    }

    /**
     *  forwards the request to input.jsp as an error with message to be displayed
     * @param message
     * @throws ServletException
     * @throws IOException
     */
//    private void showInputError(HttpServletRequest request, HttpServletResponse response,String message) throws ServletException, IOException {
//        request.getSession().setAttribute("error", message);
//        //getServletContext().getRequestDispatcher("/JavaServerPages/input.jsp").forward(request,response);
//        response.sendRedirect("/JavaServerPages/input.jsp");
//    }

    /**
     * Validates each field in form
     * @return true if all fields are valid, false and forward as error to input.jsp otherwise
     * @throws ServletException
     * @throws IOException
     */

    /**
     * Takes data from database and forwards it to result.jsp in order to be displayed
     * @throws ServletException
     * @throws IOException
     */
    private void showAllRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TreeMap<Integer, MovieDetails> movies = propertiesManager.getPropertiesAsMap();
        //String databaseOutput = "OK!<br>";
        String databaseOutput = "";
        databaseOutput+="<tr>";
        databaseOutput+="<th width=25%>Movie name</th>";
        databaseOutput+="<th width=25%>Movie description</th>";
        databaseOutput+="<th width=25%>Movie genre</th>";
        databaseOutput+="</tr>";
//        for (int i = 1; i <= movies.size(); i++) {
//            MovieDetails movie = movies.get(i);
//            databaseOutput += "<pre>" + "    Name:  " + movie.getName() + " with ID: " + i + "\n";
//            databaseOutput += "    Description:  " + movie.getDescription() + "\n";
//            databaseOutput += "    Genre:  " + movie.getGenre() + " </pre>" + "\n";
//
//        }
        for (int i = 1; i <= movies.size(); i++) {
            MovieDetails movie = movies.get(i);
            databaseOutput+="<tr>";
            databaseOutput+="<td width=25%>"+movie.getName()+"</td>";
            databaseOutput+="<td width=25%>"+movie.getDescription()+"</td>";
            databaseOutput+="<td width=25%>"+movie.getGenre()+"</td>";
            databaseOutput+="</tr>";
        }
        request.getSession().setAttribute("database", databaseOutput);
        //getServletContext().getRequestDispatcher("/JavaServerPages/result.jsp").forward(request, response);
        //request.getSession().setAttribute("lastmovieadded",request.getParameter("name"));
        response.sendRedirect("/JavaServerPages/result.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getOutputStream().println("saved");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response){

    }

    /**
     * Creates a PageState instance and a key
     * Key is stores as a cookie on client side
     * The pair is added to pageStateMap
     */
    private void setCookie(@NotNull HttpServletRequest request, HttpServletResponse response){
        String key = ""+System.currentTimeMillis();
        Cookie[] cookies = request.getCookies();
        for (Cookie c: cookies) {
            if (c.getName().equals("clientid")){
                key = c.getValue();
            }
        }
        PageState pageState = new PageState(request.getParameter("operationType"),
                request.getParameter("name"),
                request.getParameter("description"),
                request.getParameter("genre"));

        request.getSession().setAttribute("moviename",pageState.getName());
        request.getSession().setAttribute("moviedesc",pageState.getDescription());
        pageStateMap.put(key,pageState);
        Cookie cookie = new Cookie("clientid",key);
        cookie.setMaxAge(60*60);
        response.addCookie(cookie);

    }
    private boolean verifyCAPTCHA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String captcha = (String) request.getSession().getAttribute("captchakey");
        if(!request.getParameter("captcha").equals(captcha)){
            RedirectSender.showInputError(request,response,"Invalid CAPTCHA field");
            return false;
        }
        return true;
    }
    @Override
    public void destroy() {
        propertiesManager.store();
    }
}
