package controllers;

import Util.PropertiesManager;
import internal.MovieDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import java.util.TreeMap;

@WebServlet(name = "InputController" , urlPatterns = "/InputController")
public class InputController extends HttpServlet {

    PropertiesManager propertiesManager;
    @Override
    public void init() throws ServletException {
        String path = getServletContext().getRealPath("Resources/database.properties");
        propertiesManager = new PropertiesManager(path);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("operationType")==null) {
            request.getSession().setAttribute("error", "Please select type of operation");
            getServletContext().getRequestDispatcher("/input.jsp").forward(request,response);
        }else if(request.getParameter("operationType").toLowerCase().equals("create")){
            createRecord(request,response);
        }else if(request.getParameter("operationType").toLowerCase().equals("get")){
            showAllRecords(request,response);
        }else if(request.getParameter("operationType").toLowerCase().equals("update")){
            //TODO update operation with comparison on movie name
        }

    }
    private void createRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(checkRequestParameters(request,response)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String genre = request.getParameter("genre");
            MovieDetails movieDetails = new MovieDetails(name, description, genre);
            propertiesManager.addNewMovie(movieDetails);
            showAllRecords(request, response);
        }
    }
    private boolean checkRequestParameters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if(name==null){
            request.getSession().setAttribute("error", "Invalid name");
            getServletContext().getRequestDispatcher("/input.jsp").forward(request,response);
            return false;
        }
        if (name.trim().length()<=0) {
            request.getSession().setAttribute("error", "Invalid name");
            getServletContext().getRequestDispatcher("/input.jsp").forward(request, response);
            return false;
        }
        String description = request.getParameter("description");
        if(description==null){
            request.getSession().setAttribute("error", "Invalid description");
            getServletContext().getRequestDispatcher("/input.jsp").forward(request,response);
            return false;
        }
        if (description.trim().length()<=0) {
            request.getSession().setAttribute("error", "Invalid description");
            getServletContext().getRequestDispatcher("/input.jsp").forward(request, response);
            return false;
        }
        return true;
    }
    private void showAllRecords(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TreeMap<Integer, MovieDetails> movies = propertiesManager.getPropertiesAsMap();
        String databaseOutput = "OK!\n";
        for (int i = 1; i <= movies.size(); i++) {
            MovieDetails movie = movies.get(i);
            databaseOutput += "<pre>" + "    Name:  " + movie.getName() + "with ID: " + i + "\n";
            databaseOutput += "    Description:  " + movie.getDescription() + "\n";
            databaseOutput += "    Genre:  " + movie.getGenre() + " </pre>" + "\n";

        }
        request.setAttribute("database", databaseOutput);
        getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getOutputStream().println("saved");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response){

    }
    @Override
    public void destroy() {
        propertiesManager.store();
    }


}
