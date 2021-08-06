package moviedatabase.movie;

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
    private String title;
    private int year;
    private int length;
}
