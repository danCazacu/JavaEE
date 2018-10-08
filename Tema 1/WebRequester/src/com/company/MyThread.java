package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class MyThread extends Thread {
    CountDownLatch latch;
    public MyThread(CountDownLatch latch) {
        this.latch = latch;
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
            url = new URL("http://localhost:8080/Java_Lab1/Servlet1?name=dfgdfg&email=ghfghfh&submit=javaapp");
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
