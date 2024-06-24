package model;

import model.exceptions.OutOfBoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {
    private Review r1;
    private Review r2;
    private Review r3;
    private Review r4;
    private Movie titanic = new Movie();
    private Movie avengers = new Movie();

    @BeforeEach
    void runBefore() {
        titanic.setTitle("Titanic");
        titanic.setGenre("Romance");
        titanic.setLength(240);

        r1 = new Review(titanic);

        avengers.setTitle("Avengers");
        avengers.setGenre("Action");
        avengers.setLength(260);

        r2 = new Review(avengers);

        r3 = new Review(avengers);
    }

    @Test
    void testConstructor() throws OutOfBoundException {
        r1.setRating(4);
        assertEquals(4, r1.getRating());
        r1.setDate("07/01/2022");
        assertEquals("07/01/2022", r1.getDate());
        r1.setReview("This is a sample review.");
        assertEquals("This is a sample review.", r1.getReview());
        assertEquals(titanic, r1.getMovie());

        r2.setRating(5);
        assertEquals(5, r2.getRating());
        r2.setDate("06/22/2022");
        assertEquals("06/22/2022", r2.getDate());
        r2.setReview("This is a nice movie.");
        assertEquals("This is a nice movie.", r2.getReview());
        assertEquals(avengers, r2.getMovie());

        try {
            r3.setRating(7);
            fail("Invalid input. Please rate the movie from 0 to 5.");
        }  catch (OutOfBoundException e) {
            // expected
        }

        try {
            r3.setRating(-2);
            fail("Invalid input. Please rate the movie from 0 to 5.");
        } catch (OutOfBoundException e) {
            // expected
        }

        r4 = new Review(titanic , 4, "01/01/01", "sample");
        assertEquals("Titanic", r4.getMovie().getTitle());
        assertEquals("Romance", r4.getMovie().getGenre());
        assertEquals(240, r4.getMovie().getLength());
        assertEquals("01/01/01", r4.getDate());
        assertEquals(4, r4.getRating());
        assertEquals("sample", r4.getReview());




    }

    @Test
    void testToString() throws OutOfBoundException {
        r1.setRating(4);
        r1.setDate("07/01/2022");
        r1.setReview("This is a sample review.");
        assertEquals("Your review of Titanic\n" + " - Genre: Romance\n" + " - Length: 240\n" + " - Rating: 4\n"
                + " - Date of viewing: 07/01/2022\n" + " - Review:\n" + "  This is a sample review.", r1.toString());

        r2.setRating(5);
        r2.setDate("06/22/2022");
        r2.setReview("This is a nice movie.");
        assertEquals("Your review of Avengers\n" + " - Genre: Action\n" + " - Length: 260\n" + " - Rating: 5\n"
                + " - Date of viewing: 06/22/2022\n" + " - Review:\n" + "  This is a nice movie.", r2.toString());
    }

    @Test
    void testSetMovie() {
        r1.setMovie(titanic);
        assertEquals("Titanic", r1.getMovie().getTitle());
        assertEquals("Romance", r1.getMovie().getGenre());
        assertEquals(240, r1.getMovie().getLength());
    }


}
