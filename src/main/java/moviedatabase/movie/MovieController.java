package moviedatabase.movie;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("api/movies")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieDTO findMovieById(@PathVariable("id") long id) {
        return movieService.findMovieById(id);
    }

    @PostMapping
    public MovieDTO createMovie(@Valid  @RequestBody CreateMovieCommand command) {
        return movieService.createMovie(command);
    }

    @PutMapping("/{id}")
    public MovieDTO updateMovie(@PathVariable("id") long id, @Valid @RequestBody UpdateMovieCommand command) {
        return movieService.updateMovie(id, command);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") long id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/{id}/ratings")
    public MovieRatingsDTO getRatings(@PathVariable("id") long id) {
        return movieService.getRatings(id);
    }

    @PostMapping("/{id}/rating/{rating}")
    public MovieDTO addRating(@PathVariable("id") long id,
                              @PathVariable("rating")
                              @Min(value = 1, message = "Rating must be greater than or equal to 1")
                              @Max(value = 5, message = "Rating must be less than or equal to 5")
                                      int rating) {
        return movieService.addRating(id, rating);
    }
}
