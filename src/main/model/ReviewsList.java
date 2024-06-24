package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Represents a list of movies reviewed
public class ReviewsList implements Writable {
    private ArrayList<Review> movList;



    public ReviewsList() {
        movList = new ArrayList<>();
    }

    public void addEntry(Review review) {
        movList.add(review);
        EventLog.getInstance().logEvent(new Event("Review of "
                + review.getMovie().getTitle() + " is added to the collection."));
    }

    public Review get(int index) {
        return movList.get(index);
    }

    public int getSize() {
        return movList.size();
    }

    // EFFECTS: returns an unmodifiable list of reviews in the Review List
    public List<Review> getReviews() {
        return Collections.unmodifiableList(movList);
    }

    // EFFECTS: returns an array of review objects
    public Object[] reviewToObjectArray() {


        if (movList.size() == 0) {
            return null;
        } else {
            Object [] reviews = new Object[movList.size()];
            int i = 0;
            for (Review r : movList) {
                reviews[i] = r;
                i++;
            }

            return reviews;
        }
    }


    // EFFECTS: return reviewsList as a json object
    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("reviews", reviewsToJson());
//        EventLog.getInstance().logEvent(new Event("Current collection of reviews is saved to file."));
        return object;
    }


    // EFFECTS: returns reviews in this list as JSON array
    private JSONArray reviewsToJson() {
        JSONArray array = new JSONArray();

        for (Review r : movList) {
            array.put(r.toJson());
        }

        return array;
    }

}
