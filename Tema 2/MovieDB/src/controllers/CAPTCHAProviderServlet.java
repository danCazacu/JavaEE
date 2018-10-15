package controllers;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@WebServlet(name = "CAPTCHAProviderServlet", urlPatterns = "/CAPTCHAProviderServlet")
public class CAPTCHAProviderServlet extends HttpServlet {

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder s = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<4;i++){
            s.append(upper.charAt(random.nextInt(upper.length())));
        }
        int width = 100;
        int height = 25;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.white);
        g2d.drawString(s.toString(), 50, 10);
        g2d.dispose();

        ImageIO.write(bufferedImage, "png", response.getOutputStream());
        request.getSession().setAttribute("captchakey",s.toString());

    }
}
