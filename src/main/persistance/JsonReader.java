package persistance;


import model.Movie;
import model.Review;
import model.ReviewsList;
import model.exceptions.OutOfBoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads reviews from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ReviewsList read() throws IOException, OutOfBoundException {
        String data = readFile(source);
        JSONObject object = new JSONObject(data);
        return parseReviewsList(object);
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder content = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(content::append);
        }

        return content.toString();
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses ReviewList from JSON object and returns it
    private ReviewsList parseReviewsList(JSONObject object) {

        ReviewsList reviewsList = new ReviewsList();
        addReviews(reviewsList, object);
        return reviewsList;
    }


    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: reviewList
    // EFFECTS: parses reviews from JSON object and adds them to the review list
    private void addReviews(ReviewsList reviewsList, JSONObject object) {
        JSONArray array = object.getJSONArray("reviews");
        for (Object json : array) {
            JSONObject nextReview = (JSONObject) json;
            addReview(reviewsList, nextReview);
        }
    }


    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: reviewList
    // EFFECTS: parses review from JSON object and adds it to the review list

    private void addReview(ReviewsList reviewsList, JSONObject object) {

        String title = object.getString("title");
        int length = object.getInt("length");
        String genre = object.getString("genre");

        Movie movie = new Movie(title, genre, length);

        String review = object.getString("review");
        String date = object.getString("date");
        int rating = object.getInt("rating");

        Review review1 = new Review(movie, rating, date, review);

        reviewsList.addEntry(review1);


    }













}
