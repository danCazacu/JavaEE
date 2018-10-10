package internal;

public class MovieDetails {
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
