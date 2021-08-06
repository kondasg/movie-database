package moviedatabase.director;

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
class DirectorControllerIT {

    @Autowired
    TestRestTemplate template;

    DirectorDTO director;
    Long directorId;

    @BeforeEach
    void init() {
        director = template.postForObject("/api/directors",
                new CreateDirectorCommand("Steven Spielberg", LocalDate.of(1946, 1, 1)),
                DirectorDTO.class);
        template.postForObject("/api/directors",
                new CreateDirectorCommand("James Cameron", LocalDate.of(1954, 10, 1)),
                DirectorDTO.class);
        directorId = director.getId();
    }

    @Test
    void getAllDirectorsTest() {
        List<DirectorDTO> result = template.exchange("/api/directors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DirectorDTO>>() {
                })
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(DirectorDTO::getName)
                .containsExactly("Steven Spielberg", "James Cameron");
    }

    @Test
    void findDirectorByIdTest() {
        DirectorDTO result = template.exchange("/api/directors/" + directorId,
                HttpMethod.GET,
                null,
                DirectorDTO.class)
                .getBody();

        assertEquals("Steven Spielberg", result.getName());
        assertEquals(LocalDate.of(1946, 1, 1), result.getBirthday());
    }

    @Test
    void findDirectorByIdNotFoundTest() {
        Problem result = template.getForObject("/api/directors/123456789", Problem.class);

        assertEquals(URI.create("diector/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void updateDirectorTest() {
        template.put("/api/directors/" + directorId,
                new UpdateDirectorCommand("Steven Spielberg UPDATE", LocalDate.of(1966, 11, 11)),
                DirectorDTO.class);
        DirectorDTO result = template.exchange("/api/directors/" + directorId,
                HttpMethod.GET,
                null,
                DirectorDTO.class)
                .getBody();

        assertEquals("Steven Spielberg UPDATE", result.getName());
        assertEquals(LocalDate.of(1966, 11, 11), result.getBirthday());
    }

    @Test
    void deleteDirectorTest() {
        template.delete("/api/directors/" + directorId);
        List<DirectorDTO> result = template.exchange("/api/directors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DirectorDTO>>() {
                })
                .getBody();
        assertThat(result)
                .hasSize(1)
                .extracting(DirectorDTO::getName)
                .containsExactly("James Cameron");
    }
}