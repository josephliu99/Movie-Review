package model;

import org.json.JSONObject;
import persistance.Writable;

// Represents a movie that can be modified by adding the title, genre and length of movie
public class Movie {

    private String title;
    private String genre;
    private int length;

    public Movie() {
    }

    public Movie(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getLength() {
        return length;
    }



    // EFFECTS: returns a string representation of a movie
    @Override
    public String toString() {
        return  "\n   - Title: " + title + "\n" + "   - Genre: " + genre + "\n" + "   - Length: " + length + "\n";
    }
}
