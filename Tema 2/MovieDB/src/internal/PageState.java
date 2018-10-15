package internal;

/**
 * Internal class to define page state
 * This page state is used to preserve the state of the page when cookie id is received
 * */

public class PageState {
    String requestType;
    String name;
    String description;
    String genre;

    public PageState(String requestType, String name, String description, String genre) {
        this.requestType = requestType;
        this.name = name;
        this.description = description;
        this.genre = genre;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
