package Util;

import internal.MovieDetails;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class PropertiesManager extends Properties {

    Properties properties;
    FileInputStream propertiesInputStream;
    FileOutputStream propertiesOutputStream;
    public PropertiesManager(String pathToFile){

        try {
            File file = new File(pathToFile);
            file.createNewFile();
            propertiesInputStream = new FileInputStream(pathToFile);
            properties = new Properties();
            properties.load(propertiesInputStream);
            propertiesOutputStream = new FileOutputStream(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addNewMovie(MovieDetails movie){
        int id = (getMoviesCount())+1;
        properties.setProperty("movie."+id+".name", movie.getName());
        properties.setProperty("movie."+id+".description", movie.getDescription());
        properties.setProperty("movie."+id+".genre", movie.getGenre());
        properties.setProperty("total",""+id);
    }
    public int getMoviesCount(){
        String stringtotal = properties.getProperty("total");
        if(stringtotal!=null){
            return Integer.parseInt(stringtotal);
        }
        return 0;
    }
    public void store(){
        if(propertiesOutputStream !=null) {
            try {
                properties.store(propertiesOutputStream,"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public TreeMap<Integer,MovieDetails> getPropertiesAsMap(){
        TreeMap<Integer,MovieDetails> propertiesMap = new TreeMap<>();
        for(int i = 1; i <=getMoviesCount(); i++){
            String name = properties.getProperty("movie."+i+".name");
            String description = properties.getProperty("movie."+i+".description");
            String genre = properties.getProperty("movie."+i+".genre");
            MovieDetails movieDetails = new MovieDetails(name,description,genre);
            propertiesMap.put(i,movieDetails);
        }
        return propertiesMap;
    }

    public void updateMovie(MovieDetails movieDetails) {
        int id = getMovieId(movieDetails.getName());
        if(id>0){
            properties.setProperty("movie."+id+".description",movieDetails.getDescription());
            properties.setProperty("movie."+id+".genre",movieDetails.getGenre());
        }

    }
    public int getMovieId(String movieName){
        for(int i = 1; i <=getMoviesCount(); i++) {
            String name = properties.getProperty("movie."+i+".name");
            if(name.toLowerCase().equals(movieName.toLowerCase()))
                return i;
        }
        return -1;
    }
//    public String getMovieDescription(String movieName){
//        properties = new Properties();
//        try {
//            properties.load(propertiesInputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return properties.getProperty(movieName+".description");
//    }
//    public String getMovieGenre(String movieName){
//        properties = new Properties();
//        try {
//            properties.load(propertiesInputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return properties.getProperty(movieName+".genre");
//    }
//    public void setMovieDescription(String movieName, String movieDescription){
//        properties = new Properties();
//        try {
//            properties.load(propertiesInputStream);
//            properties.setProperty(movieName+".description", movieDescription);
//            properties.store(propertiesOutputStream,"");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void setMovieGenre(String movieName, String movieGenre){
//        properties = new Properties();
//        try {
//            properties.load(propertiesInputStream);
//            properties.setProperty(movieName+".genre", movieGenre);
//            properties.store(propertiesOutputStream,"");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
