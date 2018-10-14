package controllers;

import Util.PropertiesManager;
import com.sun.deploy.net.HttpRequest;
import internal.MovieDetails;
import internal.PageState;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

@WebServlet(name = "InputController" , urlPatterns = "/InputController")
public class InputController extends HttpServlet {
    static Map<String, PageState> pageStateMap = new HashMap<>();
    PropertiesManager propertiesManager;
    @Override
    public void init() throws ServletException {
        String path = getServletContext().getRealPath("Resources/database.properties");
        propertiesManager = new PropertiesManager(path);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCookie(request,response);
        if(request.getParameter("operationType")==null) {
            showInputError(request,response,"Please select type of operation");
        }else if(request.getParameter("operationType").toLowerCase().equals("create")){
            createRecord(request,response);
        }else if(request.getParameter("operationType").toLowerCase().equals("get")){
            showAllRecords(request,response);
        }else if(request.getParameter("operationType").toLowerCase().equals("update")){
            updateRecord(request,response);
        }

    }
    private void updateRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if(checkRequestParameters(request,response)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String genre = request.getParameter("genre");
            MovieDetails movieDetails = new MovieDetails(name, description, genre);
            if(propertiesManager.updateMovie(movieDetails))
                showAllRecords(request, response);
            else
                showInputError(request,response,"Movie name does not exist");
        }
    }
    private void createRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(checkRequestParameters(request,response)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String genre = request.getParameter("genre");
            MovieDetails movieDetails = new MovieDetails(name, description, genre);
            if(propertiesManager.addNewMovie(movieDetails))
            {
                showAllRecords(request, response);
            }
            else{
                showInputError(request,response,"Movie name already exists! Try update.");
            }

        }
    }
    private void showInputError(HttpServletRequest request, HttpServletResponse response,String message) throws ServletException, IOException {
        request.getSession().setAttribute("error", message);
        getServletContext().getRequestDispatcher("/input.jsp").forward(request,response);
    }
    private boolean checkRequestParameters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if(name==null){
            showInputError(request,response,"Invalid name");
            return false;
        }
        if (name.trim().length()<=0) {
            showInputError(request,response,"Invalid name");
            return false;
        }
        String description = request.getParameter("description");
        if(description==null){
            showInputError(request,response,"Invalid description");
            return false;
        }
        if (description.trim().length()<=0) {
            showInputError(request,response,"Invalid description");
            return false;
        }
        return true;
    }
    private void showAllRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TreeMap<Integer, MovieDetails> movies = propertiesManager.getPropertiesAsMap();
        String databaseOutput = "OK!<br>";
        databaseOutput+="<tr>";
        databaseOutput+="<th>Movie name</th>";
        databaseOutput+="<th>Movie description</th>";
        databaseOutput+="<th>Movie genre</th>";
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
            databaseOutput+="<th>"+movie.getName()+"</th>";
            databaseOutput+="<th>"+movie.getDescription()+"</th>";
            databaseOutput+="<th>"+movie.getGenre()+"</th>";
            databaseOutput+="</tr>";
        }
        request.setAttribute("database", databaseOutput);
        getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getOutputStream().println("saved");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response){

    }
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
    @Override
    public void destroy() {
        propertiesManager.store();
    }
}
