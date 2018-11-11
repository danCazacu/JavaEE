package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class MySecondThread extends Thread{
    CountDownLatch latch;
    public MySecondThread(CountDownLatch latch){
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
        try {
        URL url = new URL("http://localhost:8080/OptionalCourseAllocation_war_exploded/tema6/repository-operations?op=insert");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
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
