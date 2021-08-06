package moviedatabase.movie;

import moviedatabase.director.CreateDirectorCommand;
import moviedatabase.director.DirectorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"DELETE FROM `movies_ratings`", "DELETE FROM `movies`", "DELETE FROM `directors`"})
class MovieControllerIT {
    @Autowired
    TestRestTemplate template;

    DirectorDTO director;
    MovieDTO movie;
    Long movieId;

    @BeforeEach
    void init() {
        director = template.postForObject("/api/directors",
                new CreateDirectorCommand("Steven Spielberg", LocalDate.of(1946, 1, 1)),
                DirectorDTO.class);
        movie = template.postForObject("/api/movies",
                new CreateMovieCommand(director.getId(), "Különvélemény", 2002, 146),
                MovieDTO.class);
        template.postForObject("/api/movies",
                new CreateMovieCommand(director.getId(), "Jurasic Park"),
                MovieDTO.class);
        movieId = movie.getId();
    }

    @Test
    void getAllMoviesTest() {
        List<MovieDTO> result = template.exchange("/api/movies",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieDTO>>() {
                })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(MovieDTO::getTitle)
                .containsExactly("Különvélemény", "Jurasic Park");

    }

    @Test
    void findMovieByIdTest() {
        MovieDTO result = template.exchange("/api/movies/" + movieId,
                HttpMethod.GET,
                null,
                MovieDTO.class)
                .getBody();

        assertEquals("Különvélemény", result.getTitle());
        assertEquals(146, result.getLength());
    }

    @Test
    void findMovieByIdNotFoundTest() {
        Problem result = template.getForObject("/api/movies/123456789", Problem.class);

        assertEquals(URI.create("movie/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void updateMovieTest() {
        template.put("/api/movies/" + movieId,
                new UpdateMovieCommand("Különvélemény 2", 2003, 150),
                MovieDTO.class);
        MovieDTO result = template.exchange("/api/movies/" + movieId,
                HttpMethod.GET,
                null,
                MovieDTO.class)
                .getBody();

        assertEquals("Különvélemény 2", result.getTitle());
        assertEquals(2003, result.getYear());
        assertEquals(150, result.getLength());
    }

    @Test
    void deleteMovieTest() {
        template.delete("/api/movies/" + movieId);
        List<MovieDTO> result = template.exchange("/api/movies",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieDTO>>() {
                })
                .getBody();
        assertThat(result)
                .hasSize(1)
                .extracting(MovieDTO::getTitle)
                .containsExactly("Jurasic Park");
    }

    @Test
    void getRatingsTest() {
        template.postForObject("/api/movies/" + movieId + "/rating/2", null, MovieDTO.class);
        template.postForObject("/api/movies/" + movieId + "/rating/4", null, MovieDTO.class);
        template.postForObject("/api/movies/" + movieId + "/rating/5", null, MovieDTO.class);

        MovieRatingsDTO result = template.exchange("/api/movies/" + movieId + "/ratings",
                HttpMethod.GET,
                null,
                MovieRatingsDTO.class)
                .getBody();

        assertEquals(3.66, result.getAverageRating(), 0.01);
        assertEquals(3, result.getRatings().size());
    }
}