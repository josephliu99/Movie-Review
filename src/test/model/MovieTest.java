package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    private Movie m1;
    private Movie m2;
    private Movie m3;

    @BeforeEach
    void runBefore() {
        m1 = new Movie();
        m2 = new Movie();
        m3 = new Movie("Movie3", "Genre1", 100);
    }

    @Test
    void testConstructor() {
        m1.setTitle("Titanic");
        assertEquals("Titanic", m1.getTitle());
        m1.setLength(120);
        assertEquals(120, m1.getLength());
        m1.setGenre("Action");
        assertEquals("Action", m1.getGenre());

        m2.setTitle("Avengers");
        assertEquals("Avengers", m2.getTitle());
        m2.setLength(200);
        assertEquals(200, m2.getLength());
        m2.setGenre("Action");
        assertEquals("Action", m2.getGenre());

        assertEquals("Movie3",m3.getTitle());
        assertEquals("Genre1", m3.getGenre());
        assertEquals(100, m3.getLength());
    }

    @Test
    void testToString() {
        m1.setTitle("Titanic");
        m1.setLength(120);
        m1.setGenre("Action");
        assertEquals("\n   - Title: " + "Titanic" + "\n"
                + "   - Genre: " + "Action" + "\n" + "   - Length: " + 120 + "\n", m1.toString());

        m2.setTitle("Avengers");
        m2.setLength(200);
        m2.setGenre("Action");
        assertEquals("\n   - Title: " + "Avengers" + "\n"
                + "   - Genre: " + "Action" + "\n" + "   - Length: " + 200 + "\n", m2.toString());
    }
}