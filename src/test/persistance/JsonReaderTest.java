package persistance;

import model.Review;
import model.ReviewsList;
import model.exceptions.OutOfBoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {



    @Test
    void testFileNonExistent() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ReviewsList rl = reader.read();
            fail("IOException expected");
        } catch (IOException | OutOfBoundException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyList.json");
        try {
            ReviewsList rl = reader.read();
            assertEquals(0, rl.getSize());
        } catch (IOException | OutOfBoundException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderListWithReviews() {
        JsonReader reader = new JsonReader("./data/testReaderListWithItems.json");
        try {
            ReviewsList rl = reader.read();
            assertEquals(2, rl.getSize());
            List<Review> reviews = rl.getReviews();
            assertEquals(2, reviews.size());
            assertEquals("date", reviews.get(0).getDate());
            assertEquals("review", reviews.get(0).getReview());
            assertEquals("genre", reviews.get(0).getMovie().getGenre());
            assertEquals(1, reviews.get(0).getMovie().getLength());
            assertEquals(2, reviews.get(0).getRating());

            assertEquals("date2", reviews.get(1).getDate());
            assertEquals("review2", reviews.get(1).getReview());
            assertEquals("genre2", reviews.get(1).getMovie().getGenre());
            assertEquals(11, reviews.get(1).getMovie().getLength());
            assertEquals(1, reviews.get(1).getRating());
        } catch (IOException | OutOfBoundException e) {
            fail("Couldn't read from file");
        }
    }
}
