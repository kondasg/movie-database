package moviedatabase.movie;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieCommand {

    @NotNull(message = "The movie's director ID cannot be empty")
    @Positive(message = "The director's ID must be greater than 0")
    @Schema(description = "Director's ID", example = "1")
    private Long directorId;
    @NotBlank(message = "The movie's title cannot be empty")
    @Size(min = 1, max = 255, message = "The length of the movie's name must be between 1 and 255 characters")
    @Schema(description = "The movie's title", example = "Titanic")
    private String title;
    @Schema(description = "The movie's title", example = "Titanic")
    private int year;
    @Schema(description = "The movie's length (min)", example = "92")
    private int length;

    public CreateMovieCommand(Long directorId, String title) {
        this.directorId = directorId;
        this.title = title;
    }
}
