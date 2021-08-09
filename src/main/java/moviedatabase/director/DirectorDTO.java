package moviedatabase.director;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import moviedatabase.movie.MovieDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDTO {

    @Schema(description = "Director's ID", example = "1")
    private Long id;
    @Schema(description = "Director's name", example = "Steven Spielberg")
    private String name;
    @Schema(description = "Director's birtd date", example = "1980-01-01")
    private LocalDate birthday;
    @Schema(description = "Director's movies")
    private List<MovieDTO> movies;
}
