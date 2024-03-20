public class Review {
    private String title;
    private String opinion;
    private int rating;
    private String dateTime;
    private String author;

    // Constructor
    public Review(String title, String opinion, int rating, String dateTime, String author) {
        this.title = title;
        this.opinion = opinion;
        this.rating = rating;
        this.dateTime = dateTime;
        this.author = author;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getOpinion() {
        return opinion;
    }

    public int getRating() {
        return rating;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getAuthor() {
        return author;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
