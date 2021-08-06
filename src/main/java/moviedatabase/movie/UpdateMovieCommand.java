package moviedatabase.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieCommand {

    @NotNull(message = "The movie's title cannot be empty")
    @Size(min = 1, max = 255, message = "The length of the movie's name must be between 1 and 255 characters")
    @Schema(description = "The movie's title", example = "Titanic")
    private String title;
    @Schema(description = "The year of the movie's premiere", example = "2010")
    private int year;
    @Schema(description = "The movie's length (min)", example = "92")
    private int length;
}
