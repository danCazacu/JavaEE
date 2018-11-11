package com.company;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) {
        repositoryTestTema6();
    }

    public static void repositoryTestTema6(){
        CountDownLatch latch = new CountDownLatch(1);
        MySecondThread threads[] = new MySecondThread[10000];
        for(int i = 0; i<threads.length; i++){

            threads[i] = new MySecondThread(latch);
        }
        for(int i = 0; i<threads.length; i++){
            threads[i].start();
        }
        latch.countDown();

    }

    public static void tema1(){

        /**
         * get the name list
         */

        ArrayList<String> lstNames = null;
        try {

            lstNames = readFromFile_getNamesList();
        } catch (IOException e) {

            e.printStackTrace();
        }


        CountDownLatch latch = new CountDownLatch(1);
        MyThread threads[] = new MyThread[10000];
        for(int i = 0; i<threads.length; i++){

            threads[i] = new MyThread(latch, lstNames);
        }
        for(int i = 0; i<threads.length; i++){
            threads[i].start();
        }
        latch.countDown();
        for(int i = 0; i<threads.length; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Sleeeeeeeping....");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch = new CountDownLatch(1);
        MyThread threads2[] = new MyThread[1000];
        for(int i = 0; i<threads2.length; i++){
            threads2[i] = new MyThread(latch, lstNames);
        }
        for(int i = 0; i<threads2.length; i++){
            threads2[i].start();
        }
        latch.countDown();
    }

    public static ArrayList<String> readFromFile_getNamesList() throws IOException{

        ArrayList<String> lstNames = new ArrayList<>();

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(System.getProperty("user.home") + "/Desktop/JavaEE_Lab1/names.txt");
            br = new BufferedReader(fr);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        String line;
        int lineCount = 1;
        while((line = br.readLine()) != null){

            if (!line.equals("") && !line.startsWith("https"))
            {

                lstNames.add(line.replaceAll(" ", ""));
            }
        }

        fr.close();

        return lstNames;
    }

}
