package internal;

import java.io.Serializable;
/**
 * This class is used to describe a record in database/properties and thus it can be constructed from input.jsp when necessary data is provided*/
public class MovieDetails implements Serializable {
    private String name;
    private String description;
    private String genre;

    public MovieDetails(String name, String description, String genre) {
        this.name = name;
        this.description = description;
        this.genre = genre;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
