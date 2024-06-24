package model;

import model.exceptions.OutOfBoundException;
import org.json.JSONObject;
import persistance.Writable;

// Represents a movie review entry, that contains the movie's info, rating, date viewed, and the review
public class Review implements Writable {

    private Movie movie;
    private int rating;
    private String date;
    private String review;


    public Review(Movie movie) {
        this.movie = movie;
    }

    public Review(Movie movie, int rating, String date, String review) {
        this.movie = movie;
        this.rating = rating;
        this.date = date;
        this.review = review;
//        EventLog.getInstance().logEvent(new Event("New review of " + movie.getTitle() + " is created."));
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }


    public int getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public String getReview() {
        return review;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // EFFECTS: set the rating for the review
    // throws outOfBoundException if rating is not between 0-5
    public void setRating(int rating) throws OutOfBoundException {
        if (rating > 5 || rating < 0) {
            throw new OutOfBoundException();
        } else {
            this.rating = rating;
        }
    }

    public void setReview(String review) {
        this.review = review;
    }


    // EFFECTS: a review entry that is passing to toString
    public String createReview() {
        String title = movie.getTitle();
        String genre = movie.getGenre();
        int length = movie.getLength();
//        EventLog.getInstance().logEvent(new Event("New review of " + title + "created."));

        return "Your review of " + title + "\n" + " - Genre: " + genre + "\n" + " - Length: " + length + "\n"
                + " - Rating: " + rating + "\n" + " - Date of viewing: " + date + "\n" + " - Review:\n" + "  " + review;

    }


    // EFFECTS: return review as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", movie.getTitle());
        json.put("length", movie.getLength());
        json.put("genre", movie.getGenre());
        json.put("rating", rating);
        json.put("review", review);
        json.put("date", date);
        EventLog.getInstance().logEvent(new Event("Review of " + movie.getTitle() + " is saved to file."));
        return json;
    }


    // EFFECTS: returns a string representation of a review entry
    @Override
    public String toString() {
        return createReview();
    }
}
