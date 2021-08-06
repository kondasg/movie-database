package moviedatabase.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieCommand {

    private String title;
    private int year;
    private int length;
}
