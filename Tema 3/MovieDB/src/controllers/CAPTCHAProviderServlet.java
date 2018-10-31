package controllers;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.AttributedString;
import java.util.Random;

@WebServlet(name = "CAPTCHAProviderServlet", urlPatterns = "/CAPTCHAProviderServlet")
public class CAPTCHAProviderServlet extends HttpServlet {

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder s = new StringBuilder();
        Random random = new Random();
        for(int i=0;i < 4; i++){

            if(random.nextInt(100) > 50) {
                s.append(upper.charAt(random.nextInt(upper.length())));
            }else if(random.nextInt(100) < 50){

                s.append(upper.toLowerCase().charAt(random.nextInt(upper.length())));
            }else{

                s.append(random.nextInt(10));
            }
        }
        int width = 100;
        int height = 50;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.lightGray);
        Random r = new Random();
        Color randomColor0 = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()); //rgb
        Color randomColor1 = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()); //rgb
        Color randomColor2 = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()); //rgb
        Color randomColor3 = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()); //rgb

        Font font1 = new Font("Serif", Font.ITALIC, 25);
        Font font2 = new Font("Monospaced", Font.BOLD, 25);
        Font font3 = new Font("Courier", Font.ITALIC+Font.BOLD, 25);
        Font font4 = new Font("ZapfDingbats", Font.CENTER_BASELINE, 25);


        AttributedString as = new AttributedString(s.toString());
        as.addAttribute(TextAttribute.FONT, font1);
        as.addAttribute(TextAttribute.FONT, font2, 1, 2);
        as.addAttribute(TextAttribute.FONT, font3, 2, 3);
        as.addAttribute(TextAttribute.FONT, font4, 3, 4);
        as.addAttribute(TextAttribute.FOREGROUND, randomColor0, 0, 1);
        as.addAttribute(TextAttribute.FOREGROUND, randomColor1, 1, 2);
        as.addAttribute(TextAttribute.FOREGROUND, randomColor2, 2, 3);
        as.addAttribute(TextAttribute.FOREGROUND, randomColor3, 3, 4);

        g2d.fillRect(0, 0, width, height);
        g2d.drawString(as.getIterator(), 25, 25);
        g2d.dispose();

        ImageIO.write(bufferedImage, "png", response.getOutputStream());
        request.getSession().setAttribute("captchakey",s.toString());

    }
}
