package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MyThread extends Thread {

    CountDownLatch latch;
    ArrayList<String> namesList;

    public MyThread(CountDownLatch latch, ArrayList<String> names) {

        this.latch = latch;
        this.namesList = names;
    }


    @Override
    public void run() {
        try
        {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        request();

    }


    public void request()
    {
        URL url = null;
        try {

            String strURL ;
            if(namesList.size() > 0){

                Random random = new Random();
                int randomNumber = random.nextInt(namesList.size());
                String name = namesList.get(randomNumber);
                String email = name + "@javalab1.com";

                strURL = "http://localhost:8080/Java_Lab1/Servlet1?name=" + name + "&email=" + email + "&submit=javaapp";
            }else{

                strURL = "http://localhost:8080/Java_Lab1/Servlet1?name=" + "someName" + "&email=" + "someName@javalab1.com" + "&submit=javaapp";
            }

            url = new URL(strURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            long timeBeforeRequest = System.currentTimeMillis();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            long timeAfterRequest = System.currentTimeMillis();
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(""+(timeAfterRequest-timeBeforeRequest)+" "+response);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
