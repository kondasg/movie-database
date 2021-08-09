package moviedatabase.director;

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
class DirectorControllerIT {

    @Autowired
    TestRestTemplate template;

    DirectorDTO director;
    Long directorId;
    Long wrongId = 9999L;
    String longString260 = StringUtils.repeat("*", 260);

    @BeforeEach
    void init() {
        director = template.postForObject("/api/directors",
                new CreateDirectorCommand("Steven Spielberg", LocalDate.of(1946, 1, 1)),
                DirectorDTO.class);
        template.postForObject("/api/directors",
                new CreateDirectorCommand("James Cameron", null),
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
        DirectorDTO result = template.getForObject("/api/directors/" + directorId, DirectorDTO.class);

        assertEquals("Steven Spielberg", result.getName());
        assertEquals(LocalDate.of(1946, 1, 1), result.getBirthday());
    }

    @Test
    void findDirectorByIdNotFoundTest() {
        Problem result = template.getForObject("/api/directors/" + wrongId, Problem.class);

        assertEquals(URI.create("diector/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void createDirectorStatusCodeTest() {
        ResponseEntity<DirectorDTO> result = template.exchange("/api/directors/",
                HttpMethod.POST,
                new HttpEntity<>(new CreateDirectorCommand("asd", null)),
                DirectorDTO.class);

        assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    void createDirectorBlankNameTest() {
        Problem result = template.postForObject("/api/directors",
                new CreateDirectorCommand("", null),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());
    }

    @Test
    void createDirectorWrongSizeNameTest() {
        Problem result = template.postForObject("/api/directors",
                new CreateDirectorCommand("as", null),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());

        Problem result2 = template.postForObject("/api/directors",
                new CreateDirectorCommand(longString260, null),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, result2.getStatus());
        assertEquals("Constraint Violation", result2.getTitle());
    }

    @Test
    void updateDirectorTest() {
        template.put("/api/directors/" + directorId,
                new UpdateDirectorCommand("Steven Spielberg UPDATE", LocalDate.of(1966, 11, 11)),
                DirectorDTO.class);

        DirectorDTO result = template.getForObject("/api/directors/" + directorId, DirectorDTO.class);

        assertEquals("Steven Spielberg UPDATE", result.getName());
        assertEquals(LocalDate.of(1966, 11, 11), result.getBirthday());
    }

    @Test
    void updateteDirectorBlankNameTest() {
        Problem result = template.exchange("/api/directors/" + directorId,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateDirectorCommand("", null)),
                Problem.class)
                .getBody();

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());
    }

    @Test
    void updateteDirectorWrongSizeNameTest() {
        Problem result = template.exchange("/api/directors/" + directorId,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateDirectorCommand("as", null)),
                Problem.class)
                .getBody();

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());

        Problem result2 = template.exchange("/api/directors/" + directorId,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateDirectorCommand(longString260, null)),
                Problem.class)
                .getBody();

        assertEquals(Status.BAD_REQUEST, result2.getStatus());
        assertEquals("Constraint Violation", result2.getTitle());
    }

    @Test
    void updateDirectorByIdNotFoundTest() {
        Problem result = template.exchange("/api/directors/" + wrongId,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateDirectorCommand("Steven Spielberg UPDATE", LocalDate.of(1966, 11, 11))),
                Problem.class)
                .getBody();

        assertEquals(URI.create("diector/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
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

    @Test
    void deletDirectorByIdNotFoundTest() {
        Problem result = template.exchange("/api/directors/" + wrongId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Problem.class)
                .getBody();

        assertEquals(URI.create("diector/not-found"), result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }

    @Test
    void birtdayValidatorTest() {
        Problem result = template.exchange("/api/directors/" + directorId,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateDirectorCommand("asd", LocalDate.of(2100, 1, 1))),
                Problem.class)
                .getBody();

        assertEquals(Status.BAD_REQUEST, result.getStatus());
        assertEquals("Constraint Violation", result.getTitle());
    }

}