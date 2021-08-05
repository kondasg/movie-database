package moviedatabase.movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    Movie movie;

    @BeforeEach
    void init() {
        movie = new Movie();
        movie.addRating(1);
        movie.addRating(2);
        movie.addRating(3);
        movie.addRating(3);
    }

    @Test
    void addRating() {
        movie.addRating(3);
        assertEquals(5, movie.getRatings().size());
    }

    @Test
    void calculateAverageRating() {
        movie.calculateAverageRating();
        assertEquals(2.25, movie.getAverageRating(), 0.01);
    }
}