package moviedatabase.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRatingsDTO {

    @Schema(description = "Movie's ID", example = "1")
    private Long id;
    @Schema(description = "The movie's title", example = "Titanic")
    private String title;
    @Schema(description = "The movie's average rating", example = "3.15")
    private double averageRating;
    @Schema(description = "The movie's ratinga")
    private List<Integer> ratings;
}
