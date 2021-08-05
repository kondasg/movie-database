package moviedatabase.director;

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

    private Long id;
    private String name;
    private LocalDate birthday;
    //private List<MovieDTO> movies;
}
