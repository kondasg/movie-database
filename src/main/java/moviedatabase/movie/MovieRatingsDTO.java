package moviedatabase.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRatingsDTO {

    private Long id;
    private String title;
    private double averageRating;
    private List<Integer> ratings;
}
