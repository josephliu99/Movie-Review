package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ReviewsListTest {
    private ReviewsList l1;
    private Movie titanic;
    private Movie avengers;
    private Review r1;
    private Review r2;




    @BeforeEach
    void runBefore() {
        titanic = new Movie();
        titanic.setTitle("Titanic");
        titanic.setGenre("Romance");
        titanic.setLength(240);

        avengers = new Movie();
        avengers.setTitle("Avengers");
        avengers.setGenre("Action");
        avengers.setLength(260);


        r1 = new Review(titanic);
        r2 = new Review(avengers);

        l1 = new ReviewsList();
    }

    @Test
    void oneEntryTest() {
        l1.addEntry(r1);
        assertEquals(l1.get(0), r1);
        assertEquals(1,l1.getSize());
    }

    @Test
    void multipleEntryTest() {
        l1.addEntry(r1);
        l1.addEntry(r2);
        l1.addEntry(r2);

        assertEquals(l1.get(0), r1);
        assertEquals(l1.get(1), r2);
        assertEquals(l1.get(2), r2);
        assertEquals(3,l1.getSize());
    }

    @Test
    void getReviewsTest() {
        List<Review> reviews = l1.getReviews();
        assertEquals(0, reviews.size());

        l1.addEntry(r1);
        l1.addEntry(r1);
        l1.addEntry(r2);

        List<Review> reviews2 = l1.getReviews();

        assertEquals(3, reviews2.size());
    }

    @Test
    void testReviewsToObjectArray() {
        assertNull(l1.reviewToObjectArray());

        Object[] testArr = new Object[1];
        testArr[0] = r1;
        assertEquals(r1, testArr[0]);

        l1.addEntry(r1);
        Object[] resultArr = l1.reviewToObjectArray();

        assertArrayEquals(testArr, resultArr);

        l1.addEntry(r2);
        Object[] testArr2 = new Object[2];
        testArr2[0] = r1;
        testArr2[1] = r2;

        resultArr = l1.reviewToObjectArray();
        assertArrayEquals(testArr2, resultArr);




    }
}
