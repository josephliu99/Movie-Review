package persistance;

import model.Movie;
import model.Review;
import model.ReviewsList;
import model.exceptions.OutOfBoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ReviewsList list = new ReviewsList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("Invalid file name, IOException expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyList() {
        try{
            ReviewsList list = new ReviewsList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyList.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyList.json");
            list = reader.read();
            assertEquals(0, list.getSize());

        } catch (IOException | OutOfBoundException e) {
            fail("No Exception should be thrown");
        }

    }

    @Test
    void testWriterListWithReviews() {
        try {
            ReviewsList list = new ReviewsList();

            Movie movie = new Movie("title", "genre", 10);
            Review review = new Review(movie);
            review.setReview("sample review");
            review.setRating(3);
            review.setDate("01/01/01");

            list.addEntry(review);
            list.addEntry(review);
            list.addEntry(review);

            JsonWriter writer = new JsonWriter("./data/testWriterListWithReviews.json");
            writer.open();
            writer.write(list);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterListWithReviews.json");
            list = reader.read();
            assertEquals(3, list.getSize());


        } catch (IOException | OutOfBoundException e) {
            fail("No Exception should be thrown");
        }
    }
}
