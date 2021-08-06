package moviedatabase.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieCommand {

    private Long directorId;
    private String title;
    private int year;
    private int length;

    public CreateMovieCommand(Long directorId, String title) {
        this.directorId = directorId;
        this.title = title;
    }
}
