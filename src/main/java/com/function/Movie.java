package com.function;

public class Movie {
    private String title;
    private int ReleaseYear;
    private String genre;
    private String description;
    private String director;
    private String actors;
    private double averageRating;

    public Movie(String title, int ReleaseYear, String genre, String description, String director, String actors) {
        this.title = title;
        this.ReleaseYear = ReleaseYear;
        this.genre = genre;
        this.description = description;
        this.director = director;
        this.actors = actors;
    }

    public Movie(String title, int ReleaseYear, String genre, String description, String director, String actors, double averageRating) {
        this.title = title;
        this.ReleaseYear = ReleaseYear;
        this.genre = genre;
        this.description = description;
        this.director = director;
        this.actors = actors;
        this.averageRating = averageRating;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return ReleaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public double getAverageRating(){
        return averageRating;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseYear(int ReleaseYear) {
        this.ReleaseYear = ReleaseYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
