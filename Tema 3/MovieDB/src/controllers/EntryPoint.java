package controllers;

import Util.PropertiesManager;
import internal.PageState;
import jdk.internal.util.xml.impl.Input;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@WebServlet(name = "EntryPoint", urlPatterns = "/")
/**
 * Entry point servlet of the application
 * It generates select form and restores the state using cookies if needed and then forwards the request to input.jsp
 */
public class EntryPoint extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() throws ServletException {
        String path = getServletContext().getRealPath("Resources/database.properties");
        PropertiesManager propertiesManager = PropertiesManager.getPropertiesManagerInstance(path);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        PageState pageState = null;
        if(cookies!=null) {
            for (Cookie cook : cookies) {
                if (cook.getName().equals("clientid")) {
                    pageState = InputController.pageStateMap.get(cook.getValue());
                }
            }
        }
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Action");
        genres.add("Comedy");
        genres.add("Horror");
        genres.add("Thriller");
        genres.add("Drama");


        //getcookie here

        String selectedOption = "";
        if(pageState!=null){
            selectedOption = pageState.getGenre();
        }
        StringBuilder select = new StringBuilder();
        for (String s: genres) {
            String selected = "";
            if(s.equals(selectedOption))
                selected = "selected";
            select.append("<option value=\"").append(s).append("\" ").append(selected).append(">").append(s).append("</option>\n");
        }

        request.getSession().setAttribute("error","");
        request.getSession().setAttribute("select", select.toString());
        request.getSession().setAttribute("moviename","");
        request.getSession().setAttribute("moviedesc","");
        if(pageState!=null){
            request.getSession().setAttribute("moviename",pageState.getName());
            request.getSession().setAttribute("moviedesc",pageState.getDescription());
        }

        //request.getRequestDispatcher("/JavaServerPages/input.jsp").forward(request,response);
        response.sendRedirect("/JavaServerPages/input.jsp");

    }
}
