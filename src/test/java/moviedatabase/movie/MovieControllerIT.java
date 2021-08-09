package moviedatabase.movie;

import moviedatabase.director.CreateDirectorCommand;
import moviedatabase.director.DirectorDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    Long wrongId = 9999L;
    String longString260 = StringUtils.repeat("*", 260);


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
        MovieDTO result = template.getForObject("/api/movies/" + movieId, MovieDTO.class);

        assertEquals("Különvélemény", result.getTitle());
        assertEquals(146, result.getLength());
    }

    @Test
    void findMovieByIdNotFoundTest() {
        Problem result = template.getForObject("/api/movies/" + wrongId, Problem.class);

        assertEquals(URI.create("movie/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void createDirectorStatusCodeTest() {
        ResponseEntity<MovieDTO> result = template.exchange("/api/movies/",
                HttpMethod.POST,
                new HttpEntity<>(new CreateMovieCommand(director.getId(), "asd")),
                MovieDTO.class);

        assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    void createMovieBlankTitleTest() {
        Problem result = template.postForObject("/api/movies",
                new CreateMovieCommand(director.getId(), ""),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());
    }

    @Test
    void createDirectorWrongSizeTitleTest() {
        Problem result2 = template.postForObject("/api/directors",
                new CreateMovieCommand(director.getId(), longString260),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result2.getStatus());
        assertEquals("Constraint Violation", result2.getTitle());
    }

    @Test
    void createMovieWromgDirectorIdTest() {
        Problem result = template.postForObject("/api/movies",
                new CreateMovieCommand(null, ""),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());

        Problem result2 = template.postForObject("/api/movies",
                new CreateMovieCommand(-100L, ""),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result2.getStatus());
        assertEquals("Constraint Violation", result2.getTitle());
    }

    @Test
    void updateMovieTest() {
        template.put("/api/movies/" + movieId,
                new UpdateMovieCommand("Különvélemény 2", 2003, 150),
                MovieDTO.class);

        MovieDTO result = template.getForObject("/api/movies/" + movieId, MovieDTO.class);

        assertEquals("Különvélemény 2", result.getTitle());
        assertEquals(2003, result.getYear());
        assertEquals(150, result.getLength());
    }

    @Test
    void updateteMovieBlankNameTest() {
        Problem result = template.exchange("/api/movies/" + movieId,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateMovieCommand("", 0, 0)),
                Problem.class)
                .getBody();

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());
    }

    @Test
    void updateteMovieWrongSizeNameTest() {
        Problem result2 = template.exchange("/api/movies/" + movieId,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateMovieCommand(longString260, 0, 0)),
                Problem.class)
                .getBody();

        assertEquals(Status.BAD_REQUEST, result2.getStatus());
        assertEquals("Constraint Violation", result2.getTitle());
    }

    @Test
    void updateMovieByIdNotFoundTest() {
        Problem result = template.exchange("/api/movies/" + wrongId,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateMovieCommand("Különvélemény 2", 0, 0)),
                Problem.class)
                .getBody();

        assertEquals(URI.create("movie/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
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
    void deletMovieByIdNotFoundTest() {
        Problem result = template.exchange("/api/movies/" + wrongId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Problem.class)
                .getBody();

        assertEquals(URI.create("movie/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void addRatingByNotFoundId() {
        Problem result = template.postForObject("/api/movies/" + wrongId + "/rating/2", null, Problem.class);

        assertEquals(URI.create("movie/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void addRatingWrongNumber() {
        Problem result = template.postForObject("/api/movies/" + movieId + "/rating/0", null, Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());

        Problem result2 = template.postForObject("/api/movies/" + movieId + "/rating/6", null, Problem.class);

        assertEquals(Status.BAD_REQUEST, result2.getStatus());
        assertEquals("Constraint Violation", result2.getTitle());
    }

    @Test
    void getRatingsTest() {
        MovieRatingsDTO result = template.getForObject("/api/movies/" + movieId + "/ratings", MovieRatingsDTO.class);

        assertEquals(0, result.getAverageRating(), 0.01);
        assertEquals(0, result.getRatings().size());

        template.postForObject("/api/movies/" + movieId + "/rating/2", null, MovieDTO.class);
        template.postForObject("/api/movies/" + movieId + "/rating/4", null, MovieDTO.class);
        template.postForObject("/api/movies/" + movieId + "/rating/5", null, MovieDTO.class);

        MovieRatingsDTO result2 = template.getForObject("/api/movies/" + movieId + "/ratings", MovieRatingsDTO.class);

        assertEquals(3.66, result2.getAverageRating(), 0.01);
        assertEquals(3, result2.getRatings().size());
    }

    @Test
    void getRatingsMovieByIdNotFoundTest() {
        Problem result = template.getForObject("/api/movies/" + wrongId + "/ratings", Problem.class);

        assertEquals(URI.create("movie/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }
}