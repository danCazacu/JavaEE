package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "EntryPoint", urlPatterns = "/")
public class EntryPoint extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //needs a different approach for number and genre names
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Action");
        genres.add("Comedy");
        genres.add("Horror");
        genres.add("Thriller");
        genres.add("Drama");


        String selectedOption = "Thriller";
        StringBuilder select = new StringBuilder();
        for (String s: genres) {
            String selected = "";
            if(s.equals(selectedOption))
                selected = "selected";
            select.append("<option value=\"").append(s).append("\" ").append(selected).append(">").append(s).append("</option>\n");
        }

//                "        <option value=\"action\" selected>Action</option>\n" +
//                "        <option value=\"comedy\">Comedy</option>\n" +
//                "        <option value=\"horror\">Horror</option>\n" +
//                "        <option value=\"thriller\">Thriller</option>\n" +
//                "        <option value=\"drama\">Drama</option>";
        request.getSession().setAttribute("error","");
        request.getSession().setAttribute("select", select.toString());
        request.getRequestDispatcher("/input.jsp").forward(request,response);


    }
}
