package moviedatabase.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    @Schema(description = "Movie's ID", example = "1")
    private Long id;
    @Schema(description = "The movie's title", example = "Titanic")
    private String title;
    @Schema(description = "The movie's released (year)", example = "1980")
    private int year;
    @Schema(description = "The movie's length (min)", example = "92")
    private int length;
    @Schema(description = "The movie's average rating", example = "3.15")
    private double averageRating;
}
