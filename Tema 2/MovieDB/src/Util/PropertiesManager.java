package Util;

import internal.MovieDetails;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
/**
 * Utilitarian class used for interaction with properties file.
 */

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

    /**
     *
     * @param movie to be added
     * @return True if success, false if movie name already exists
     */
    public boolean addNewMovie(MovieDetails movie){
        int id = (getMoviesCount())+1;
        if(getMovieId(movie.getName())<0) {
            properties.setProperty("movie." + id + ".name", movie.getName());
            properties.setProperty("movie." + id + ".description", movie.getDescription());
            properties.setProperty("movie." + id + ".genre", movie.getGenre());
            properties.setProperty("total", "" + id);
            return true;
        }
        return false;
    }

    /**
     *
     * @return Number of movies in database(file), 0 if empty
     */
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

    /**
     *
     * @return Ordered map where the key is movie id from file and value is data associated
     */
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

    /**
     *
     * @param movieDetails movie to be update. Description and genre will be updated based on movie name
     * @return True if success, False if movie name does not exist in database
     */
    public boolean updateMovie(MovieDetails movieDetails) {
        int id = getMovieId(movieDetails.getName());
        if(id>0){
            properties.setProperty("movie."+id+".description",movieDetails.getDescription());
            properties.setProperty("movie."+id+".genre",movieDetails.getGenre());
            return true;
        }
        return false;

    }

    /**
     *
     * @param movieName to search and get it
     * @return returns the id or -1 if movie name does not exist
     */
    public int getMovieId(String movieName){
        for(int i = 1; i <=getMoviesCount(); i++) {
            String name = properties.getProperty("movie."+i+".name");
            if(name.toLowerCase().equals(movieName.toLowerCase()))
                return i;
        }
        return -1;
    }
}
