public class Movie {
    private String title;
    private int year;
    private String genre;
    private String description;
    private String director;
    private String actors;

    public Movie(String title, int year, String genre, String description, String director, String actors) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.description = description;
        this.director = director;
        this.actors = actors;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
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

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
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
}
