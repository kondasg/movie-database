package moviedatabase.movie;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("api/movies")
@AllArgsConstructor
@Tag(name = "Operations on movies")
@Validated
public class MovieController {

    private MovieService movieService;

    @GetMapping
    @Operation(summary = "List of all movies")
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find movie by ID")
    public MovieDTO findMovieById(
            @Parameter(description = "ID of the movie", example = "1")
            @PathVariable("id") long id) {
        return movieService.findMovieById(id);
    }

    @PostMapping
    @Operation(summary = "Create movie")
    public MovieDTO createMovie(@Valid @RequestBody CreateMovieCommand command) {
        return movieService.createMovie(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update movie by ID")
    public MovieDTO updateMovie(
            @Parameter(description = "ID of the movie", example = "1")
            @PathVariable("id") long id,
            @Valid @RequestBody UpdateMovieCommand command) {
        return movieService.updateMovie(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie by ID")
    public void deleteMovie(
            @Parameter(description = "ID of the movie", example = "1")
            @PathVariable("id") long id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/{id}/ratings")
    @Operation(summary = "List of movie ratings by movie ID")
    public MovieRatingsDTO getRatings(
            @Parameter(description = "ID of the movie", example = "1")
            @PathVariable("id") long id) {
        return movieService.getRatings(id);
    }

    @PostMapping("/{id}/rating/{rating}")
    @Operation(summary = "Add rating on the movie by ID")
    public MovieDTO addRating(
            @Parameter(description = "ID of the movie", example = "1")
            @PathVariable("id") long id,
            @Parameter(description = "The value of the rating", example = "5")
            @PathVariable("rating")
            @Min(value = 1, message = "Rating must be greater than or equal to 1")
            @Max(value = 5, message = "Rating must be less than or equal to 5")
                    int rating) {
        return movieService.addRating(id, rating);
    }
}
