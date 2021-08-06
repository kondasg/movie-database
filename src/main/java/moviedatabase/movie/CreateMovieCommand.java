package moviedatabase.movie;

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
    private Long directorId;
    @NotBlank(message = "The movie's title cannot be empty")
    @Size(min = 1, max = 255, message = "The length of the movie's name must be between 1 and 255 characters")
    private String title;
    private int year;
    private int length;

    public CreateMovieCommand(Long directorId, String title) {
        this.directorId = directorId;
        this.title = title;
    }
}
